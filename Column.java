import java.util.*;

/**
*	This class represents the column in the game.
*
*   This class follows the game rules, so maximum of 9 columns can be created. The rule is 4 columns must have 7 cards, another 4 columns must have 6 cards, the last 1 column is left empty.
*	Exception is thrown when more than 9 columns are created. 
*   If more columns are needed to be created, use of method resetNumOfCol() is required.
*	After the use of method resetNumOfCol(), 9 columns with the same rule can be created again.
*
*	This class also provides 2 move method that follows the game rules.
*
*	@author Group 4
*/
public class Column{

	private List<Card> cardsList = new ArrayList<Card>();
	private static int numOfColOf7Cards = 0;
	private static int numOfColOf6Cards = 0;
	private static int numOfCol = 0;
	
	/**
	*Creates a column.  
	*/
	public Column(){
		try{
			//check if too many columns are created (maximum is 9 columns)
			numOfCol++;
			if (numOfCol > 9)
				throw new InvalidColNumException();
		}
		catch(InvalidColNumException ex){
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	*Creates a column, then add cards into the column.
	*@param amountOfCards  the amount of cards to add into the cardsList.
	*@param filledCards  a list filled with cards used to add cards into the cardsList.
	*/
	public Column(int amountOfCards, List<Card> filledCards){
		this();  //Calls no arg constructor
		
		try{
			//Check if wrong number of cards is added into this column
			if(amountOfCards != 6 && amountOfCards != 7){
				numOfCol--;  //failed to create
				throw new IllegalArgumentException("Amount of cards inserted should be 6 or 7");
			}
			
			//Check if user still inputs 7 cards when already exists 4 columns with 7 cards
			if(numOfColOf7Cards == 4 && amountOfCards == 7){
				numOfCol--;   //failed to create
				throw new IllegalArgumentException("Already have 4 columns with 7 cards");
			}
			
			//Check if user still inputs 6 cards when already exists 4 columns with 6 cards
			if(numOfColOf6Cards == 4 && amountOfCards == 6){
				numOfCol--;   //failed to create
				throw new IllegalArgumentException("Already have 4 columns with 6 cards");
			}
			
			if(amountOfCards == 7)
				numOfColOf7Cards++;
			
			if(amountOfCards == 6)
				numOfColOf6Cards++;
			
			//Add cards to cardsList; the card added is removed from filledCards at the same time.
			for(int i = 0 ; i < amountOfCards ; i++){
				if(!filledCards.isEmpty())
					cardsList.add(filledCards.remove(0));
			}
		}	
		catch(IllegalArgumentException ex){
			ex.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	*Moves the specified card(s) from this column to the end of the specified column.
	*These rules must be follwed:
	*1. The card to be moved must be one point smaller than the last card of the column which the card move to.
	*2. When moving a group of consecutive right-most cards, the cards' face have to be in descending order.
	*@param cardStr  the card to be moved.
	*@param toCol  the column which the card move to.
	*@throws InvalidMoveException  If the specified card moves without following the rules.
	*@throws InvalidCardException  If the specified card is invalid.
	*@throws InvalidColException  If this column is empty.
	*/
	public void move(String cardStr, Column toCol) throws InvalidMoveException, InvalidCardException, InvalidColException{
		Card cardInput = new Card(cardStr.charAt(0),cardStr.charAt(1));
		int numOfCardsToMove = getLastIndex() - getIndexOf(cardInput) + 1;
		int cardIndex = getIndexOf(cardInput);
		
		//check if the card inputted is in this column
		if(!(contain(cardInput)))
			throw new InvalidCardException();
		
		//check if this column is empty
		else if(cardsList.isEmpty())
			throw new InvalidColException();
		
		//move to empty column, so no need to check if the last card of the column is 1 bigger than the card you want to move
		else if(toCol.cardsList.isEmpty() && inDescOrder(getIndexOf(cardInput))){
				for(int i = 0 ; i < numOfCardsToMove ; i++)
					toCol.cardsList.add(cardsList.remove(cardIndex));
		}
		
		//move a group of cards, will check if the cardInput is one point smaller than the last card of toCol and faces of the group of cards is in descending order
		else if(cardInput.oneValueBigger(toCol.cardsList.get(toCol.getLastIndex())) == -1 && inDescOrder(getIndexOf(cardInput))){
				for(int i = 0 ; i < numOfCardsToMove ; i++)
					toCol.cardsList.add(cardsList.remove(cardIndex));
		}

		else
			throw new InvalidMoveException();
	}
	
	/**
	*Moves the specified card(s) from this column to the end of the specified pile.
	*These rules must be follwed:
	*1. The card to be moved must be one point bigger than the last card of the pile which the card move to.
	*2. When moving a group of consecutive right-most cards, the cards' face have to be in descending order.
	*@param cardStr  the card to be moved.
	*@param toPile  the pile which the card move to.  
	*@param key  the suit of the pile which the card move to.
	*@throws InvalidMoveException  If the specified card moves without following the rules.
	*@throws InvalidCardException  If the specified card is invalid.
	*@throws InvalidColException  If this column is empty.
	*@throws InvalidPileException  If the specified card's suit does not match the specified pile's suit. 
	*/
	public void move(String cardStr, Piles toPile, String key) throws InvalidMoveException, InvalidCardException, InvalidColException, InvalidPileException{
		
		Card cardInput = new Card(cardStr.charAt(0),cardStr.charAt(1));
		int numOfCardsToMove = getLastIndex() - getIndexOf(cardInput) + 1;
		int cardIndex = getIndexOf(cardInput);
		
		if(!(contain(cardInput)))
			throw new InvalidCardException();
		
		else if(cardsList.isEmpty())
			throw new InvalidColException();
		
		//make sure the card is in sequence when moving many cards
		else if(inDescOrder(getIndexOf(cardInput))){
			for(int i = 0 ; i < numOfCardsToMove ; i++){
				toPile.add(cardsList.get(getLastIndex()), key);
				cardsList.remove(getLastIndex());
			}
		}
		
		else
			throw new InvalidMoveException();	
	}
	
	//Returns true if and only if the face of the group of cards in this column is in descending order.
	private boolean inDescOrder(int n){
		if(n == getLastIndex())  //base case
			return true;
		else if (cardsList.get(n).oneValueBigger(cardsList.get(n + 1)) != 1)
			return false;
		else
			return inDescOrder(n + 1);
	}
	
	//Returns last index of this column.
	private int getLastIndex(){
		return cardsList.size() - 1;
	}
	
	//Returns the index within this column of the first occurrence of the specified card.
	private int getIndexOf(Card card){
		for(Card cardObject : cardsList)  {
			if(cardObject.equal(card))
				return cardsList.indexOf(cardObject);
		}
		return -1;
	}
	
	//Returns true if only if this column contains the specified card.
	private boolean contain(Card card){
		for(Card cardObject : cardsList){
			if(cardObject.equal(card))
				return true;
		}
		return false;
	}
	
	/**
	*Sets the number of columns created to 0. 
	*/
	public static void resetNumOfCol(){
		numOfCol = 0;
		numOfColOf7Cards = 0;
		numOfColOf6Cards = 0;
	}
	
	/**
	*Moves the last element of this column to the front of this column.
	*@throws InvalidColException  If this column is empty.
	*/
	public void rotateCol() throws InvalidColException{
		if(cardsList.isEmpty())
			throw new InvalidColException();
		else
			cardsList.add(0, cardsList.remove(cardsList.size() - 1));
	}
	
	/**
	*Returns the string representation of this column. The format is same as the toString method of class java.util.AbstractCollection.
	*@return a string representation of this column. 
	*/
	public String toString(){
		return cardsList.toString();
	}

}