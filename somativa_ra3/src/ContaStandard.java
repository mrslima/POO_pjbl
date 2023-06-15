import java.util.Scanner;

public class ContaStandard extends Conta {
	public ContaStandard(String senha) {
		super(senha, "Standard", 3);
	}

	@Override
	public void contaInfo() {
		System.out.println("Tipo de conta: " + getTipoConta());
		System.out.println("Limite: " + getLimite());
	}
	
    @Override
    public void alterarLimite(Scanner scanner) {
        System.out.println("[ERRO] O limite de contas Standard não pode ser alterado!");
    }
}
