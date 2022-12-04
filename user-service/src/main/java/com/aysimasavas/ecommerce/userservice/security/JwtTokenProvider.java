package com.aysimasavas.ecommerce.userservice.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${commerceapp.app.secret}")
	private String APP_SECRET;

	@Value("${commerce.expires.in}")
	private long EXPIRES_IN;

	public String generateToken(Authentication auth) {
		JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
		Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
		return Jwts.builder().setSubject(Long.toString(userDetails.getId())).setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public int getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
		return Integer.parseInt(claims.getSubject());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
		}
	}

}
