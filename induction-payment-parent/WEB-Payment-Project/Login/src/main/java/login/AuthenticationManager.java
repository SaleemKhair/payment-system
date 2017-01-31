/**
 * 
 */
package login;

import loginImpl.AuthenticationException;

/**
 * @author PSLPT 147
 *
 */
public interface AuthenticationManager {

	public UserInfo authenticate(AuthenticateRequest request) throws AuthenticationException;
}
