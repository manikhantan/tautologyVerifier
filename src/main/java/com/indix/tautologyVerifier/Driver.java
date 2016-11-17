package com.indix.tautologyVerifier;

import java.util.Scanner;

/**
 * Created by mani on 11/18/16.
 */
public class Driver {

    public static void main(String[] args) {

        TautologyVerifier tautologyVerifier = new TautologyVerifier();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Number of expressions: ");
        int count = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please type in the expressions: ");

        while(count>0) {
            String expression = scanner.nextLine();
            System.out.println(tautologyVerifier.isTautology(expression));
            count--;
        }
    }
}
