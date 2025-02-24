package it.cnr.contab.exception;

public class MissingTesoreriaExcpetion extends RuntimeException{
    public MissingTesoreriaExcpetion(String msg) {
        super(msg);
    }

    public MissingTesoreriaExcpetion(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MissingTesoreriaExcpetion(Throwable cause) {
        super(cause);
    }
}
