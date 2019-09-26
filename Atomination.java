import java.util.*;

public class Atomination {
	
	private String[] input;
	private String command;
	private boolean gameStarted;
	private int numberOfPlayers;
	private int numberOfActivePlayers;
	private int width;
	private int height;
	private Grid[][] gameGrid;

	private Player[] playerList;
	private int playerListPointer = 0;
	private Player currPlayer;

	private final String[] playerColour = {"Red","Green","Purple","Blue"};
	
	private final String HELP = "HELP	displays this help message\n" 
		+ "QUIT	quits the current game\n\n" 
		+ "DISPLAY	draws the game board in terminal\n" 
		+ "START	<number of players> <width> <height> starts the game\n"
		+ "PLACE	<x> <y> places an atom in a grid space\n" 
		+ "UNDO	undoes the last move made\n" 
		+ "STAT	displays game statistics\n"
		+ "SAVE	<filename> saves the state of the game\n"
		+ "LOAD	<filename> loads a save file\n";
	
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	} 
	
	private void bye(){
		System.out.println("Bye!");
		System.exit(0);
	}
	
	private void help(){
		System.out.println(HELP);
	}
	
	private void setNumOfPlayers(int p){
		if (p>=2 && p<=4){
			numberOfPlayers = p;
		}
	}
	
	private void setWidth(int w){
		if (w>=2 && w<=255){
			width = w;
		}
	}
	
	private void setHeight(int h){
		if (h>=2 && h<=255){
			height = h;
		}
	}
	
	private void createPlayers(){
		playerList = new Player[numberOfPlayers];
		for (int i=0; i<numberOfPlayers; i++){
			playerList[i] = new Player(playerColour[i]);
		}
	}
	
	// private 
	
	private void beginGame(Atomination a){
		a.gameStarted = true;
		a = new Atomination();
		a.numberOfActivePlayers = a.numberOfPlayers;
		a.gameGrid = new Grid[a.height][a.width];
		for (int h=0; h<a.height; h++){
			for (int w=0; w<a.width; w++){
				a.gameGrid[h][w] = new Grid(h,w,a);
				System.out.println(a.gameGrid[h][w]);
			}
		}
		System.out.println("Game Ready");
		currPlayer = playerList[playerListPointer];
		currPlayerTurnStatement();
	}
	
	private void printTop(Atomination a){
		System.out.print("\n+");
		int len = 2+(a.width-1)*3;
		for (int w=0; w<len; w++){
			System.out.print("-");
		}
		System.out.println("+");
	}
	private void printBottom(Atomination a){
		System.out.print("+");
		int len = 2+(a.width-1)*3;
		for (int w=0; w<len; w++){
			System.out.print("-");
		}
		System.out.print("+\n");
	}
	private void printMiddle(Atomination a){
		for (int h=0; h<a.height; h++){
			for (int w=0; w<a.width; w++){
				System.out.print("|");
				if (a.gameGrid[h][w].hasOwner()==true){
					System.out.print(a.gameGrid[h][w]);
				} else {
					System.out.print("  ");
				}
			}
			System.out.println("|");
		}
	}
	
	private void updateCurrPlayer(Atomination a){
		if (a.playerListPointer<a.numberOfPlayers){
			a.playerListPointer++;
		} else {
			a.playerListPointer=0;
		}
		a.currPlayer = a.playerList[playerListPointer];
	}
	
	private void currPlayerTurnStatement(){
		System.out.println(currPlayer.getColour() + "'s Turn\n");
	}
	
	private void place(int x, int y){
		// gameGrid[y][x].setOw
		if (numberOfActivePlayers==1){
			win();
		} else {
			updateCurrPlayer();
			currPlayerTurnStatement();
		}
	}
	
	private void win(){
		System.out.println(currPlayer.getColour() + "Wins\n<porgram ends>");
	}
	
	
//####################################################################################################################
//###                                         MAIN FUNCTION!!!                                                     ###
//####################################################################################################################
	
	public static void main(String[] args) {
		Atomination a = new Atomination();
		//Your game starts here!
		Scanner scan = new Scanner(System.in);
		
		while (true){
			a.input = scan.nextLine().split(" ");
			a.command = a.input[0].toUpperCase();
			
			if (a.command.equals("QUIT")){
				a.bye();
			}
			
			else if (a.command.equals("HELP")){
				a.help();
			}
			
			else if (a.command.equals("START")){
				if (a.gameStarted==true){
					System.out.println("Invalid Command");
					continue;
				}
				int len = a.input.length;
				if (len>4){ 
					System.out.println("Missing Argument");
				} else if (len<4){
					System.out.println("Too Many Arguments");
				} else {
					try{
						a.setNumOfPlayers(Integer.parseInt(a.input[1]));
						a.setWidth(Integer.parseInt(a.input[2]));
						a.setHeight(Integer.parseInt(a.input[3]));
						a.createPlayers();
						a.beginGame(a);
					} catch (Exception e){
						System.out.println("Invalid command arguments");
					}
				}
			} //end of START
			
			else if (a.command.equals("STAT")){
				if (a.gameStarted==false){
					System.out.println("Game Not In Progress");
				} else {
					for (Player p : a.playerList){
						System.out.println(p);
					}
				}
			} //end of STAT
			
			else if (a.command.equals("PLACE")){
				String invalid = "Invalid Coordinates";
				int x,y;
				try{
					if (a.input.length!=3){
						System.out.println(invalid);
						continue;
					} else {
						x = Integer.parseInt(a.input[1]);
						y = Integer.parseInt(a.input[2]);
						if ( (x>=0 && x<a.width) && (y>=0 && y<a.height) ){
							a.place(x,y);
						} else {
							System.out.println(invalid);
							continue;
						}
 					}
				} catch (Exception e){
					System.out.println(invalid);
					// System.out.println("here3");
				}
			} //end of PLACE
			
			else if (a.command.equals("DISPLAY")){
				a.printTop(a);
				a.printMiddle(a);
				a.printBottom(a);
			}
			
			
		} //end of while loop
		
		
	} //end of main
} //end of class