import cards.*;

public class TestDeck{
	public static void main(String []args){
		Deck d = new Deck(2);
		System.out.println(d);
		d.shuffle();
		System.out.println(d);
	}
}