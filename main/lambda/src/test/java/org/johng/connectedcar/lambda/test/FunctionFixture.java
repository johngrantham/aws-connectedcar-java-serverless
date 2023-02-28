package org.johng.connectedcar.lambda.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.mockito.Mock;
import org.mockito.Mockito;

public class FunctionFixture {

  private Injector injector;

  public FunctionFixture() {
	  injector = Guice.createInjector(new TestModule());
  }

  public Injector getInjector() {
    return injector;
  }
  
  @Mock
  private Context dummyLambdaContext = null;

  public Context getDummyLambdaContext() {
    if (dummyLambdaContext == null) {
      Context context = Mockito.mock(Context.class);
      LambdaLogger logger = Mockito.mock(LambdaLogger.class);
      Mockito.when(context.getLogger()).thenReturn(logger);
      
      dummyLambdaContext = context;
    }

    return dummyLambdaContext;
  }
}
