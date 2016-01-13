package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

public class ProjetoResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(){
		Projeto projeto = new ProjetoDAO().busca(1l);
		return projeto.toXML();
	}
}
