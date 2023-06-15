public class Produto {
	private String nome;
	private int estoque;
	private double preco;
	private String cor;
	private String categoria;

	public Produto(String nome, int estoque, double preco, String cor, String categoria) {
		this.nome = nome;
		this.estoque = estoque;
		this.preco = preco;
		this.cor = cor;
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public double getPreco() {
		return preco;
	}

	public String getCor() {
		return cor;
	}

	public String getCategoria() {
		return categoria;
	}
}

