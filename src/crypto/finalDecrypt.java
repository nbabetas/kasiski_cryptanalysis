/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author babs
 */
public class finalDecrypt {

    public finalDecrypt(String txt, ArrayList<Integer> key) throws FileNotFoundException {
        String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        int j = 0;
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < txt.length(); i++) {
            if (Character.isLetter(txt.charAt(i))) {
                for (int a = 0; a < alpha.length; a++) {
                    if (alpha[a].charAt(0) == txt.charAt(i)) {
                        text.append(alpha[mod(a - key.get(j), 26)]);
                        j++;
                    }
                }

            } else {
                text.append(txt.charAt(i));
            }
            if (j == key.size()) {
                j = 0;
            }
        }
        System.out.println(text);
        
        PrintWriter f  =new PrintWriter("Text.txt");
        f.printf("The key is: "); 
        for(int i=0;i<key.size();i++){
            f.printf("%c", alpha[key.get(i)].charAt(0));
            f.flush();
        }
        f.println();
        f.println();
        f.println(text);
        f.close();
    }

    int mod(int a, int b) {
        int c = a % b;
        return (c < 0) ? c + b : c;
    }
}
