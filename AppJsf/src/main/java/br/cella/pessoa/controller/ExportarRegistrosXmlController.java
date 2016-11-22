// Classe ExportarRegistrosXmlController
package br.cella.pessoa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import br.cella.model.PessoaModel;
import br.cella.repository.PessoaRepository;

/**
 * Essa classe é responsável por fazer a exportação de Pessoas em arquivo XML
 * 
 * @author Willian Cella - 15 de nov de 2016 - 17:08:26
 */

// Bean gerenciado pelo CDI com escopo do tipo @RequestScoped
@Named(value = "exportarRegistrosXmlController")
@RequestScoped
public class ExportarRegistrosXmlController implements Serializable {
	private static final long serialVersionUID = 1L;

	// Injeção de PessoaRepository
	@Inject
	transient PessoaRepository pessoaRepository;

	private StreamedContent arquivoDownload;

	// Aqui é realizado o Download do arquivo XML
	public StreamedContent getArquivoDownload() {
		this.DownlaodArquivoXmlPessoa();
		return arquivoDownload;
	}

	// Aqui o sistema gera o arquivo XML
	private File GerarXmlPessoas() {

		// Formatação da data para ser mostrada no arquivo XML
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		// Instancia de Lista de pessoa
		List<PessoaModel> pessoasModel = pessoaRepository.GetPessoas();
		// Raiz do arquivo XML
		Element elementPessoas = new Element("Pessoas");
		// Noco documento de Pessoa
		Document documentoPessoas = new Document(elementPessoas);

		// Varre o modelo de pessoa adicionando todas as tags XML necessárias
		// para a exportação
		pessoasModel.forEach(pessoa -> {

			// Adicionando as tags XML
			Element elementPessoa = new Element("pessoa");
			elementPessoa.addContent(new Element("codigo").setText(pessoa.getCodigo().toString()));
			elementPessoa.addContent(new Element("nome").setText(pessoa.getNome()));
			elementPessoa.addContent(new Element("sexo").setText(pessoa.getSexo().substring(0,1)));

			// Formatando a data que vem do banco de dados
			String dataCadastroFormatada = pessoa.getDataCadastro().format(dateTimeFormatter);

			// Adicionando o conteúdo do banco em formato XML
			elementPessoa.addContent(new Element("dataCadastro").setText(dataCadastroFormatada));
			elementPessoa.addContent(new Element("email").setText(pessoa.getEmail()));
			elementPessoa.addContent(new Element("endereco").setText(pessoa.getEndereco()));
			elementPessoa.addContent(new Element("origemCadastro").setText(pessoa.getOrigemCadastro()));
			elementPessoa.addContent(new Element("usuarioCadastro").setText(pessoa.getUsuarioModel().getUsuario()));
			// Adicionando os elementos
			elementPessoas.addContent(elementPessoa);
		});

		XMLOutputter xmlGerado = new XMLOutputter();

		try {

			// Gerando o nome do arquivo
			String nomeArquivo = "pessoas_".concat(java.util.UUID.randomUUID().toString()).concat(".xml");

			// Diretório que vai ser gerado o arquivo XML
			File arquivo = new File("C:\\Users\\Willian\\Desktop\\".concat(nomeArquivo));
			FileWriter fileWriter = new FileWriter(arquivo);
			xmlGerado.output(documentoPessoas, fileWriter);
			// Retorna o arquivo XML. O arquivo está pronto!
			return arquivo;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// Método para realizar o download do arquivo XML
	public void DownlaodArquivoXmlPessoa() {

		File arquivoXml = this.GerarXmlPessoas();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(arquivoXml.getPath());
			arquivoDownload = new DefaultStreamedContent(inputStream, "application/xml", arquivoXml.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}