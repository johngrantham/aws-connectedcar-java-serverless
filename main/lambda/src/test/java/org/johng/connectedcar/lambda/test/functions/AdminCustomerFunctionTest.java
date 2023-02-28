package org.johng.connectedcar.lambda.test.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.johng.connectedcar.lambda.functions.admin.CreateDealerFunction;
import org.johng.connectedcar.lambda.functions.admin.GetDealerFunction;
import org.johng.connectedcar.lambda.functions.admin.GetDealersFunction;
import org.johng.connectedcar.lambda.test.FunctionFixture;
import org.johng.connectedcar.lambda.test.FunctionTest;
import org.johng.connectedcar.core.shared.data.entities.Dealer;
import org.junit.jupiter.api.Test;

public class AdminCustomerFunctionTest extends FunctionTest {

  private static final FunctionFixture fixture = new FunctionFixture();

  @Test                                               
  public void testCreate() {
    Dealer dealer = getDealer();

    CreateDealerFunction createFunction = new CreateDealerFunction(fixture.getInjector());
    APIGatewayProxyRequestEvent createRequest = new APIGatewayProxyRequestEvent().withBody(serializeItem(dealer));
    APIGatewayProxyResponseEvent createResponse = createFunction.handleRequest(createRequest, fixture.getDummyLambdaContext());

    assertEquals(201, createResponse.getStatusCode());

    String location = createResponse.getHeaders().get("Location");
    String dealerId = parseLocation(location);

    assertNotNull(dealerId);
  }  

  @Test
  public void testRetrieve() {
    Dealer dealer = getDealer();

    CreateDealerFunction createFunction = new CreateDealerFunction(fixture.getInjector());
    APIGatewayProxyRequestEvent createRequest = new APIGatewayProxyRequestEvent().withBody(serializeItem(dealer));
    APIGatewayProxyResponseEvent createResponse = createFunction.handleRequest(createRequest, fixture.getDummyLambdaContext());

    String location = createResponse.getHeaders().get("Location");
    String dealerId = parseLocation(location);

    Map<String,String> parameters = new HashMap<String,String>()
    {{
        put("dealerId", dealerId);
    }};

    GetDealerFunction retrieveFunction = new GetDealerFunction(fixture.getInjector());
    APIGatewayProxyRequestEvent retrieveRequest = new APIGatewayProxyRequestEvent().withPathParameters(parameters);
    APIGatewayProxyResponseEvent retrieveResponse = retrieveFunction.handleRequest(retrieveRequest, fixture.getDummyLambdaContext());

    assertEquals(200, retrieveResponse.getStatusCode());
    assertNotNull(retrieveResponse.getBody());

    Dealer retrieved = deserializeItem(retrieveResponse.getBody(), Dealer.class);

    assertEquals(dealer.getName(), retrieved.getName());
  }

  @Test
  public void testList() {
    Dealer dealer = getDealer();

    CreateDealerFunction createFunction = new CreateDealerFunction(fixture.getInjector());
    APIGatewayProxyRequestEvent createRequest = new APIGatewayProxyRequestEvent().withBody(serializeItem(dealer));
    APIGatewayProxyResponseEvent createResponse = createFunction.handleRequest(createRequest, fixture.getDummyLambdaContext());

    String location = createResponse.getHeaders().get("Location");
    String dealerId = parseLocation(location);

    Map<String,String> parameters = new HashMap<String,String>()
    {{
        put("stateCode", dealer.getStateCode().toString());
    }};

    GetDealersFunction listFunction = new GetDealersFunction(fixture.getInjector());
    APIGatewayProxyRequestEvent listRequest = new APIGatewayProxyRequestEvent().withQueryStringParameters(parameters);
    APIGatewayProxyResponseEvent listResponse = listFunction.handleRequest(listRequest, fixture.getDummyLambdaContext());

    assertEquals(200, listResponse.getStatusCode());
    assertNotNull(listResponse.getBody());

    Dealer[] array = deserializeItem(listResponse.getBody(), Dealer[].class);

    assertNotNull(array);

    List<Dealer> matches = Arrays.stream(array)
      .filter(p -> p.getDealerId().equals(dealerId))
      .collect(Collectors.toList());

    assertNotEquals(0, matches.size());
    assertEquals(dealer.getName(), array[0].getName());
  }
}
