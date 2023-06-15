import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

public class Loja {
	private static Map<String, Conta> clientes = new HashMap<>();

	public static void main(String[] args) {

		boolean finalizar = false;
		int userInput;
		int pagina_sessao = 1;
		String current_user = "";

		clientes.put("dani", new ContaStandard("1234"));
		clientes.put("admin", new ContaPremium("admin"));

		try (Scanner scanner = new Scanner(System.in)) {
			List<Produto> produtos_loja = new ArrayList<>();
			List<Produto> carrinho = new ArrayList<>();
			Map<String, Map<String, Object>> produtos = FileHandler
					.lerCSV("C:\\Users\\daniela\\eclipse-workspace\\POO_PJBL\\src\\somativa_ra3\\estoque_inicial.csv");

			// Carregar o estoque da loja - read from csv file
			for (Map<String, Object> produto : produtos.values()) {
				String nome = (String) produto.get("nome");
				int estoque = (int) produto.get("estoque");
				double preco = (double) produto.get("preco");
				String cor = (String) produto.get("cor");
				String categoria = (String) produto.get("categoria");

				Produto item = new Produto(nome, estoque, preco, cor, categoria);
				produtos_loja.add(item);
			}

			do {
				userInput = menu(pagina_sessao, scanner);

				switch (pagina_sessao) {
					case 1:
						switch (userInput) {
							case 1:
								System.out.println("\nFAZER LOGIN:\n");
								String usuario = login(scanner);
								if (!usuario.isEmpty()) {
									System.out.println("Login realizado com sucesso!");
									pagina_sessao = 2;
									current_user = usuario;
								}
								break;
							case 2:
								System.out.println("\nFAZER CADASTRO:\n");
								cadastrarUsuario(scanner);
								break;
							case 3:
								finalizar = true;
								break;
							case 999:
								System.out.println("Clientes cadastrados:\n");
								extraImprimirClientes(clientes);
								break;
						} break;
					case 2:
						switch (userInput) {
							case 1:
								System.out.println("\n\n[ PRODUTOS DA LOJA ]");
								listarProdutos(produtos_loja);
								break;
							case 2:
								System.out.println("\n\n[ CARRINHO ]");
								listarCarrinho(carrinho);
								break;
							case 3:
								System.out.println("ADICIONAR PRODUTO AO CARRINHO");
								adicionarAoCarrinho(produtos_loja, carrinho, scanner, current_user);
								break;
							case 4:
								System.out.println("REMOVER PRODUTO DO CARRIHNO");
								removerDoCarrinho(carrinho, produtos_loja, scanner);
								break;
							case 5:
								System.out.println("\n\nCOMPRA FINALIZADA.");
								FileHandler.gerarNota(carrinho, current_user);
								finalizar = true;
								break;
							case 6:
								System.out.println("\n\nCOMPRA CANCELADA.");
								finalizar = true;
								break;
							case 7:
								System.out.println("\n\nINDFORMAÇÕES DA CONTA");
								clientes.get(current_user).contaInfo();
								pagina_sessao = 3;
								break;
						} break;
					case 3:
						if (userInput == 1) {
							clientes.get(current_user).alterarLimite(scanner);
						} else {
							pagina_sessao = 2;
						}
				}
			} while (!finalizar);

			System.out.println("\n[ PROGRAMA ENCERRADO ]");

			// Exportar o estoque restante da loja - write to csv file
			FileHandler.escreverCSV(produtos_loja, "estoque_final.csv");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void extraImprimirClientes(Map<String, Conta> clientes) {
		for (Map.Entry<String, Conta> entry : clientes.entrySet()) {
			String usuario = entry.getKey();
			Conta conta = entry.getValue();
			System.out.println("---------------");
			System.out.println("Usuário: " + usuario);
			System.out.println("Senha: " + conta.getSenha());
			System.out.println("---------------");
		}
	}

	public static int menu(int pagina, Scanner scanner) {
		int userInput = 0;

		switch (pagina) {
			case 1: // Menu principal
				do {
					System.out.println();
					System.out.println("[1] Entrar");
					System.out.println("[2] Cadastrar");
					System.out.println("[3] Sair");
					System.out.print("\nSelecione uma opção: ");

					userInput = scanner.nextInt();
					scanner.nextLine();
				} while (!(userInput == 1 || userInput == 2 || userInput == 3 || userInput == 999));
				break;

			case 2: // Usuario logado
				do {
					System.out.println();
					System.out.println("[1] Listar produtos da loja");
					System.out.println("[2] Listar carrinho");
					System.out.println("[3] Adicionar produto ao carrinho");
					System.out.println("[4] Remover produto do carrinho");
					System.out.println("[5] Finalizar compra");
					System.out.println("[6] Cancelar compra");
					System.out.println("[7] Informações da conta");
					System.out.print("\nSelecione uma opção: ");

					userInput = scanner.nextInt();
					scanner.nextLine();
				} while (!(userInput == 1 || userInput == 2 || userInput == 3 || userInput == 4 || userInput == 5
						|| userInput == 6 || userInput == 7));
				break;
			case 3:
				do {
					System.out.println();
					System.out.println("[1] Alterar limite");
					System.out.println("[2] Voltar");
					System.out.print("\nSelecione uma opção: ");

					userInput = scanner.nextInt();
					scanner.nextLine();
				} while (!(userInput == 1 || userInput == 2));
				break;				
		}

		return userInput;

	}

	public static String login(Scanner scanner) throws UsuarioInexistenteException, SenhaInvalidaException {
		try {
			System.out.print("Digite o nome de usuário: ");
			String usuario = scanner.nextLine();

			System.out.print("Digite a senha: ");
			String senha = scanner.nextLine();

			// Verifica se o usuário existe no objeto clientes
			if (!clientes.containsKey(usuario)) {
				throw new UsuarioInexistenteException();
			}

			Conta conta = clientes.get(usuario);

			// Verifica se a senha corresponde ao usuário
			if (!conta.getSenha().equals(senha)) {
				throw new SenhaInvalidaException();
			}

			return usuario;

		} catch (UsuarioInexistenteException e) {
			System.out.println(e.getMessage());
		} catch (SenhaInvalidaException e) {
			System.out.println(e.getMessage());
		}

		return "";
	}

	public static void cadastrarUsuario(Scanner scanner) {
		Conta conta;

		System.out.print("Digite o nome de usuário: ");
		String usuario = scanner.nextLine();

		if (clientes.containsKey(usuario)) {
			System.out.println("Usuário já cadastrado. Por favor, escolha outro nome de usuário.");
			return; // Sai do método sem realizar o cadastro
		}

		System.out.print("Digite a senha: ");
		String senha = scanner.nextLine();

		System.out.println(
				"[PLANOS]\n\tStandard: limite de 3 itens por compra\n\tPremium: limite de 10 itens por compra");
		System.out.print("Digite o tipo de conta: ");
		String tipoConta = scanner.nextLine().toLowerCase();

		if (tipoConta.equals("standard")) {
			conta = new ContaStandard(senha);
		} else {
			conta = new ContaPremium(senha);
		}

		clientes.put(usuario, conta);

		System.out.println("Usuário cadastrado com sucesso!");
	}


	public static void listarProdutos(List<Produto> produtos) {
		System.out.format("%-5s%-20s%-10s%-10s%-10s%-15s\n", "ID", "Nome", "Estoque", "Preço", "Cor", "Categoria");
		System.out.println("--------------------------------------------------------------------");

		for (int i = 0; i < produtos.size(); i++) {
			Produto produto = produtos.get(i);
			System.out.format("%-5s%-20s%-10s%-10s%-10s%-15s\n",
					i + 1, produto.getNome(), produto.getEstoque(), produto.getPreco(), produto.getCor(),
					produto.getCategoria());
		}
	}

	public static void adicionarAoCarrinho(List<Produto> produtosLoja, List<Produto> carrinho, Scanner scanner, String current_user)
			throws ProdutoIndisponivelException {
		try {
			System.out.print("Selecione o índice do produto que deseja adicionar ao carrinho: ");
			int index = scanner.nextInt();
			scanner.nextLine(); // Limpar o buffer do scanner

			index -= 1; // Para o usuário, o índice começa em 1

			if (index >= 0 && index < produtosLoja.size()) {
				Produto produtoSelecionado = produtosLoja.get(index);
				if (produtoSelecionado.getEstoque() > 0) {
					Conta conta = clientes.get(current_user);
					int limite = conta.getLimite();

					if (carrinho.size() < limite) {
						carrinho.add(produtoSelecionado);
						produtoSelecionado.setEstoque(produtoSelecionado.getEstoque() - 1);
						System.out.println("Produto adicionado ao carrinho!");
					} else {
						throw new LimiteExcedidoException(limite);
					}
				} else {
					throw new ProdutoIndisponivelException();
				}
			} else {
				System.out.println("Índice inválido.");
				System.out.println("Produto adicionado ao carrinho!");
			}
		} catch (ProdutoIndisponivelException e) {
			System.out.println(e.getMessage());
		} catch (LimiteExcedidoException e) {
			System.out.println(e.getMessage());
		}
	}


	public static void listarCarrinho(List<Produto> carrinho) {
		System.out.format("%-5s%-20s%-10s%-10s%-15s\n", "ID", "Nome", "Preço", "Cor", "Categoria");
		System.out.println("--------------------------------------------------------------------");

		for (int i = 0; i < carrinho.size(); i++) {
			Produto produto = carrinho.get(i);
			System.out.format("%-5s%-20s%-10s%-10s%-15s\n",
					i + 1, produto.getNome(), produto.getPreco(), produto.getCor(),
					produto.getCategoria());
		}
	}

	public static void removerDoCarrinho(List<Produto> carrinho, List<Produto> produtos_loja, Scanner scanner) {
		System.out.print("Selecione o índice do produto que deseja remover do carrinho: ");
		int index = scanner.nextInt();
		scanner.nextLine(); // Limpar o buffer do scanner

		index -= 1; // Para o usuario o index comeca em 1

		if (index >= 0 && index < carrinho.size()) {
			Produto produtoRemovido = carrinho.remove(index);
			produtoRemovido.setEstoque(produtoRemovido.getEstoque() + 1);
			System.out.println("Produto removido do carrinho e devolvido ao estoque!");
		} else {
			System.out.println("Índice inválido.");
		}
	}
}