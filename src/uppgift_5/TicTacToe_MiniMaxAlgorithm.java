package uppgift_5;

import java.awt.*;

// Hugo Söderholm & Malte Enlund

// Class to represent Move objects
class Move {
    int val;   // Value of the move
    int row;   // Row and column coordinates
    int col;
    
    public Move(int v, int r, int c) {
    	val=v;
    	row=r;
    	col=c;
    }
    public int getValue() {
    	return val;
    }
    public int getRow() {
    	return row;
    }
    public int getCol() {
    	return col;
    }
}

// Class Button extends JButton with (x,y) coordinates
class Button extends javax.swing.JButton {
    public int i;   // The row and column coordinate of the button in a GridLayout
    public int j;

    public Button (int x, int y) {
	// Create a JButton with a blank icon. This also gives the button its correct size.
	super();
	super.setIcon(new javax.swing.ImageIcon(getClass().getResource("None.png")));
	this.i = x;
	this.j = y;
    }

    // Return row coordinate
    public int get_i () {
	return i;
    }

    // Return column coordinate
    public int get_j () {
	return j;
    }
}

public class TicTacToe_MiniMaxAlgorithm extends javax.swing.JFrame {

    // Marks on the board
    public static final int EMPTY    = 0;
    public static final int HUMAN    = 1;
    public static final int COMPUTER = 2;

    // Outcomes of the game
    public static final int HUMAN_WIN    = 4;
    public static final int DRAW         = 5;
    public static final int CONTINUE     = 6;
    public static final int COMPUTER_WIN = 7;
    
    public static final int SIZE = 3;
    private int[][] board = new int[SIZE][SIZE];  // The marks on the board
    private javax.swing.JButton[][] jB;           // The buttons of the board

    /* Constructor for the Tic Tac Toe game */
    public TicTacToe_MiniMaxAlgorithm() {
	// Close the window when the user exits
	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	initBoard();      // Set up the board with all marks empty
    }

    // Initalize an empty board. 
    private void initBoard(){
	// Create a SIZE*SIZE gridlayput to hold the buttons	
	java.awt.GridLayout layout = new GridLayout(SIZE, SIZE);
	getContentPane().setLayout(layout);

	// The board is a grid of buttons
	jB = new Button[SIZE][SIZE];
	for (int i=0; i<SIZE; i++) {
	    for (int j=0; j<SIZE; j++) {
		// Create a new button and add an actionListerner to it
		jB[i][j] = new Button(i,j);
		// Add an action listener to the button to handle mouse clicks
		jB[i][j].addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent act) {
			    jBAction(act);
			}
		    });
		add(jB[i][j]);   // Add the buttons to the GridLayout
		
