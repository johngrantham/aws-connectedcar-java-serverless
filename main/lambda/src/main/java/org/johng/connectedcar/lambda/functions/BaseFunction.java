package org.johng.connectedcar.lambda.functions;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.johng.connectedcar.core.services.context.IServiceContext;
import org.johng.connectedcar.core.services.modules.TracingModule;
import org.johng.connectedcar.core.shared.orchestrators.IAdminOrchestrator;
import org.johng.connectedcar.core.shared.orchestrators.ICustomerOrchestrator;
import org.johng.connectedcar.core.shared.services.IAppointmentService;
import org.johng.connectedcar.core.shared.services.ICustomerService;
import org.johng.connectedcar.core.shared.services.IDealerService;
import org.johng.connectedcar.core.shared.services.IEventService;
import org.johng.connectedcar.core.shared.services.IRegistrationService;
import org.johng.connectedcar.core.shared.services.ITimeslotService;
import org.johng.connectedcar.core.shared.services.IUserService;
import org.johng.connectedcar.core.shared.services.IVehicleService;

public abstract class BaseFunction {
	
  private Injector injector;
  
  protected BaseFunction() {
    injector = Guice.createInjector(new TracingModule());
  }

  protected BaseFunction(Injector injector) {
    if (injector == null)
      throw new IllegalArgumentException();

    this.injector = injector;
  }

  protected IServiceContext getServiceContext() {
    return injector.getInstance(IServiceContext.class);
  }

  protected IDealerService getDealerService() {
    return injector.getInstance(IDealerService.class);
  }

  protected ITimeslotService getTimeslotService() {
    return injector.getInstance(ITimeslotService.class);
  }

  protected IAppointmentService getAppointmentService() {
    return injector.getInstance(IAppointmentService.class);
  }

  protected ICustomerService getCustomerService() {
    return injector.getInstance(ICustomerService.class);
  }

  protected IRegistrationService getRegistrationService() {
    return injector.getInstance(IRegistrationService.class);
  }

  protected IVehicleService getVehicleService() {
    return injector.getInstance(IVehicleService.class);
  }
  
  protected IEventService getEventService() {
    return injector.getInstance(IEventService.class);
  }

  protected IUserService getUserService() {
    return injector.getInstance(IUserService.class);
  }

  protected IAdminOrchestrator getAdminOrchestrator() {
    return injector.getInstance(IAdminOrchestrator.class);
  }

  protected ICustomerOrchestrator getCustomerOrchestrator() {
    return injector.getInstance(ICustomerOrchestrator.class);
  }
}