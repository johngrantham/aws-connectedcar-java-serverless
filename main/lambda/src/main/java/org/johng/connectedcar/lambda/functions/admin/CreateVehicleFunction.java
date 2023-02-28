package org.johng.connectedcar.lambda.functions.admin;

import org.apache.http.HttpStatus;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Vehicle;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class CreateVehicleFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      Vehicle vehicle = deserializeItem(request, Vehicle.class);
      getVehicleService().createVehicle(vehicle);
      
      return new APIGatewayProxyResponseEvent()
        .withHeaders(new HashMap<String, String>() {{put("Location", "/admin/vehicles/" + vehicle.getVin());}})
        .withStatusCode(HttpStatus.SC_CREATED);
    }, context);
  }
}