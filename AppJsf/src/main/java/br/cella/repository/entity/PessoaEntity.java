// Classe PessoaEntity com os métodos Getters e Setters
package br.cella.repository.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Essa classe é uma entidade Pessoa, onde a partir dela os dados de Pessoa será
 * persistida no banco de dados
 * 
 * @author Willian Cella - 14 de nov de 2016 - 21:25:15
 */

/*
 * Simples POJO com indicação que essa classe é um entidade do banco. A tabela
 * do banco para essa classe será: "tb_pessoa" Cada atributo vai ter uma coluna
 * da tabela "tb_pessoa" O ID vai ter um Identificador gerado automaticamente
 */
@Entity
@Table(name = "tb_pessoa")
// Retorna todos os registros cadastrado no banco de dados
@NamedQueries({ @NamedQuery(name = "PessoaEntity.findAll", query = "SELECT p FROM PessoaEntity p") })

public class PessoaEntity {

	@Id
	@GeneratedValue
	@Column(name = "id_pessoa")
	private Integer codigo;

	@Column(name = "nm_pessoa")
	private String nome;

	@Column(name = "fl_sexo")
	private String sexo;

	@Column(name = "dt_cadastro")
	private LocalDateTime dataCadastro;

	@Column(name = "ds_email")
	private String email;

	@Column(name = "ds_endereco")
	private String endereco;

	@Column(name = "fl_origemCadastro")
	private String origemCadastro;

	@OneToOne
	@JoinColumn(name = "id_usuario_cadastro")
	private UsuarioEntity usuarioEntity;

	// Método Get e Set do atributo codigo
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	// Método Get e Set do atributo nome
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// Método Get e Set do atributo sexo
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	// Método Get e Set do horário do cadastro da Pessoa
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	// Método Get e Set do atributo email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Método Get e Set do atributo endereco
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	// Método Get e Set do atributo OrigemCadastro
	public String getOrigemCadastro() {
		return origemCadastro;
	}

	public void setOrigemCadastro(String origemCadastro) {
		this.origemCadastro = origemCadastro;
	}

	// Método Get e Set do usuário que cadastrou a Pessoa
	public UsuarioEntity getUsuarioEntity() {
		return usuarioEntity;
	}

	public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}

}