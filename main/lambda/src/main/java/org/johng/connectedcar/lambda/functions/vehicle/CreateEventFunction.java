package org.johng.connectedcar.lambda.functions.vehicle;

import org.apache.http.HttpStatus;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Event;
import org.johng.connectedcar.lambda.functions.BaseVehicleFunction;

public class CreateEventFunction extends BaseVehicleFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String vin = getHeaderValue(request, HEADER_X_VIN);
      Event event = deserializeItem(request, Event.class);
      event.setVin(vin);
      getEventService().createEvent(event);
      
      return new APIGatewayProxyResponseEvent()
        .withHeaders(new HashMap<String, String>() {{put("Location", "/vehicle/events/" + event.getTimestamp());}})
        .withStatusCode(HttpStatus.SC_CREATED);
    }, context);
  }
}