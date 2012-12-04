package uk.co.froot.openid.auth;


/**
 * <p>Simple representation of a User to provide the following to Resources:<br>
 * <ul>
 * <li>Storage of user state</li>
 * </ul>
 * </p>
 */
public interface OpenIDUser {


  public String getOpenIDIdentifier();

}
