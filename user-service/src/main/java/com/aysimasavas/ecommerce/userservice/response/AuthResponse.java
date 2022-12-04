package com.aysimasavas.ecommerce.userservice.response;

public class AuthResponse {
	String message;
	int userId;
	String accessToken;
	String refreshToken;

	public AuthResponse() {

	}

	public AuthResponse(String message, int userId, String accessToken, String refreshToken) {
		super();
		this.message = message;
		this.userId = userId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
