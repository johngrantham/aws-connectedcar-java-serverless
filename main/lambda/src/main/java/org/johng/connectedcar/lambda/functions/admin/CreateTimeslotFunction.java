package org.johng.connectedcar.lambda.functions.admin;

import org.apache.http.HttpStatus;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Timeslot;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class CreateTimeslotFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      Timeslot timeslot = deserializeItem(request, Timeslot.class);
      getTimeslotService().createTimeslot(timeslot);
      
      return new APIGatewayProxyResponseEvent()
        .withHeaders(new HashMap<String, String>() {{put("Location", "/admin/dealers/" + timeslot.getDealerId() + "/timeslot/" + timeslot.getServiceDateHour());}})
        .withStatusCode(HttpStatus.SC_CREATED);
    }, context);
  }
}