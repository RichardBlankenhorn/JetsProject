package com.skilldistillery.jets;

public class Bomber extends Jet{

	public Bomber() {
		super();
	}

	public Bomber(String model, double speed, int range, long price) {
		super(model, speed, range, price);
	}
	
	public void bomb() {
		System.out.println("We are bombing our objective now!!!");
	}
	
	
}
