/**
 * 
 */
package login;

import login.impl.AuthenticationException;

/**
 * @author PSLPT 147
 *
 */
@FunctionalInterface
public interface AuthenticationManager {

	public UserInfo authenticate(AuthenticateRequest request) throws AuthenticationException;
}
