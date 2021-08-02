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

public class SaldoTest extends BaseTest {

	


	
	//Checa se o valor adicionado no sistema é realmente 400
	@Test
	public void deveCalcularSaldoDasContas() {
		Integer CONTA_ID= BarrigaUtils.getIdContaPeloNome("Conta para saldo"); 
		
		given()
			//.header("Authorization", "JWT " + TOKEN)
		.when()
			.get("/saldo")
		.then()
		.statusCode(200)
		.body("find{it.conta_id=="+CONTA_ID+"}.saldo", is("534.00"))
		;
	}
	
	
	
	//public Integer getIdContaPeloNome(String nome) {
		//return RestAssured.get("/contas?nome="+nome).then().extract().path("id[0]");
	//}

}
