/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.FileNotFoundException;

/**
 *
 * @author babs
 */
public class Crypto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        ReadFromFile read = new ReadFromFile();
        tokenization token = new tokenization(read.getText());
        Kasiski solver = new Kasiski(token.getText());
        Decrypt decrypt = new Decrypt(token.getText(),solver.getKey());
        finalDecrypt d = new finalDecrypt(read.getText(),decrypt.getKey());
    }
    
}
