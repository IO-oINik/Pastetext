package ru.edu.pasteservice.exceptions;

public class JwtNotFoundException extends Exception{
    public JwtNotFoundException(String msg) {
        super(msg);
    }
}
