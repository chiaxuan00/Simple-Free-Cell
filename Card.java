import java.util.*;

/**
*	This class represents a card.
*
*	@author Group 4
*/
public class Card {
	
	private char suit;
	private char face;
	private int faceValue;
	
	/**
	*Creates a card.
	*@param suit  suit of the card.
	*@param face  face of the card.
	*/
	public Card(char suit,char face){
		this.suit = suit;
		this.face = face;
	}
	
	/**
	*Returns the point value based on face of this card.
	*@return the int point value of this card's face.
	*/
	public int getFaceValue(){
		switch(face){
			case 'A':
				faceValue = 1;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				faceValue = Character.getNumericValue(face);
				break;
			case 'X':
				faceValue = 10;
				break;
			case 'J':
				faceValue = 11;
				break;
			case 'Q':
				faceValue = 12;
				break;
			case 'K':
				faceValue = 13;
				break;
		}
		return faceValue;
	}

	/**
	*Returns the card's suit.
	*@return card's suit
	*/
	public char getSuit(){
		return suit;
	}
	
	/**
	*Returns the card's face.
	*@return card's face
	*/
	public char getFace(){
		return face;
	}
	
	/**
	*Returns the string representation of this card. The format is suit + face.
	*@return a string representation of this card.
	*/
	@Override
	public String toString(){
		return new StringBuilder().append(suit).append(face).toString();
	}
	
	/**
	*Compares this card with the specified card. Returns 1 or -1 as this card's face is one point bigger or one point smaller than the specified card. Returns 0 for other cases.
	*@param c  the card to be compared.
	*@return 1 or -1 as this card's face is one point bigger or one point smaller than the specified card. 0 for other cases.
	*/
	public int oneValueBigger(Card c){
		//compare values between 2 cards using getFaceValue()
		if(getFaceValue() - 1 == c.getFaceValue())   //bigger 1
			return 1;
		else if (getFaceValue() + 1 == c.getFaceValue())   //smaller 1 
			return -1;
		else
			return 0;
	}
	
	/**
	*Returns true if and only if the face and suit of the card is equal to the specified card.
	*@param c  the card with which to compare.
	*@return true if this card is the same as the c argument; false otherwise.
	*/
	public boolean equal(Card c){
		if(face == c.face && suit == c.suit)
			return true;
		else
			return false;
	}
	
	
}

