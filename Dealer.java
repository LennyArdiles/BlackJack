/**
 *@author: Lenny Ardiles
 *@description: A class for a single playing card
 *@date: 05-05-2001
 *Last Modified: 05-05-2001
 */
import java.util.Vector;
import cards.*;
import javax.swing.*;

public class Dealer{
	BlackJackFrame bj;
	private String name;
	private Deck cards;
	public Vector dealerCards;
	public Vector playerCards;
	private int turn;
	public static final int DEALER = 0;
	public static final int PLAYER = 1;
	public int state;
	public static final int BID = 0;
	public static final int DEAL = 1;
	public static final int PLAY = 2;
	public static final int DEALERSPLAY = 3;
	public static final int ENDHAND = 4;
	private int result;
	private static final int NOTDETERMINED = 0;
	private static final int LOST = 1;
	private static final int LOSTDOUBLE = 2;
	private static final int WON = 3;
	private static final int WONDOUBLE = 4;
	private static final int PUSH = 5;
	private static final int WONBLACKJACK = 6;
	private boolean isStakesDoubled;

	public Dealer( BlackJackFrame bj ){
		this("Dealer", bj);
	}

	public Dealer( String name, BlackJackFrame bj ){
		this.bj = bj;
		cards = new Deck(4);
		playerCards = new Vector();
		dealerCards = new Vector();
		shuffle();
		turn = PLAYER;
		state = BID;
		result = NOTDETERMINED;
		isStakesDoubled = false;
	}

	public String getName(){
		return name;
	}

	public void setNumberOfDecks( int numOfDecks ){
		cards = new Deck( numOfDecks );
		shuffle();
	}

	public void shuffle(){
		cards.shuffle();
	}

	public Vector getPlayerCards(){
		return playerCards;
	}

	public Vector getDealerCards(){
		return dealerCards;
	}

	public void deal(){
		if( validateBet() ){
			removeCards();
			turn = PLAYER;
			state = DEAL;
			result = NOTDETERMINED;
			isStakesDoubled = false;
			for( int d = 0; d < 4; d++) {	//Deales 4 cards
				hit();
				nextTurn();			//Alternates who to hit to
			}
			state = PLAY;
			if(!isBlackJack()){
				//if Dealer shows Ace,ask about Insurance;
				checkStateOfGame();
			}
			if( state == ENDHAND ){
				endHand();
			}
		}
	}

	public void hit(){
		Card c = cards.removeTopCard(); //Top card from deck
		if(turn == PLAYER){
			c.setShowing(true);			//All player cards showing
			bj.jPanelPlayer.add(c);		//display card on table
			playerCards.add(c);			//place card in players cards
			bj.repaint();
			bj.jPanelPlayer.repaint();
///
			bj.jPanelPlayer.setVisible(true);
			System.out.println("Hit Player:"+c+".");
		}else if(turn == DEALER){
			if( dealerCards.size() >= 1 ){
				c.setShowing(true);		//All but first dealer card showing
				c.repaint();
			}
			dealerCards.add(c);			//place card in dealers cards
			bj.jPanelDealer.add(c);		//display card on table
			bj.jPanelDealer.repaint();
///			c.setVisible(true);
			System.out.println("Hit Dealer:"+c+".");
		}else{System.out.println("Error in Dealer Hit");
		}
		//////////////////////END///HIT//////////
		bj.jProgressBarDeck.setValue( cards.getSizeOfDeck() );
//		System.out.println(bj.jProgressBarDeck.getValue());
/*		c.repaint();
		bj.repaint();
		try{
			System.out.println("Sleeping...");
			Thread.sleep(1000);				//Causes wait after hit
		}catch( InterruptedException e ){
			e.printStackTrace();
		}
*/		if(state == PLAY){
			checkStateOfGame();
			if(state == ENDHAND){
				endHand();
			}
		}
		bj.setSums();
	}

	/**STAY; switches turns*/
	public void stay(){
		if(turn==PLAYER){
			nextTurn();
			dealerRules();
		}else if(turn==DEALER){
			determineWinner();
			endHand();
		}
	}

	public void doublePlay(){
		isStakesDoubled = true;
		hit();
		if(turn == PLAYER){
			stay();
		}
	}

	public void split(){
		isStakesDoubled = true;
//MORE IMPLEMENTATION
	}

	public void endHand(){
		bj.setSums();
		if( result == LOST ){
			bj.trip.lostCash();
		}else if( result == WON ){
			bj.trip.wonCash();
		}else if( result == LOSTDOUBLE ){
			bj.trip.lostCash(2);
		}else if( result == WONDOUBLE ){
			bj.trip.wonCash(2);
		}else if( result == WONBLACKJACK ){
			bj.trip.wonCash(1.5);
		}else if( result == PUSH ){
			//NO EXCHANGE OF MONEY
		}else{ //(result == NOTDETERMINED){
			System.out.println("Ended Hand Prematurely");
			bj.trip.lostCash();
		}
		System.out.println("result="+result+"\tcardTotals: d="+calculateSum(dealerCards)+"  P="+calculateSum(playerCards));
		//bj.jPanelPlayer.removeAll();
		//bj.jPanelDealer.removeAll();
		bj.repaint();
		setMoneyFields();
		state = BID;
		result = NOTDETERMINED;
	}

