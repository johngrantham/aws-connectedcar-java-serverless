package org.johng.connectedcar.lambda.test;

import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.johng.connectedcar.core.shared.data.attributes.Address;
import org.johng.connectedcar.core.shared.data.entities.Dealer;
import org.johng.connectedcar.core.shared.data.enums.StateCodeEnum;

public abstract class FunctionTest {

  private ObjectMapper mapper;
  private Random random;

  protected FunctionTest() {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    random = new Random();
  }

  protected <T> T deserializeItem(String json, Class<T> classType) {
    try {
      T item = mapper.readValue(json, classType);
      
      return item;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  protected String serializeItem(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  protected String parseLocation(String location)
  {
    if (location != null)
    {
      var paths = location.split("/");

      if (paths.length > 0)
      {
        return paths[paths.length-1];
      }
    }

    return null;
  }

  /******************************************************************************************/

  private String getRandomNumbers() {
    return Integer.toString(random.nextInt(9999 - 1000) + 1000);
  }

  protected Dealer getDealer()
  {
    Dealer dealer = new Dealer();

    dealer.setName("Test Dealer " + getRandomNumbers());
    dealer.setAddress(new Address());
    dealer.getAddress().setStreetAddress(getRandomNumbers() + " Main Street");
    dealer.getAddress().setCity("Phoenix");
    dealer.getAddress().setState("AZ");
    dealer.getAddress().setZipCode("12345");
    dealer.setStateCode(StateCodeEnum.AZ);

    return dealer;
  }

}
