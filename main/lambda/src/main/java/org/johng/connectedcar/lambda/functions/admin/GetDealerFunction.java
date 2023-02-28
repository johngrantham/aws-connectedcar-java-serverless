package org.johng.connectedcar.lambda.functions.admin;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.inject.Injector;

import org.johng.connectedcar.core.shared.data.entities.Dealer;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class GetDealerFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  public GetDealerFunction() {}

  public GetDealerFunction(Injector injector) {
    super(injector);
  }

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String dealerId = getPathParameter(request, PATH_DEALER_ID);
      Dealer dealer = getDealerService().getDealer(dealerId);
      
      if (dealer != null) {
        return new APIGatewayProxyResponseEvent()
          .withBody(serializeItem(dealer))
          .withHeaders(RESPONSE_HEADER)
          .withStatusCode(HttpStatus.SC_OK);
      }
      else {
        return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_NOT_FOUND);     
      }
    }, context);
  }
}