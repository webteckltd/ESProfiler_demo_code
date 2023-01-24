package com.demo.ravioAuth2.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;


public class CognitoAccessTokenConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
	private static final String COGNITO_GROUPS = "cognito:groups";
	private static final String COGNITO_SCOPE = "scope";

	 @Override
	    public Collection<GrantedAuthority> convert(Jwt jwtToken) {
		 System.out.println("Test inside CognitoAccessTokenConverter.convert()");
		 final Collection<String> groups = (Collection<String>) jwtToken.getClaims().getOrDefault(COGNITO_GROUPS,List.of());
		 final String cognitoScopeLust =   (String)jwtToken.getClaims().getOrDefault(COGNITO_SCOPE,"");
		 Collection<GrantedAuthority> scopeAuthority = Stream.of(cognitoScopeLust.split(" ")).filter(scope -> scope.length() >0 ).map(scopeName -> "SCOPE_" + scopeName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		 Collection<GrantedAuthority> groupAuthority = groups.stream().filter(group -> group.length() >0 ).map(roleName ->"CGROUP_" + roleName) .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		 return Stream.concat(scopeAuthority.stream(),groupAuthority.stream()).collect(Collectors.toList());
	    }
}