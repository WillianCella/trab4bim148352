//Classe UsuarioRepository com o método ValidaUsuario
package br.cella.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.cella.model.UsuarioModel;
import br.cella.repository.entity.UsuarioEntity;
import br.cella.uteis.Uteis;

/**
 * Essa classe vai passar o usuário e a senha para a classe UsuariEntity através
 * dos parâmetros usuario e senha e retornar o usuário caso ele exista no banco
 * 
 * @author Willian Cella - 14 de nov de 2016 - 15:41:21
 */

public class UsuarioRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	public UsuarioEntity ValidaUsuario(UsuarioModel usuarioModel) {
		try {
			// Query que vai ser executada (UsuarioEntity.findUser)
			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");
			// Parâmetros da Query
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha());
			// Vai retornar o usuário se ele foi localizado no banco de dados
			return (UsuarioEntity) query.getSingleResult();
		} catch (Exception e) {
			// Se der alguma exção, vai retornar nulo
			return null;
		}

	}
}