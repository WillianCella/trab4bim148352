// Classe PessoaRepository
package br.cella.repository;

import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import br.cella.model.PessoaModel;
import br.cella.repository.entity.PessoaEntity;
import br.cella.repository.entity.UsuarioEntity;
import br.cella.uteis.Uteis;

/**
 * Essa classe é responsável para a realização da persistêcia pessoa na
 * PessoaEntity
 * 
 * @author Willian Cella - 14 de nov de 2016 - 21:40:46
 *
 */
public class PessoaRepository {

	@Inject
	PessoaEntity pessoaEntity;
	EntityManager entityManager;

	/***
	 * @param pessoaModel a pessoa na tabela
	 */
	// Método para salvar a pessoa na entidade PessoaEntity
	public void SalvarNovoRegistro(PessoaModel pessoaModel) {

		entityManager = Uteis.JpaEntityManager();
		// Instancia a PessoaEntity
		pessoaEntity = new PessoaEntity();
		// Seta os valores dos atributos
		pessoaEntity.setDataCadastro(LocalDateTime.now());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setOrigemCadastro(pessoaModel.getOrigemCadastro());
		pessoaEntity.setSexo(pessoaModel.getSexo());
		// Busca o código do usuário que está realizando o cadastro da Pessoa
		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class,
				pessoaModel.getUsuarioModel().getCodigo());
		// Persiste o usuário que realizou o cadastro
		pessoaEntity.setUsuarioEntity(usuarioEntity);
		// Chamada do método para a persistencia da Pessoa
		entityManager.persist(pessoaEntity);

	}
}