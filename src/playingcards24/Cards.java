/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playingcards24;

import javafx.scene.image.Image;

/**
 *
 * @author peterschellhorn
 */
public class Cards {
    // Variables for Card Object
	private String number, type;
	private Image image;
	private int value;

	public Cards() {
	}

	// Constructor for Card Object
	public Cards(String n, String t, int v) {
		this.number = n;
		this.type = t;
		this.value = v;
		image = new Image("/CardImages/" + this.number + "_of_" + this.type + ".png");
	}

	// Getters for Card Object
	public String getNumber() {
		return this.number;
	}

	public String getType() {
		return this.type;
	}

	public Image getImage() {
		return this.image;
	}

	public int getValue() {
		return this.value;
	}

	// Setters for Card object
	public void setNumber(String n) {
		this.number = n;
	}

	public void setType(String t) {
		this.type = t;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setValue(int value) {
		this.value = value;
	}

	// ToString for Card Object
	public String toString() {
		return this.number + "_of_" + this.type;
	}
}


