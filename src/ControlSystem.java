import java.util.Random;
import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import java.io.FileNotFoundException ;

/**
 * This class controls Tic Tac Toe gameplay and maintains GUI.
 */
public class ControlSystem
{
	private static Player computer ;
	private static Player user ;
	private static final Board board = new Board() ;
	private static Log log ;
	private static final JFrame frame = new JFrame() ;
	private static String fileData ;

	private static int turn ;
	private static final int COMPUTER_TURN = 0 ;
	private static final int USER_TURN = 1 ;
	
	//State number
	private static int state ;
	private static final int WELCOME = 0 ;
	private static final int CHOOSE_MARK = 1 ;
	private static final int CHOOSE_DIFFICULTY = 2 ;
	private static final int PLAY_GAME = 3 ;
	private static final int CONCLUSION = 4 ;
	private static final int NO_GAME = 5 ;
	private static final int CHOOSE_GAME = 6 ;
	private static final int SHOW_GAME = 7 ;
	
	//Button number
	private static final int NULL = 0 ;
	private static final int NEW_GAME = 1 ;
	private static final int CHOOSE_X = 2 ;
	private static final int CHOOSE_O = 3 ;
	private static final int EASY_AI = 4 ;
	private static final int HARD_AI = 5 ;
	private static final int MAKE_MOVE = 6 ;
	private static final int KEEP_USING = 7 ;
	private static final int LOAD_GAME = 8 ;
	
	/**
	 * Overriden method to show the state of this object as a string.
	 */
	public String toString()
	{
		return getClass().getName() + "state = " + state + ", turn = " + turn + ", fileData = " + fileData + ", computer = " + computer.toString() 
		+ ", user = " + user.toString() + ", board = " + board.toString() + ", log = " + log.toString() + "]";
	}
	
	/**
	 * Constructs a ControlSystem object.
	 */
	public ControlSystem(Log l)
	{
		state = WELCOME ;
		log = l ;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		frame.setVisible(true) ;
		paintFrame() ;
	}
	
	/**
	 * Chooses which GUI to display based on the state.
	 */
	private static void paintFrame()
	{
		frame.getContentPane().removeAll() ;
		
		switch (state)
		{
			case WELCOME:
				paintWelcome() ;
				break ;
				
			case CHOOSE_MARK:
				paintMark() ;
				break ;
				
			case CHOOSE_DIFFICULTY:
				paintDifficulty() ;
				break ;
				
			case PLAY_GAME:
				playGame() ;
				break ;
				
			case CONCLUSION:
				paintConclusion() ;
				break ;
				
			case NO_GAME:
				paintNoGame() ;
				break ;
				
			case CHOOSE_GAME:
				paintOldGames() ;
				break ;
				
			case SHOW_GAME:
				showGame() ;
				break ;
		}

		frame.pack() ;
		frame.revalidate() ;
		frame.repaint() ;
	}
	
	/**
	 * Designs the GUI for the welcome screen.
	 */
	private static void paintWelcome()
	{
		JButton newGame = new JButton("Play a new game") ;
		newGame.addActionListener(createButtonListener(NEW_GAME, NULL, NULL)) ;
		
		JButton loadGame = new JButton("Load an old game") ;
		loadGame.addActionListener(createButtonListener(LOAD_GAME, NULL, NULL)) ;
		
		JPanel welcomePanel = new JPanel() ;
		welcomePanel.add(newGame) ;
		welcomePanel.add(loadGame) ;
		
		frame.add(new JLabel("Welcome to Le Duy Vu's Tic Tac Toe game"), BorderLayout.NORTH) ;
		frame.add(welcomePanel, BorderLayout.SOUTH) ;
	}
	
