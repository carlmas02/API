package com.courseservice.course_service.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Cloudinary integration in the application. This class provides
 * a Cloudinary bean configured with the necessary credentials to interact with the Cloudinary
 * service.
 */
@Configuration
public class CloudinaryConfig {

  /**
   * The Cloudinary cloud name. Injected from the application properties using the key
   * 'cloudinary.cloud-name'.
   */
  @Value("${cloudinary.cloud-name}")
  private String cloudName;

  /**
   * The Cloudinary API key. Injected from the application properties using the key
   * 'cloudinary.api-key'.
   */
  @Value("${cloudinary.api-key}")
  private String apiKey;

  /**
   * The Cloudinary API secret. Injected from the application properties using the key
   * 'cloudinary.api-secret'.
   */
  @Value("${cloudinary.api-secret}")
  private String apiSecret;

  /**
   * Creates a Cloudinary bean configured with the provided credentials. This bean is used to
   * interact with Cloudinary for image and video management.
   *
   * @return a configured {@link Cloudinary} instance
   */
  @Bean
  public Cloudinary cloudinary() {
    return new Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret));
  }
}
