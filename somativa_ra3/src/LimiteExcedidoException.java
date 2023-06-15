@SuppressWarnings("serial")
public class LimiteExcedidoException extends Exception {
    private int limite;

    public LimiteExcedidoException(int limite) {
        this.limite = limite;
    }

    @Override
    public String getMessage() {
        return "Limite de itens excedido. Limite atual: " + limite;
    }
}
