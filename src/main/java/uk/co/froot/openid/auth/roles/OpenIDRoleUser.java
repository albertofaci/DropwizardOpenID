package uk.co.froot.openid.auth.roles;

import java.util.Set;

import uk.co.froot.openid.auth.OpenIDUser;

/**
 * <p>Simple representation of a User to provide the following to Resources:<br>
 * <ul>
 * <li>Storage of user state</li>
 * </ul>
 * </p>
 */
public interface OpenIDRoleUser extends OpenIDUser {

  public boolean hasAllAuthorities(Set<Authority> requiredAuthorities);

  public boolean hasAuthority(Authority authority);

}
