package com.global.UserManagement.Security.Services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.global.UserManagement.Dto.JWTResponseDto;
import com.global.UserManagement.Entity.AppUser;
import com.global.UserManagement.Entity.AppUserDetails;
import com.global.UserManagement.Entity.TokenInfo;
import com.global.UserManagement.Security.Utils.TokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	@SuppressWarnings("unused")
	private final TokenUtil JwtTokenUtils;
	private final HttpServletRequest httpRequest;
	private final tokenInfoService tokenInfoService;

	public JWTResponseDto login(String login, String password) {
		/*
		 * UsernamePasswordAuthenticationToken: This class is used to store the username
		 * and password of a user attempting to authenticate, and it is passed to an
		 * AuthenticationManager for validation.
		 */
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login, password));
		log.debug("Valid userDetails credentials.");

		AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenInfo tokenInfo = createLoginToken(login, userDetails.getId());

		return JWTResponseDto.builder().accessToken(tokenInfo.getAccessToken())
				.refreshToken(tokenInfo.getRefreshToken()).email(userDetails.getUsername()).build();
	}

	public TokenInfo createLoginToken(String userName, Long userId) {
		String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String accessTokenId = UUID.randomUUID().toString();
		String accessToken = TokenUtil.generateToken(userName, accessTokenId, false);
		log.info("Access token created. [tokenId={}]", accessTokenId);

		String refreshTokenId = UUID.randomUUID().toString();
		String refreshToken = TokenUtil.generateToken(userName, refreshTokenId, true);
		log.info("Refresh token created. [tokenId={}]", accessTokenId);

		TokenInfo tokenInfo = new TokenInfo(accessToken, refreshToken);
		tokenInfo.setUser(new AppUser(userId));
		tokenInfo.setUserAgentText(userAgent);
		tokenInfo.setLocalIpAddress(ip.getHostAddress());
		tokenInfo.setRemoteIpAddress(httpRequest.getRemoteAddr());
		// tokenInfo.setLoginInfo(createLoginInfoFromRequestUserAgent());
		return tokenInfoService.save(tokenInfo);
	}
}
