package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Inventory {

    private int trayCount = 0;
    private final int STARTING_COUNT = 5;   //readme states that vending machine always starts app with 5 of each product
    private LinkedHashMap<String, List<Product>> trayMap = new LinkedHashMap<>();


    //read inventory file and put data into trayMap
    public Inventory() {

        //scanner for inventory file "vendingmachine.csv"
        File inputFile = new File("vendingmachine.csv");

        //scanner to read file that holds vending machine inventory
        try (Scanner inventoryFileScanner = new Scanner(inputFile)) {

            //while the file has lines, delimit the line string by the pipe symbol '|'
            while (inventoryFileScanner.hasNextLine()) {

                //assign each line of the file to the local variable inventoryFileLine
                String inventoryFileLine = inventoryFileScanner.nextLine();

                //split the string inventoryLineFile with pipe delimiter into an array of strings inventoryLineArray
                String[] inventoryLineArray = inventoryFileLine.split("\\|");

                //get data from vendingmachine.csv inventory file
                String trayID = (inventoryLineArray[0]);
                String productNameString = (inventoryLineArray[1]); //  () ARE WEIRD HERE

                //convert the string productPrice to a double
                double productPrice = Double.parseDouble(inventoryLineArray[2]);
                String productTypeString = (inventoryLineArray[3]);

                //each tray of vendingMachine will be a list named trayList that gets populated with product from ProductClass
                List<Product> trayList = new ArrayList();

                //for loop to add product to tray 5 times for max capacity
                for (int i = 0; i < STARTING_COUNT; i++) {
                    Product product = new Product(productNameString, productPrice, productTypeString);
                    trayList.add(product);

                }
                //add our trayID and trayList to our trayMap
                trayMap.put(trayID, trayList);

            }
        } catch (FileNotFoundException e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //check to see if tray is sold out
    public boolean isSoldOut(String trayID) {
        boolean soldOut = false;
        if ( trayMap.get(trayID).size() == 0)
            soldOut = true;
        return soldOut;
    }

    //getters
    public LinkedHashMap<String, List<Product>> getTrayMap() {
        return trayMap;
    }

}
