import java.util.*;

/**
*   This class represents the FreeCell game.
*
*	@author Group 4
*/
public class Game{
	public static void main (String[] args){
		
		Scanner input = new Scanner(System.in);
		
		//Declaration	
		boolean win = false;
		boolean restart = false;
		int rotate = -1;  
		
		String fromStr = "";      //store 1st input (from column)
		String cardStr = "";      //store 2nd input (card)
		String toStr = "";        //store 3rd input (to column)
		int fromInt = -1;         //store fromStr in int
		int toInt = -1;           //store toStr in int
		String tempStr = "";
		
		char[] faceArr = {'A','2','3','4','5','6','7','8','9','X','J','Q','K'};
	//	char[] faceArr2 = {'K','Q','J','X','9','8','7','6','5','4','3','2','A'};
		char[] suitArr = {'c','d','h','s'};
		
		//Create a list to store all 52 cards
		List<Card> filledCards = new ArrayList<>();
		for(int i = 0 ; i < 4 ; i++){
			for(int j = 0 ; j < 13 ; j++){
				filledCards.add(new Card(suitArr[i],faceArr[j]));
			}
		}
		Collections.shuffle(filledCards);  //Shuffle the cards

		Piles piles = new Piles(); //Create piles
		
		//Create 9 columns and add cards
		Column[] column = new Column[9];
		for(int i = 0 ; i < 4 ; i++)
			column[i] = new Column(7, filledCards);
		for(int i = 4 ; i < 8 ; i++)
			column[i] = new Column(6, filledCards);
		column[8] = new Column();
		
		System.out.println(" COMMANDS to play: ");
		System.out.println("+---------------------+--------------------------------------------------------------+---------+");
		System.out.println("|       action        |                            command                           | example |");
		System.out.println("+---------------------+--------------------------------------------------------------+---------+");
		System.out.println("| move card to column | [from which column] [card wanted to move] [to which column]  | 1 cA 2  |");
		System.out.println("| move card to pile   | [from which column] [card wanted to move] [to which pile]    | 1 cA c  |");
		System.out.println("| column rotation     | r[column you want to rotate]                                 | r1      |");
		System.out.println("| restart game        | r                                                            | r       |");
		System.out.println("| exit game           | x                                                            | x       |");
		System.out.println("+---------------------+--------------------------------------------------------------+---------+");
		System.out.println();
		
		do{
			restart = false;  //reset to false everytime
			
			//Print the piles and columns
			System.out.print(piles);
			for(int i = 0 ; i < 9 ; i++){
				System.out.println("Column " + (i+1) + ": " + column[i]);
			}
			
			//Check if the game is solved. Ends the do while loop if true.
			win = piles.win();
			if(win)
				continue;
			
			System.out.print("Command > ");
			//Get the user input then interpret and check the validity of it
			try{
				//////////////////get 1st input and check//////////////////
				fromStr = input.next().toLowerCase();  
				//If not rotate command but not only input 1 character
				if(fromStr.length() != 1 && !(fromStr.length() == 2 && fromStr.charAt(0) == 'r' && fromStr.charAt(1) > '0' && fromStr.charAt(1) <= '9'))
					throw new InvalidCmdException();  //invalid command
				//Check if 1st character is 'r' and 2nd character is numeric character are inputted.
				else if(fromStr.length() == 2 && (fromStr.charAt(0) == 'r') && (fromStr.charAt(1) > '0' && fromStr.charAt(1) <= '9')){
					rotate = Integer.parseInt(fromStr.substring(1,2));
					column[rotate - 1].rotateCol();  //rotate the column
					System.out.println();
					continue;  //jumps to the beginning of the loop(skip next 2 inputs)
				}
				else if(fromStr.charAt(0) == 'x')
					System.exit(0);
				else if(fromStr.charAt(0) == 'r')
					restart = true;
				//If numeric character is inputted, convert to int type
				else if(fromStr.charAt(0) > '0' && fromStr.charAt(0) <= '9')
					fromInt = Integer.parseInt(fromStr);
				else if(fromStr.charAt(0) == '0')
					throw new InvalidColException();  //invalid column
				else
					throw new InvalidCmdException();  
				
				//If 'r' is inputted, shuffle the cards and restart the game
				if(restart){
					System.out.println();
					
					//add cards to filledCards again and then shuffle
					for(int i = 0 ; i < 4 ; i++){
						for(int j = 0 ; j < 13 ; j++){
							filledCards.add(new Card(suitArr[i],faceArr[j]));
						}
					}
					Collections.shuffle(filledCards);
					
					piles = new Piles();
					
					Column.resetNumOfCol(); //reset numOfCol to 0 before creating new columns
					column = new Column[9];
					for(int i = 0 ; i < 4 ; i++)
						column[i] = new Column(7, filledCards);
					for(int i = 4 ; i < 8 ; i++)
						column[i] = new Column(6, filledCards);
					column[8] = new Column();
					
					continue;  //jumps to the beginning of the loop
				}

				
				
				//////////////////get 2nd input and check//////////////////
				tempStr = input.next();  //temporarily store the string
				if(tempStr.length() != 2)
					throw new InvalidCmdException();  
				cardStr = "" + tempStr.toLowerCase().charAt(0) + tempStr.toUpperCase().charAt(1);
				if(cardStr.charAt(0) != 'c' && cardStr.charAt(0) != 'd' && cardStr.charAt(0) != 'h' && cardStr.charAt(0) != 's')
					throw new InvalidCardException();
				else if(cardStr.charAt(1) <= '0' && cardStr.charAt(1) > '9')
					throw new InvalidCardException();
				
				
				
				//////////////////get 3rd input and check//////////////////
				toStr = input.next().toLowerCase();
				if(toStr.length() != 1)
					throw new InvalidCmdException();  
				if(toStr.charAt(0) != 'c' && toStr.charAt(0) != 'd' && toStr.charAt(0) != 'h' && toStr.charAt(0) != 's' && !(toStr.charAt(0) > '0' && toStr.charAt(0) <= '9')){
					throw new InvalidCmdException();
				}
				//If numeric character is inputted, means the card is moved to a column
				else if(toStr.charAt(0) > '0' && toStr.charAt(0) <= '9'){ 
					toInt = Integer.parseInt(toStr);  //convert to int type
					column[fromInt - 1].move(cardStr, column[toInt - 1]);  //move card to column
				}
				else{
					column[fromInt - 1].move(cardStr, piles, toStr);  //move card to pile
				}
			}
			catch(InvalidCmdException | InvalidColException | InvalidCardException | InvalidPileException | InvalidMoveException ex){
				System.out.println(ex);
			}
			
			System.out.println();
			
		}while(!win);	//loop if game is not solved
	}
}