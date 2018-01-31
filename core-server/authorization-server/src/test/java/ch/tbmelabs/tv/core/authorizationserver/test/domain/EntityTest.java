package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Entity;

import org.junit.Test;
import org.reflections.Reflections;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class EntityTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final Integer EXPECTED_ENTITIES_COUNT = 12;

  @Test
  public void allEntitiesShouldBeAnnotated() {
    assertThat(
        new Reflections(Application.class.getPackage().getName() + ".domain").getTypesAnnotatedWith(Entity.class))
            .hasSize(EXPECTED_ENTITIES_COUNT);
  }

  @Test
  public void allEntitiesShouldExtendTheConfiguredSuperType() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain")
        .getSubTypesOf(NicelyDocumentedJDBCResource.class)).hasSize(EXPECTED_ENTITIES_COUNT);
  }
}