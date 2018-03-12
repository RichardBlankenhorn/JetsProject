package com.skilldistillery.jets;

import java.util.Scanner;

public class JetsApplication {

	private int answer, rg; // Saved Response to Scanner and Field Range for Jets
	private double spd;     // Field Speed for new Jets
	private long pc;        // Field Price for new Jets
	private String mod;     // Field Model for new Jets
	
	private CargoCarrier[] carriers; // Array to hold Cargo Planes
	private AirField airField;

	static Scanner scanner = new Scanner(System.in);

	// Run the application
	public static void main(String[] args) {
		JetsApplication app = new JetsApplication();
		app.launch();
	}

	// This method is called from MAIN and starts the application.
	// Several Jets are created and added to the AirField (airField)
    // The user is continuously prompted to make selections until they exit the program
	private void launch() {
		
		// Create new AirField and set array to 6 slots
		airField = new AirField();
		airField.setJetsArr(6);

		// Create 6 new Jets
		Jet fighter = new FighterJet("F150", 300, 1000, 1_000_000);
		Jet cargoCarrier = new CargoPlane("Big Duty Cargo", 200, 3000, 10_000_000);
		Jet speedFighter = new FighterJet("Blue Angel", 500, 2000, 2_000_000);
		Jet dreamlifter = new CargoPlane("Dreamlifter", 400, 4000, 12_000_000);
		Jet stealthBomber = new Bomber("Stealth Bomber", 800, 10000, 22_000_000);
		Jet jet = new JetImpl("F-153 Strike Eagle", 600, 5000, 9_000_000);

		// Add Jets to the AirField
		airField.addJet(fighter);
		airField.addJet(cargoCarrier);
		airField.addJet(speedFighter);
		airField.addJet(dreamlifter);
		airField.addJet(stealthBomber);
		airField.addJet(jet);

		// Loop through the Menu making selections until user quits
		while (true) {
			makeSelection();
			actOnSelection(airField);
			System.out.println("\nYou may make another selection");
		}
	}

	// This method will display the User Menu each time it is called
	private void displayUserMenu() {
		System.out.println("Please select from the following options:");
		System.out.println(
				"1. List Fleet\n" + "2. Fly all jets\n" + "3. View fastest jet\n" + "4. View jet with longest range\n"
						+ "5. Load all Cargo jets\n" + "6. Dogfight!\n" + "7. Add a jet to the Fleet\n" + "8. Quit");
	}

	// This method extracts a response from the user for the User Menu
	// If the response is a letter, the user will be prompted again
	// If the number is out of range, the user will be prompted again
	public void makeSelection() {
		String response = "";
		answer = 0;
		while (answer == 0) {
			displayUserMenu();
			while (!scanner.hasNextInt()) {
				response = scanner.next();
				System.out.print("The response of " + response + " is not valid\n");
				displayUserMenu();
			}
			answer = scanner.nextInt();
			if (answer > 8 || answer < 1) {
				answer = 0;
				System.out.println("The number provided was out of range");
			}
		}
	}

	// This method accepts an AirField object and then checks the current value of answer
	// Depending on the value of answer, a different method is called to perform a function or task
	public void actOnSelection(AirField airField) {
		if (this.answer == 1) {
			listJets(airField);
		} else if (this.answer == 2) {
			flyJets(airField);
		} else if (this.answer == 3) {
			fastestJet(airField);
		} else if (this.answer == 4) {
			longestRange(airField);
		} else if (this.answer == 5) {
			getCarrierNum(airField);
			addCarriers(airField);
		} else if (this.answer == 7) {
			makeAddSelection();
			obtainJetInfo();
			addJet(airField);
		} else if (this.answer == 8) {
			System.out.println("Thank you and Goodbye!");
			System.exit(0);
		}
	}

	// This method will list all of the Jets in the AirField when called
	public void listJets(AirField airField) {
		System.out.println("\n************************************************");
		System.out.println("The list of jets in the airfield is as follows:");
		System.out.println("************************************************");
		for (int i = 0; i < airField.getJets().length; i++) {
			System.out.println(i + 1 + ": " + airField.getJets()[i].toString());
		}
	}

	// This method will call the fly() method of all the Jets in the AirField
	public void flyJets(AirField airField) {
		for (int i = 0; i < airField.getJets().length; i++) {
			airField.getJets()[i].fly();
		}
	}

