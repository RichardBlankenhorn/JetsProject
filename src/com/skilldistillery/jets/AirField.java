package com.skilldistillery.jets;

public class AirField {

	private Jet[] jets;
	private int index = 0;

	public AirField() {

	}

	public void addJet(Jet jet) {
		jets[index] = jet;
		index++;
	}

	public Jet[] getJets() {
		return jets;
	}

	public void setJetsArr(int num) {
		this.jets = new Jet[num];
	}

}
