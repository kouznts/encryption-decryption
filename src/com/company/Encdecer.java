package com.company;

public interface Encdecer {
    String enc(int key, String data);

    default  String dec(int key, String data) {
        return enc(-key, data);
    }
}
