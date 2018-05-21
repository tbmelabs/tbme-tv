package ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserRoleAssociationId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long userId;
  private Long userRoleId;
}
