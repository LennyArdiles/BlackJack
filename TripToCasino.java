/**
 *@author: Lenny Ardiles
 *@description: A class for a single playing card
 *@date: 05-05-2001
 *Last Modified: 05-05-2001
 */

public class TripToCasino{
	private String player;
	private int beginTripWithCash;
	private int cashOnHand;
	private int bet;
	private int handsPlayed;
	private int handsWon;
	private int handsLost;
	private int mostWon;
	private int mostLost;
	private int winningStreak;
	private int loosingStreak;
	private int longestWinningStreak;
	private int longestLoosingStreak;

	TripToCasino(String player){
		this.player = player;
		handsPlayed = 0;
		handsWon = 0;
		handsLost = 0;
		mostWon = 0;
		mostLost = 0;
		winningStreak = 0;
		loosingStreak = 0;
		longestWinningStreak = 0;
		longestLoosingStreak = 0;
		//if(playerExists){getCashAmt;}
		//else{ //new player start with $100
			cashOnHand = 100;
		beginTripWithCash = cashOnHand;
	}

	public String getPlayerName(){
		return player;
	}

	public int getAvailableCash(){
		return cashOnHand;
	}

	public int getBet(){
		return bet;
	}

	public void setBet( int bet ){
		this.bet = bet;
	}

	public int getCashOnHand(){
		return cashOnHand;
	}

	public int getHandsPlayed(){
		return handsPlayed;
	}

	public int getHandsWon(){
		return handsWon;
	}

	public int getHandsLost(){
		return handsLost;
	}

	public int getMostWon(){
		return mostWon;
	}

	public int getMostLost(){
		return mostLost;
	}

	public int getLongestWinningStreak(){
		return longestWinningStreak;
	}

	public int getLongestLoosingStreak(){
		return longestLoosingStreak;
	}

	public boolean hasCash( ){
		if( bet <= cashOnHand ){
			return true;
		} else {
			return false;
		}
	}

	public void wonCash(){
		wonCash( 1 );
	}

	public void wonCash( double multiple ){
		cashOnHand += (int)(multiple*bet);
		handsPlayed++;
		handsWon++;
		if( (int)(multiple*bet) > mostWon ){	//MostWon
			mostWon = (int)(multiple*bet);
		}
		winningStreak++;
		loosingStreak = 0;
		if( winningStreak > longestWinningStreak ){
			longestWinningStreak = winningStreak;
		}
	}

	public void lostCash( ){
		lostCash( 1 );
	}

	public void lostCash( double multiple ){
		cashOnHand -= (int)(multiple*bet);
		handsPlayed++;
		handsLost++;
		if( (int)(multiple*bet) > mostLost ){	//MostLost
			mostLost = (int)(multiple*bet);
		}
		loosingStreak++;
		winningStreak = 0;
		if( loosingStreak > longestLoosingStreak ){
			longestLoosingStreak = loosingStreak;
		}
	}

	public int tripWinnings(){
		return cashOnHand - beginTripWithCash;
	}
}