package org.johng.connectedcar.lambda.functions.customer;

import org.apache.http.HttpStatus;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Appointment;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class CreateAppointmentFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String username = getCognitoUsername(request);
      Appointment appointment = deserializeItem(request, Appointment.class);
      
      String appointmentId = getCustomerOrchestrator().createAppointment(username, appointment);

      if (appointment != null) {
        return new APIGatewayProxyResponseEvent()
          .withHeaders(new HashMap<String, String>() {{put("Location", "/customer/appointments/" + appointmentId);}})
          .withStatusCode(HttpStatus.SC_CREATED);
      }

      return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_BAD_REQUEST);
    }, context);
  }
}