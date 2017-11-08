package com.example.asus.encrypto;

import java.util.Scanner;

public class Decrypt {

    public static String degroup(String string){
        String data="";
        for (int i=0;i<string.length();i++){
            if(string.charAt(i)==' ' || string.charAt(i)=='x'){
                continue;
            }
            data+=string.charAt(i);
        }
        return data;
    }
    public static char deshiftAlphabet(char ch, int n) {
        int asci = (int) ch;
        if (asci - n < 65) {
            int rem = asci-65;
            n = n - rem;
            asci = 91 - n;
        } else {
            asci = asci - n;
        }
        char c = (char) asci;
        return c;
    }
    public static String deceaser(String string,int n){
        String csr = "";
        char ch;
        for (int i = 0; i < string.length(); i++) {
            ch = string.charAt(i);
            ch = deshiftAlphabet(ch, n);
            csr += ch;
        }
        return csr;
    }
    public static String deobify(String string) {
        String oby = "";
        for (int i = 0; i < string.length(); i++) {
            if (i + 2 < string.length()) {
                if (string.charAt(i + 2) == 'A' || string.charAt(i + 2) == 'E' || string.charAt(i + 2) == 'I' || string.charAt(i + 2) == 'O' || string.charAt(i + 2) == 'U') {
                    i = i + 2;
                }
            }
            oby += string.charAt(i);
        }
        return oby;
    }
}
