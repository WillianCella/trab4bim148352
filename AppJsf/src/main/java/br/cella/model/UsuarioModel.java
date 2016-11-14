//Classe UsuarioModel com os getters e setters de cada atributo
package br.cella.model;

import java.io.Serializable;

/**
 * Essa classe serializada é um simples POJO que vai conter os dados do usuário
 * 
 * @author Willian Cella - 8 de nov de 2016 - 21:13:58
 */

public class UsuarioModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String usuario;
	private String senha;

	// Métodos Getters e Setters do atributo codigo
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	// Métodos Getters e Setters do atributo usuario
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	// Métodos Getters e Setters do atributo senha
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}