package com.example.connectionmysql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyURL {

    private String sourceFilePath;
    private String filename;

    private String destinationFilePath;
    public CopyURL(){
        this.destinationFilePath = "src/main/resources/";
    }

    protected void setSource(String source){
        this.sourceFilePath = source.replace("file:", "");
    }
    public void copyFile(){
        try{
            Path path = Paths.get(sourceFilePath);
            String pathway = path.getParent().toString();
            String fileName = path.getFileName().toString();

            Path sourcePath = Paths.get(pathway+'/'+fileName);
            Path destinationPath = Paths.get(destinationFilePath+fileName);

            Files.copy(sourcePath, destinationPath);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public String getDestinationPath(){
        Path path = Paths.get(sourceFilePath);
        String fileName = path.getFileName().toString();
        Path destinationPath2 = Paths.get(destinationFilePath+fileName);
        return(destinationPath2.toString());
    }

}
