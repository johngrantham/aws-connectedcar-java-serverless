package org.johng.connectedcar.lambda.functions.customer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
      final String DATE_FORMAT = "yyyy-MM-dd";
      SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

      Date today = new Date();
      Calendar calendar = Calendar.getInstance(); 
      calendar.setTime(today);
      calendar.add(Calendar.DATE, 30);
      Date future = calendar.getTime();

      String dealerId = getPathParameter(request, PATH_DEALER_ID);
      String startDate = formatter.format(today);
      String endDate = formatter.format(future);

      List<Timeslot> timeslots = getTimeslotService().getTimeslots(dealerId, startDate, endDate);
      
      return new APIGatewayProxyResponseEvent()
        .withBody(serializeItem(timeslots))
        .withHeaders(RESPONSE_HEADER)
        .withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}