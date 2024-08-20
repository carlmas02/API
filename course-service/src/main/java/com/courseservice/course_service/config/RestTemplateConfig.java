package com.courseservice.course_service.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import brave.Tracing;
import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;

/**
 * Configuration class for setting up a {@link RestTemplate} bean with load balancing
 * and tracing capabilities in a Spring Boot application.
 */
@Configuration
public class RestTemplateConfig {

  /**
   * Creates an {@link HttpTracing} bean that is used to trace HTTP requests.
   * The tracing information is captured and reported using the provided {@link Tracing} instance.
   *
   * @param tracing the {@link Tracing} instance used to build the {@link HttpTracing}
   * @return a configured {@link HttpTracing} instance
   */
  @Bean
  public HttpTracing create(Tracing tracing) {
    return HttpTracing.newBuilder(tracing).build();
  }

  /**
   * Creates a {@link RestTemplate} bean with load balancing and tracing capabilities.
   * The {@link RestTemplate} is configured to use the {@link TracingClientHttpRequestInterceptor}
   * to trace outgoing HTTP requests and to use a load balancer for service-to-service communication.
   *
   * @param httpTracing the {@link HttpTracing} instance used for tracing HTTP requests
   * @return a configured {@link RestTemplate} instance
   */
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate(HttpTracing httpTracing) {
    return new RestTemplateBuilder()
            .interceptors(TracingClientHttpRequestInterceptor.create(httpTracing))
            .build();
  }
}
