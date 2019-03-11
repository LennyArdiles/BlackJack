/**
 *@author: Lenny Ardiles
 *@description: A class for a deck of playing cards
 *@date: 05-05-2001
 *Last Modified: 05-05-2001
 */
package cards;

import java.util.Vector;

public class Deck{
	private Vector cards;		//Contains objects of type Card
	private int numberOfDecks;
	
	/**
	 *Default Constructor; creates a single deck of 52 cards
	 */
	public Deck(){
		this(1);
	}
	
	/**
	 *Constructor takes int as param; creates 52 times that many cards
	 */
	public Deck( int numberOfDecksOfCards ){
		numberOfDecks = numberOfDecksOfCards;
		createCards();
	}
	
	private void createCards(){
		cards = new Vector();
		for( int deck = 0; deck < numberOfDecks; deck++){
			for( int face = 0; face < 4; face++ ){
				for( int val = 2; val <= 14; val++ ){
					cards.add( new Card(val, face) );	//Creates new Card && adds to deck
				}
			}
		}
	}
	
	public int getSizeOfDeck(){
		return cards.size();
	}
	
	public void shuffle(){
		Vector shuffledDeck = new Vector();
		int sizeOfDeck = getSizeOfDeck();
		int remainingCards = sizeOfDeck;
		for( int c = 0; c < sizeOfDeck; c++ ){
			remainingCards = getSizeOfDeck();
			shuffledDeck.add( cards.remove((int)( Math.random()*100%remainingCards )));
		}
		cards = shuffledDeck;
	}
	
	public Card removeTopCard(){
		if(getSizeOfDeck() > 0){ //return
		}else{ 					//no more cards
			createCards();
			shuffle();
		}
		return (Card)cards.remove(0);
	}
	
	public String toString(){
		String s = new String("");
		for( int c = 0; c < cards.size(); c++ ){
			s += cards.get(c);
			if( ((c+1)%4) == 0 )	{ s = s +"\t"+(c+1)+"\n"; }
		}
		return s;
	}
}