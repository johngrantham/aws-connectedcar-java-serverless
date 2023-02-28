package org.johng.connectedcar.lambda.functions;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

@FunctionalInterface
public interface ProcessFunc {
  public APIGatewayProxyResponseEvent execute() throws Exception;
}
