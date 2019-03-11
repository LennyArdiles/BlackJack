import java.awt.*;
import java.awt.geom.*;

public class ResultImage extends Canvas{
	private int result = PUSH;
	public static int WON = 1;
	public static int LOST = 2;
	public static int PUSH = 0;

	public ResultImage(){
		super();
		setFont( new Font(null, Font.BOLD, 30) );
	}

	public void setResult( int r ){
		result = r;
	}

	public void paint( Graphics g ){
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D r = new Rectangle2D.Float( 0F, 0F, 25F, 25F );
		g2.setPaint(Color.black);
		g2.fill(r);
		if(result == PUSH){
			g2.setPaint(Color.yellow);
			g2.drawString("*",3,29);
		}else{	//if( WON || LOST ){
			g2.setPaint(Color.green);
			g2.drawString("$",1,24);
			if(result == LOST){
				g2.setPaint(Color.red);
				g2.drawString("X",-1,24);
			}
		}
	}
}