package com.techelevator.view;

public class Product {

    private String productName;
    private double productPrice;
    private String productType;

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public Product(String productName, double productPrice, String productType) {

        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;

    }

    public String getDispensedMessage () {
        String message = "";
        //check product type
        if (productType.equals("Chip")) {
            message = "Crunch Crunch, Yum!";
        } else if (productType.equals("Candy")) {
            message = "Munch Munch, Yum!";
        } else if (productType.equals("Drink")) {
            message = "Glug Glug, Yum!";
        } else {
            message = "Chew Chew, Yum!";
        }
        return message;

        }
    }