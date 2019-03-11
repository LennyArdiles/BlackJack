import java.awt.*;
import javax.swing.*;

public class StatisticsWindow extends JFrame{
	TripToCasino t;
	JLabel jLabelName;
	JLabel jLabelCashOnHand;
	JLabel jLabelHandsPlayed;
	JLabel jLabelHandsWon;
	JLabel jLabelHandsLost;
	JLabel jLabelMostWon;
	JLabel jLabelMostLost;
	JLabel jLabelWinningStreak;
	JLabel jLabelLoosingStreak;

	public StatisticsWindow(){
		super("Stats");
		setLabels();
	}

	private void setLabels(){
		jLabelName = new JLabel("Name:", Label.RIGHT);
		jLabelCashOnHand = new JLabel("Cash On Hand:", Label.RIGHT);
		jLabelHandsPlayed = new JLabel("Hands Played:", Label.RIGHT);
		jLabelHandsWon = new JLabel("Hands Won:", Label.RIGHT);
		jLabelHandsLost = new JLabel("Hands Lost:", Label.RIGHT);
		jLabelMostWon = new JLabel("Most Won:", Label.RIGHT);
		jLabelMostLost = new JLabel("Most Lost:", Label.RIGHT);
		jLabelWinningStreak = new JLabel("Winning Streak:", Label.RIGHT);
		jLabelLoosingStreak = new JLabel("Loosing Streak:", Label.RIGHT);

		jLabelName.setSize(75, 25);
		jLabelCashOnHand.setSize(75, 25);
		jLabelHandsPlayed.setSize(75, 25);
		jLabelHandsWon.setSize(75, 25);
		jLabelHandsLost.setSize(75, 25);
		jLabelMostWon.setSize(75, 25);
		jLabelMostLost.setSize(75, 25);
		jLabelWinningStreak.setSize(75, 25);
		jLabelLoosingStreak.setSize(75, 25);

		getContentPane().setLayout( new BoxLayout(getContentPane(), BoxLayout.Y_AXIS) );
		getContentPane().add(jLabelName);
		getContentPane().add(jLabelCashOnHand);
		getContentPane().add(jLabelHandsPlayed);
		getContentPane().add(jLabelHandsWon);
		getContentPane().add(jLabelHandsLost);
		getContentPane().add(jLabelMostWon);
		getContentPane().add(jLabelMostLost);
		getContentPane().add(jLabelWinningStreak);
		getContentPane().add(jLabelLoosingStreak);
	}

	public void setData( TripToCasino trip ){
		t = trip;
	}

	public void paint( Graphics g ){
		super.paint(g);
		int x = 95;
		int y = 37;
		int h = 17;
		//g.setFont( new Font() );
		g.drawString( ""+t.getPlayerName(), x, y+0*h );
		g.drawString( ""+t.getAvailableCash(), x, y+1*h );
		g.drawString( ""+t.getHandsPlayed(), x, y+2*h );
		g.drawString( ""+t.getHandsWon(), x, y+3*h );
		g.drawString( ""+t.getHandsLost(), x, y+4*h );
		g.drawString( ""+t.getMostWon(), x, y+5*h );
		g.drawString( ""+t.getMostLost(), x, y+6*h );
		g.drawString( ""+t.getLongestWinningStreak(), x, y+7*h );
		g.drawString( ""+t.getLongestLoosingStreak(), x, y+8*h );
	}
}