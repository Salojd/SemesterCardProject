/**
 * This is the entire Playing Cards 24 package including the game and its tests
 */

package playingcards24;

import javafx.scene.image.Image;

/**
 *
 * BCS 345 Semester Project
 * 
 * Prof. Alrajab
 * 
 * @author Peter Schellhorn, Brett Silver, Jonathan Saloman, Aayushma Pal
 * 
 * @version 1.0
 * 
 *      Description: This application randomly generates four different cards from a deck. 
 *      The deck contains four suits which includes clubs, diamonds, hearts, and spades. 
 *      Each card contains a numbered value. For example, the value of Ace no matter which suit is picked is 1, 
 *      the value of two of whichever suit is 2, Queen is 12, King is 13, etc. The objective of this game is to use these four randomly
 *      generated cards to implement them into a math equation that results in the value of 24. You can either add, subtract, 
 *      multiply, or divide when creating a math equation.
 * 
 * This class has all the attributes of each card object
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


