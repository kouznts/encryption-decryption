package com.company.Encdec;

import com.company.Encdec.Encdec;

public class UnicodeEncdec implements Encdec {
    public String enc(int key, String data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            sb.append(data.charAt(i) + key);
        }

        return sb.toString();
    }
}
