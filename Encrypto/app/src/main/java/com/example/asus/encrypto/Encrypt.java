package com.example.asus.encrypto;

import java.util.Scanner;

public class Encrypt {

    public static String obify(String string) {
        String oby = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == 'A' || string.charAt(i) == 'E' || string.charAt(i) == 'I' || string.charAt(i) == 'O' || string.charAt(i) == 'U') {
                oby = oby + "XX";
            }
            oby += string.charAt(i);
        }
        return oby;
    }

    public static String normalize(String string) {
        String normal = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '.' || string.charAt(i) == '?' || string.charAt(i) == ' ' || string.charAt(i) == '!' || string.charAt(i) == ',' || string.charAt(i) == '(' || string.charAt(i) == ')' || string.charAt(i) == '/') {
                continue;
            }
            normal += string.charAt(i);
        }
        normal = normal.toUpperCase();
        return normal;
    }

    public static char shiftAlphabet(char ch, int n) {
        int asci = (int) ch;
        if (asci + n > 90) {
            int rem = 90 - asci;
            n = n - rem;
            asci = 64 + n;
        } else {
            asci = asci + n;
        }
        char c = (char) asci;
        return c;
    }

    public static String ceaser(String string, int n) {
        String csr = "";
        char ch;
        for (int i = 0; i < string.length(); i++) {
            ch = string.charAt(i);
            ch = shiftAlphabet(ch, n);
            csr += ch;
        }
        return csr;
    }

    public static String group(String string, int n) {
        String grouped = "";
        for (int i = 0; i < string.length(); i++) {
            if (i != 0 && i % n == 0) {
                grouped += " ";
            }
            grouped += string.charAt(i);
        }
        if (n != 1 && n!=string.length() && string.length()%n!=0) {
            int rem = string.length() % n;
            rem = n - rem;
            for (int i = 0; i < rem; i++)
                grouped += "x";
        }
        return grouped;
    }
}