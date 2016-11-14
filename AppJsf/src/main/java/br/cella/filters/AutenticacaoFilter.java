//Classe AutenticacaoFilter	com as requisições HTTP
package br.cella.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.cella.model.UsuarioModel;

/**
 * Esse Filter vai realizar o filtro para todas as requisições que estiveram
 * dentro de /sistema/, vai verificar se o usuário tem o acesso a tais páginas,
 * se não tiver, vai ser redirecionado para a página de autenticação
 * 
 * @author Willian Cella - 14 de nov de 2016 - 20:05:42
 */

// Indica que o filtro vai ser realizado no diretório sistema
@WebFilter("/sistema/*")
public class AutenticacaoFilter implements Filter {

	// Construtor vazio!
	public AutenticacaoFilter() {
	}

	// Método destrutor
	public void destroy() {
	}

	// Filtro para as requisições HTTP
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Pega a sessão do navegador
		HttpSession httpSession = ((HttpServletRequest) request).getSession();
		// Pega a requisição do Servlet
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// Pega a resposta do Servlet
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if (httpServletRequest.getRequestURI().indexOf("index.xhtml") <= -1) {
			UsuarioModel usuarioModel = (UsuarioModel) httpSession.getAttribute("usuarioAutenticado");

			// Se o usuário não estiver logado e tentar acessar uma página
			// dentro do filtro, vai ser redirecionado para a página de login
			if (usuarioModel == null) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index.xhtml");

				// Se o usuário for autenticado, libera o acesso!
			} else {
				chain.doFilter(request, response);
			}

			// Se o usuário for autenticado, libera o acesso!
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}