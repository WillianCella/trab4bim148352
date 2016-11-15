// Classe ConsultarPessoaCarouselController
package br.cella.pessoa.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.cella.model.PessoaModel;
import br.cella.repository.PessoaRepository;

/**
 * Essa classe Realiza a consulta de Pessoas utilizando Carousel, que mostra as
 * pessoas por coluna com todos os dados cadastrados
 * 
 * @author Willian Cella - 15 de nov de 2016 - 16:05:49
 */

// Essa classe é um Bean gerenciado pelo CDI com escopo do tipo @ViewScoped
@Named(value = "consultarPessoaCarouselController")
@ViewScoped
public class ConsultarPessoaCarouselController implements Serializable {
	private static final long serialVersionUID = 1L;

	// Injeção de PessoaRepository
	@Inject
	transient private PessoaRepository pessoaRepository;

	// Declaração de uma lista de pessoas
	@Produces
	private List<PessoaModel> pessoas;

	// Instancia de uma lista de Pessoas
	public List<PessoaModel> getPessoas() {
		return pessoas;
	}

	// Depois de construir a aplicação, pessoas recebe todas as pessoas do Banco
	// de dados
	@PostConstruct
	private void init() {
		this.pessoas = pessoaRepository.GetPessoas();
	}

}