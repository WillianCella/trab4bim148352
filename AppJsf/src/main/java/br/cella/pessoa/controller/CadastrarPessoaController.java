// Classe CadastrarPessoaController
package br.cella.pessoa.controller;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import br.cella.model.PessoaModel;
import br.cella.repository.PessoaRepository;
import br.cella.usuario.controller.UsuarioController;
import br.cella.uteis.Uteis;

/**
 * Essa classe é responsável por salvar a Pessoa no banco de dados
 * 
 * @author Willian Cella - 14 de nov de 2016 - 22:02:46
 */

// Classe gerenciada pelo CDI e com escopo do tipo @RequestScoped
// Possui os objetos com injeção de dependência
@Named(value = "cadastrarPessoaController")
@RequestScoped
public class CadastrarPessoaController {

	// Injeção de pessoaModel
	@Inject
	PessoaModel pessoaModel;

	// Injeção de usuarioController
	@Inject
	UsuarioController usuarioController;

	// Injeção de pessoaRepository
	@Inject
	PessoaRepository pessoaRepository;

	private UploadedFile file;

	// Get e Set de UploadFile
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	// Get e Set de PessoaModel
	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	// Salva uma nova pesso através do método input do HTTP
	public void SalvarNovaPessoa() {

		// Pega o usuário que está logado
		pessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
		// Informa a origem do cadastro, nesse caso, é I (Input)
		pessoaModel.setOrigemCadastro("I");
		// Salvando uma nova Pessoa
		pessoaRepository.SalvarNovoRegistro(this.pessoaModel);
		this.pessoaModel = null;
		// Mensagem de sucesso de cadastro
		Uteis.MensagemInfo("Registro cadastrado com sucesso");
	}

	// Método para realizar o upload de arquivos
	public void UploadRegistros() {
		// Instancia de factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			// Se o arquivo for vazio, vai retornar uma mensagem!
			if (this.file.getFileName().equals("")) {
				Uteis.MensagemAtencao("Nenhum arquivo selecionado!");
				return;
			}
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(this.file.getInputstream());
			Element element = doc.getDocumentElement();
			NodeList nodes = element.getChildNodes();
			// Varre todo o arquivo buscando cada Pessoa
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elementPessoa = (Element) node;
					// Pegando os atributos no arquivo xml
					String nome = elementPessoa.getElementsByTagName("nome").item(0).getChildNodes().item(0)
							.getNodeValue();
					String sexo = elementPessoa.getElementsByTagName("sexo").item(0).getChildNodes().item(0)
							.getNodeValue();
					String email = elementPessoa.getElementsByTagName("email").item(0).getChildNodes().item(0)
							.getNodeValue();
					String endereco = elementPessoa.getElementsByTagName("endereco").item(0).getChildNodes().item(0)
							.getNodeValue();
					// Instanciando uma nova pessoa
					PessoaModel newPessoaModel = new PessoaModel();
					// Setando cada atribudo
					newPessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
					newPessoaModel.setEmail(email);
					newPessoaModel.setEndereco(endereco);
					newPessoaModel.setNome(nome);
					newPessoaModel.setOrigemCadastro("X");
					newPessoaModel.setSexo(sexo);
					// Salvando um registro de pessoa que veio atrav´s de um
					// arquivo xml
					pessoaRepository.SalvarNovoRegistro(newPessoaModel);
				}
			}
			// Mensagem de sucesso na persistencia!
			Uteis.MensagemInfo("Registros cadastrados com sucesso!");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}