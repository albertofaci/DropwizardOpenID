package uk.co.froot.openid.auth.simple;


import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.yammer.dropwizard.auth.Auth;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.logging.Log;

/**
 * <p>Authentication provider to provide the following to Jersey:</p>
 * <ul>
 * <li>Bridge between Dropwizard and Jersey for HMAC authentication</li>
 * <li>Provides additional {@link uk.co.froot.openid.auth.roles.Authority} information</li>
 * </ul>
 *
 * @param <T>    the principal type.
 * @since 0.0.1
 */
public class OpenIDAuthProvider implements InjectableProvider<Auth, Parameter> {
  static final Log LOG = Log.forClass(OpenIDAuthProvider.class);

  private final Authenticator<OpenIDCredentials, T> authenticator;
  private final String realm;

  /**
   * Creates a new {@link OpenIDAuthProvider} with the given {@link com.yammer.dropwizard.auth.Authenticator} and realm.
   *
   * @param authenticator the authenticator which will take the {@link OpenIDCredentials} and
   *                      convert them into instances of {@code T}
   * @param realm         the name of the authentication realm
   */
  public OpenIDAuthProvider(Authenticator<OpenIDCredentials, T> authenticator, String realm) {
    this.authenticator = authenticator;
    this.realm = realm;
  }

  public ComponentScope getScope() {
    return ComponentScope.PerRequest;
  }

  public Injectable<?> getInjectable(ComponentContext ic,
		  								Auth a,
                                     Parameter c) {
    return new OpenIDAuthInjectable<T>(authenticator, realm, a.required());
  }
}

