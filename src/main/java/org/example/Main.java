package org.example;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter membership status (Regular, VIP, Premium): ");
        String membership = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        double price = 0.0;

        if (membership.equalsIgnoreCase("Regular")) {
            if (age > 64){
                price = 75;
            } else if (age > 18){
                price = 100;
            } else {
                price = 50;
            }
        } else if (membership.equalsIgnoreCase("VIP")) {
            if (age > 64){
                price = 112.5;
            } else if (age > 18){
                price = 150;
            } else {
                price = 75;
            }
        } else if (membership.equalsIgnoreCase("Premium")) {
            if (age > 64){
                price = 150;
            } else if (age > 18){
                price = 200;
            } else {
                price = 100;
            }
        } else {
            System.out.print("Invalid membership status entered.");
        }

        System.out.print("Price: $".concat(String.format("%.2f",price)));
    }
}