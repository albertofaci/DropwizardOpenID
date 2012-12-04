package uk.co.froot.openid.demo;

import org.eclipse.jetty.server.session.SessionHandler;

import uk.co.froot.openid.auth.roles.OpenIDRoleAuthenticator;
import uk.co.froot.openid.auth.roles.OpenIDRoleProvider;
import uk.co.froot.openid.demo.auth.InMemoryUserCache;
import uk.co.froot.openid.demo.model.User;
import uk.co.froot.openid.demo.resources.PublicHomeResource;

import com.google.common.cache.CacheBuilderSpec;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;

/**
 * <p>Service to provide the following to application:</p>
 * <ul>
 * <li>Provision of access to resources</li>
 * </ul>
 * <p>Use <code>java -jar mbm-develop-SNAPSHOT.jar server openid-demo.yml</code> to start the demo</p>
 *
 * @since 0.0.1
 *         
 */
public class OpenIDDemoService extends Service<OpenIDDemoConfiguration> {

  /**
   * Main entry point to the application
   *
   * @param args CLI arguments
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    new OpenIDDemoService().run(new String[]{"server", "C:\\workspace\\DropwizardOpenID\\openid-demo.yml"});
  }

  private OpenIDDemoService() {
    super("store");
    CacheBuilderSpec cacheBuilderSpec = (System.getenv("FILE_CACHE_ENABLED") == null) ? CacheBuilderSpec.parse("maximumSize=0") : AssetsBundle.DEFAULT_CACHE_SPEC;
    addBundle(new AssetsBundle("/assets/images", cacheBuilderSpec, "/images"));

  }

  @Override
  protected void initialize(OpenIDDemoConfiguration configuration,
                            Environment environment) {

    // Configure authenticator
    OpenIDRoleAuthenticator<User> authenticator = new OpenIDRoleAuthenticator<User>(InMemoryUserCache.INSTANCE);

    // Configure environment
    environment.scanPackagesForResourcesAndProviders(PublicHomeResource.class);

    // Health checks
    environment.addHealthCheck(new uk.co.froot.openid.demo.health.OpenIdDemoHealthCheck());

    // Providers
    environment.addProvider(new ViewMessageBodyWriter());
    environment.addProvider(new OpenIDRoleProvider<User>(authenticator, "OpenID"));

    // Session handler
    environment.setSessionHandler(new SessionHandler());

    // Bundles
    addBundle(new ViewBundle());

  }

}
