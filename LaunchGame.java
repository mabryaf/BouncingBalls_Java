/**
 * This program contains the code for the drawing component.
 * It draws game elements such as the middle bar, the gaps in the middle bar,
 * the color labels, the title of the game, the balls, etc.
 * It checks if the players have won the game,
 * and updates the positions of the balls.
 * It also contains the code for the hierarchy listener and the key listener, 
 * and the main method.
 *
 * @author Faine Mabry A. Fonseca
 * @author Anna Katrina L. Lagdan
 * @version 2016.05.22
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.*;

//JComponent for drawing
public class LaunchGame extends JComponent {
    
	 // A reference to the Jframe will be passed to the JComponent
    // so that we can access the frame (to add listeners to it, for example).
    private JFrame theFrame;
	
	 // myID stores whether you are player 1 or 2. The server will let you know which one you are.
     // your Name which you will send to the server
     // the other player's Name, which gets passed to you by the server
    private int myID; 
    private String myName; 
    private String otherName; 
    
	// contentPane clickable
    // The listener will initiate the connection request via a mouse click
    private MyMouseListener mml; 
    
	//msg that will be drawn
    private String msg;
	
	public static LaunchGame launch;
	private Gap p, p2;
	private MyHierarchyListener mhl;
	
	private boolean upPress, downPress, upPress2, downPress2;

	private int MAXX, MAXY;
	private final int WIDTH = 10;
	private ArrayList<BouncingBalls> bb;
	
	private boolean sendData;
	private boolean playerThreads;
	private boolean bothConnected;
	
	private int p2Y;

	/**
     * Sets the values for MAXX and MAXY
	 * creates the initial message,
	 * initializes the boolean values for the keys,
	 * creates the arraylist for the balls, and add the balls to the array list,
	 * instantiates the Gap for two players,
	 * and adds a hierarchy listener and a mouse listener.
     * 
     * @param f the frame
	 * 
     */
    public LaunchGame(JFrame f) {
        theFrame = f;
        msg = "CLICK ANYWHERE, THEN ENTER YOUR NAME IN THE CONSOLE"; // initial message
        mml = new MyMouseListener();
        theFrame.getContentPane().addMouseListener(mml); // add the mouse listener
        theFrame.getContentPane().setFocusable(true);
        System.out.println("===== PLAYER CONSOLE INITIATED =====");
		
		MAXX = 900;
		MAXY = 600;
		
		//boolean for keylistener
		upPress = false;
		downPress = false;
		upPress2 = false;
		downPress2 = false;
		
		sendData = true;
		
		//arraylist for bouncingballs
		bb =  new ArrayList<BouncingBalls>();		
	
		//adds new balls to arraylist
		bb.add(new BouncingBalls(20, 100, 550,  745, 545, Color.RED));
		bb.add(new BouncingBalls(20, 400, 115, 395, 120, Color.RED));
		bb.add(new BouncingBalls(20, 100, 225, 505, 220, Color.RED));
		bb.add(new BouncingBalls(15, 335, 335, 330, 330, Color.RED));
		bb.add(new BouncingBalls(15, 310, 55,  315, 60, Color.RED));
		bb.add(new BouncingBalls(20, 465, 445, 460, 440, Color.BLUE));
		bb.add(new BouncingBalls(20, 545, 555, 560, 550, Color.BLUE));
		bb.add(new BouncingBalls(20, 500, 285, 365, 290, Color.BLUE));
		bb.add(new BouncingBalls(15, 500, 365, 425, 370, Color.BLUE));
		bb.add(new BouncingBalls(15, 100, 10,  155, 15, Color.BLUE));
		
		p = new Gap(MAXX/2 - WIDTH/2, 0, WIDTH, WIDTH*6);
		p2 = new Gap(MAXX/2 - WIDTH/2, MAXY - WIDTH*6, WIDTH, WIDTH*6);
		
		mhl = new MyHierarchyListener();
		this.addHierarchyListener( mhl );
	}
    
    /**
     * Casts Graphics to Graphics g2d, creates rendering hints for g2d,
	 * draws the necessary elements for the layout of the game,
	 * and tells the boolean which method to call.
     * 
     * @param g - Graphics object
     */	
    protected void paintComponent( Graphics g ) {
		// Casting to g2d
		Graphics2D g2d = (Graphics2D) g;
		
		// Creates rendering hints for g2d
		RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		
		// Draws background
			g2d.setColor(new Color(255, 218, 102));
			g2d.fillRect(0,0, MAXX, MAXY);
		
		// Draws title
			g2d.setFont(new Font("Arial", Font.BOLD, 75));
			g2d.setColor(Color.WHITE);
			g2d.drawString("IMPOSS", 145, 100);
			g2d.setFont(new Font("Arial", Font.BOLD, 100));
			g2d.drawString("BALLS", 465, 100);
		
		// Draws middle border
			g2d.setColor(Color.white);
			g2d.fillRect(MAXX/2 - WIDTH/2,0,WIDTH,MAXY);
		
		// Draws labels
			g2d.setFont(new Font("Arial", Font.BOLD, 70));
			g2d.setColor(new Color(157, 37, 60, 180));
			g2d.drawString("RED", 150, 300);
			g2d.setColor(new Color(61, 199, 176, 180));
			g2d.drawString("BLUE", 590, 300);
		
		// Draws images
		// g2d.drawImage(arvin, 150, 150,null);
		// g2d.drawImage(niko, 610, 140, null);
		
		//draw directions
		// g2d.setColor(Color.BLUE);
		// g2d.drawString("Directions", 541, 410);
		// g2d.drawString("1.Use the up and down", 500,430);
		// g2d.drawString("arrows to move", 510,442);
		// g2d.drawString("2.Get the balls on their", 500, 457);
		// g2d.drawString("proper sides", 510, 470);
		
		// Draws the gaps that the balls can pass through in the middle bar
			g2d.setColor(new Color(228, 170, 192, 150));
			p.draw(g2d);	
			g2d.setColor(new Color(152, 179, 246, 150));
			p2.draw(g2d);
		
		// Calls methods for booleans
			if (upPress)
				p.up();
			if(downPress)
				p.down();
			if (upPress2)
				p2.up();
			if (downPress2)
				p2.down();
			
		// Draws balls
			for(int x = 0; x < bb.size(); x++)
			{
				BouncingBalls b = bb.get(x);
				g2d.setColor(b.getColor());
				g2d.fillOval(b.getCurrentX(), b.getCurrentY(), b.getSize(),b.getSize());
			}	
			// if(bothConnected){
			callBounceBall();
			// }
			repaint();
		
		// Draws elements necessary when players win
		if (checkWin())
		{
				g2d.setFont(new Font("Arial", Font.BOLD, 75));
				g2d.setColor(new Color(255, 218, 102));
				g2d.drawString("IM", 145, 100);
			
				g2d.setColor(Color.RED);
				g2d.setFont(new Font("ARIAL", Font.BOLD, 30));
				g2d.drawString("it's", 190, 70);
			
				g2d.setFont(new Font("Arial", Font.BOLD, 75));
				g2d.setColor(Color.RED);
				g2d.drawString("POSS", 229, 100);
			
				g2d.setColor(Color.RED);
				g2d.fillRect(MAXX/2 - WIDTH/2,0,WIDTH,MAXY);
			
				g2d.setFont(new Font("Arial", Font.BOLD, 100));
				g2d.drawString("BALLS", 465, 100);
			
				g2d.setColor(Color.BLUE);
				g2d.setFont(new Font("ARIAL", Font.BOLD + Font.ITALIC, 150));
				g2d.drawString("YOU", MAXX/20, 500);
				g2d.drawString("WIN", (MAXX/2) + 80, 500);	
			
				g2d.setFont(new Font("Arial", Font.BOLD, 70));
				g2d.setColor(new Color(255, 218, 102));
				g2d.drawString("RED", 150,300);
				g2d.drawString("BLUE", 590, 300);
			
				try {
					Thread.sleep (100); 
				}
				catch (Exception e) { }
		}
	}
	
	/**
     * Animates balls in the arraylist.
     */
	public void callBounceBall()
	{
		for(int x = 0; x < bb.size(); x++)
		{
			// Gets the current ball position
			BouncingBalls b = bb.get(x);
			
			// Updates its position
			b.bounceBall(p.getY2(),p.getY1(), MAXX/2 - WIDTH/2, MAXX/2 + WIDTH/2, MAXX, MAXY, p2.getY2(), p2.getY1() );
		}
		
		try
		{
			Thread.sleep (20); 
		}
		catch (Exception e) { }
	}
	
	/**
     * Checks the balls positions and sees if the players have won
     */
	public boolean checkWin()
	{
		for(int x = 0; x < bb.size(); x++)
		{
			BouncingBalls temp = bb.get(x);
			
			if(temp.getColor() == (Color.BLUE))
			{
				if( !(temp.getCurrentX()<MAXX/2 - WIDTH/2) )
				{
					return false;
				}
			}			
			else if (temp.getColor() == Color.RED)
			{
				if( !(temp.getCurrentX()>MAXX/2 + WIDTH/2) )
				{
					return false;
				}
			}
		}
			
			return true;
		
	}

	/**
     * Class for KeyListener
     */
	class MyKeyListener implements KeyListener {

		private LaunchGame launch = LaunchGame.this;

		public void keyTyped( KeyEvent ke ) {
			
		}
		
		// Sets boolean values for the the up and down arrow keys when they are released
		public void keyReleased( KeyEvent ke ) {
			int keyCode = ke.getKeyCode();
		    			
				switch(keyCode) {
	            case KeyEvent.VK_UP:
					if (myID == 1)
						upPress = false;
					else
						upPress2 = false;
	                break;
					
	            case KeyEvent.VK_DOWN:
					if (myID == 1)
						downPress = false;
					else
						downPress2 = false;
	                break;
					
				// case KeyEvent.VK_W:
					// upPress2 = false;
					// break;
					
				// case KeyEvent.VK_S:
					// downPress2 = false;
					// break;
					
	            // default :
	            	// break;
				} 	
		}
		
		// Sets boolean values for the the up and down arrow keys when they are pressed
		public void keyPressed( KeyEvent ke ) {
			int keyCode = ke.getKeyCode();
		    			
				switch(keyCode) {
	            case KeyEvent.VK_UP:
					if(myID == 1)
						upPress = true;
					else
						upPress2 = true;
	                break;
					
	            case KeyEvent.VK_DOWN:
					if (myID == 1)
						downPress = true;	
					else
						downPress2 = true;
	                break;
					
				// case KeyEvent.VK_W:
					// upPress2 = true;
					// break;
					
				// case KeyEvent.VK_S:
					// downPress2 = true;
					// break;
					
	            default :
	            	break;
				} 	
		}
	}
	
	/**
	 * Class for hierarchy listener
	 */	
	class MyHierarchyListener implements HierarchyListener {

		private LaunchGame launch = LaunchGame.this;
		private MyKeyListener mkl = new MyKeyListener();

		public void hierarchyChanged( HierarchyEvent he ) {
			JFrame frame = (JFrame) SwingUtilities.getRoot( launch );
			frame.getContentPane().addKeyListener( mkl );
			frame.getContentPane().setFocusable( true );
		}

	}

    /**
	 * Class for mouse listener
	 */
    class MyMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            // remove listener right away once clicked to avoid double 
            // connection requests
            LaunchGame.this.theFrame.getContentPane().removeMouseListener(mml);
            LaunchGame.this.connectToServer(); // initiate connection to server
            // see connectToServer() method
        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

    }
    
    /**
	 * Method that handles connection to server
	 */
    public void connectToServer() {
        Socket s;
        try {
            // Initiate connection. Change local host to ip address of server
            // when testing over a network.
            s = new Socket("10.100.66.248", 8888);
            
            // A thread is created for communicating with the server.
            // Why? Because we don't want the GUI code and the network code
            // to be in the same thread.
            TalkToServerThread ttst = new TalkToServerThread(s);
            Thread t = new Thread(ttst);
            t.start();
        } catch (IOException ex) {
            System.out.println("Error in connectToServer() method.");
        }
    }

    /**
	 * initialize the socket
	 */
    class TalkToServerThread implements Runnable {

        private Socket theSocket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public TalkToServerThread(Socket s) {
            theSocket = s;
            try {
                dataIn = new DataInputStream(theSocket.getInputStream());
                dataOut = new DataOutputStream(theSocket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("Error in TalkToServerThread constructor.");
            }
        }

        public void run() {	
            readWriteToServer();
        }

        public void readWriteToServer() {
            // scanner asks you to input your name once you've connected to the server.
            Scanner console = new Scanner(System.in);
            System.out.println("Enter your name:");
            myName = console.nextLine();
            try {
                // server sends us our player ID
                myID = dataIn.readInt();
                
                // and then we send the server our name
                dataOut.writeUTF(myName);
                dataOut.flush();
      
                System.out.println("Hello, " + myName + "! You are connected to the game server as player #" + myID + ".");
                

                // You'll receive the name of the other player.
                otherName = dataIn.readUTF();
				
				while(sendData){
					if(myID == 1){
						dataOut.writeBoolean(upPress);
						dataOut.writeBoolean(downPress);
						
						upPress2 = dataIn.readBoolean();
						downPress2 = dataIn.readBoolean();
						
						dataOut.flush();
					}
					else{
						dataOut.writeBoolean(upPress2);
						dataOut.writeBoolean(downPress2);
						
						upPress = dataIn.readBoolean();
						downPress = dataIn.readBoolean();
						
						dataOut.flush();
					}
					
                
                // updates the frame with a new message once both players have connected.
					SwingUtilities.invokeLater( new Runnable() {
						public void run() {
								bothConnected = true;
                            
						}
					});
				}
            } catch (IOException ex) {
                System.out.println("Error in readWriteToServer() method.");
            }
        }
    }
	
    /**
	 * Main method
	 *
	 * @param args command line arguments
	 */
	public static void main (String [] args) {
		// Sets w and h values
		int w = 900;
		int h = 600;
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				JFrame f = new JFrame();
				launch = new LaunchGame(f);

				f.getContentPane().setPreferredSize( new Dimension( w - 20, h - 20 ) );
				f.setTitle( "impossiBALLS" );
				f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );		
				f.add( launch );
				f.pack();
				f.setVisible( true );
				f.setResizable ( false );
				f.revalidate();
            
        }
	});
	}
}