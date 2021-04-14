/**
*	This exception is thrown when suit of card input does not match the pile's suit.
*
*	@author Group 4
*/

public class InvalidPileException extends Exception{
	
	public InvalidPileException(){
		super("Invalid Pile!");
	}
	
}