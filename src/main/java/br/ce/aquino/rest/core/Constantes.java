package br.ce.aquino.rest.core;

import io.restassured.http.ContentType;

public interface Constantes {

	String APP_BASE_URL="http://barrigarest.wcaquino.me";
	//site https://seubarriga.wcaquino.me/logar
	//String APP_BASE_URL="http://srbarriga.herokuapp.com/login";
	Integer APP_PORT=80; 
	String APP_BASE_PATH="";
	
	ContentType APP_CONTENT_TYPE= ContentType.JSON;
	
	long MAX_TIMEOUT=5000L;
	
}
