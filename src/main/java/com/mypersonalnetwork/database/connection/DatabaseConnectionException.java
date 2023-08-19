package com.mypersonalnetwork.database.connection;

public class DatabaseConnectionException extends Exception{
    DatabaseConnectionException(){}
    public DatabaseConnectionException(String e){
        System.out.println(e);
    }
}
