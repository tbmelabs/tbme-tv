package ch.tbmelabs.tv.shared.constants.spring;

public class SpringApplicationProfile {
  /**
   * Productive systems.
   */
  public static final String PROD = "prod";

  /**
   * Development profile with debug logs.
   */
  public static final String DEV = "dev";

  /**
   * Used while (maven) testing is active.
   */
  public static final String TEST = "test";

  /**
   * Activates logging via Logstash and ELK stack.
   */
  public static final String ELK = "elk";

  /**
   * Disables the Eureka discovery client if present.
   */
  public static final String NO_EUREKA = "no-eureka";

  /**
   * Disables chaching via REDIS-Server.
   */
  public static final String NO_REDIS = "no-redis";

  private SpringApplicationProfile() {
    // Hidden constructor
  }
}