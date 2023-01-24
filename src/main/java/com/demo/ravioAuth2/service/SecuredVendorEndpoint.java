package com.demo.ravioAuth2.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ravioAuth2.DataObject.ResponseData;
import com.demo.ravioAuth2.exception.CustomAppException;

@RestController
@RequestMapping("/manageVendor")
@PreAuthorize("isAuthenticated()")
public class SecuredVendorEndpoint {
	private static final String vendor_id = "custom:VID";
	
	@Value("${vendor.unauth.err}")
	private String vendorErrorMessage;
	
	@GetMapping("/authenticated")
	public ResponseEntity<ResponseData> processAuthenticateUser(JwtAuthenticationToken auth) {
		try {
			ResponseData resp  = new ResponseData();
			resp.setMessage("Looks like you have been authenticated");
			resp.setName((String) auth.getTokenAttributes().getOrDefault(StandardClaimNames.GIVEN_NAME, " ")+ " " + (String)auth.getTokenAttributes().getOrDefault(StandardClaimNames.FAMILY_NAME, ""));
			resp.setGrantedAuthorities(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			throw new CustomAppException(e.getMessage(), e);
		}
	}
	
	@GetMapping("/vendors")
	public ResponseEntity<ResponseData> processAuthenticateVendor(JwtAuthenticationToken auth) {
		try {
			ResponseData resp  = new ResponseData();
			resp.setMessage("Welcome to the vendor group!”");
			resp.setName((String) auth.getTokenAttributes().getOrDefault(StandardClaimNames.GIVEN_NAME, " ")+ " " + (String)auth.getTokenAttributes().getOrDefault(StandardClaimNames.FAMILY_NAME, ""));
			resp.setGrantedAuthorities(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			throw new CustomAppException(e.getMessage(), e);
		}
	}
	
	
	@GetMapping("/vendors/{vid}")
	public ResponseEntity<?> manageVendorsByID(JwtAuthenticationToken auth,@PathVariable String vid) {
		//auth.getTokenAttributes().forEach((k, v) -> System.out.println((k + ":" + v)));
		if(vid.equalsIgnoreCase((String)auth.getTokenAttributes().getOrDefault(vendor_id, ""))) {
		
		try {
			ResponseData resp  = new ResponseData();
			resp.setMessage("Welcome to the vendor group!”");
			resp.setName((String) auth.getTokenAttributes().getOrDefault(StandardClaimNames.GIVEN_NAME, " ")+ " " + (String)auth.getTokenAttributes().getOrDefault(StandardClaimNames.FAMILY_NAME, ""));
			resp.setGrantedAuthorities(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
			resp.setVid(vid);
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			throw new CustomAppException(e.getMessage(), e);
		}
		}else{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(vendorErrorMessage);
			
		}
	}

}
