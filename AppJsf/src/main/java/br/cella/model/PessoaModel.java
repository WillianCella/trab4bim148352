//Classe PessoaModel com os métodos Getters e Setters dos atributos de Pessoa
package br.cella.model;

import java.time.LocalDateTime;

/**
 * Essa classe vai receber as informações que estão na página de cadastro de
 * pessoa
 * 
 * @author Willian Cella - 14 de nov de 2016 - 21:31:01
 *
 */

// Simples POJO
public class PessoaModel {

	private Integer codigo;
	private String nome;
	private String sexo;
	private LocalDateTime dataCadastro;
	private String email;
	private String endereco;
	private String origemCadastro;
	private UsuarioModel usuarioModel;

	// Métodos Getters e Setters do atributo codigo
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	// Métodos Getters e Setters do atributo nome
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	//// Métodos Getters e Setters do atributo sexo
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
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

}