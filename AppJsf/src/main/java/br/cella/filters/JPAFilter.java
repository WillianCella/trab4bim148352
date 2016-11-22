//Declaração da classe JPAFilter com o @WebFilter
package br.cella.filters;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Esse Filter vai ser chamado toda vez que for realizado uma chamada para o
 * FacesServlet. Esse Servlet, gerencia o ciclo de vida de processamento de um
 * web application que utiliza JSF
 * 
 * @author Willian Cella - 8 de nov de 2016 - 20:30:16
 */
@WebFilter(servletNames = { "Faces Servlet" })
public class JPAFilter implements Filter {

	private EntityManagerFactory entityManagerFactory;
	private String persistence_unit_name = "unit_app";

	/* Esse método fecha o entityManager antes de iniciar o filtro */
	public void destroy() {
		this.entityManagerFactory.close();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/* Instanciando um EntityManager */
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();

		/* Setando o EntityManager na requisição */
		request.setAttribute("entityManager", entityManager);

		/* Iniciando uma transação */
		entityManager.getTransaction().begin();

		/* Iniciando Faces Servlet */
		chain.doFilter(request, response);

		try {

			/* Se não houver erro na operação, vai ser executado um commit */
			entityManager.getTransaction().commit();

		} catch (Exception e) {

			/*
			 * Caso ocorra algum erro, vai ser feito um rollback para voltar ao
			 * estado anterior
			 */
			entityManager.getTransaction().rollback();
		} finally {

			/*
			 * Depois de realizar uma das operações a cima, o EntityManager será
			 * fechado
			 */
			entityManager.close();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

		/*
		 * Cria o entityManagerFactory com os parâmetros definidos no arquivo de
		 * persistência persistence.xml
		 */
		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistence_unit_name);
	}

}