	private void setMoneyFields(){
		bj.setMoneyField( bj.jTextFieldGameWinnings, bj.trip.tripWinnings() );
		bj.setMoneyField( bj.jTextFieldCash, bj.trip.getCashOnHand());
	}

	private void nextTurn(){
		if( turn == DEALER ){
			turn = PLAYER;
//			state = PLAY;
		}else{					//if( turn == PLAYER ){
			turn = DEALER;
//			state = DEALER;
		}
	}

	private void checkStateOfGame(){
		int playerSum = calculateSum(playerCards);
		int dealerSum = calculateSum(dealerCards);
		if( dealerSum > 21 ){		//DEALER BUST!!!
			won();
		}else if( playerSum > 21 ){		//BUST!!!
			lost();
		}else if( playerSum == 21){	//!BJ
			stay();
		}else if( dealerSum == 21 ){
			state = ENDHAND;
			determineWinner();
		} //else{ DO NOTHING//CONTINUE PLAYING
	}

	private void won(){
		if(!isStakesDoubled){
			result = WON;
		}else{ //if( isStakesDoubled ){
			result = WONDOUBLE;
		}
		state = ENDHAND;
		bj.image.setResult(ResultImage.WON);
		bj.image.repaint();
		System.out.println("WON");
	}

	private void lost(){
		if(!isStakesDoubled){
			result = LOST;
		}else{ //if( isStakesDoubled ){
			result = LOSTDOUBLE;
		}
		state = ENDHAND;
		bj.image.setResult(ResultImage.LOST);
		bj.image.repaint();
		System.out.println("LOST");
	}

	private void push(){
		result = PUSH;
		state = ENDHAND;
		bj.image.setResult(ResultImage.PUSH);
		bj.image.repaint();
	}

	private boolean isBlackJack(){
		if(calculateSum(playerCards) == 21){
			result = WONBLACKJACK;
			state = ENDHAND;
			return true;
		}else{ return false; }
	}

	public int calculateSum( Vector c ){
		int sum = 0;
		int aces = 0;
		Card card;
		int cardValue;
		for( int i = 0; i < c.size(); i++ ){
			card = (Card)c.get(i);
			if( card.getShowing() ){
				if( card.isAce() ){
					aces++;
					cardValue = 11; //Ace can be reduced to 1 in next loop
				}else if( card.isFaceCard() ){
					cardValue = 10;
				}else{				//card is between 2-9
					cardValue = card.getValue();
				}
				sum += cardValue;
			}//else if (!card.isShowing()){ doNothing(); }
			else{
				//System.out.println(card+" is not Showing");
			}
		}
		//following loop reduces least amount of aces necessary
		//to not go over 21 if possible
		for( int a = 0; a < aces; a++ ){
			if( sum > 21 ){
				sum -= 10; //Make an Ace worth 1 instead of 14
			}else{
				return sum;
			}
		}
		return sum;	//returns sum if not over 21 or over 21 and all aces reduced to 1
	}


	private void dealerRules(){
		((Card)dealerCards.get(0)).setShowing(true);
		((Card)dealerCards.get(0)).repaint();
		state = DEALERSPLAY;
		while( calculateSum(dealerCards) <= 16 ){
			hit();
			checkStateOfGame();
			if( state == ENDHAND ){
				endHand();
			}
		}
		if( state == DEALERSPLAY ){//&&( 16 < dealerSum <=21 )
			stay();
		}
	}

	/**Who has higher card count*///state==NOTDETERMINED, Nobody BUSTED
	private void determineWinner(){
		int playerSum = calculateSum(playerCards);
		int DealerSum = calculateSum(dealerCards);

		if( playerSum > DealerSum){
			won();
		}else if (playerSum < DealerSum ){
			lost();
		}else{
			push();						//tie
		}
	}

	/**RemoveCards from vectors and BlackJackTable(for display purposes)*/
	private void removeCards(){
		int dealerSize = dealerCards.size();
		int playerSize = playerCards.size();
//		System.out.println("DcardsBefore="+dealerSize+"  PcardsBefore="+playerSize);
		for( int d = 0; d < dealerSize; d++ ){
			bj.jPanelDealer.remove((Card)dealerCards.remove(0));
		}
		for( int p = 0; p < playerSize; p++ ){
			bj.jPanelPlayer.remove((Card)playerCards.remove(0));
		}
//		dealerSize = dealerCards.size();
//		playerSize = playerCards.size();
//		System.out.println("DcardsAfter="+dealerSize+"  PcardsAfter="+playerSize);
		dealerCards = new Vector();
		playerCards = new Vector();
	}

	private boolean validateBet(){
		if( bj.trip.getCashOnHand() >= bj.trip.getBet() ){
			return true;
		}else{
			invalidBet();
			return false;
		}
	}

	private void invalidBet(){	//not enough cash for bet
		JOptionPane.showMessageDialog(bj,
			"You do not have enough cash to place that bet", "INVALID BET",
			JOptionPane.WARNING_MESSAGE);
		bj.jTextFieldBet.requestFocus();
	}

}