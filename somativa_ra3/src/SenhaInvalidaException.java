@SuppressWarnings("serial")
public class SenhaInvalidaException extends SegurancaException {
    public SenhaInvalidaException() {
        super("[ERRO] Senha inválida.");
    }

    @Override
    public String toString() {
        return "SenhaInvalidaException []";
    }
}