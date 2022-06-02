package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class Main {

    private static int p, q, n, z, d = 0, e, i = 0, a, msg;
    private static int nummes[] = new int[100];
    private static int encripted[] = new int[100];
    private static int decripted[] = new int[100];
    private static int j = 0, nofelem = 0;
    private static String message;

    public static void Keys() {
        n = p * q;
        System.out.println("value of n = " + n);
        // z = fi(n)
        z = (p - 1) * (q - 1);
        System.out.println("value of fi(n) = " + z);

        //e for public key with euclid formula
        for (e = 2; e < z; e++) {
            if (gcd(e, z) == 1) {
                break;
            }
        }
        System.out.println("value of e = " + e);
        System.out.println("------------------------------");
        System.out.println("Public key (e = " + e + ", n = " + n + ")");
        System.out.println("------------------------------");

        //d for private key exponent

        for (i = 2; i < z; i++)
            if ((e * i - 1) % z == 0) break;
        d = i;
        System.out.println("value of d = " + d);
        System.out.println("------------------------------");
        System.out.println("Private Key (d = " + d + ", n = " + n + ")");
        System.out.println("------------------------------");


    }

    //euclid
    static int gcd(int e, int z) {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }


    public static void encryption(String message, int e) throws FileNotFoundException {
        for (i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            int a = (int) c;
            nummes[i] = c - 96;
        }
        nofelem = message.length();
        for (i = 0; i < nofelem; i++) {
            encripted[i] = 1;
            for (j = 0; j < e; j++) {
                encripted[i] = (encripted[i] * nummes[i]) % n;
            }
        }
        System.out.println("\nEncripted message\n");
        System.out.println("------------------------------");
        for (i = 0; i < nofelem; i++) {
            System.out.print(encripted[i]);
            System.out.print((char) (encripted[i] + 96));
            saveToFile(encripted);
        }
        System.out.println("\n------------------------------");
        System.out.println("\n Saving to a file..");


    }

    public static void decriptFromFile(int d, int n) throws FileNotFoundException {

        File text = new File("encrypted.txt");
        // to read File
        Scanner scnr = new Scanner(text);
        int[] arr = new int[50];
        int i = 0;

        while (scnr.hasNextInt()) {

                arr[i++] = scnr.nextInt();
        }
        System.out.println("Reading from a file..");
        // to see array got from file
      /*  for (int j = 0; j < arr.length; j++) {
            System.out.println("\n"+ arr[j]);
        }*/
        //nofelem is used for words length.
        nofelem = arr.length;
        for (i = 0; i < nofelem; i++) {
            decripted[i] = 1;
            for (j = 0; j < d; j++) {
                decripted[i] = (decripted[i] * arr[i]) % n;
            }
        }
        System.out.println("\nDecripted message\n");
        System.out.println("------------------------------");
        for (i = 0; i < nofelem; i++) {
            System.out.print((char) (decripted[i] + 96));
        }
        System.out.println("\n------------------------------");


    }

    public static void decripted() {
        for (i = 0; i < nofelem; i++) {
            decripted[i] = 1;
            for (j = 0; j < d; j++) {
                decripted[i] = (decripted[i] * encripted[i]) % n;
            }
        }
        System.out.println("\nDecripted message\n");
        System.out.println("------------------------------");
        for (i = 0; i < nofelem; i++) {
            System.out.print((char) (decripted[i] + 96));
        }
        System.out.println("\n------------------------------");
    }


    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nChose option:\n1.To encrypt text and see decrypted.\n2.To Decrypt from file\n0.Exit");
             choice = sc.nextInt();
            sc.nextLine();
            if(choice == 1) {
                System.out.println("Enter your message to encript:");
                message = sc.nextLine();
                System.out.println("Enter 1st prime number p:");
                p = sc.nextInt();
                System.out.println("Enter 2nd prime number q:");
                q = sc.nextInt();
                Keys();
                encryption(message, e);
                decripted();
            }else if(choice == 2){
                System.out.println("Enter 1st private key number d:");
                d = sc.nextInt();
                System.out.println("Enter 2nd private key  number n:");
                n = sc.nextInt();
                decriptFromFile(d,n);
            }

        }while(choice != 0);


    }

// method to save to the file
    private static void saveToFile(int encripted[]) throws FileNotFoundException {
        File enryptedFile = new File("encrypted.txt");
        PrintStream writer = new PrintStream(enryptedFile);
        int index = 0;
        while (index < encripted.length) {

            if (encripted[index] != 0) {

                writer.println(encripted[index]);
            }
            index = index + 1;
        }
        writer.close();
    }

}