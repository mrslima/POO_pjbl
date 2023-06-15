@SuppressWarnings("serial")
public class ProdutoIndisponivelException extends Exception {

	public ProdutoIndisponivelException() {
        super("[ERRO] Desculpe, o produto selecionado est\u00E1 fora de estoque.");
    }
    
    @Override
    public void printStackTrace() {
        System.err.println(getMessage());
    }
}