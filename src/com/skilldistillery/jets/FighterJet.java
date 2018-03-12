package com.skilldistillery.jets;

public class FighterJet extends Jet {

	public FighterJet() {

	}

	public FighterJet(String model, double speed, int range, long price) {
		super(model, speed, range, price);
	}

	public void fight() {
		System.out.println("Are you ready " + super.getModel() + ". We are going in to battle! Lock and load!!!!");
	}

}
