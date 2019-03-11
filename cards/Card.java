/**
 *@author: Lenny Ardiles
 *@description: A class for a single playing card
 *@date: 05-05-2001
 *Last Modified: 05-05-2001
 */
package cards;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Card extends Canvas{
	private int value;			//value is an int between 2 and 14
	private int suit;
	private String s;			//suit as string
	private String v;			//value as string
	private boolean showing;	//true if card is visible to players

	public final int SPADE = 0;
	public final int CLUB = 1;
	public final int DIAMOND = 2;
	public final int HEART = 3;
	public final String S = " SPADES ";
	public final String C = " CLUBS  ";
	public final String D = "DIAMONDS";
	public final String H = " HEARTS ";

	/**
	 *Constructor takes numeric value and suit as params
	 *suit could be passed as Card.SPADE, Card.DIAMOND, etc.
	 */
	public Card( int value, int suit ){
		setSize(50,65);
		this.value = value;
		this.suit = suit;
		showing = false;
		if( value < 10 )		{ v = " " + value;}
		else if( value == 10 )	{ v = "10"; }
		else if( value == 11 )	{ v = " J"; }
		else if( value == 12 )	{ v = " Q"; }
		else if( value == 13 )	{ v = " K"; }
		else if( value == 14 )	{ v = " A"; }
		else {
			v = "XX ";
			System.out.println("Should not happen: value = " + value);
		}
		if( suit == SPADE )			{ s = S; }
		else if( suit == CLUB )		{ s = C; }
		else if( suit == DIAMOND )	{ s = D; }
		else if( suit == HEART )	{ s = H; }
		else {
			s = "NO SUIT";
			System.out.println("Should not happen: suit = " + suit);
		}
	}

	public int getValue(){
		return value;
	}

	public int getSuit(){
		return suit;
	}

	public boolean getShowing(){
		return showing;
	}

	public void setShowing( boolean s ){
		showing = s;
	}

	public boolean isFaceCard(){
		if( value > 10 && value < 14 ) { return true; }
		else { return false; }
	}

	public boolean isAce(){
		if( value == 14 ) { return true; }
		else { return false; }
	}

	public String toString(){
		return v + " of " + s;
	}

	public void paint( Graphics g ){
		Graphics2D g2 = (Graphics2D)g;
		RoundRectangle2D card = new RoundRectangle2D.Float( 0F, 0F, 50F, 65F, 15F, 15F);
		if( showing ){
			g2.setPaint(Color.white);
			g2.fill(card);
			if( (suit==SPADE) || (suit==CLUB) ){
				g2.setPaint(Color.black);
			}else{//if( (suit==DIAMOND) || (suit==HEART) ){
				g2.setPaint(Color.red);
			}
			g2.drawString(v, 5, 15);
			g2.drawString(s, 0, 40);
			g2.drawString(v, 30, 60);
		}else{
			g2.setPaint(Color.blue);
			g2.fill(card);
		}

	}
}