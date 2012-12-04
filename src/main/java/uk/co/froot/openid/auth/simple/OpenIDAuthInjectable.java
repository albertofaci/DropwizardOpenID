package uk.co.froot.openid.auth.simple;

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.logging.Log;

public class OpenIDAuthInjectable<T> extends AbstractHttpContextInjectable<T> {
	
	   

	  private static final Log LOG = Log.forClass(OpenIDAuthInjectable.class);

	  private final Authenticator<OpenIDCredentials, T> authenticator;
	  private final String realm;
	  private final  boolean required;

	  /**
	   * @param authenticator The Authenticator that will compare credentials
	   * @param realm The authentication realm
	   * @param requiredAuthorities The required authorities as provided by the RestrictedTo annotation
	   */
	  OpenIDAuthInjectable(
	    Authenticator<OpenIDCredentials, T> authenticator,
	    String realm,
	    boolean required) {
	    this.authenticator = authenticator;
	    this.realm = realm;
	    this.required = required;
	  }

	  public Authenticator<OpenIDCredentials, T> getAuthenticator() {
	    return authenticator;
	  }

	  public String getRealm() {
	    return realm;
	  }

	  public boolean isRequired() {
	    return required;
	  }

	  @Override
	  public T getValue(HttpContext httpContext) {

	    try {

	      // Get the Authorization header
	      final Map<String,Cookie> cookieMap = httpContext.getRequest().getCookies();
	      if (!cookieMap.containsKey("JSESSIONID")) {
	        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
	      }

	      String sessionId = cookieMap.get("JSESSIONID").getValue();

	      if (sessionId != null) {

	        final OpenIDCredentials credentials = new OpenIDCredentials(sessionId);

	        final Optional<T> result = authenticator.authenticate(credentials);
	        if (result.isPresent()) {
	          return result.get();
	        }
	      }
	    } catch (IllegalArgumentException e) {
	      OpenIDAuthProvider.LOG.debug(e, "Error decoding credentials");
	    } catch (AuthenticationException e) {
	      OpenIDAuthProvider.LOG.warn(e, "Error authenticating credentials");
	      throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
	    }

	    // Must have failed to be here
	    throw new WebApplicationException(Response.Status.UNAUTHORIZED);
	  }

	}
