// Classe GraficoPizzaPessoaController
package br.cella.pessoa.controller;

import java.util.Hashtable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;
import br.cella.repository.PessoaRepository;

/**
 * Essa Classe é responsável por montar gráfico pizza na tela, ele busca os
 * dados do banco e monta o gráfico
 * 
 * @author Willian Cella - 15 de nov de 2016 - 16:55:15
 */

// Bean gerenciado pelo CDI com escopo do tipo @RequestScoped
@Named(value = "graficoPizzaPessoaController")
@RequestScoped
public class GraficoPizzaPessoaController {

	// Injeção de PessoaRepository
	@Inject
	private PessoaRepository pessoaRepository;

	// Instancia do Objeto pieChartModel
	private PieChartModel pieChartModel;

	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}

	// Após construir a classe, vai ser montado o gráfico
	@PostConstruct
	public void init() {
		this.pieChartModel = new PieChartModel();
		this.MontaGrafico();
	}

	// Método para montar o gráfico na página
	private void MontaGrafico() {
		// Traz os dados para realizar a monstagem do gráfico
		Hashtable<String, Integer> hashtableRegistros = pessoaRepository.GetOrigemPessoa();
		// Informa os valores para montar o gráfico
		hashtableRegistros.forEach((chave, valor) -> {
			pieChartModel.set(chave, valor);
		});
		// Seta os valores do gráfico
		pieChartModel.setTitle("Total de Pessoas cadastrado por Tipo");
		pieChartModel.setShowDataLabels(true);
		pieChartModel.setLegendPosition("e");

	}
}