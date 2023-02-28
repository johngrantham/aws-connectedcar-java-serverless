package org.johng.connectedcar.lambda.functions.customer;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.updates.CustomerPatch;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class UpdateCustomerFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String username = getCognitoUsername(request);
      CustomerPatch patch = deserializeItem(request, CustomerPatch.class);
      patch.setUsername(username);
      getCustomerService().updateCustomer(patch);
      
      return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}