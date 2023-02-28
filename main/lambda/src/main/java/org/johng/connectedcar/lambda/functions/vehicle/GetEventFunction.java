package org.johng.connectedcar.lambda.functions.vehicle;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Event;
import org.johng.connectedcar.lambda.functions.BaseVehicleFunction;

public class GetEventFunction extends BaseVehicleFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String vin = getHeaderValue(request, HEADER_X_VIN);
      String timestamp = getPathParameter(request, PATH_TIMESTAMP);

      Event event = getEventService().getEvent(vin, Long.parseLong(timestamp));
      
      if (event != null) {
        return new APIGatewayProxyResponseEvent()
          .withBody(serializeItem(event))
          .withHeaders(RESPONSE_HEADER)
          .withStatusCode(HttpStatus.SC_OK);
      }
      else {
        return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_NOT_FOUND);
      }
    }, context);
  }
}