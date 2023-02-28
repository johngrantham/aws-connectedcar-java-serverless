package org.johng.connectedcar.lambda.functions.admin;

import java.util.List;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Customer;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class GetCustomersFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String lastname = getQueryStringParameter(request, QUERY_LASTNAME);
      List<Customer> customers = getCustomerService().getCustomers(lastname);
      
      return new APIGatewayProxyResponseEvent()
        .withBody(serializeItem(customers))
        .withHeaders(RESPONSE_HEADER)
        .withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}