package ch.tbmelabs.tv.core.authorizationserver.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Table(name = "client_grant_types")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantType extends NicelyDocumentedJDBCResource {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "client_grant_types_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 32)
  private String name;

  @JsonProperty(access = Access.WRITE_ONLY)
  @JsonManagedReference("grant_type_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "clientGrantType")
  private Set<ClientGrantTypeAssociation> clientsWithGrantTypes;

  public GrantType(String name) {
    setName(name);
  }
}
