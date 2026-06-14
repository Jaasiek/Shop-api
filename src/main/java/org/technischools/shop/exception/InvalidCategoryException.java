package org.technischools.shop.exception;

public class InvalidCategoryException extends RuntimeException {
    public InvalidCategoryException(String message) {
        super(message);
    }
}
