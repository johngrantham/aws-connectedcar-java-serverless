package org.johng.connectedcar.lambda.functions.customer;

import java.util.List;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Appointment;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class GetAppointmentsFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String username = getCognitoUsername(request);
      String vin = getPathParameter(request, PATH_VIN);
      
      List<Appointment> appointments = getAppointmentService().getRegistrationAppointments(username, vin);
      
      return new APIGatewayProxyResponseEvent()
        .withBody(serializeItem(appointments))
        .withHeaders(RESPONSE_HEADER)
        .withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}