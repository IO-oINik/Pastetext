package ru.edu.pasteservice.exceptions;

public class PasteNotFoundException extends Exception{
    public PasteNotFoundException(String msg){
        super(msg);
    }
}
