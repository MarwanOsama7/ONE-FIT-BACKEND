package com.global.UserManagement.Security.Filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.global.UserManagement.Entity.AppUserDetails;
import com.global.UserManagement.Security.Utils.TokenUtil;
import com.global.UserManagement.Services.UserServices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthFilter extends OncePerRequestFilter {
	/*
	 * The AuthFilter class: Extracts the JWT token from the Authorization header.
	 * Validates the token. Extracts the username from the token. Loads user
	 * details. Validates the token against the user details. Sets the
	 * authentication information in the security context if the token is valid.
	 * Proceeds with the filter chain.
	 */
	
	@Autowired
	private UserServices userService;

	@Autowired
	private TokenUtil tokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Retrieves the Authorization header from the HTTP request.
		final String jwtTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		log.info("Path is >> " + request.getRequestURL());

		// This context holds the authentication information for the current user
		final SecurityContext securityContext = SecurityContextHolder.getContext();

		if (jwtTokenHeader != null && securityContext.getAuthentication() == null) {
			String jwtToken = jwtTokenHeader.substring("Bearer ".length());
			if (tokenUtil.validateToken(jwtToken, request)) {
				String username = tokenUtil.getUserNameFromToken(jwtToken);
				if (username != null) {
					AppUserDetails userDetails = (AppUserDetails) userService.loadUserByUsername(username);
					if (tokenUtil.isTokenValid(jwtToken, userDetails)) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		}

		filterChain.doFilter(request, response);

	}

}
