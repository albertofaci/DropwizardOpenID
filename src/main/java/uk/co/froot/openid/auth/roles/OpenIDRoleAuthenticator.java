package uk.co.froot.openid.auth.roles;

import uk.co.froot.openid.auth.SessionProvider;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;

/**
 * <p>Authenticator to provide the following to application:</p>
 * <ul>
 * <li>Verifies the provided credentials are valid</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class OpenIDRoleAuthenticator<U extends OpenIDRoleUser> implements Authenticator<OpenIDRoleCredentials, U> {

  private SessionProvider<U> sessionProvider;
  
  public OpenIDRoleAuthenticator(SessionProvider<U> sessionProvider) {
	  this.sessionProvider = sessionProvider;
  }
	
  public Optional<U> authenticate(OpenIDRoleCredentials credentials) throws AuthenticationException {

    // Get the User referred to by the API key
	Optional<U> user = sessionProvider.getBySessionId(credentials.getSessionId());
	  
    if (!user.isPresent()) {
      return Optional.absent();
    }

    // Check that their authorities match their credentials
    if (!user.get().hasAllAuthorities(credentials.getRequiredAuthorities())) {
      return Optional.absent();
    }

    return user;

  }

}
