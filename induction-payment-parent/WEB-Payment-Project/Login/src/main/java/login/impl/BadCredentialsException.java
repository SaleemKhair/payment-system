/**
 * 
 */
package login.impl;

/**
 * @author PSLPT 147
 *
 */
public class BadCredentialsException extends AuthenticationException {


	private static final long serialVersionUID = 1L;

	public BadCredentialsException(String message) {
		super(message);
	}

}
