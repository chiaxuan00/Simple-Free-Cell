/**
*	This exception is thrown when the card chosen is invalid or not in the column specified.
*
*	@author Group 4
*/

public class InvalidCardException extends Exception{
	
	public InvalidCardException(){
		super("Invalid Card!");
	}
	
}