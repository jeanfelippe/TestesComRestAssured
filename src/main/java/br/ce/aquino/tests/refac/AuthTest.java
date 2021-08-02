package br.ce.aquino.tests.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import br.ce.aquino.rest.core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTest extends BaseTest {

	
	
	

	@Test
	public void naoDeveAceitarAPISemToken() {
		FilterableRequestSpecification req= (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		//removeheader para remover a autenticacao login e deixar o metodo realizar a tentativa de acesso
		//sem o token de login 
		
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
			;
	}
	
	
	

}
