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
    private enum Commands {
        alg,
        in,
        data,
        mode,
        key,
        out
    }

    private enum AlgsCommands {
        shift,
        unicode
    }

    private enum ModeCommands {
        enc,
        dec
    }

    private static String alg = AlgsCommands.unicode.name();
    private static String in = "";
    private static String data = "";
    private static String mode = ModeCommands.enc.name();
    private static int key = 0;
    private static String out = "";

    public static void main(String[] args) {
        getProgramParamsFromArgs(args);
        encOrDecData();
        outputData();
    }

    private static void getProgramParamsFromArgs(String[] args) {
        setParams(args);
        setAlg();
        setDataFromFileIfCan();
    }

    private static void setParams(String[] args) {
        for (int i = 0; i < args.length; i++) {
            final String currWord = args[i];
            if (currWord.equals('-' + Commands.alg.name()))
                alg = args[++i];
            else if (currWord.equals('-' + Commands.in.name()))
                in = args[++i];
            else if (currWord.equals('-' + Commands.data.name()))
                data = args[++i];
            else if (currWord.equals('-' + Commands.mode.name()))
                mode = args[++i];
            else if (currWord.equals('-' + Commands.key.name()))
                key = Integer.parseInt(args[++i]);
            else if (currWord.equals('-' + Commands.out.name()))
                out = args[++i];
        }
    }

    private static void setAlg() {
        if (alg.equals(AlgsCommands.shift.name())) {
            alg = ShiftEncdecer.class.getName();
        } else if (alg.equals(AlgsCommands.unicode.name())) {
            alg = UnicodeEncdecer.class.getName();
        }
    }

    private static void setDataFromFileIfCan() {
        if (!in.equals("") && data.equals("")) {
            try {
                File file = new File(in);
                Scanner scanner = new Scanner(file);

                StringBuilder sb = new StringBuilder();
                while (scanner.hasNext())
                    sb.append(scanner.nextLine());
                scanner.close();
                data = sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void encOrDecData() {
        Encdecer encdecer = EncdecerStaticFactory.newInstance(alg);
        if (mode.equals(ModeCommands.enc.name()))
            data = encdecer.enc(key, data);
        else if ((mode.equals(ModeCommands.dec.name())))
            data = encdecer.dec(key, data);
        else
            throw new UnsupportedOperationException();
    }

    private static void outputData() {
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