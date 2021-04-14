/**
*	This exception is thrown when column number exceeds the maximum column number.
*
*	@author Group 4
*/

public class InvalidColNumException extends Exception{
	
	public InvalidColNumException(){
		super("Too many columns created, only can create 9 columns");
	}
	
}