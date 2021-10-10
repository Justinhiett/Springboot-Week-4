package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat; 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.controller.support.CreateOrderTestSupport;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) 
@ActiveProfiles 
@Sql(scripts = {
    "classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
    "classpath:flyway/migrations/V1.1__Jeep_Data.sql"}, 
    config = @SqlConfig(encoding = "utf-8"))

class CreateOrderTest extends CreateOrderTestSupport {  
  @LocalServerPort
  public String serverPort;  
  
  public TestRestTemplate restTemplate;
  
  
  
  /** 
   * 
   */
  @Test
  void testCreateOrderReturnsSuccess201() { 
    
    
    // Given an order as JSON
    String body = createOrderBody(); 
    String uri = String.format("http://localhost:%d/orders", serverPort); 
    
    HttpHeaders headers = new HttpHeaders(); 
    headers.setContentType(MediaType.APPLICATION_JSON); 
    
    HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);
    
    // When: the order is sent  
    ResponseEntity<Order> response = getRestTemplate().exchange(uri, HttpMethod.POST, bodyEntity, Order.class);
    
    // Then: a 201 status is returned 
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED); 
    assertThat(response.getBody()).isNotNull(); 
    
    Order order = response.getBody(); 
    assertThat(order.getCustomer().getCustomerId()).isEqualTo("MORISON_LINA");
    assertThat(order.getModel().getModelId()).isEqualTo(JeepModel.WRANGLER);
    assertThat(order.getModel().getTrimlevel()).isEqualTo("Sport Altitude");
    assertThat(order.getModel().getNumDoors()).isEqualTo(4);
    assertThat(order.getColor().getColorId()).isEqualTo("EXT_NACHO");
    assertThat(order.getEngine().getEngineId()).isEqualTo("2_0_TURBO");
    assertThat(order.getTire().getTireId()).isEqualTo("35_TOYO");
    assertThat(order.getOptions()).hasSize(6);

    // And: the returned order is correct
  } 
  
  protected String createOrderBody() { 
    
    return "{\n"
        + "  \"customer\":\"MORISON_LINA\",\n"
        + "  \"model\":\"WRANGLER\",\n"
        + "  \"trim\":\"Sport Altitude\",\n"
        + "  \"doors\":4,\n"
        + "  \"color\":\"EXT_NACHO\",\n"
        + "  \"engine\":\"2_0_TURBO\",\n"
        + "  \"tire\":\"35_TOYO\",\n"
        + "  \"options\":[\n"
        + "    \"DOOR_QUAD_4\",\n"
        + "    \"EXT_AEV_LIFT\",\n"
        + "    \"EXT_WARN_WINCH\",\n"
        + "    \"EXT_WARN_BUMPER_FRONT\",\n"
        + "    \"EXT_WARN_BUMPER_REAR\",\n"
        + "    \"EXT_ARB_COMPRESSOR\"\n"
        + "  ]\n"
        + "}"; 
    
    // @formatter:on
  }

}
