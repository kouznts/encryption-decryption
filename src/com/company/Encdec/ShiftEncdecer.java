package com.company.Encdec;

public class ShiftEncdecer implements Encdecer {
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public String enc(int key, String data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            char currChar = data.charAt(i);

            if (!Character.isLetter(currChar)) {
                sb.append(currChar);
                continue;
            }

            final boolean isUpperCaseLetter = Character.isUpperCase(currChar);
            if (isUpperCaseLetter)
                currChar = Character.toLowerCase(currChar);

            int indexInAlphabet = findCharFirstOccurrenceIndexInAlphabet(currChar);

            if (indexInAlphabet == -1) {
                sb.append(currChar);
                continue;
            }

            int encryptedCharIndexInAlphabet = findCorrectNewIndex(indexInAlphabet, key, alphabet.length() - 1);
            currChar = alphabet.charAt(encryptedCharIndexInAlphabet);

            if (isUpperCaseLetter) {
                currChar = Character.toUpperCase(currChar);
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
        if (key >= 0) {
            while (key != 0) {
                currIndex = setNewIndex(currIndex + 1, lastIndex);
                key--;
            }
        } else {
            while (key != 0) {
                currIndex = setNewIndex(currIndex - 1, lastIndex);
                key++;
            }
        }

        return currIndex;
    }

    private int setNewIndex(int currIndexPlusOne, int lastIndex) {
        if (currIndexPlusOne == lastIndex + 1)
            return 0;
        else if (currIndexPlusOne == -1)
            return lastIndex;
        else {
            return currIndexPlusOne;
        }
    }
}
