package com.jp.stock.service.config;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * configuration for the ActiveMQ
 *
 * @author chandresh.mishra
 */
@EnableJms
@Configuration
public class ActiveMQConfig {

  private static final String STOCK_QUEUE = "stock-queue";

  @Bean
  public JmsListenerContainerFactory<?> queueListenerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setMessageConverter(messageConverter());
    return factory;
  }

  @Bean
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
    return connectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate() {

    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
    jmsTemplate.setDefaultDestinationName(STOCK_QUEUE);
    jmsTemplate.setConnectionFactory(connectionFactory());
    jmsTemplate.setMessageConverter(messageConverter());
    return jmsTemplate;
  }
}
