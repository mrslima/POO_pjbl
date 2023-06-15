import java.util.Scanner;

abstract class Conta {
	private String senha;
	private String tipoConta;
	private int limite;

	public Conta(String senha, String tipoConta, int limite) {
		this.senha = senha;
		this.tipoConta = tipoConta;
		this.limite = limite;
	}

	public String getSenha() {
		return senha;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public int getLimite() {
		return limite;
	}

	public void alterarLimite(Scanner scanner) {
		System.out.print("\nInforme o novo limite: ");

		int novo_limite = scanner.nextInt();
		scanner.nextLine();
		
		limite = novo_limite;
		System.out.println("NOVO LIMITE: " + this.limite);
	}


	public abstract void contaInfo();
}