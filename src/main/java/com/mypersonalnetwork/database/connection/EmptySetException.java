package com.mypersonalnetwork.database.connection;

public class EmptySetException extends Exception{
    public EmptySetException(){}
    public EmptySetException(String msg) {
        super(msg);
    }
}
