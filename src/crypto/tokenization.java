/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

/**
 *
 * @author babs
 */
public class tokenization {

    StringBuilder build = new StringBuilder();

    public tokenization(String text) {

        build.append(text);
        for (int i = 0; i < build.length(); i++) {
            if (!Character.isLetter(build.charAt(i))) {
                build.deleteCharAt(i);

            }

        }
        for (int i = 0; i < build.length(); i++) {
            if (!Character.isLetter(build.charAt(i))) {
                build.deleteCharAt(i);

            }

        }
    }

        String getText(){
        return build.toString();
        }
    }
