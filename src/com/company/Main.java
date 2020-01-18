package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        int key = 0;
        String data = "";
        String in = "";
        String out = "";

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

                case "-in":
                    in = args[++i];
                    break;

                case "-out":
                    out = args[++i];
                    break;
            }
        }

        if (!in.equals("") && data.equals("")) {
            try {
                File file = new File(in);
                Scanner scanner = new Scanner(file);

                StringBuilder sb = new StringBuilder();
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }
                scanner.close();
                data = sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        data = encdec(mode, key, data);

        if (out.equals("")) {
            System.out.println(data);
        } else {
            try {
                File file = new File(out);
                FileWriter writer = new FileWriter(file);
                writer.write(data);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String encdec(String mode, int key, String data) {
        if (mode.equals("dec")) {
            key = -key;
        } else if (!mode.equals("enc")) {
            throw new UnsupportedOperationException();
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