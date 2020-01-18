package com.company;

class UnicodeEncdecer {
    String enc(int key, String data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            sb.append(data.charAt(i) + key);
        }

        return sb.toString();
    }

    String dec(int key, String data) {
        return enc(-key, data);
    }
}
