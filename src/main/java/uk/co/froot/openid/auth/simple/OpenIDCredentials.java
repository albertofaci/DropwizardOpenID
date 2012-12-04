package uk.co.froot.openid.auth.simple;

import static com.google.common.base.Preconditions.checkNotNull;
import uk.co.froot.openid.auth.roles.RestrictedTo;

import com.google.common.base.Objects;

/**
 * <p>Value object to provide the following to {@link OpenIDAuthenticator}:</p>
 * <ul>
 * <li>Storage of the necessary credentials for OpenID authentication</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class OpenIDCredentials {

  private final String sessionId;

  /**
   * @param sessionId           The session ID acting as a surrogate for the OpenID token
   * @param requiredAuthorities The authorities required to authenticate (provided by the {@link uk.co.froot.openid.auth.roles.RestrictedTo} annotation)
   */
  public OpenIDCredentials(
    String sessionId) {
    this.sessionId = checkNotNull(sessionId);
  }

  /**
   * @return The OpenID token
   */
  public String getSessionId() {
    return sessionId;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (getClass() != obj.getClass())) {
      return false;
    }
    final OpenIDCredentials that = (OpenIDCredentials) obj;

    return sessionId.equals(that.sessionId);
  }

  @Override
  public int hashCode() {
    return (31 * sessionId.hashCode());
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
      .add("sessionId", sessionId)
      .toString();
  }

}
