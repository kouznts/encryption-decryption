package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String targetOperation = in.nextLine();
        String messageOrCyphertext = in.nextLine();
        int key = in.nextInt();

        messageOrCyphertext = encdec(targetOperation, messageOrCyphertext, key);
        System.out.println(messageOrCyphertext);
    }

    private static String encdec(String targetOperation, String messageOrCyphertext, int key) {
        if (targetOperation.equals("enc")) {
        } else if (targetOperation.equals("dec")) {
            key = -key;
        } else {
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < messageOrCyphertext.length(); i++) {
            int currChar = messageOrCyphertext.charAt(i);
            currChar += key;
            sb.append((char) currChar);
        }

        return sb.toString();
    }
}