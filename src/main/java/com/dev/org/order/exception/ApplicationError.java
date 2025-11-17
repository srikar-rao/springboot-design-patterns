package com.dev.org.order.exception;

public sealed interface ApplicationError permits DomainError, SystemError {
}
