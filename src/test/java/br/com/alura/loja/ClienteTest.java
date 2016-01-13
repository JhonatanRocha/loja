package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import junit.framework.Assert;

import org.junit.Test;

public class ClienteTest {

	@Test
	public void testConnection(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://www.mocky.io");
		String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
		//System.out.println(conteudo);
		Assert.assertTrue(conteudo.contains("Rua Vergueiro"));
	}
	
	@Test
	public void testProjetoConnection(){
		
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String conteudo = target.path("/projetos").request().get(String.class);
        Assert.assertTrue(conteudo.contains("<nome>Minha loja"));
	}
}
