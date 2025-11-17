package com.dev.org.order.exception;

public sealed interface SystemError extends ApplicationError {

    record RemoteServiceError(String service,
                              String message) implements SystemError {

    }

}
