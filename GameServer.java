import java.io.*;
import java.net.*;
//This is the Server for the game


public class GameServer {
	
	//initializes the variables
    private ServerSocket theServer;
    
	//max of 2 players for the game
    private static final int MAX_PLAYERS = 2;
    private int numPlayers;
    
    private String p1Name;
    private String p2Name;
	private boolean p1Up, p1Down, p2Up, p2Down;
	private boolean sendData;
    
	//threads for each player
    private TalkToClientThread p1Thread;
    private TalkToClientThread p2Thread;

    public GameServer() {
		sendData = true;
        numPlayers = 0;
        try {
            theServer = new ServerSocket(8888);
            System.out.println("===== THE SERVER IS NOW ACCEPTING CONNECTIONS =====");
        } catch (IOException ex) {
            System.out.println("Error in GameServer constructor.");
        }
    }

    public void acceptConnections() {
        try {
			//loop that limits 2 players
            while (numPlayers < MAX_PLAYERS) {
                Socket s = theServer.accept();
                
                numPlayers++; // increment numPlayers after each connection
                
                System.out.println("[PLAYER " + numPlayers + "] has connected.");
                
				// When the first player connects, the thread for that player
                // is constructed. But both threads will only start once the 
                // second player has connected as well. The socket is passed to the thread,
                // and so is the current value of numPlayers. For the first player,
                // numPlayers will be 1. And for the second player, numPlayers would 
                // have been incremented already, so it gets a value of 2. This will be 
                // used to identify which thread is which.
                if(numPlayers == 1) {
                    p1Thread = new TalkToClientThread(s, numPlayers);
                } else {
                    p2Thread = new TalkToClientThread(s, numPlayers);
                    Thread t1 = new Thread(p1Thread);
                    Thread t2 = new Thread(p2Thread);
                    t1.start();
                    t2.start();
                }
            }    
        } catch (IOException ex) {
            System.out.println("Error in acceptConnections() method.");  
        }
    }

    class TalkToClientThread implements Runnable {
        
        private int playerID;
        private Socket theSocket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public TalkToClientThread(Socket s, int pid) {
            playerID = pid;
            theSocket = s;
            try {  
                dataIn = new DataInputStream(theSocket.getInputStream());
                dataOut = new DataOutputStream(theSocket.getOutputStream());
                
				// Here, we see the playerID field being used.
                // Once a connection is established, the server sends the playerID back to the player.
                // The player doesn't actually know their ID number yet. The server is the one 
                // that sets that based on who connects first. 
                // Then, after the ID number is sent, the player sends us their name.
                // So we read the name and assign it to the correct field.
                // If the playerID is 1, then the name gets assigned to p1name. If 2, then p2name.
                // Compare the flow of data passing here with the flow in the Player class thread. 
                // Each dataOut here, corresponds to a dataIn in the Player class thread.
                if (playerID == 1) {
                    dataOut.writeInt(playerID);
                    dataOut.flush();
                    p1Name = dataIn.readUTF();
                    System.out.println("[PLAYER " + playerID + "] name is " + p1Name);
                } else {
                    dataOut.writeInt(playerID);
                    dataOut.flush();
                    p2Name = dataIn.readUTF();
                    System.out.println("[PLAYER " + playerID + "] name is " + p2Name);
                }    
            } catch (IOException ex) {    
                System.out.println("Error in TalkToClientThread constructor.");   
            }
        }

        public void run() {
			// This part runs once the threads are started. As mentioned 
            // in the comments in the acceptConnections() method, threads will start only
            // when two players have connected.
            readWriteToClient();
        }

        public void readWriteToClient() {
			
            try {
                if(playerID == 1) {
                    dataOut.writeUTF(p2Name);
                    dataOut.flush();
                } else {
                    dataOut.writeUTF(p1Name);
                    dataOut.flush();
                }
            } catch (IOException ex) {
                System.out.println("Error in TalkToClientThread");
            }
			//loop that sends data to each player constantly
			while (sendData){
				try {
                if(playerID == 1) {
					p1Up = dataIn.readBoolean();
					p1Down = dataIn.readBoolean();

					dataOut.writeBoolean(p2Up);
					dataOut.writeBoolean(p2Down);
					
                    dataOut.flush();
                } else {
					p2Up = dataIn.readBoolean();
					p2Down = dataIn.readBoolean();
				   
					dataOut.writeBoolean(p1Up);
					dataOut.writeBoolean(p1Down);
					
                    dataOut.flush();
                }
            } catch (IOException ex) {
                System.out.println("Error in TalkToClientThread");
            }
			}
			
        }

    }
    
    // main method for game server
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }

}
