package com.javarush.cryptoanalyser.Controller;

import com.javarush.cryptoanalyser.Model.Crypt;
import com.javarush.cryptoanalyser.Model.DeCrypt;

import java.nio.file.Files;
import java.nio.file.Path;

public class Control {
    private String filePath;
    private String encryptFilePath;
    private String decryptFilePath;

    public String checkFile(String filePath, int key) {
        if (Files.exists(Path.of(filePath))) {
            this.filePath = filePath;
            Path path = Path.of(filePath);
            if(key>0) {
                encryptFilePath = path.getParent() + "\\encrypt_" + path.getFileName();
                cryptFile(key);
                return "Your encrypt file is " + encryptFilePath;
            } else{
                decryptFilePath = path.getParent() + "\\decrypt_" + path.getFileName();
                deCryptFile();
                return "Your decrypt file is " + decryptFilePath;
            }
        }
        return "File don't exist or the path is incorrect.";
    }

    private void cryptFile(int key){
        new Crypt(filePath, encryptFilePath, key);
    }

    private void deCryptFile(){
        new DeCrypt(filePath, decryptFilePath);
    }
}
