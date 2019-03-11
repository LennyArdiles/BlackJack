import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.Vector;

public class BlackJackTable extends JPanel{
	Vector cards;
	FlowLayout fl;
	JLabel sum;
	public BlackJackTable( Vector cards ){
		fl  = new FlowLayout( FlowLayout.LEFT );
		setLayout( fl );
		sum = new JLabel();
		add(sum);
		this.cards = cards;
	}

	public Component add( Component c ){
		super.add( c );
		repaint();
		return c;
	}

	public void paint( Graphics g ){
		super.paint( g );
		setBackground(Color.green);
	}
}