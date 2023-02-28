package org.johng.connectedcar.lambda.functions.customer;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Appointment;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class DeleteAppointmentFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String username = getCognitoUsername(request);
      String appointmentId = getPathParameter(request, PATH_APPOINTMENT_ID);
      
      Appointment appointment = getAppointmentService().getAppointment(appointmentId);
      
      if (appointment != null && appointment.getRegistrationKey().getUsername().equals(username)) {
        getAppointmentService().deleteAppointment(appointmentId);
        return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_OK);
      }

      return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_BAD_REQUEST);
      
    }, context);
  }
}