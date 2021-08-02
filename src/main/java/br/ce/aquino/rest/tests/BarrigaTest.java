package br.ce.aquino.rest.tests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.is;



import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;


import java.util.HashMap;
import java.util.Map;

import br.ce.aquino.rest.core.BaseTest;
import br.ce.aquino.rest.utils.DataUtils;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarrigaTest extends BaseTest {
	
	
	private static Integer CONTA_ID;
	private static Integer MOV_ID;
	private static String CONTA_NAME="Conta" +System.nanoTime();
	

	
	/*
	//nao conseguirá logar sem autenticacao e login
	@Test
	public void t01_naoDeveAceitarAPISemToken() {
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
			;
	}
	*/
	
	
	
	@Test
	public void t05_deveInserirUmaMovimentacaoComSucesso() {
		
		Movimentacao mov = getMovimentacaoValida();
		
		
		
		MOV_ID=given()
			//.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			.extract().path("id");
	}
	
	
	@Test
	public void t06_deveValidarCamposObrigatoriosMovimentacao() {
		given()
			//.header("Authorization", "JWT " + TOKEN)
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg",hasItems(
					"Data da Movimentação é obrigatório",
					"Data do pagamento é obrigatório",
					"Descrição é obrigatório",
					"Interessado é obrigatório",
					"Valor é obrigatório",
					"Valor deve ser um número",
					"Conta é obrigatório",
					"Situação é obrigatório"
					));
		
	}
	
	@Test
	public void t07_naoDeveInserirMovimentacaoComDataFutura() {
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao(DataUtils.getDataDiferencaDias(2));
		//retornará a data do dia atual+ 2
		
		given()
			//.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(1))
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		;
	}
	
	@Test
	public void t08_naoDeveRemoverContaComMovimentacao() {
		given()
			//.header("Authorization", "JWT " + TOKEN)
			.pathParam("id", CONTA_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint",is("transacoes_conta_id_foreign"));
	}
	
	
	//Checa se o valor adicionado no sistema é realmente 400
	@Test
	public void t09_deveCalcularSaldoDasContas() {
		given()
			//.header("Authorization", "JWT " + TOKEN)
		.when()
			.get("/saldo")
		.then()
		.statusCode(200)
		.body("find{it.conta_id=="+CONTA_ID+"}.saldo", is("500.00"))
		;
	}
	
	@Test
	public void t10_DeveRemoverMovimentacao() {
	given()
		//.header("Authorization", "JWT " + TOKEN)
		.pathParam("id", MOV_ID)
	.when()
		.delete("/transacoes/{id}")
	.then()
		.statusCode(204)
		//.body("find{it.conta_id==659172}.saldo", is("200.00"))
	;
		
	}
	
	
	@Test
	public void t11_naoDeveAceitarAPISemToken() {
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
	
		
	private Movimentacao getMovimentacaoValida() {
		
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(CONTA_ID);
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
	
	
	
	
	
	
	
	


