package org.johng.connectedcar.lambda.functions.customer;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Vehicle;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class GetVehicleFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String username = getCognitoUsername(request);
      String vin = getPathParameter(request, PATH_VIN);

      Vehicle vehicle = getCustomerOrchestrator().getVehicle(username, vin);

      if (vehicle != null) {
        return new APIGatewayProxyResponseEvent()
          .withBody(serializeItem(vehicle))
          .withHeaders(RESPONSE_HEADER)
          .withStatusCode(HttpStatus.SC_OK);
      }
        
      return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_NOT_FOUND);     
      
    }, context);
  }
}