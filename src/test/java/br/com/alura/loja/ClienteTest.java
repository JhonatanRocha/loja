package br.com.alura.loja;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;

import com.thoughtworks.xstream.XStream;

public class ClienteTest {

	private HttpServer server;
	
	private WebTarget getWebTarget() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		return target;
	}
	
	/**
	 *Executado antes dos testes 
	 */
	@Before
	public void startupServer(){
		server = Servidor.startupServer();
	}
	
	/**
	 *Executado após os testes 
	 */
	@After
	public void shutdownServer(){
		server.stop();
	}
	
	@Test
	public void testCarrinhoGET(){
		
		WebTarget target = getWebTarget();
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		//System.out.println(conteudo);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		
		assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}

	
	@Test
	public void testProjetoGET(){
		
		WebTarget target = getWebTarget();
        String conteudo = target.path("/projetos/1").request().get(String.class);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);

        assertEquals(1l, projeto.getId());
        assertEquals("Minha loja", projeto.getNome());
	}
	
	@Test
	public void testCarrinhoPOST(){
		Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
       
        String xml = carrinho.toXML();
        
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Response response = getWebTarget().path("/carrinhos").request().post(entity);
        
        assertEquals("<status>sucesso</status>", response.readEntity(String.class));
	}
	
	@Test
	public void testProjetoPOST(){
		Projeto projeto = new Projeto(3l, "Projeto Test", 2016);
		
		String xml = projeto.toXML();
		
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = getWebTarget().path("/projetos").request().post(entity);
		assertEquals("<status>sucesso</status>", response.readEntity(String.class));
	}
}
