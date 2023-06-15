@SuppressWarnings("serial")
public class SegurancaException extends Exception {
    public SegurancaException(String string) {
        super(string);
    }

    @Override
    public void printStackTrace() {
        System.err.println(getMessage());
    }
}