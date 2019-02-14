/**
 * 
 */
package com.terry.iat.service.common.exception;

public class RefuseAccessException extends RuntimeException {

    private static final long serialVersionUID = 5399432443593767230L;

    public RefuseAccessException() {
        super();
    }

    public RefuseAccessException(String message) {
        super(message);
    }

    public RefuseAccessException(Throwable e) {
        super(e);
    }
}
