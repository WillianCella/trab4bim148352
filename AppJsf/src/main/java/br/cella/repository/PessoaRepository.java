// Classe PessoaRepository
package br.cella.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.cella.model.PessoaModel;
import br.cella.model.UsuarioModel;
import br.cella.repository.entity.PessoaEntity;
import br.cella.repository.entity.UsuarioEntity;
import br.cella.uteis.Uteis;

/**
 * Essa classe é responsável para a realização da persistêcia pessoa na
 * PessoaEntity e Consulta de Pessoa no banco de dados
 * 
 * @author Willian Cella - 14 de nov de 2016 - 21:40:46
 *
 */
public class PessoaRepository {

	@Inject
	PessoaEntity pessoaEntity;
	EntityManager entityManager;

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

	// Método que realiza a consulta de Pessoa
	public List<PessoaModel> GetPessoas() {
		List<PessoaModel> pessoasModel = new ArrayList<PessoaModel>();
		entityManager = Uteis.JpaEntityManager();
		Query query = entityManager.createNamedQuery("PessoaEntity.findAll");

		@SuppressWarnings("unchecked")
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>) query.getResultList();

		PessoaModel pessoaModel = null;

		for (PessoaEntity pessoaEntity : pessoasEntity) {

			pessoaModel = new PessoaModel();
			pessoaModel.setCodigo(pessoaEntity.getCodigo());
			pessoaModel.setDataCadastro(pessoaEntity.getDataCadastro());
			pessoaModel.setEmail(pessoaEntity.getEmail());
			pessoaModel.setEndereco(pessoaEntity.getEndereco());
			pessoaModel.setNome(pessoaEntity.getNome());

			if (pessoaEntity.getOrigemCadastro().equals("X"))
				pessoaModel.setOrigemCadastro("XML");
			else
				pessoaModel.setOrigemCadastro("INPUT");

			if (pessoaEntity.getSexo().equals("M"))
				pessoaModel.setSexo("Masculino");
			else
				pessoaModel.setSexo("Feminino");

			UsuarioEntity usuarioEntity = pessoaEntity.getUsuarioEntity();

			UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setUsuario(usuarioEntity.getUsuario());

			pessoaModel.setUsuarioModel(usuarioModel);

			pessoasModel.add(pessoaModel);
		}

		return pessoasModel;

	}
}