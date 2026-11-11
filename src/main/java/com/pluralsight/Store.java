package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();


        // Load inventory from the data file (pipe-delimited: SKU|Product Name|Price|Department)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {

        try (BufferedReader bufReader= new BufferedReader(new FileReader(fileName))) {

            String line;

            bufReader.readLine();

            while ((line= bufReader.readLine()) != null) {
                String [] inv = line.split("\\|");
                String Id = inv[0];
                String productName= inv[1];
                double Price= Double.parseDouble(inv[2]);
                String type = inv[3];

                Product product = new Product(Id, productName, Price,type );
                inventory.add(product);


            }


        }      catch (Exception ex) {
            System.out.println("file has an error");
        }
    }
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        for ( Product product : inventory) {
            System.out.println(product.getId() + " | " +
                    product.getInventry() + " | " +
                    product.getPrice() + " | " +
                    product.getType());

        }
        System.out.println(" please enter ID if you would like to make a purchase " + "\n or press x to return");

        String exit = scanner.nextLine();
        if (exit.equalsIgnoreCase("x")) {
            return;
        } for (Product product : inventory) {
            if (product.getId().equalsIgnoreCase(exit)) {
                cart.add(product);
                System.out.println("Product added to cart.");
            }
        }
    }
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {


        double totalAmount = 0;

        System.out.println("Your Cart is currently :");

        for (Product product : cart) {
            System.out.println(
                    product.getId() + " | " +
                            product.getInventry() + " | " +
                            product.getPrice()
            );

            totalAmount += product.getPrice();
        }
        System.out.println(totalAmount);

        System.out.println("Enter C to check out");
        System.out.println("Enter X to return to the menu");

        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("C")) {
            checkOut(cart, totalAmount, scanner);
        }
    }
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        System.out.println("please enter payment amount ");
        double payment = scanner.nextDouble();
        scanner.nextLine();
        if (payment < totalAmount) {
            System.out.println("Payment is not enough. Please try again.");
        } else {
            double change = payment - totalAmount;

            System.out.println("Receipt:");

            for (Product product : cart) {
                System.out.println(
                        product.getId() + " | "
                                + product.getInventry() + " | "
                                + product.getPrice()
                );
                System.out.println("your total is " + totalAmount + " and you have "+ change + " back");
            }
        }




    }
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        return null;
    }
}
