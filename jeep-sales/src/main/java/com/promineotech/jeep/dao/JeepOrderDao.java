package com.promineotech.jeep.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.promineotech.jeep.dao.DefaultJeepOrderDao.SqlParams;
import com.promineotech.jeep.entity.Color;
import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.Engine;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Option;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.Tire;

public interface JeepOrderDao { 
  Order saveOrder(Customer customer, Jeep jeep, Color color, Engine engine, Tire tire, BigDecimal price, List<Option> options);  
  
  SqlParams params = generateInsertSql(customer, jeep, color, engine,  tire, price); 
  
  KeyHolder keyHolder = new GeneratedKeyHolder();
  
  jdbcTemplate.update(params.sql, params.source, keyHolder);
  
  Long orderPK = keyHolder.getKey().longValue(); 
  saveOptions(options, orderPK) 
  
  // @formatter:off
   
  // @formatter:on
   
  
  private void saveOptions(List<Option> options, Long orderPK) {
    for(Option option : options) {
      SqlParams params = generateInsertSql(option, orderPK); 
      jdbcTemplate.update(params.sql, params.source);
    }
  }
  List<Option> fetchOptions(List<String> optionIds);
  Optional<Customer> fetchCustomer(String customerId);
  Optional<Jeep> fetchModel(JeepModel model, String trim, int doors);
  Optional<Color> fetchColor(String colorId);
  Optional<Engine> fetchEngine(String engineId);
  Optional<Tire> fetchTire(String tireId);
}