package br.com.fiap.mindtrack.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){ super(message); }
}
