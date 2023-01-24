package com.demo.ravioAuth2.DataObject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseData {
private String message;
private String name;
private List<String> grantedAuthorities;
private String vid;


public ResponseData() {
	super();
}

public ResponseData(String message, String name) {
	super();
	this.message = message;
	this.name = name;
}



public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public List<String> getGrantedAuthorities() {
	return grantedAuthorities;
}

public void setGrantedAuthorities(List<String> grantedAuthorities) {
	this.grantedAuthorities = grantedAuthorities;
}

public String getVid() {
	return vid;
}

public void setVid(String vid) {
	this.vid = vid;
}

@Override
public String toString() {
	return "ResponseData [message=" + message + ", name=" + name + ", grantedAuthorities=" + grantedAuthorities
			+ ", vid=" + vid + "]";
}

}
