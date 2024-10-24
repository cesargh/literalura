package com.github.cesargh.literalura.service;

public class BuscadorException extends RuntimeException {

    public BuscadorException(String message) {
        super(message);
    }

    public BuscadorException(Throwable e) {
        super(e);
    }

}
