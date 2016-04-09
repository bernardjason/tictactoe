package bjason.swagger.entity;

import javax.persistence.Entity;

import org.springframework.hateoas.ResourceSupport;

public class OauthToken extends ResourceSupport {

	String tokenUrl;
	String state;
	String access_token;
	String token_type;
	String refreshToken;
	String expires_in;
	String scope;
	public String getTokenUrl() {
		return tokenUrl;
	}
	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		return "OauthToken [tokenUrl=" + tokenUrl + ", state=" + state
				+ ", access_token=" + access_token + ", token_type="
				+ token_type + ", refreshToken=" + refreshToken
				+ ", expires_in=" + expires_in + ", scope=" + scope + "]";
	}
	
	
}
