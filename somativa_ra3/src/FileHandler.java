import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler {
    public static Map<String, Map<String, Object>> lerCSV(String csvFile) {
        String line = "";
        String csvSeparator = ",";

        Map<String, Map<String, Object>> produtos = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile, StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                String[] dados = line.split(csvSeparator);

                String nome = dados[0];
                int estoque = Integer.parseInt(dados[1]);
                double preco = Double.parseDouble(dados[2]);
                String cor = dados[3];
                String categoria = dados[4];

                Map<String, Object> produto = new HashMap<>();
                produto.put("nome", nome);
                produto.put("estoque", estoque);
                produto.put("preco", preco);
                produto.put("cor", cor);
                produto.put("categoria", categoria);

                String chave = "produto_" + (produtos.size() + 1);

                produtos.put(chave, produto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return produtos;
    }
    
    public static void escreverCSV(List<Produto> produtos_loja, String csvFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, StandardCharsets.UTF_8))) {
            // Escrever os dados dos produtos no CSV
            for (Produto produto : produtos_loja) {
                String nome = produto.getNome();
                int estoque = produto.getEstoque();
                double preco = produto.getPreco();
                String cor = produto.getCor();
                String categoria = produto.getCategoria();

                // Escrever os valores separados por vírgulas
                bw.write(nome + "," + estoque + "," + preco + "," + cor + "," + categoria);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void gerarNota(List<Produto> carrinho, String nomeUsuario) {

		String nomeArquivo = "nota_fiscal.txt";

		try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
			writer.println("NOTA FISCAL");
			writer.println("Usuário: " + nomeUsuario);
			writer.println();
			writer.println("Lista de itens do carrinho:");

			double valorTotal = 0.0;

			for (Produto produto : carrinho) {
				writer.println("Nome: " + produto.getNome());
				writer.println("Preço: " + produto.getPreco());
				writer.println("Cor: " + produto.getCor());
				writer.println("Categoria: " + produto.getCategoria());
				writer.println();

				valorTotal += produto.getPreco();
			}

			// Arredondar o valor total para duas casas decimais
			double valorTotalArredondado = Math.round(valorTotal * 100.0) / 100.0;

			writer.println("Valor Total: $" + valorTotalArredondado);

			System.out.println("Nota gerada com sucesso! Verifique o arquivo " + nomeArquivo);
		} catch (IOException e) {
			System.out.println("Erro ao gerar a nota: " + e.getMessage());
		}
	}
}
