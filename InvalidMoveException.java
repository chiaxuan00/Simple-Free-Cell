/**
*	This exception is thrown when the card is commanded to move but without following the rules.
*
*	@author Group 4
*/

public class InvalidMoveException extends Exception{
	
	public InvalidMoveException(){
		super("Invalid Move!");
	}
	
}