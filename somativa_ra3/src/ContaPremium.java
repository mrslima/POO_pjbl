public class ContaPremium extends Conta {
	public ContaPremium(String senha) {
		super(senha, "Premium", 10);
	}

	@Override
	public void contaInfo() {
		System.out.println("Tipo de conta: " + getTipoConta());
		System.out.println("Limite: " + getLimite());
	}
}