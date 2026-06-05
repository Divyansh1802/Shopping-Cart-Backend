package com.E_COMM.Dream_shop.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super();
    }
    public ProductNotFoundException(String message) {
        super(message);
    }
}
