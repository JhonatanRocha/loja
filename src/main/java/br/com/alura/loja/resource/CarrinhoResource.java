package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

import com.thoughtworks.xstream.XStream;

@Path("carrinhos")
public class CarrinhoResource {

	/**
	 * Exp�e um XML atr
	 * @param id
	 * @return
	 */
	@Path("{id}")
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id){
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		//return carrinho.toJSON();
		return carrinho.toXML();
	}
	
	
	/**
	 * Recebe um XML atrav�s de uma requisi��o pelo m�todo POST
	 * @param conteudo
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo){
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();
	}
}
