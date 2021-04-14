/**
*	This exception is thrown when column is invalid or trying to move cards from a empty column.
*
*	@author Group 4
*/

public class InvalidColException extends Exception{
	
	public InvalidColException(){
		super("Invalid Column!");
	}
	
}