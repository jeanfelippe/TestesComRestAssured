package br.ce.aquino.tests.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import br.ce.aquino.rest.core.BaseTest;
import br.ce.aquino.rest.tests.Movimentacao;
import br.ce.aquino.rest.utils.BarrigaUtils;
import br.ce.aquino.rest.utils.DataUtils;
import io.restassured.RestAssured;

public class MovimentacaoTest extends BaseTest {

	

	

	@Test
	public void deveInserirUmaMovimentacaoComSucesso() {
		
		Movimentacao mov = getMovimentacaoValida();
		
		
		
		given()
			//.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			;
	}
	
	@Test
	public void deveValidarCamposObrigatoriosMovimentacao() {
		given()
			//.header("Authorization", "JWT " + TOKEN)
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg",hasItems(
					"Data da Movimenta??o ? obrigat?rio",
					"Data do pagamento ? obrigat?rio",
					"Descri??o ? obrigat?rio",
					"Interessado ? obrigat?rio",
					"Valor ? obrigat?rio",
					"Valor deve ser um n?mero",
					"Conta ? obrigat?rio",
					"Situa??o ? obrigat?rio"
					));
		
	}

	
	
	
	
	
	@Test
	public void naoDeveInserirMovimentacaoComDataFutura() {
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao(DataUtils.getDataDiferencaDias(2));
		//retornar? a data do dia atual+ 2
		
		given()
			//.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(1))
			.body("msg", hasItem("Data da Movimenta??o deve ser menor ou igual ? data atual"))
		;
	}
	
	@Test
	public void naoDeveRemoverContaComMovimentacao() {
		Integer CONTA_ID=BarrigaUtils.getIdContaPeloNome("Conta com movimentacao");
		
		given()
			.pathParam("id",CONTA_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"));
		;
	}
	
	
	@Test
	public void deveRemoverMovimentacao() {
		Integer MOV_ID=BarrigaUtils.getIdMovPelaDescricao("Movimentacao para exclusao");
		
		given()
			.pathParam("id",MOV_ID)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
		;
	}
	
	
	
	
	
	
	
	
private Movimentacao getMovimentacaoValida() {
		
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(BarrigaUtils.getIdContaPeloNome("Conta para movimentacoes"));
		mov.setDescricao("Descricao da movimentacao");
		mov.setEnvolvido("Envolvido na mov");
		mov.setTipo("REC");
		mov.setData_transacao(DataUtils.getDataDiferencaDias(-1));
		mov.setData_pagamento(DataUtils.getDataDiferencaDias(5));
		mov.setValor(500f);
		mov.setStatus(true);
		return mov;
	}

}
