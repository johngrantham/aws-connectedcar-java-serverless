package org.johng.connectedcar.lambda.functions.vehicle;

import java.util.List;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Event;
import org.johng.connectedcar.lambda.functions.BaseVehicleFunction;

public class GetEventsFunction extends BaseVehicleFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String vin = getHeaderValue(request, HEADER_X_VIN);
      List<Event> events = getEventService().getEvents(vin);
      
      return new APIGatewayProxyResponseEvent()
        .withBody(serializeItem(events))
        .withHeaders(RESPONSE_HEADER)
        .withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}