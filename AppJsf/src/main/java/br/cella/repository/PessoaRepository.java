// Classe PessoaRepository
package br.cella.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
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
		// Buscando todas as pessoas no banco de dados
		Query query = entityManager.createNamedQuery("PessoaEntity.findAll");

		// Organizando a lista de pessoas por resultado de busca
		@SuppressWarnings("unchecked")
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>) query.getResultList();
		PessoaModel pessoaModel = null;

		// Varredura para setar todas as Pessoas em uma lista
		for (PessoaEntity pessoaEntity : pessoasEntity) {

			// Setando os atributos de pessoa em seus respectivos campos
			pessoaModel = new PessoaModel();
			pessoaModel.setCodigo(pessoaEntity.getCodigo());
			pessoaModel.setDataCadastro(pessoaEntity.getDataCadastro());
			pessoaModel.setEmail(pessoaEntity.getEmail());
			pessoaModel.setEndereco(pessoaEntity.getEndereco());
			pessoaModel.setNome(pessoaEntity.getNome());

			// Se a origem de cadastro for "x" então significa que veio por xml
			if (pessoaEntity.getOrigemCadastro().equals("X"))
				pessoaModel.setOrigemCadastro("XML");
			// Se não for "x" então, significa que veio por input
			else
				pessoaModel.setOrigemCadastro("INPUT");

			// Se o sexo for "m" então é masculino
			if (pessoaEntity.getSexo().equals("M"))
				pessoaModel.setSexo("Masculino");
			// Se não, então é feminino
			else
				pessoaModel.setSexo("Feminino");

			// Recupera o usuário
			UsuarioEntity usuarioEntity = pessoaEntity.getUsuarioEntity();
			UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setUsuario(usuarioEntity.getUsuario());
			pessoaModel.setUsuarioModel(usuarioModel);
			// Adicionando o modelo de pessoa na lista
			pessoasModel.add(pessoaModel);
		}

		// Retorna a lista com todas as pessoas cadastrados no banco de dados
		return pessoasModel;

	}

	// Consulta um Pessoa através do ocódigo
	private PessoaEntity GetPessoa(int codigo) {
		entityManager = Uteis.JpaEntityManager();
		return entityManager.find(PessoaEntity.class, codigo);
	}

	// Altera uma Pessoa no banco de dados
	public void AlterarRegistro(PessoaModel pessoaModel) {

		entityManager = Uteis.JpaEntityManager();

		PessoaEntity pessoaEntity = this.GetPessoa(pessoaModel.getCodigo());

		// Seta as novas informações em cada atribudo
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setSexo(pessoaModel.getSexo());

		// Realiza o merge para juntar as informações
		entityManager.merge(pessoaEntity);
	}

	// Exclui uma Pessoa no banco de dados a partir do código da Pessoa
	public void ExcluirRegistro(int codigo) {
		entityManager = Uteis.JpaEntityManager();
		PessoaEntity pessoaEntity = this.GetPessoa(codigo);
		// Removendo a Pessoa da entidade
		entityManager.remove(pessoaEntity);
	}

	// Esse método retona as pessoas agrupadas por origem de cadastro
	public Hashtable<String, Integer> GetOrigemPessoa() {
		Hashtable<String, Integer> hashtableRegistros = new Hashtable<String, Integer>();

		// Query para buscar as pessoas no banco ordenado por origem de cadastro
		entityManager = Uteis.JpaEntityManager();
		Query query = entityManager.createNamedQuery("PessoaEntity.GroupByOrigemCadastro");

		// Ordena por resultado da query
		@SuppressWarnings("unchecked")
		Collection<Object[]> collectionRegistros = (Collection<Object[]>) query.getResultList();

		// Varre todos os registros para saber qual o tipo de cadastro que foi
		// realizado
		for (Object[] objects : collectionRegistros) {
			String tipoPessoa = (String) objects[0];
			int totalDeRegistros = ((Number) objects[1]).intValue();
			if (tipoPessoa.equals("X"))
				tipoPessoa = "XML";
			else
				tipoPessoa = "INPUT";
			hashtableRegistros.put(tipoPessoa, totalDeRegistros);
		}
		// Retorna o HashTable de registros
		return hashtableRegistros;
	}
}