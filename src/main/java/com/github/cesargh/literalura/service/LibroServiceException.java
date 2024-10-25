package com.github.cesargh.literalura.service;

public class LibroServiceException extends RuntimeException {

  public LibroServiceException(String message) {
    super(message);
  }

  public LibroServiceException(Throwable e) {
    super(e);
  }

}
