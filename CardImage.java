import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class CardImage extends Canvas{
	String s;
	public CardImage(String suit, String value){
		s = suit + " " + value;
		setSize(50,65);
	}
	
	public void paint( Graphics g ){
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.white);
		RoundRectangle2D card = new RoundRectangle2D.Float( 0F, 0F, 50F, 65F, 15F, 15F);
		g2.fill(card);
		g2.setPaint(Color.black);
		
		g2.drawString(s, 10, 40);
	}
}