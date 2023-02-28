package org.johng.connectedcar.lambda.functions.admin;

import java.util.List;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.johng.connectedcar.core.shared.data.entities.Timeslot;
import org.johng.connectedcar.lambda.functions.BaseRequestFunction;

public class GetTimeslotsFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String dealerId = getPathParameter(request, PATH_DEALER_ID);
      String startDate = getQueryStringParameter(request, QUERY_START_DATE);
      String endDate = getQueryStringParameter(request, QUERY_END_DATE);

      List<Timeslot> timeslots = getTimeslotService().getTimeslots(dealerId, startDate, endDate);
      
      return new APIGatewayProxyResponseEvent()
        .withBody(serializeItem(timeslots))
        .withHeaders(RESPONSE_HEADER)
        .withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}