	/**
	 * Designs the GUI where the user chooses mark.
	 */
	private static void paintMark()
	{
		JButton chooseX = new JButton("X") ;
		chooseX.addActionListener(createButtonListener(CHOOSE_X, NULL, NULL)) ;
		
		JButton chooseO = new JButton("O") ;
		chooseO.addActionListener(createButtonListener(CHOOSE_O, NULL, NULL)) ;
		
		JPanel chooseMarkPanel = new JPanel() ;
		chooseMarkPanel.add(chooseX) ;
		chooseMarkPanel.add(chooseO) ;
		
		frame.add(new JLabel("Choose to play as X or O"), BorderLayout.NORTH) ;
		frame.add(chooseMarkPanel, BorderLayout.SOUTH) ;
	}
	
	/**
	 * Designs the GUI where the user chooses difficulty.
	 */
	private static void paintDifficulty()
	{
		JButton easy = new JButton("Easy") ;
		easy.addActionListener(createButtonListener(EASY_AI, NULL, NULL)) ;
		
		JButton hard = new JButton("Hard") ;
		hard.addActionListener(createButtonListener(HARD_AI, NULL, NULL)) ;
		
		JPanel chooseDifficultyPanel = new JPanel() ;
		chooseDifficultyPanel.add(easy) ;
		chooseDifficultyPanel.add(hard) ;
		
		frame.add(new JLabel("Choose difficulty level"), BorderLayout.NORTH) ;
		frame.add(chooseDifficultyPanel, BorderLayout.SOUTH) ;
	}
	
	/**
	 * Lets the computer make its move and update the board.
	 */
	private static void playGame()
	{
		if (turn == COMPUTER_TURN)
		{
			int move ;
			do
			{
				move = computer.play(board.getBoard()) ;
			} while (board.checkComputerMove(move, computer.getMark())) ;
			
			turn = USER_TURN ;
			
			if (board.gameOver() || board.isDraw())
			{
				state = CONCLUSION ;
				paintFrame() ;
			}
			else
				paintBoard() ;
		}
		else
			paintBoard() ;
	}
	
	/**
	 * Designs and maintains the GUI of the board where the user plays.
	 */
	private static void paintBoard()
	{
		char[][] currentBoard = board.getBoard() ;
		
		JPanel boardPanel = new JPanel() ;
		boardPanel.setLayout(new GridLayout(3, 3)) ;
		for (int i = 0; i < currentBoard.length; i++)
		{
			final int row = i ;
			for (int j = 0; j < currentBoard.length; j++)
			{
				final int col = j ;
				JButton cellButton = new JButton("" + currentBoard[i][j]) ;
				boardPanel.add(cellButton) ;
				
				if (currentBoard[i][j] == ' ')
					cellButton.addActionListener(createButtonListener(MAKE_MOVE, row, col)) ;
			}
		}
		
		frame.add(new JLabel("Your turn"), BorderLayout.NORTH) ;
		frame.add(boardPanel, BorderLayout.CENTER) ;
	}
	
	/**
	 * Designs the final screen after the game is over and writes data into file.
	 */
	private static void paintConclusion()
	{
		if (!board.gameOver())
			frame.add(new JLabel("It's a draw"), BorderLayout.NORTH) ;
		else
			if (turn == COMPUTER_TURN)
				frame.add(new JLabel("Congratulation! You won"), BorderLayout.NORTH) ;
			else
				frame.add(new JLabel("Computer won"), BorderLayout.NORTH) ;
		
		char[][] currentBoard = board.getBoard() ;
		JPanel boardPanel = new JPanel() ;
		boardPanel.setLayout(new GridLayout(3, 3)) ;
		for (int i = 0; i < currentBoard.length; i++)
			for (int j = 0; j < currentBoard.length; j++)
			{
				JButton cellButton = new JButton("" + currentBoard[i][j]) ;
				boardPanel.add(cellButton) ;
			}
		frame.add(boardPanel, BorderLayout.CENTER) ;
		
		writeFile() ;
	}
	
