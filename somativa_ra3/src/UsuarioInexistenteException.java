@SuppressWarnings("serial")
public class UsuarioInexistenteException extends SegurancaException {
    public UsuarioInexistenteException() {
        super("[ERRO] Usuário não encontrado.");
    }

    @Override
    public String toString() {
        return "UsuarioInexistenteException []";
    }
}