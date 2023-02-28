package org.johng.connectedcar.lambda.functions.admin;

import org.apache.http.HttpStatus;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.updates.CustomerProvision;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class CreateCustomerFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      CustomerProvision provision = deserializeItem(request, CustomerProvision.class);

      getAdminOrchestrator().createCustomer(provision);

      return new APIGatewayProxyResponseEvent()
        .withHeaders(new HashMap<String, String>() {{put("Location", "/admin/customers/" + provision.getUsername());}})
        .withStatusCode(HttpStatus.SC_CREATED);
    }, context);
  }
}