	/**
	 * Writes data into file.
	 */
	private static void writeFile()
	{
		JPanel confirmPanel = new JPanel() ;
		try
		{
			String moves = board.getMovesPlayed() ;
			if (board.isDraw())
				moves += "0" ;
			log.writeFile(fileData + moves) ;
			confirmPanel.add(new JLabel("Game saved succesfully")) ;
		}
		catch (FileNotFoundException ex)
		{
			confirmPanel.add(new JLabel("Unexpected error. Game can't be saved")) ;
		}
		finally
		{
			JButton keepUsing = new JButton("OK") ;
			keepUsing.addActionListener(createButtonListener(KEEP_USING, NULL, NULL)) ;
			confirmPanel.add(keepUsing) ;
			frame.add(confirmPanel, BorderLayout.SOUTH) ;
		}
	}
	
	/**
	 * Designs the screen shown when there is no game to load.
	 */
	private static void paintNoGame()
	{
		frame.add(new JLabel("There is no old game in the database"), BorderLayout.NORTH) ;
		JButton acknowledge = new JButton("OK") ;
		acknowledge.addActionListener(createButtonListener(KEEP_USING, NULL, NULL)) ;
		frame.add(acknowledge, BorderLayout.SOUTH) ;
	}
	
	/**
	 * Designs the screen where user chooses old game to load.
	 */
	private static void paintOldGames()
	{
		JPanel gamesPanel = new JPanel() ;
		gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS)) ;
		for (int i = 1; i <= log.getFileCount(); i++)
		{
			final int gameNo = i ;
			JButton gameButton = new JButton("Game " + i) ;
			gamesPanel.add(gameButton) ;
			gameButton.addActionListener(new 
					ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							try
							{
								log.setCurrentGame(gameNo) ;
								state = SHOW_GAME ;
								paintFrame() ;
							}
							catch (FileNotFoundException ex)
							{
								gameButton.setText("Error: Can't open file") ;
							}
						}
					}) ;
		}
		
		JButton back = new JButton("Back to menu") ;
		back.addActionListener(createButtonListener(KEEP_USING, NULL, NULL)) ;
		
		frame.add(new JLabel("Choose the game you want to load"), BorderLayout.NORTH) ;
		frame.add(gamesPanel, BorderLayout.CENTER) ;
		frame.add(back, BorderLayout.SOUTH) ;
	}
	
	/**
	 * Designs and maintain the GUI where each move of the past game is shown.
	 */
	private static void showGame()
	{
		boolean playerFirst = paintDetail() ;
		JButton[][] buttonList = createButtons() ;
		
		JPanel toolBar = new JPanel() ;
		JLabel winner = new JLabel("") ;
		toolBar.add(winner) ;
		JButton nextMove = new JButton("Next move") ;
		toolBar.add(nextMove) ;
		frame.add(toolBar, BorderLayout.SOUTH) ;
		
		nextMove.addActionListener(new 
			ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					if (log.isEnd() || log.isDraw())
					{
						if (log.isDraw())
							winner.setText("It's a draw") ;
						else
							if ((playerFirst && log.getNumMoves() % 2 != 0) || (!playerFirst && log.getNumMoves() % 2 == 0))
								winner.setText("User wins") ;
							else
								winner.setText("Computer wins") ;
						
						nextMove.setText("Done") ;
						nextMove.removeActionListener(nextMove.getActionListeners()[0]) ;
						nextMove.addActionListener(createButtonListener(LOAD_GAME, NULL, NULL)) ;
					}
					else
					{
						if ((playerFirst && log.getIndex() % 2 == 0) || (!playerFirst && log.getIndex() % 2 != 0))
							buttonList[(log.getCell() - 1) / board.getSize()][(log.getCell() - 1) % board.getSize()].setText(fileData.substring(0, 1)) ;
						else
							buttonList[(log.getCell() - 1) / board.getSize()][(log.getCell() - 1) % board.getSize()].setText(fileData.substring(5, 6)) ;
						log.advanceIndex() ;
					}
				}
			}) ;
	}
	
	/**
	 * Designs the part of the screen showing the property of the old game.
	 * @return true if the user goes first in that game
	 */
	private static boolean paintDetail()
	{
		fileData = log.getMarks() ;
		JPanel detailPanel = new JPanel() ;
		
		detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS)) ;
		detailPanel.add(new JLabel("User as " + fileData.substring(0, 1))) ;
		detailPanel.add(new JLabel("Computer as " + fileData.substring(5, 6))) ;
		detailPanel.add(new JLabel("Difficulty: " + fileData.substring(1, 5))) ;
		
		try
		{
			if (fileData.substring(6, 7).equals(fileData.substring(0, 1)))
			{
				detailPanel.add(new JLabel("User go first")) ;
				turn = 1 ;
				return true;
			}
			else
			{
				detailPanel.add(new JLabel("Computer go first")) ;
				turn = 0 ;
				return false;
			}
		}
		finally
		{
			frame.add(detailPanel, BorderLayout.NORTH) ;
		}
	}
	
	/**
	 * Designs the board of the old game.
	 * @return the list of buttons, each button is a cell on the board
	 */
	private static JButton[][] createButtons()
	{
		JButton[][] buttonList = new JButton[board.getSize()][board.getSize()] ;
		JPanel boardPanel = new JPanel() ;
		boardPanel.setLayout(new GridLayout(3, 3)) ;
		for (int i = 0; i < board.getSize(); i++)
			for (int j = 0; j < board.getSize(); j++)
			{
				JButton cellButton = new JButton(" ") ;
				buttonList[i][j] = cellButton ;	
				boardPanel.add(cellButton) ;
			}
		frame.add(boardPanel, BorderLayout.CENTER) ;
		
		log.getMoves() ;
		return buttonList;
	}
	
	/**
	 * Creates ActionListener object when a button is pressed and refreshes the GUI based on the new state.
	 * @param button the button being pressed
	 * @param x any additional argument
	 * @param y any additional argument
	 * @return the ActionListener object
	 */
	private static ActionListener createButtonListener(final int button, final int x, final int y)
	{
		return new 
			ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					switch (button)
					{
						case NEW_GAME:
							state = CHOOSE_MARK ;
							break ;
							
						case CHOOSE_X:
							state = CHOOSE_DIFFICULTY ;
							user = new User('X') ;
							fileData = user.getMark() + "" ;
							break ;
							
						case CHOOSE_O:
							state = CHOOSE_DIFFICULTY ;
							user = new User('O') ;
							fileData = user.getMark() + "" ;
							break ;
							
						case EASY_AI:
							state = PLAY_GAME ;
							createEasyAI(true) ;
							break ;
							
						case HARD_AI:
							state = PLAY_GAME ;
							createEasyAI(false) ;
							break ;
							
						case MAKE_MOVE:
							board.updateBoard(x, y, user.getMark()) ;
							turn = COMPUTER_TURN ;
							if (board.gameOver() || board.isDraw())
								state = CONCLUSION ;
							break ;
						
						case KEEP_USING:
							state = WELCOME ;
							break ;
							
						case LOAD_GAME:
							if (log.getFileCount() == 0)
								state = NO_GAME ;
							else
								state = CHOOSE_GAME ;
							break ;
					}
					
					paintFrame() ;
				}
			};
	}
	
	/**
	 * Creates an AI object based on the choice of the user, cleans the board, and determines which player goes first.
	 * @param isEasy true if the user chooses easy difficulty, false if hard
	 */
	private static void createEasyAI(boolean isEasy)
	{
		if (isEasy)
		{
			computer = new EasyAI(user.getMark()) ;
			fileData += "Easy" ;
		}
		else
		{
			computer = new HardAI(user.getMark()) ;
			fileData += "Hard" ;
		}
		
		board.resetBoard() ;
		fileData += computer.getMark() + "" ;

		if (new Random().nextInt(2) == 0)
		{
			turn = COMPUTER_TURN ;
			fileData += computer.getMark() + "\n" ;
		}
		else
		{
			turn = USER_TURN ;
			fileData += user.getMark() + "\n" ;
		}
	}
}