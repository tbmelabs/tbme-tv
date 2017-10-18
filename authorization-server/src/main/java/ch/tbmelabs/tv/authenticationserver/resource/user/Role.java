package ch.tbmelabs.tv.authenticationserver.resource.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ch.tbmelabs.tv.authenticationserver.resource.NicelyDocumentedJDBCResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_roles")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends NicelyDocumentedJDBCResource implements GrantedAuthority {
  @Transient
  private static final long serialVersionUID = 1L;

  @Transient
  public static final String ROLE_PREFIX = "ROLE_";

  // TODO: Fetch from repository
  // @Transient
  // public static final Role ROLE_USER = new Role("USER");
  // @Transient
  // public static final Role ROLE_ADMIN = new Role("ADMIN");

  @Id
  @GenericGenerator(name = "pk_sequence", strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY, parameters = {
      @Parameter(name = "sequence_name", value = "user_roles_id_seq"),
      @Parameter(name = "increment_size", value = "1") })
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 8)
  private String name;

  public Role(String name) {
    this.name = name;
  }

  @Override
  public String getAuthority() {
    return ROLE_PREFIX + getName();
  }
}