/**
 * 
 */
package login;

import login.impl.AuthenticationException;

/**
 * @author PSLPT 147
 *
 */
public interface AuthenticationManager {

	public UserInfo authenticate(AuthenticateRequest request) throws AuthenticationException;
}
