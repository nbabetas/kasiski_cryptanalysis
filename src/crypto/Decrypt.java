/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 *
 * @author babs
 */
public class Decrypt {

    String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    ArrayList<StringBuilder> subtext = new ArrayList<>();
    ArrayList<Integer> key = new ArrayList<>();
    public Decrypt(String text, int key1) {
        int temp = text.length() % key1;
        StringBuilder sub;
        for (int i = 0; i < key1; i++) {
            subtext.add(new StringBuilder());
        }
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (count < key1) {
                subtext.get(count).append(text.charAt(i));
                count++;
            }
            if (count == key1) {
                count = 0;
            }
        }

        for (int i = 0; i < subtext.size(); i++) {
            findKey(subtext.get(i));

        }

    }

    void findKey(StringBuilder text) {
        int index = 0;
        int fre = 0;
        int max = 0;
        for (int a = 0; a < alpha.length; a++) {
            for (int j = 0; j < text.length(); j++) {
                if (text.charAt(j) == alpha[a].charAt(0)) {
                    fre++;
                }
            }
            if (max < fre) {
                max = fre;
                index = a;
            }
            fre = 0;
        }
        for (int i = 0; i < alpha.length; i++) {
            if (mod(index - i, 26) == 4) {
               key.add(i);
            }
        }

    }

    int mod(int a, int b) {
        int c = a % b;
        return (c < 0) ? c + b : c;
    }
    
    ArrayList<Integer> getKey(){
        return key;
    }
   
    
    
}
