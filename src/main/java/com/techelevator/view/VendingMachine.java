package com.techelevator.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {

    private final double QUARTERS = 0.25;
    private final double DIME = 0.10;
    private final double NICKEL = 0.05;
    private final double PENNY = 0.01;
    private Inventory inventory;

    //money starting in machine is 0 until customer enters an amount
    private double moneyProvided = 0.00;

    public void setMoneyProvided(double moneyProvided) {
        this.moneyProvided += moneyProvided;
    }

    public double getMoneyProvided() {
        return moneyProvided;
    }

    public VendingMachine () {
        //instantiate new inventory
        this.inventory = new Inventory();
    }

    //methods
    //display contents
    public String printInventoryContents() {
        String inventoryDisplayString = "";
        LinkedHashMap<String, List<Product>> trayMap = inventory.getTrayMap();
        for(Map.Entry<String, List<Product>> specificTray : trayMap.entrySet()) {
            List<Product> inventoryList = specificTray.getValue();
            //this causes indexOutOfBoundsException when product goes to 0 left =(
            //try the isEmpty() to see if the list is empty a.k.a. sold out to print a different line
            if(inventoryList.isEmpty()) {
                System.out.println("SORRY, ITEM " + specificTray.getKey() +  " IS SOLD OUT!");
            } else {
                Product inventoryItem = inventoryList.get(0);
                inventoryDisplayString += specificTray.getKey() + " : " + inventoryItem.getProductName() + " | Price $" + inventoryItem.getProductPrice() + " | " + inventoryItem.getProductType() + " | Remaining Count: " + inventoryList.size() + "\n";
            }
        }
        return inventoryDisplayString;
    }

    //log Items
    //money fed and subtract to be used on log
    public String printLogFedMoney(double totalMoney){
        String salesLog = "";
        if(totalMoney>=0){
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa ");
            Date date = new Date();
            double fedMoney = totalMoney;
            totalMoney += this.moneyProvided;
            salesLog = dateFormat.format(date) + "FEED MONEY: $" + fedMoney + " $" + totalMoney;
        }

        return salesLog;
    }

    // product item given and price is subtracted and used on log
    public String printLogProductSold(String trayID){
        String salesLog="";
        if(!trayID.equals(null)){
            //causes index out of bounds exception
            Product dispensedProduct = inventory.getTrayMap().get(trayID).get(0);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa ");
            Date date = new Date();
            salesLog = dateFormat.format(date) + " "+ dispensedProduct.getProductName() + " " + trayID + " " + dispensedProduct.getProductPrice() + " " + moneyProvided;
        }
        return salesLog;
    }

    //Log for complete transaction
    public String printLogCompleteTransaction(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa ");
        Date date = new Date();
        String salesLog = dateFormat.format(date) + " GIVE CHANGE: $"+ moneyProvided + " $0.00";
        return salesLog;
    }
    //    Total sales report
    public String totalSalesReport() {
        String displaySalesReport = "";
        LinkedHashMap<String, List<Product>> trayMap = inventory.getTrayMap();
        for (Map.Entry<String, List<Product>> specificTray : trayMap.entrySet()) {
            List<Product> inventoryList = specificTray.getValue();
            Product inventoryItem = inventoryList.get(0);
            int finalSize = 5 - inventoryList.size();
            displaySalesReport += inventoryItem.getProductName() + "|" + finalSize + "\n";
        }
        return displaySalesReport;
    }

    //customer purchase
    public void makePurchase(String userInputMapKey) {
        //if (!userInputMapKey null)
        if (!userInputMapKey.equals(null)) {
            //if the choice is not a valid trayID alert and return to purchase menu
            if (!inventory.getTrayMap().containsKey(userInputMapKey)) {
                System.out.println("Invalid entry, please try again");
                //if tray is sold out alert and return to purchase menu
            } else if (inventory.isSoldOut(userInputMapKey)) {
                System.out.println("Sorry, a previous customer was really hungry and ate all of these. Please try another item...");
            } else {
                //get selected product from tray and store its value as a product it can be reprinted
                Product dispensedProduct = inventory.getTrayMap().get(userInputMapKey).get(0);
                //check price of product and make sure <= money provided
                if(dispensedProduct.getProductPrice() <= moneyProvided) {
                    //subtract price of product from moneyProvided
                    moneyProvided -= dispensedProduct.getProductPrice();
                    //remove purchased/dispensed product from the tray
                    inventory.getTrayMap().get(userInputMapKey).remove(0);

                    String dispenseMessage = dispensedProduct.getProductName() + ": $" + dispensedProduct.getProductPrice() + ". You now have: $" + moneyProvided + " left." + "\n";
                    System.out.println(dispenseMessage);
                    System.out.println(dispensedProduct.getDispensedMessage());

                } else {
                    System.out.println("Sorry, you don't have enough money. Please get a job and come back.");
                }

            }
        }

    }

}
