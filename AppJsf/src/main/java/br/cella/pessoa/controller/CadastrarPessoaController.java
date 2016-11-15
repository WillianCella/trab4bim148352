// Classe CadastrarPessoaController
package br.cella.pessoa.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.cella.model.PessoaModel;
import br.cella.repository.PessoaRepository;
import br.cella.usuario.controller.UsuarioController;
import br.cella.uteis.Uteis;

/**
 * Essa classe é responsável por salvar a Pessoa no banco de dados
 * 
 * @author Willian Cella - 14 de nov de 2016 - 22:02:46
 */

// Classe gerenciada pelo CDI e com escopo do tipo @RequestScoped
// Possui os objetos com injeção de dependência
@Named(value = "cadastrarPessoaController")
@RequestScoped
public class CadastrarPessoaController {

	// Injeção de pessoaModel
	@Inject
	PessoaModel pessoaModel;

	// Injeção de usuarioController
	@Inject
	UsuarioController usuarioController;

	// Injeção de pessoaRepository
	@Inject
	PessoaRepository pessoaRepository;

	// Get e Set de PessoaModel
	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	// Salva uma nova pesso através do método input do HTTP
	public void SalvarNovaPessoa() {

		// Pega o usuário que está logado
		pessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
		// Informa a origem do cadastro, nesse caso, é I (Input)
		pessoaModel.setOrigemCadastro("I");
		// Salvando uma nova Pessoa
		pessoaRepository.SalvarNovoRegistro(this.pessoaModel);
		this.pessoaModel = null;
		// Mensagem de sucesso de cadastro
		Uteis.MensagemInfo("Registro cadastrado com sucesso");
	}
}