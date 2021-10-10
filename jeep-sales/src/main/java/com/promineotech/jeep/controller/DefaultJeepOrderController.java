package com.promineotech.jeep.controller;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import com.promineotech.jeep.service.JeepOrderService;
import lombok.extern.slf4j.Slf4j;

@RestController 
@Slf4j
public class DefaultJeepOrderController implements JeepOrderController {
  
  @Autowired 
  private JeepOrderService jeepOrderService;
  
  @Override
  public Order createOrder(OrderRequest orderRequest) {
    log.debug("Order={}", orderRequest);
    return jeepOrderService.createOrder(orderRequest);
     
  }

  @Override
  public List<Jeep> fetchJeeps(JeepModel model, String trim) {
    // TODO Auto-generated method stub
    return null;
  }

}
