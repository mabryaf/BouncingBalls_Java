/**
 * This code is for the balls.
 * It creates the balls parameters and
 * specifies their movement.
 *
 * @author Faine Mabry A. Fonseca
 * @author Anna Katrina L. Lagdan
 * @version 2016.05.22
 */
 
import java.awt.*;
import java.awt.geom.*;

public class BouncingBalls
{	
	private int size;
	private int currentX;
	private int currentY;
	private int prevX;
	private int prevY;
	
	Color color;
	
    /** 
	 * Sets ball's values for size, x and y (previous and current), and color
	 */
	public BouncingBalls(int size, int currentX, int currentY, int prevX, int prevY, Color color)
	{
		this.size = size;
		this.currentX = currentX;
		this.currentY = currentY;
		this.prevX = prevX;
		this.prevY = prevY;
		this.color = color;
	}
	
	/** 
	 * Getter method for ball's size
	 */ 
	public int getSize()
	{
		return size;
	}
	
	/** 
	 * Getter method for ball's current x value
	 */ 
	public int getCurrentX()
	{
		return currentX;
	}
	
	/**
	 * Getter method for balls current y value
	 */
	public int getCurrentY()
	{
		return currentY;
	}
	
	/**
	 * Getter method for balls previous x value
	 */
	public int getPrevX()
	{
		return prevX;
	}
	
	/**
	 * Getter method for balls previous y value
	 */
	public int getPrevY()
	{
		return prevY;
	}
	
	/** 
	 * Getter method for ball's color
	 */
	public Color getColor()
	{
		return color;
	}
	
