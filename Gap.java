import java.awt.*;
import java.awt.geom.*;

public class Gap {

	private int x, y;
	private int w,h;
	
	public Gap(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void draw(Graphics2D g2d) {
		Rectangle2D.Double rect = new Rectangle2D.Double(x,y,w,h);
		g2d.fill(rect);

	}
	
	public int getX1() {
		return x;
	}
	
	public int getX2() {
		return (x+w);
	}
	
	public int getY1() {
		return y;
	}
	
	public int getY2() {
		return (y+h);
	}
	
	public void setY(int i){
		y = i;
	}
	
	public void up() {
		if (y> 10)
		{
		y-=5;
		}	
	}
	
	public void down() {
		if (y < (600-h)){
			y+=5;
		}
	}
}