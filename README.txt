A classe BarrigaTest possui todos os testes de API da aplicação 

Ao executar a Classe Suite, as classes  AuthTest, ContasTest, MovimentacaoTest, SaldoTest serão executadas

Material usado como Estudos em Rest Assured baseado no conteúdo lecionado por Wagner Aquino na Udemy




Caso queira executar cada teste(método) separadamente, incluir o token de autenticação e inserir no given o contentyType desejado e descomentar o header,
como no exemplo abaixo:



@Test
	public void deveIncluirContaComSucesso() {
		System.out.println("incluir");
		
		// trecho com a autenticação
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
		
		given()
		
			.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"Conta nova\" }")
			.contentType("application/json")
			//Necessário o ContentType para funcionar
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			;
		
		
		
	}
