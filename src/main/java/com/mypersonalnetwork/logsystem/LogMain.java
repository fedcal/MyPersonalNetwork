package com.mypersonalnetwork.logsystem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogMain {
    private static Logger logger;
    private static final String mainLog="mainLog.txt";
    private static final String exceptionLog="exceptionLog.txt";
    private static final String pathFolder="./logging/";

    public LogMain() throws IOException {
        Path mainFolder = Path.of(pathFolder);
        if(!Files.exists(mainFolder)) {
            Files.createDirectories(mainFolder);
            Files.createFile(Path.of(pathFolder+mainLog));
            Files.createFile(Path.of(pathFolder+exceptionLog));
        }
    }

    public static final void writeLog(String msg, Level typeLog, String nameClass)throws IOException{
        logger = Logger.getLogger(nameClass);
        if(typeLog.equals("WARNING")){
            writeExceptionLog(msg);
        }else {
            writeMainLog(msg);
        }
    }

    private static void writeMainLog(String msg) throws IOException {
        Stream<String> lines = Files.lines(Path.of(pathFolder+mainLog));
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        File myObj = new File(pathFolder+mainLog);
        myObj.delete();
        myObj.createNewFile();
        Files.write(Path.of(pathFolder+mainLog), (LocalDateTime.now()+": "+msg).getBytes(), StandardOpenOption.APPEND);
        Files.write(Path.of(pathFolder+mainLog),data.getBytes(), StandardOpenOption.APPEND);
    }

    private static void writeExceptionLog(String msg) throws IOException {
        Stream<String> lines = Files.lines(Path.of(pathFolder+exceptionLog));
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        File myObj = new File(pathFolder+exceptionLog);
        myObj.delete();
        myObj.createNewFile();
        Files.write(Path.of(pathFolder+exceptionLog), (LocalDateTime.now()+": "+msg).getBytes(), StandardOpenOption.APPEND);
        Files.write(Path.of(pathFolder+exceptionLog),data.getBytes(), StandardOpenOption.APPEND);
    }
}
