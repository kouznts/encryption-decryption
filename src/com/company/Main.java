package com.company;

import com.company.Encdec.Encdecer;
import com.company.Encdec.EncdecerStaticFactory;
import com.company.Encdec.ShiftEncdecer;
import com.company.Encdec.UnicodeEncdecer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String alg = "shift";
        String in = "";
        String data = "AWelcome to hyperskill!";
        String mode = "enc";
        int key = 5;
        String out = "";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-alg":
                    alg = args[++i];
                    break;

                case "-in":
                    in = args[++i];
                    break;

                case "-data":
                    data = args[++i];
                    break;

                case "-mode":
                    mode = args[++i];
                    break;

                case "-key":
                    key = Integer.parseInt(args[++i]);
                    break;

                case "-out":
                    out = args[++i];
                    break;
            }
        }

        if (alg.equals("shift")) {
            alg = ShiftEncdecer.class.getName();
        } else if (alg.equals("unicode")) {
            alg = UnicodeEncdecer.class.getName();
        }
        Encdecer encdecer = EncdecerStaticFactory.newInstance(alg);

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

        if (mode.equals("enc"))
            data = encdecer.enc(key, data);
        else if ((mode.equals("dec")))
            data = encdecer.dec(key, data);
        else
            throw new UnsupportedOperationException();

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
}