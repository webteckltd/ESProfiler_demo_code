package com.demo.ravioAuth2.filter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserInfoFilter extends OncePerRequestFilter {
	
	private String userInfoUri;
	public UserInfoFilter(String userInfoUri) {
		super();
		this.userInfoUri = userInfoUri;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (null != authentication && authentication instanceof JwtAuthenticationToken) {
	        	HttpURLConnection connection = null;
	        	try {
					JwtAuthenticationToken token  =  (JwtAuthenticationToken)authentication;
					logger.trace(" UserInfoFilter() -  token value  = " + token.getToken().getTokenValue());
					logger.trace(" UserInfoFilter() - userInfoUri  = " + userInfoUri);
					URL url = new URL(userInfoUri);
					connection = (HttpURLConnection) url.openConnection();
			
					connection.setDoInput(true);
					connection.setRequestMethod("GET");
					connection.setRequestProperty("Accept", "application/json");
					connection.setRequestProperty("Authorization","Bearer "+token.getToken().getTokenValue());
					int responseCode = connection.getResponseCode();
					logger.debug(" UserInfoFilter() - responseCode  = " + responseCode);
					if (responseCode == HttpURLConnection.HTTP_OK) {
						String jsonBody = IOUtils.toString(connection.getInputStream());
						logger.trace(" UserInfoFilter() -  Raw json value  = " + jsonBody);
						if (null != jsonBody && jsonBody.length() > 10) {
							logger.trace("getInterResourceID() -  jsonBody = " + jsonBody);
					     	JSONObject respJson  = new JSONObject(jsonBody);
					     	
					     	 Map<String, Object> claimsMap  = new LinkedHashMap<String, Object>();
					     	
					     	Stream.of(respJson.getNames(respJson)).forEach( name -> {
					     		claimsMap.putIfAbsent(name, respJson.get(name));					     			
					     	});
					     						     	
					     /**  Replacing old claims with UserInfo details and setting in context  - Start */	
					     token.getToken().getClaims().forEach((k, v) -> claimsMap.putIfAbsent(k, v));
					     Jwt  newJwtToken  =  new Jwt(token.getToken().getTokenValue(),token.getToken().getIssuedAt(),token.getToken().getExpiresAt(),token.getToken().getHeaders(),claimsMap);
					     JwtAuthenticationToken newAuthToken  =  new JwtAuthenticationToken(newJwtToken,token.getAuthorities());
					     
					     SecurityContextHolder.getContext().setAuthentication(newAuthToken);
					     /**  Replacing old claims with UserInfo details and setting in context  - end */	
						} else {
							logger.debug("Failed to fetch UserInfo details ");
						}
					} else {
						logger.debug("Failed to fetch UserInfo details ");
						
					}
				}  catch (Exception e) {
					logger.error("Error while fetching user info detail |", e);
					e.printStackTrace();
				}finally {
					if(null!= connection)
						connection.disconnect() ;
				}
				
	    
	        	
	        }
	        filterChain.doFilter(request, response);

		
	}

}
