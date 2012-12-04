package uk.co.froot.openid.auth.roles;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import uk.co.froot.openid.auth.simple.OpenIDAuthenticator;
import uk.co.froot.openid.auth.simple.OpenIDCredentials;

import com.google.common.base.Objects;


/**
 * <p>Value object to provide the following to {@link OpenIDAuthenticator}:</p>
 * <ul>
 * <li>Storage of the necessary credentials for OpenID authentication</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class OpenIDRoleCredentials extends OpenIDCredentials {

	  private final Set<Authority> requiredAuthorities;

	  /**
	   * @param sessionId           The session ID acting as a surrogate for the OpenID token
	   * @param requiredAuthorities The authorities required to authenticate (provided by the {@link uk.co.froot.openid.auth.roles.RestrictedTo} annotation)
	   */
	  public OpenIDRoleCredentials(
	    String sessionId,
	    Set<Authority> requiredAuthorities) {
	   super(sessionId);
	    this.requiredAuthorities = checkNotNull(requiredAuthorities);
	  }


	  /**
	   * @return The authorities required to successfully authenticate
	   */
	  public Set<Authority> getRequiredAuthorities() {
	    return requiredAuthorities;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj) {
	      return true;
	    }
	    if ((obj == null) || (getClass() != obj.getClass())) {
	      return false;
	    }
	    final OpenIDRoleCredentials that = (OpenIDRoleCredentials) obj;

	    return super.equals(that);
	  }

	  @Override
	  public int hashCode() {
	    return (33 * super.hashCode());
	  }

	  @Override
	  public String toString() {
	    return Objects.toStringHelper(this)
	      .add("sessionId", getSessionId())
	      .add("authorities", requiredAuthorities)
	      .toString();
	  }

}
