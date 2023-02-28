package org.johng.connectedcar.lambda.functions;

import com.google.inject.Injector;

import org.crac.Core;
import org.crac.Resource;
import org.johng.connectedcar.core.shared.data.enums.StateCodeEnum;

public abstract class BaseVehicleFunction extends BaseRequestFunction implements Resource {
	
  protected BaseVehicleFunction() {
    Core.getGlobalContext().register(this);    
  }

  public BaseVehicleFunction(Injector injector) {
    super(injector);
  }

  @Override
  public void beforeCheckpoint(org.crac.Context<? extends Resource> context) throws Exception {
    getDealerService().getDealers(StateCodeEnum.OR);
  }

  @Override
  public void afterRestore(org.crac.Context<? extends Resource> context) throws Exception {
  }
}