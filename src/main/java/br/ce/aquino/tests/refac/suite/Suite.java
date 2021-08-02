package br.ce.aquino.tests.refac.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.aquino.rest.core.BaseTest;
import br.ce.aquino.rest.tests.Movimentacao;
import br.ce.aquino.tests.refac.AuthTest;
import br.ce.aquino.tests.refac.ContasTest;
import br.ce.aquino.tests.refac.MovimentacaoTest;
import br.ce.aquino.tests.refac.SaldoTest;
import io.restassured.RestAssured;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacaoTest.class,
	SaldoTest.class,
	AuthTest.class
	
})


public class Suite extends BaseTest {
	
	@BeforeClass
	public static void login() {
	Map<String,String> login= new HashMap<>();
	
	//site para criar cadastro https://seubarriga.wcaquino.me/logar
	login.put("email", "jeanfelippe300@gmail.com");
	login.put("senha", "123456");


	String TOKEN = given()
			.body(login)
		.when()
				.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
	
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
		
		RestAssured.get("/reset").then().statusCode(200);
	}

	
	

}
