package com.company;

public class Encdecer {
    private Encdec encdecer;

    public void createInstance(Encdec encdecer) {
        this.encdecer = encdecer;
    }

    public String enc(int key, String data) {
        return encdecer.enc(key, data);
    }

    public String dec(int key, String data) {
        return encdecer.dec(key, data);
    }
}
