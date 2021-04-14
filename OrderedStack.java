import java.util.*;

/**
*	This class represents a last-in-first-out stack of objects that extends Card. It represents a pile in the FreeCell game.
*	This class is similiar to Stack class, but the push method follows the game rules.
*
*	@author Group 4
*/
public class OrderedStack <E extends Card>{
	
	private List<E> orderedStack = new ArrayList<>();
	private int size = 0;
	private String suit;
	
	/**
	*Creates an empty orderedStack.
	*@param suit  the suit of card this orderedStack want to contain.
	*/
	public OrderedStack(String suit){
		this.suit = suit;
	}
	
	/**
	*Push an item onto the top of this orderedStack. 
	*These rules must be followed:
	*1. The suit of the card to be pushed and the suit of orderedStack must match
	*2. The face value of the card to be pushed must one point bigger than the card on the top of this orderedStack.
	*@param cardInput  the item to be pushed onto this orderedStack.
	*@throws InvalidMoveException  If the specified card is pushed onto without following the rules.
	*@throws InvalidPileException  If the specified card's suit does not match this OrderedStack's suit.
	*/
	public void push(E cardInput) throws InvalidMoveException, InvalidPileException{
		//first confirm the card and the pile is in same suit
		if(cardInput.getSuit() == suit.charAt(0)){
			//if the pile is empty and the card's face is A, directly add the card 
			if(empty()){
				if(cardInput.getFace() == 'A'){
					orderedStack.add(cardInput);
					size++;
				}
				else
					throw new InvalidMoveException();
			}
				
			//make sure the card is one point bigger than the last card in orderedStack before adding in
			else if(cardInput.oneValueBigger(peek()) == 1){
				orderedStack.add(cardInput);
				size++;
			}
			
			else
				throw new InvalidMoveException();
			
		}
		
		else{
			System.out.println("**suit not matched, wrong Pile**");
			throw new InvalidPileException();
		}
	}
	
	/**
	*Looks at the item at the top of this orderedStack without removing it from the stack.
	*@return the item at the top of this orderedStack.
	*@throws EmptyStackException  If the stack is empty.
	*/
	public E peek(){
		if(empty())
			throw new EmptyStackException();  //runtime exception
		else
			return orderedStack.get(size - 1);
	}
	
	/**
	*Returns true if this orderedStack is empty.
	*@return true if and only if this stack contains no items; false otherwise.
	*/
	public boolean empty(){
		return size == 0;
	}
	
	/**
	*Returns the string representation of this OrderedStack. The format is same as the toString method of class java.util.Vector.
	*@return a string representation of this OrderedStack.
	*/
	public String toString(){
		return orderedStack.toString();
	}
}




