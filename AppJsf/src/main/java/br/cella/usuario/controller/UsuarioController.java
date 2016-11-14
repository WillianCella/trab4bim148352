//Classe UsuarioController com as instancias das classes UsuarioModel, UsuarioRepository, UsuarioEntity
//Possui os métodos para EfetuarLogin e fazer Logout
package br.cella.usuario.controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import br.cella.model.UsuarioModel;
import br.cella.repository.UsuarioRepository;
import br.cella.repository.entity.UsuarioEntity;
import br.cella.uteis.Uteis;

/**
 * Essa classe é um Bean gerenciado pelo CDI com escopo SessionScoped, as
 * instancias das classes UsuarioModel, UsuarioRepository e UsuarioEntity
 * possuem anotação @Inject onde são injetadas dependências nas Classes. Possue
 * também, os métodos get e set do UsuarioModel e também o método get do
 * UsuarioSession.
 * 
 * Possui todo o controle da realização do Login e também um método para fazer o
 * Logout da aplicação
 * 
 * @author Willian Cella - 14 de nov de 2016 - 18:34:12
 */

@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Injeção de dependência na classe UsuarioModel
	@Inject
	private UsuarioModel usuarioModel;

	// Injeção de dependência na classe UsuarioRepository
	@Inject
	private UsuarioRepository usuarioRepository;

	// Injeção de dependência na classe UsuarioEntity
	@Inject
	private UsuarioEntity usuarioEntity;

	// Busca o usuário autenticado atual
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	// retorna o usuário autenticado atual
	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	// Retorna o usuário logado no sistema
	public UsuarioModel GetUsuarioSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (UsuarioModel) facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	// Realiza o Logout da aplicação e redireciona para a página index.xhtml,
	// que é a página de login
	public String Logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	// Controle para a realização do Login
	public String EfetuarLogin() {
		// Se o campo de login for vazio, retorna uma mensagem para informar o
		// login e retorna null
		if (StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())) {
			Uteis.Mensagem("Favor informar o login!");
			return null;
			// Se o campo senha for vazio, retorna uma mensagem para informar a
			// senha e retorna null
		} else if (StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())) {
			Uteis.Mensagem("Favor informara senha!");
			return null;

		} else {
			// usuarioEntity recebe o usuário autenticado
			usuarioEntity = usuarioRepository.ValidaUsuario(usuarioModel);

			// Se o usuarioEntity for nulo, seta a senha como nula e o código
			// como código da entidade
			if (usuarioEntity != null) {
				usuarioModel.setSenha(null);
				usuarioModel.setCodigo(usuarioEntity.getCodigo());

				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);

				// Se passas por todos os testes, o usuário vai ser
				// redirecionado para a página home
				return "sistema/home?faces-redirect=true";

				// Caso os dados informados forem errados ou inexistentes,
				// retorna uma mensagem
			} else {
				Uteis.Mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}

	}

}