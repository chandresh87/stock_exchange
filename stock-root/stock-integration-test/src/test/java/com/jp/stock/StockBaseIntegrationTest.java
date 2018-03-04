package com.jp.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.stock.api.config.ApiConfig;
import com.jp.stock.api.config.SwaggerConfig;
import com.jp.stock.config.IntegrationTestConfig;
import com.jp.stock.service.config.GemfireConfiguration;
import com.jp.stock.service.config.ServiceConfig;
import java.nio.charset.Charset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base class for all integration test classes to extend.
 *
 * @author chandresh.mishra
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ContextConfiguration(
  classes = {
    GemfireConfiguration.class,
    ServiceConfig.class,
    ApiConfig.class,
    SwaggerConfig.class,
    IntegrationTestConfig.class
  },
  loader = AnnotationConfigWebContextLoader.class
)
public abstract class StockBaseIntegrationTest {

  protected MockMvc mockMvc;

  @Autowired private WebApplicationContext wac;

  @Autowired protected ObjectMapper objectMapper;

  protected static final MediaType APPLICATION_JSON_UTF8 =
      new MediaType(
          MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));
  /**
   * Setup method - executes once before any test in a class. Common setup for all integration test
   * classes.
   */
  @Before
  public void setUp() {
    Assert.assertNotNull(this.wac);

    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    Assert.assertNotNull(this.mockMvc);
  }
}
