/**
 * This class represents a computer at difficulty level hard.
 */
public class HardAI extends AbstractAI
{
	/**
	 * Overriden method to compare this object with another object.
	 */
	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		HardAI otherObject = (HardAI) other ;
		return getMark() == otherObject.getMark();
	}
	
	/**
	 * Constructs an HardAI object.
	 * @param mark the input mark to pass to super constructor
	 */
	public HardAI(char mark)
	{
		super(mark) ;
	}
	
	/**
	 * Generates a "calculated" move.
	 */
	public int play(char[][] board)
	{
		char[][] original = new char[board.length][board.length] ;
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				original[i][j] = board[i][j] ;
		
		if (checkRowCol(board, true))
			return makeCalculatedMove(original, board);
		
		if (checkRowCol(board, false))
			return makeCalculatedMove(original, board);
		
		if (checkDiagonal(board, true))
			return makeCalculatedMove(original, board);
		
		if (checkDiagonal(board, false))
			return makeCalculatedMove(original, board);
		
		return makeRandomMove(board);
	}
	
	/**
	 * Check if the game can be won by row or column
	 * @param board the current data on the board
	 * @param x true if check row, false if check column
	 * @return true if the game can be won in next turn
	 */
	private boolean checkRowCol(char[][] board, boolean x)
	{
		int chance, danger ;
		int row = -1, col = -1;
		
		for (int i = 0; i < board.length; i++)
		{
			chance = 0 ;
			danger = 0 ;
			
			for (int j = 0; j < board.length; j++)
				if (x)
					if (board[i][j] == getMark())
						chance++ ;
					else if (board[i][j] == ' ')
					{
						row = i ;
						col = j ;
					}
					else
						danger++ ;
				else
					if (board[j][i] == getMark())
						chance++ ;
					else if (board[j][i] == ' ')
					{
						row = j ;
						col = i ;
					}
					else
						danger++ ;
		
			if ((chance == 2 && danger == 0) || (chance == 0 && danger == 2))
			{
				board[row][col] = getMark() ;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check if the game can be won by diagonal
	 * @param board the current data on the board
	 * @param x true if check left-right diagonal, false if check right-left
	 * @return true if the game can be won in next turn
	 */
	private boolean checkDiagonal(char[][] board, boolean x)
	{
		int chance = 0, danger = 0;
		int row = -1, col = -1;
		
		if (x)
			for (int i = 0; i < board.length; i++)
			{
				if (board[i][i] == getMark())
					chance++ ;
				else if(board[i][i] == ' ')
					row = col = i ;
				else
					danger++ ;
			}
		else
			for (int i = 0, j = board.length - 1; i < board.length && j >= 0; i++, j--)
			{
				if (board[i][j] == getMark())
					chance++ ;
				else if(board[i][j] == ' ')
					row = col = i ;
				else
					danger++ ;
			}
		
		if ((chance == 2 && danger == 0) || (chance == 0 && danger == 2))
		{
			board[row][col] = getMark() ;
			return true;
		}
		return false;
	}
	
	/**
	 * Makes the move that makes the computer win or blocks the user win.
	 * @param original the current status of the board
	 * @param board the status of the board after this move is produced
	 * @return the calculated move
	 */
	private int makeCalculatedMove(char[][] original, char[][] board)
	{
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				if (original[i][j] != board[i][j])
					return i * board.length + j + 1;
		return 0;
	}
	
	/**
	 * Makes a random move.
	 * @param board the current status of the board
	 * @return the random move
	 */
	private int makeRandomMove(char[][] board)
	{
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				if (board[i][j] == ' ')
					return i * board.length + j + 1;
		return 0;
	}
}