package ru.edu.pasteservice.exceptions;

public class PasteIsExpiredException extends Exception{
    public PasteIsExpiredException(String msg){
        super(msg);
    }
}
