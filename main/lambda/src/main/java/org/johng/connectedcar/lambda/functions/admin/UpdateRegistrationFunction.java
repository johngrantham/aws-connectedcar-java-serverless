package org.johng.connectedcar.lambda.functions.admin;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.updates.RegistrationPatch;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class UpdateRegistrationFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      RegistrationPatch patch = deserializeItem(request, RegistrationPatch.class);
      getRegistrationService().updateRegistration(patch);
      
      return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}