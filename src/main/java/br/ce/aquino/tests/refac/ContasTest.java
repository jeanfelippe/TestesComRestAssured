package br.ce.aquino.tests.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import br.ce.aquino.rest.core.BaseTest;
import br.ce.aquino.rest.utils.BarrigaUtils;
import io.restassured.RestAssured;

public class ContasTest extends BaseTest {

	

	

	//Caso queira executar apenas o metodo deveincluircontatocomsucesso, descomentar o bloco de cod referente a autenticacao
	//logar e incluir contato
	@Test
	public void deveIncluirContaComSucesso() {
		System.out.println("incluir");
		/*
				
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
		*/
		
		given()
			
			
			
			//.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"Conta inserida\" }")
			.contentType("application/json")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			;
		
		
		
	}
	
	//irá usar o id da conta criada que se encontra na url dentro do sistema "705923" e irá alterar 
	//irá alterar o titulo da conta para conta alterada
	@Test
	public void deveAlterarContaComSucesso() {
		Integer CONTA_ID=BarrigaUtils.getIdContaPeloNome("Conta para alterar");

		given()
			//.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"conta alterada\" }")
			.pathParam("id",CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome",is("conta alterada"));
		
	}
	
	
	
	@Test
	public void naoDeveIncluirContaComNomeRepetido () {
		given()
			//.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"Conta mesmo nome\" }")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error",is("Já existe uma conta com esse nome!"))
			;
		
	}
	
	

}
