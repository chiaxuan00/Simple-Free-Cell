import java.util.*;
import java.util.Map.Entry;

/**
*	This class represents the four piles in the FreeCell game with suit c, d, h, s. The four piles can be accessed using their respective suit.
*	The add method follows the rules of the game.
*
*	@author Group 4
*/
public class Piles{
	
	private Map<String, OrderedStack<Card>> maps = new LinkedHashMap<>();
	private OrderedStack<Card> clubs;
	private OrderedStack<Card> diamonds;
	private OrderedStack<Card> hearts;
	private OrderedStack<Card> spades;
	
	/**
	*Creates four piles with suit c, d, h, s.
	*/
	public Piles(){
		
		maps.put("c", new OrderedStack<Card>("c"));
		maps.put("d", new OrderedStack<Card>("d"));
		maps.put("h", new OrderedStack<Card>("h"));
		maps.put("s", new OrderedStack<Card>("s")); 
		
		//obtain reference(address)
		clubs = maps.get("c");  
		diamonds = maps.get("d");
		hearts = maps.get("h");
		spades = maps.get("s");
	
	}
	
	/**
	*Returns the specified pile.
	*@param suit  suit of the pile.
	*@return the specified pile.
	*/
	public OrderedStack<Card> getPile(String suit){
		return maps.get(suit);
	}
	
	/**
	*Add a card into the end of the specified pile.
	*These rules must be followed:
	*1. The suit of the card to be pushed and the key of orderedStack must match
	*2. The face value of the card to be pushed must one point bigger than the card on the top of this orderedStack.
	*@param c  the card to add into the specified pile.
	*@param suit  suit of the pile.
	*@throws InvalidMoveException  If the specified card is added without following the rules.
	*@throws InvalidPileException  If the specified card's suit does not match the specified pile's suit.
	*/
	public void add(Card c, String suit) throws InvalidMoveException, InvalidPileException{
		maps.get(suit).push(c);
	}
	
	/**
	*Returns true if and only if the game is solved.
	*@return true if and only if the game is solved; false otherwise.
	*/
	public boolean win(){
		try{
			//check if the top card's face of all four orderedStack is K
			if(maps.get("c").peek().getFace() == 'K' && maps.get("d").peek().getFace() == 'K' && maps.get("h").peek().getFace() == 'K' && maps.get("s").peek().getFace() == 'K'){
				System.out.println("!!!!!!!!YOU WIN!!!!!!!!");
				return true;
			}
			else 
				return false;
		}
		catch(EmptyStackException ex){
			return false;
		}
	}
	
	/**
	*Returns the string representation of this pile. The format is "Pile   " + key + ": " + OrderedStack + "\n".
	*@return a string representation of this piles.
	*/
	@Override
	public String toString(){
		String s = "";
		Set<Entry<String, OrderedStack<Card>>> entries = maps.entrySet();
			
		for(Map.Entry<String, OrderedStack<Card>> entry : entries){
			s = s + "Pile   " + entry.getKey() + ": " + entry.getValue() + "\n";
		}
		return s;
	}
	
	
	
}