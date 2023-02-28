package org.johng.connectedcar.lambda.functions.customer;

import java.util.List;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Dealer;
import org.johng.connectedcar.core.shared.data.enums.StateCodeEnum;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class GetDealersFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      StateCodeEnum stateCode = getStateCode(request);
      List<Dealer> dealers = getDealerService().getDealers(stateCode);
      
      return new APIGatewayProxyResponseEvent()
        .withBody(serializeItem(dealers))
        .withHeaders(RESPONSE_HEADER)
        .withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}