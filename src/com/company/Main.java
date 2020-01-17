package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();

        int step = in.nextInt();

        str = encrypt(str, step);
        System.out.println(str);
    }

    private static String encrypt(String str, int step) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            int indexInAlphabet = findCharFirstOccurrenceIndexInString(str.charAt(i), alphabet);

            if (indexInAlphabet != -1) {
                int encryptedCharIndexInAlphabet = findCorrectNewIndex(indexInAlphabet, step, alphabet.length());
                sb.append(alphabet.charAt(encryptedCharIndexInAlphabet));
            } else {
                sb.append(str.charAt(i));
            }
        }

        return sb.toString();
    }

    private static int findCharFirstOccurrenceIndexInString(char ch, String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                return i;
            }
        }

        return -1;
    }

    private static int findCorrectNewIndex(int currIndex, int step, int strLen) {
        int newIndex = currIndex + step;
        int lastIndex = strLen - 1;

        if (newIndex > lastIndex) {
            return ((newIndex + 1) % strLen) - 1;
        } else {
            return newIndex;
        }
    }
}