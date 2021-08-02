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

	

	

	//A classe ContasTest possui o before class e o token de login
	//logar e incluir contato
	@Test
	public void deveIncluirContaComSucesso() {
		System.out.println("incluir");
		given()
			//.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"Conta inserida\" }")
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