	/** 
	 * Method for bouncing motion
	 */ 
	public void bounceBall(int lowGap, int highGap, int leftBorder1, int rightBorder1, int maxx, int maxy, int lowGap2, int highGap2)
	{
		int lg = lowGap;
		int hg = highGap;
		int lb1 = leftBorder1;
		int rb1 = rightBorder1;
		int maxX = maxx;
		int maxY = maxy;
		int lg2 = lowGap2;
		int hg2 = highGap2;
		int x=0;
		int y=0;
		
		//bouncing for corners of middle border
		if( (getCurrentX()>=rb1) && (getCurrentX()<=rb1 + 5) && (getCurrentY()<=5) && (getCurrentY()<=hg || getCurrentY()>=lg) && (getCurrentY()<=hg2  || getCurrentY()>=lg2 ) )
		{
			x = 5;
			y = 5;
			currentX = rb1 + 2;
			currentY = 2;
		}
		else if( (getCurrentX()>=rb1) && (getCurrentX()<=rb1 + 5) && (getCurrentY()>=maxY - 5 -getSize()) && (getCurrentY()<=hg || getCurrentY()>=lg)  && (getCurrentY()<=hg2  || getCurrentY()>=lg2 ))
		{
			x = 5;
			y = -5;
			currentX = rb1 + 2;
			currentY = maxY-getSize()-2;
		}
		else if( (getCurrentX()>=lb1 - 5-getSize()) && (getCurrentX()<=lb1-getSize()) && (getCurrentY()<=5) && (getCurrentY()<=hg || getCurrentY()>=lg) && (getCurrentY()<=hg2  || getCurrentY()>=lg2 ) )
		{
			x = -5;
			y = 5;
			currentX = lb1 - 5-getSize()-2;
			currentY = 2;
		}
		else if( (getCurrentX()>=lb1 - 5-getSize()) && (getCurrentX()<=lb1-getSize()) && (getCurrentY()>=maxY - 5-getSize()) && (getCurrentY()<=hg || getCurrentY()>=lg) && (getCurrentY()<=hg2  || getCurrentY()>=lg2 ) )
		{
			x = -5;
			y = -5;
			currentX = lb1-getSize()-2;
			currentY = maxY-getSize()-2;
		}
		
		//bouncing for sides of middle border
		//checks for previous y and x in relation to current y and x to determine movement of balls
		else if(( ( (getCurrentX()>=lb1-getSize() && getCurrentX()<=rb1-getSize()) && (getCurrentY()<=hg  || getCurrentY() + 10>=lg )) && (getCurrentY()<=hg2  || getCurrentY() + 10 >=lg2 )) && (getCurrentX()-getPrevX()>=5) && (getCurrentY()-getPrevY()>=5) )
		{
			x-=5;
			y+=5;
		}
		else if(( ( (getCurrentX()>=lb1-getSize() && getCurrentX()<=rb1-getSize()) && (getCurrentY()<=hg || getCurrentY() + 10 >=lg  ) ) && (getCurrentY()<=hg2  || getCurrentY() + 10 >=lg2)) && (getCurrentX()-getPrevX()>=5) && (getPrevY()-getCurrentY()>=5) ) 
		{
			x-=5;
			y-=5;
		}
		else if(( ( (getCurrentX()>=lb1 && getCurrentX()<=rb1) && (getCurrentY()<=hg || getCurrentY() + 10 >=lg ) ) && (getCurrentY()<=hg2  || getCurrentY() + 10 >=lg2)) && (getPrevX()-getCurrentX()>=5) && (getCurrentY()-getPrevY()>=5) )
		{
			x+=5;
			y+=5;
		}
		else if(( ( (getCurrentX()>=lb1 && getCurrentX()<=rb1) && (getCurrentY()<=hg || getCurrentY() + 10 >=lg ) ) && (getCurrentY()<=hg2  || getCurrentY() + 10 >=lg2 )) && (getPrevX()-getCurrentX()>=5) && (getPrevY()-getCurrentY()>=5) )
		{
			x+=5;
			y-=5;
		}
		
		// Motion
		// normal motion of balls around the frame when it is not hitting any obstacle
		else if( !(getCurrentY()>=maxY-getSize()) && !(getCurrentX()>=maxX-getSize()) && !(getCurrentY()<=0) && !(getCurrentX()<=0) && (getCurrentX()>=5) && (getCurrentY()>=5) && (getCurrentX()-getPrevX()>=5) && (getCurrentY()-getPrevY()>=5) )
		{
			x+=5;
			y+=5;
		}
		else if( !(getCurrentY()>=maxY-getSize()) && !(getCurrentX()>=maxX-getSize()) && !(getCurrentY()<=0) && !(getCurrentX()<=0) && (getCurrentX()>=5)&& (getCurrentY()>=5) && (getCurrentX()-getPrevX()>=5) && (getPrevY()-getCurrentY()>=5) )
		{
			x+=5;
			y-=5;
		}
		else if( !(getCurrentY()>=maxY-getSize()) && !(getCurrentX()>=maxX-getSize()) && !(getCurrentY()<=0) && !(getCurrentX()<=0) && (getCurrentX()>=5)&& (getCurrentY()>=5) && (getPrevX()-getCurrentX()>=5) && (getCurrentY()-getPrevY()>=5) )
		{
			x-=5;
			y+=5;
		}
		else if( !(getCurrentY()>=maxY-getSize()) && !(getCurrentX()>=maxX-getSize()) && !(getCurrentY()<=0) && !(getCurrentX()<=0) && (getCurrentX()>=5)&& (getCurrentY()>=5) && (getPrevX()-getCurrentX()>=5) && (getPrevY()-getCurrentY()>=5) )
		{
			x-=5;
			y-=5;		
		}	
		
		//bouncing for corners of background
		else if( (getCurrentX()<=4) && (getCurrentY()<=4) ) 
		{
			x = 5;
			y = 5;
			currentX = 2;
			currentY = 2;
		}
		else if( (getCurrentX()<=4) && (getCurrentY()>=maxY-getSize()-4) )
		{
			x = 5;
			y = -5;
			currentX = 2;
			currentY = maxY-getSize()-2;
		}
		else if( (getCurrentY()<=4) && (getCurrentX()>=maxX-getSize()-4) )
		{
			x = -5;
			y = 5;
			currentX = maxX-getSize()-2;
			currentY = 2;
		}
		else if( (getCurrentY()>=maxY-getSize()-4) && (getCurrentX()>=maxX-getSize()-4) )
		{
			x = -5;
			y = -5;
			currentX = maxX-getSize()-2;
			currentY = maxY-getSize()-2;
		}
		
		//bouncing for sides of background
		//checks previous x and y in relation to current x and y to check how the ball will bounce
		else if( (getCurrentY() >= maxY-getSize()) && (getCurrentX()-getPrevX()>= 5) && (getCurrentY()-getPrevY()>=5) )
		{	
			x+=5;
			y-=5;
		}
		else if( (getCurrentY()>= maxY-getSize()) && (getPrevX()-getCurrentX()>=5) && (getCurrentY()-getPrevY()>=5) )
		{
			x-=5;
			y-=5;
		}
		else if( (getCurrentY() <= 4) && (getCurrentX()-getPrevX() >=5) && (getPrevY()-getCurrentY()>=5) )
		{
			x+=5;
			y+=5;
		}
		else if( (getCurrentY()<=4) &&  (getPrevX()-getCurrentX()>=5) && (getPrevY()-getCurrentY()>=5) )
		{
			x-=5;
			y+=5;
		}
		else if( (getCurrentX()>=maxX-getSize()) &&  (getCurrentX()-getPrevX()>=5) && (getCurrentY()-getPrevY()>=5) )
		{
			x-=5;
			y+=5;
		}
		else if( (getCurrentX()>=maxX-getSize()) && (getCurrentX()-getPrevX()>=5) && (getPrevY()-getCurrentY()>=5) )
		{
			x-=5;
			y-=5;
		}
		else if( (getCurrentX()<=4)  && (getPrevX()-getCurrentX()>=5) && (getCurrentY()-getPrevY()>=5) )
		{	
			x+=5;
			y+=5;
		}
		else if( (getCurrentX()<=4) &&  (getPrevX()-getCurrentX()>=5) && (getPrevY()-getCurrentY()>=5) )
		{
			x+=5;
			y-=5;
		}
		
		//sets the old and new X and Y of the ball for movement
		prevX = getCurrentX();
		prevY = getCurrentY();
		currentX = getCurrentX() + x;
		currentY = getCurrentY() + y;
	}
}