	// This method will calculate the fastest Jet in the AirField
	public void fastestJet(AirField airField) {
		int fastest = 0;
		for (int i = 0; i < airField.getJets().length; i++) {
			if (airField.getJets()[fastest].getSpeed() < airField.getJets()[i].getSpeed()) {
				fastest = i;
			}
		}
		System.out.println("\nThe fastest jet in the fleet is the " + airField.getJets()[fastest].getModel()
				+ " and this jets speed is the " + airField.getJets()[fastest].getSpeed() + " MPH!");
	}

	// This method will calculate the Jet with the longest range in the AirField
	public void longestRange(AirField airField) {
		int longest = 0;
		for (int i = 0; i < airField.getJets().length; i++) {
			if (airField.getJets()[longest].getRange() < airField.getJets()[i].getRange()) {
				longest = i;
			}
		}
		System.out.println("\nThe jet in the fleet with the longest range is " + airField.getJets()[longest].getModel()
				+ " and this jets range is " + airField.getJets()[longest].getRange() + " Miles!");
	}

	// This method goes through the current AirField object and determines the number
	// of Jets that are considered Cargo Plans
	// The method carrierNew(count) is then called passing the total number of Cargo Planes.
	public void getCarrierNum(AirField airField) {
		int count = 0;
		for (int i = 0; i < airField.getJets().length; i++) {
			String name = airField.getJets()[i].getClass().getSimpleName();
			if (name.equals("CargoPlane")) {
				count++;
			}
		}
		carrierNew(count);
	}

	// This method, called from the method getCarrierNum(), creates a new Carrier array
	// to hold Jets that are Cargo Planes only
	public void carrierNew(int howMany) {
		carriers = new CargoCarrier[howMany];
	}

	// This method adds Cargo Planes only to the carriers array and also calls each of their loadCargo()
	// methods.
	public void addCarriers(AirField airField) {
		int index = 0;
		for (int i = 0; i < airField.getJets().length; i++) {
			String name = airField.getJets()[i].getClass().getSimpleName();
			if (name.equals("CargoPlane")) {
				carriers[index] = (CargoPlane) (airField.getJets()[i]);
				carriers[index].loadCargo();
				index++;
			}
		}
	}

	// This method will add a new Jet to the AirField
	// A new airField array is created with the current count plus one additional space
	// The Jets in the old airField are then added to the new sequentially
	// Lastly, the recently created Jet is added to the array using the fields mod, spd, rg and pc
	public void addJet(AirField af) {
		int len = af.getJets().length;
		airField = new AirField();
		airField.setJetsArr(len + 1);
		for (int i = 0; i < af.getJets().length; i++) {
			airField.addJet(af.getJets()[i]);
		}
		if (answer == 1) {
			Jet jet1 = new CargoPlane(mod, spd, rg, pc);
			airField.addJet(jet1);
		} else if (answer == 2) {
			Jet jet1 = new FighterJet(mod, spd, rg, pc);
			airField.addJet(jet1);
		} else if (answer == 3) {
			Jet jet1 = new Bomber(mod, spd, rg, pc);
			airField.addJet(jet1);
		} else if (answer == 4) {
			Jet jet1 = new JetImpl(mod, spd, rg, pc);
			airField.addJet(jet1);
		}
		System.out.println("The jet has been added to the airfield!\n");

	}

	// This method will print out a menu when the user elects to add a new Jet
	public void addJetMenu() {
		System.out.println("Which type of Jet would you like to add to the AirField?");
		System.out.println("1: Cargo Plane\n2: Figher Jet\n3: Bomber\n4: Standard Jet");
	}

	// This method prompts the user to input information for the Jet
	// The user input is stored in the private fields
	public void obtainJetInfo() {
		System.out.println("Please provide the jet model: (Do not include spaces) ");
		mod = scanner.next();
		System.out.println("Please provide the jet speed: ");
		spd = scanner.nextDouble();
		System.out.println("Please provide the jet range: ");
		rg = scanner.nextInt();
		System.out.println("Please provide the jet price: ");
		pc = scanner.nextLong();
	}

	// This method insures that the user chooses an option from the Add Jet menu
	// that is valid and within the required range
	public void makeAddSelection() {
		String response = "";
		answer = 0;
		while (answer == 0) {
			addJetMenu();
			while (!scanner.hasNextInt()) {
				response = scanner.next();
				System.out.print("The response of " + response + " is not valid\n");
				addJetMenu();
			}
			answer = scanner.nextInt();
			if (answer > 4 || answer < 1) {
				answer = 0;
				System.out.println("The number entered is out of range");
			}
		}
	}

}