		board[i][j] = EMPTY;     // Initialize all marks on the board to empty
	    }
	}
	// Pack the GridLayout and make it visible
	pack();
    }

    // Action listener which handles mouse clicks on the buttons
    private void jBAction(java.awt.event.ActionEvent act) {
    	Button thisButton = (Button) act.getSource();
    	int i = thisButton.get_i();
		int j = thisButton.get_j();
		System.out.println("Button[" + i + "][" + j + "] was clicked by you");
		
		// HUMAN move
		if (checkResult() == CONTINUE) {
			if (checkSquare(i, j) == EMPTY) {
				thisButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("X.png")));
			}
			place(i, j, HUMAN);
		}
		// COMPUTER move
		if (checkResult() == CONTINUE) {
			Move move = findMove(COMPUTER);
			if (checkSquare(move.getRow(), move.getCol()) == EMPTY) {
				thisButton = (Button) jB[move.getRow()][move.getCol()];
				System.out.println("Button[" + move.getRow() + "][" + move.getCol() + "] was clicked by the AI");  // DEBUG
				thisButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("O.png")));
			}
			place (move.getRow(), move.getCol(), COMPUTER);
		}
    }

    private Move findMove(int turn) {
    	int value = 0, bestMoveRow=0, bestMoveCol=0;
    	/* Undersök först terminala positiner */
    	Move check = immediateCompWin();
    	if (check != null) {
    		return check;
    	}
    	else if (fullboard()) value = DRAW;
    	else { /* Undersök icke-terminala positiner */
    		if (turn == COMPUTER) value = HUMAN_WIN; /* Börja med minsta värde */
    		else if (turn == HUMAN) value = COMPUTER_WIN;
    		for (int i=0; i<SIZE; i++) { /* Kolla alla rutor */
    			for (int j=0; j<SIZE; j++) {
    				if (checkSquare(i,j) == EMPTY) { /* Välj en tom ruta */
        				place(i, j, turn); /* Undersök den */
        				turn = (turn == HUMAN) ? COMPUTER : HUMAN; // Byter värde på "turn" för att rekursivt anropa motståndarens drag
        				int respVal = findMove(turn).getValue();
        				unplace(i, j); /* Återställ brädet */
        				turn = (turn == HUMAN) ? COMPUTER : HUMAN; // Byter tillbaka
        				if (turn == COMPUTER) {
        					if (respVal > value) { /* Uppdatera bästa draget */
        						value = respVal;
        						bestMoveRow = i;
        						bestMoveCol = j;
        					}
        				}
        				else if (turn == HUMAN) {
           					if (respVal < value) { /* Uppdatera bästa draget */
           						value = respVal;
           						bestMoveRow = i;
           						bestMoveCol = j;
           					}
           				}
        			}
    			}
    		}
    	}
    	return new Move(value, bestMoveRow, bestMoveCol);
    }

	private void unplace(int i, int j) {
		board[i][j] = EMPTY;
	}

	private int checkSquare(int row, int col) {
		return board[row][col];
	}

	// This function checks if one player (HUMAN or COMPUTER) wins, if the board is full (DRAW)
	// or if the game should continue.
	private Move immediateCompWin() {
		// Checking for rows for X or O victory.
		for (int row = 0; row < SIZE; row++) {
			if (board[row][0] == board[row][1] &&
				board[row][1] == board[row][2]) {
				if (board[row][0] == HUMAN)
					return new Move(HUMAN_WIN, row, 0);
				else if (board[row][0] == COMPUTER)
					return new Move(COMPUTER_WIN, row, 0);
			}
		}
		// Checking for columns for X or O victory.
		for (int col = 0; col < SIZE; col++) {
			if (board[0][col] == board[1][col] &&
				board[1][col] == board[2][col]) {
				if (board[0][col] == HUMAN)
					return new Move(HUMAN_WIN, 0, col);
				else if (board[0][col] == COMPUTER)
					return new Move(COMPUTER_WIN, 0, col);
			}
		}
		// Checking for diagonals for X or O victory.
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			if (board[0][0] == HUMAN)
				return new Move(HUMAN_WIN, 0, 0);
			else if (board[0][0] == COMPUTER)
				return new Move(COMPUTER_WIN, 0, 0);
		}
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			if (board[0][2] == HUMAN)
				return new Move(HUMAN_WIN, 0, 2);
			else if (board[0][2] == COMPUTER)
				return new Move(COMPUTER_WIN, 0, 2);
		}
		return null;
    }
	
	private int checkResult() {
		Move result = immediateCompWin();
		if (result != null) {
			return result.getValue();
		}
		else if (fullboard()) {
	    	return DRAW;
	    }
	    else return CONTINUE;
	}
	
    private boolean fullboard() {
    	int counter = 0;
    	for (int i = 0; i < SIZE; i++) {
    		for (int j = 0; j < SIZE; j++) {
    			if (checkSquare(i,j) != EMPTY) {
    				counter++;
    			}
    		}
    	}
    	if (counter == SIZE*SIZE) {
    		return true;
    	}
    	return false;
    }
    
    // Place a mark for one of the players (HUMAN or COMPUTER) in the specified position
    public void place (int row, int col, int player){
    	board [row][col] = player;
    }

    public static void main (String [] args){

    	String threadName = Thread.currentThread().getName();
    	TicTacToe_MiniMaxAlgorithm lsGUI = new TicTacToe_MiniMaxAlgorithm();      // Create a new user interface for the game
    	lsGUI.setVisible(true);
	
    	java.awt.EventQueue.invokeLater (new Runnable() {
    		public void run() {
    			while ( (Thread.currentThread().getName() == threadName) &&
		    	    (lsGUI.checkResult() == CONTINUE) ){
    				try {
    					Thread.sleep(100);  // Sleep for 100 millisecond, wait for button press
    				} catch (InterruptedException e) { };
    			}
    		}
	    });
    }
}
