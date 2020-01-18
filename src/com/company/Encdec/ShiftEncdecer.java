package com.company.Encdec;

public class ShiftEncdecer implements Encdecer {
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public String enc(int key, String data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            char currChar = data.charAt(i);

            if (Character.isLetter(currChar)) {
                final boolean isUpperCaseLetter = Character.isUpperCase(currChar);
                if (isUpperCaseLetter) {
                    currChar = Character.toLowerCase(currChar);
                }

                int indexInAlphabet = findCharFirstOccurrenceIndexInAlphabet(currChar);

                if (indexInAlphabet != -1) {
                    int encryptedCharIndexInAlphabet = findCorrectNewIndex(indexInAlphabet, key, alphabet.length() - 1);
                    currChar = alphabet.charAt(encryptedCharIndexInAlphabet);

                    if (isUpperCaseLetter) {
                        currChar = Character.toUpperCase(currChar);
                    }
                    sb.append(currChar);
                    continue;
                }
            }

            sb.append(currChar);
        }

        return sb.toString();
    }

    private int findCharFirstOccurrenceIndexInAlphabet(char ch) {
        for (int i = 0; i < alphabet.length(); i++) {
            if (alphabet.charAt(i) == ch) {
                return i;
            }
        }

        return -1;
    }

    private int findCorrectNewIndex(int currIndex, int key, int lastIndex) {
        while (key != 0) {
            int temp = currIndex + 1;

            if (temp == lastIndex + 1)
                currIndex  = 0;
            else if (temp == -1)
                currIndex  = lastIndex;
            else {
                currIndex = temp;
            }

            key--;
        }

        return currIndex;
    }
}
