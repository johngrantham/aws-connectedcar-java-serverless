package org.johng.connectedcar.lambda.functions.admin;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Registration;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class GetRegistrationFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String username = getPathParameter(request, PATH_USERNAME);
      String vin = getPathParameter(request, PATH_VIN);

      Registration registration = getRegistrationService().getRegistration(username, vin);

      if (registration != null) {
        return new APIGatewayProxyResponseEvent()
          .withBody(serializeItem(registration))
          .withHeaders(RESPONSE_HEADER)
          .withStatusCode(HttpStatus.SC_OK);
      }      
      else {
        return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_NOT_FOUND);
      }
    }, context);
  }
}