package com.global.UserManagement.Security.Utils;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtUnAuthResponse implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex)
			throws IOException, ServletException {
		
		ex.printStackTrace();
		
		final String expired = (String) req.getAttribute("expired");
		
		System.out.println("ex >>>>" + expired);
		
		if (expired!=null){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED,expired);
        }else{
		res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You would need to provide the Jwt token to access this resource");
        }
	}

}
