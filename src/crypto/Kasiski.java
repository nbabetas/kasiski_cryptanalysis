/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author babs
 */
public class Kasiski {

    String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    ArrayList<Integer> size = new ArrayList<>();
    ArrayList<String> mot = new ArrayList<>();
    ArrayList<Integer> dist = new ArrayList<>();
    ArrayList<String> dictionary = new ArrayList<>();
    ArrayList<StringBuilder> subtext = new ArrayList<>();
    ArrayList<Integer> length = new ArrayList<>();
    ArrayList<Float> oc = new ArrayList<>();
    boolean flag = false;
    int result1 = 0;
    int result2 = 0;
    int result3 = 0;
    int keylength = 0;
    float pIC = 0;

    public Kasiski(String text) throws FileNotFoundException {

        int counter = 0;
        int k = 3;
        int max1 = 1;
        int max2 = 1;
        int max3 = 1;
        do {
            k++;
            for (int t = 0; t < text.length() - k; t++) {
                String word1 = text.substring(t, t + k);

                counter = 0;
                for (int d = 0; d < dictionary.size(); d++) {
                    if (word1.equals(dictionary.get(d))) {
                        flag = false;
                    }
                }
                for (int i = t + k; i < text.length() - k; i++) {

                    String word2 = text.substring(i, i + k);
                    if (flag) {
                        if (word1.equals(word2)) {
                            dictionary.add(word1);
                            dist.add(i - t);
                            mot.add(word1);
                            counter++;
                            size.add(counter);
                        }

                    }
                }

                if (counter > max1) {

                    max1 = counter;
                } else if (max1 > counter && max2 < counter) {

                    max2 = counter;
                } else if (max1 > counter && max2 > counter && max3 < counter) {

                    max3 = counter;
                }

                flag = true;

            }

        } while (k != 5);

        String trigram1 = "";
        String trigram2 = "";
        String trigram3 = "";
        for (int i = 0; i < size.size(); i++) {
            if (size.get(i) == max1) {
                trigram1 = mot.get(i);
                result1 = dist.get(i - 0);
                for (int j = 1; j < max1; j++) {
                    result1 = gcd(result1, dist.get(i - j));
                }
            } else if (size.get(i) == max2) {
                trigram2 = mot.get(i);
                result2 = dist.get(i - 0);
                for (int j = 1; j < max2; j++) {
                    result2 = gcd(result2, dist.get(i - j));
                }
            } else if (size.get(i) == max3) {
                trigram3 = mot.get(i);
                result3 = dist.get(i - 0);
                for (int j = 1; j < max3; j++) {
                    result3 = gcd(result3, dist.get(i - j));
                }
            }
        }
        length.add(result1);
        length.add(result2);
        length.add(result3);

        for (int l = 0; l < length.size(); l++) {
            subtext = new ArrayList<>();
            int temp = text.length() % length.get(l);
            StringBuilder sub;
            for (int i = 0; i < length.get(l); i++) {
                subtext.add(new StringBuilder());
            }
            int count = 0;
            for (int i = 0; i < text.length(); i++) {
                if (count < length.get(l)) {
                    subtext.get(count).append(text.charAt(i));
                    count++;
                }
                if (count == length.get(l)) {
                    count = 0;
                }

            }

            float ic = 0;
            for (int f = 0; f < length.get(l); f++) {
                ic = ic + IC(subtext.get(f).toString());
            }

            ic = ic / length.get(l);
            oc.add(ic);

            if (Math.abs(ic - 0.066) < Math.abs(pIC - 0.066)) {
                keylength = length.get(l);
                pIC = ic;
            }

        }
        Save();
        SaveIC();
    }

    static int gcd(int a, int b) {
        int min = a > b ? b : a, max = a + b - min, div = min;
        for (int i = 1; i < min; div = min / ++i) {
            if (max % div == 0) {
                return div;

            }
        }
        return 1;
    }

    float IC(String plaintext) {
        int c = 0;
        int size;
        BigDecimal divide;
        BigDecimal ic = new BigDecimal(0);
        size = plaintext.length();
        BigDecimal p = new BigDecimal((size * (size - 1)));
        BigDecimal a = new BigDecimal(0);
        BigDecimal fic = null;
        for (int j = 0; j < alpha.length; j++) {
            String temp1 = alpha[j];
            for (int i = 0; i < size; i++) {
                if (plaintext.charAt(i) == temp1.charAt(0)) {
                    c = c + 1;

                }
            }

            a = new BigDecimal(c * (c - 1));
            divide = a.divide(p, 10, RoundingMode.CEILING);
            fic = ic.add(divide);
            ic = fic;
            c = 0;
        }
        //System.out.println(fic);
        return fic.floatValue();
    }

    int getKey() {
        return keylength;
    }

    void Save() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("Grams.txt");
        for (int i = 0; i < mot.size(); i++) {
            out.println("Gram :" + mot.get(i) + "\tDistance:" + dist.get(i) + " Found:" + size.get(i) + "\ttimes");
            out.flush();
        }
        out.close();
    }

    void SaveIC() throws FileNotFoundException {
        PrintWriter ioc = new PrintWriter("Index of Coincidence.txt");
        for (int i = 0; i < length.size(); i++) {
            ioc.println("For Key length:" + length.get(i) + "\tAverage Index of Coincidence is:" + oc.get(i));
            ioc.flush();
        }
        ioc.close();
    }

}
