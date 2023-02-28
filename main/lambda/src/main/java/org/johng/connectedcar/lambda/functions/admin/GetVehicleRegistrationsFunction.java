package org.johng.connectedcar.lambda.functions.admin;

import java.util.List;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Registration;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class GetVehicleRegistrationsFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String vin = getPathParameter(request, PATH_VIN);
      List<Registration> registrations = getRegistrationService().getVehicleRegistrations(vin);
      
      return new APIGatewayProxyResponseEvent()
        .withBody(serializeItem(registrations))
        .withHeaders(RESPONSE_HEADER)
        .withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}