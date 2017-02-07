package login.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.AuthenticateRequest;
import login.AuthenticationManager;
import login.UserInfo;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("login", true);
		req.setAttribute("pageContent", "/WEB-INF/views/login.jsp");
		req.setAttribute("sideNav", "/WEB-INF/views/empty.jsp");
		req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuthenticateRequest authenticateRequest = extractAuthenticateRequest(req);
		UserInfo userInfo;
		AuthenticationManager manager = new MemoryAuthenticationManager();
		try {
			userInfo = manager.authenticate(authenticateRequest);
			HttpSession session = req.getSession();
			session.setAttribute("userInfo", userInfo);
			resp.sendRedirect(req.getServletContext().getContextPath());
		} catch (AuthenticationException e) {
			req.setAttribute("loginFailure", e);
			req.setAttribute("pageContent", "/WEB-INF/views/login.jsp");
			req.setAttribute("sideNav", "/WEB-INF/views/empty.jsp");
			req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
		}
	}

	private AuthenticateRequest extractAuthenticateRequest(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String address = req.getRemoteAddr();

		AuthenticateRequest authenticateRequest = new AuthenticateRequest() {

			@Override
			public String getUsername() {
				return username;
			}

			@Override
			public String getPassword() {
				return password;
			}

			@Override
			public String getIPAddress() {
				return address;
			}
		};
		return authenticateRequest;
	}
}
