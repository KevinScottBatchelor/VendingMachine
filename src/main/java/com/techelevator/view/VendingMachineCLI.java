package com.techelevator.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VendingMachineCLI extends Inventory {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
//	private static final String SALES_REPORT_OPTION = "4"; //this constant is for hidden option
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};

	//These are the purchase menu options for option 2 from MAIN MENU OPTIONS
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	//create new object menu from Menu class
	private Inventory inventory;
	private Menu menu;
	private VendingMachine vendingMachine;
	private Scanner purchaseMenuInput = new Scanner(System.in);
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.vendingMachine = new VendingMachine();

	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				//present list of all items in the vending machine with the quantity remaining
				//use method in vending machine to display the inventory contents
				System.out.println(vendingMachine.printInventoryContents());

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

				System.out.println("Current Money Provide: $" + vendingMachine.getMoneyProvided());

				// show purchase options using menu class with purchase_menu_options[]
				String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

				//changed choice to Purchase menu options
				if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)){
					try {
						System.out.print("How much would you like to enter? (Whole $ amount only! >>> ");
						String moneyInserted = purchaseMenuInput.nextLine();
						System.out.println("You inserted: $" + moneyInserted);


						//Creating Log for money fed on vending machine class. printLogFedMoney method
						File inputFile = new File("log.txt");
						try(BufferedWriter out = new BufferedWriter(new FileWriter(inputFile,true))){

							vendingMachine.printLogFedMoney(Double.parseDouble(moneyInserted));
							out.write(vendingMachine.printLogFedMoney(Double.parseDouble(moneyInserted)));
							out.newLine();
							out.flush();
						}catch(IOException err){
							System.out.println("We've encountered an issue. Sorry!" + err.getMessage());
						}

						vendingMachine.setMoneyProvided(Double.parseDouble(moneyInserted));


					}catch(NullPointerException e){
						System.out.println("User Input value reference error");
					}
				}else if(purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT )){
					//select product method && lower our inventory count
					System.out.println(vendingMachine.printInventoryContents());
					System.out.print("Select tray ID >>> ");
					String traySelection = purchaseMenuInput.nextLine();

					System.out.print("You selected: " + traySelection + ", ");
					vendingMachine.makePurchase(traySelection);

					//sending trayID into vendingMachine
					File inputFile = new File("log.txt");
					try(BufferedWriter out = new BufferedWriter(new FileWriter(inputFile, true))){
						vendingMachine.printLogProductSold(traySelection);
						out.write(vendingMachine.printLogProductSold(traySelection));
						out.newLine();
					}catch(IOException err){
						System.out.println("File chosen is faulty or non-existent. " + err.getMessage());
					}

				}else if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)){
					//change needed and exit back to main menu option with moneyProvided being updated back to $0
					double changeNeeded = vendingMachine.getMoneyProvided();
					System.out.println("Here's your change: $" + changeNeeded + ". Please come again!");
					//output into log.txt
					File inputFile = new File("log.txt");
					try(BufferedWriter out = new BufferedWriter(new FileWriter(inputFile, true))){
						vendingMachine.printLogCompleteTransaction();
						out.write(vendingMachine.printLogCompleteTransaction());
						out.newLine();
					}catch(IOException err){
						System.out.println("File chosen is faulty or non-existent. " + err.getMessage());
						System.exit(0);
					}

				}

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				//exit and reset starting count to the 5
				System.out.println("Stay hungry, please come back next time!");
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();

	}

}