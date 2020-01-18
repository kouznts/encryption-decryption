package com.company;

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        int key = 0;
        String data = "";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    mode = args[++i];
                    break;
                case "-key":
                    key = Integer.parseInt(args[++i]);
                    break;
                case "-data":
                    data = args[++i];
                    break;
            }
        }

        data = encdec(mode, key, data);
        System.out.println(data);
    }

    private static String encdec(String mode, int key, String data) {
        if (mode.equals("enc")) {
        } else if (mode.equals("dec")) {
            key = -key;
        } else {
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            int currChar = data.charAt(i);
            currChar += key;
            sb.append((char) currChar);
        }

        return sb.toString();
    }
}