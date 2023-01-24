package com.demo.ravioAuth2.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ravioAuth2.DataObject.ResponseData;

@RestController
@RequestMapping("/generalTopic")
public class PublicEndpoint {
	
	@GetMapping("/unauthenticated")
	public ResponseEntity<ResponseData> processAuthenticateUser() {
		ResponseData resp  = new ResponseData();
		resp.setMessage("Free for all to see");
		resp.setName("Anonymous");
		System.out.println("resp");
		return ResponseEntity.ok(resp);
	}

}
