package com.company.Encdec;

public class UnicodeEncdecer implements Encdecer {
    public String enc(int key, String data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            final char newChar = (char) ((int) data.charAt(i) + key);
            sb.append(newChar);
        }

        return sb.toString();
    }
}
