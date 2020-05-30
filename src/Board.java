import java.util.ArrayList ;

/**
 * This class represents a Tic Tac Toe board.
 */
public class Board
{
	private static final int SIZE = 3 ;
	private static final char[][] board = new char[SIZE][SIZE] ;
	private static final ArrayList<Integer> movesPlayed = new ArrayList<Integer>() ;
	
	/**
	 * Overriden method to show the state of this object as a string.
	 */
	public String toString()
	{
		String boardContent = "" ;
		for (int i = 0; i < SIZE; i++)
		{
			boardContent += "[" ;
			for (int j = 0; j < SIZE; j++)
				if (j != SIZE - 1)
					boardContent += board[i][j] + ", " ;
				else
					boardContent += board[i][j] + "]" ;
		}
		
		return getClass().getName() + "[board = " + boardContent + ", movesPlayed = " + movesPlayed + "]";
	}
	
	/**
	 * Refreshes the content of the board and the list of moves.
	 */
	public void resetBoard()
	{
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				board[i][j] = ' ' ;
		movesPlayed.clear() ;
	}
	
	/**
	 * Gets the size of the board.
	 * @return the size
	 */
	public int getSize()
	{
		return SIZE;
	}
	
	/**
	 * Gets the content of the board.
	 * @return a copy of the board
	 */
	public char[][] getBoard()
	{
		return (char[][]) board.clone();
	}
	
	/**
	 * Gets the moves that have been played as a string.
	 * @return the past moves
	 */
	public String getMovesPlayed()
	{
		String moves = "" ;
		for (int i = 0; i < movesPlayed.size(); i++)
			moves += movesPlayed.get(i).toString() ;
		return moves;
	}
	
	/**
	 * Checks if the input move is legal or not, then changes the board if legal.
	 * @param move the move that needs to be checked
	 * @param mark the mark that should be placed on the board if legal
	 * @return true if legal, false otherwise
	 * @precondition move >= 1 && move <= SIZE * SIZE
	 */
	public boolean checkComputerMove(int move, char mark)
	{
		assert(move >= 1 && move <= SIZE * SIZE): "Invalid move" ;
		if (movesPlayed.contains(move))
			return true;
		board[(move - 1) / SIZE][(move - 1) % SIZE] = mark ;
		movesPlayed.add(move) ;
		return false;
	}
	
	/**
	 * Change a cell of the board.
	 * @param row the row index of the cell
	 * @param col the column index of the cell
	 * @param mark the mark that should be placed
	 * @precondition row >= 0 && col >= 0
	 */
	public void updateBoard(int row, int col, char mark)
	{
		assert(row >= 0 && col >= 0): "Invalid arguments" ;
		board[row][col] = mark ;
		movesPlayed.add(row * SIZE + col + 1) ;
	}
	
	/**
	 * Checks if the game has been won by row.
	 * @return true if someone won by row
	 */
	private boolean winRow()
	{
		for (int i = 0; i < SIZE; i++)
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ')
				return true;
		return false;
	}
	
	/**
	 * Checks if the game has been won by column.
	 * @return true if someone won by column
	 */
	private boolean winColumn()
	{
		for (int i = 0; i < SIZE; i++)
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ')
				return true;
		return false;
	}
	
	/**
	 * Checks if the game has been won by diagonal.
	 * @return true if someone won by diagonal
	 */
	private boolean winDiagonal()
	{
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ')
			return true;
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ')
			return true;
		return false;
	}
	
	/**
	 * Checks if someone won the game.
	 * @return true if someone won
	 */
	public boolean gameOver()
	{
		return (winRow() || winColumn() || winDiagonal());
	}
	
	/**
	 * Checks if no one won the game.
	 * @return true if the game ends up draw
	 */
	public boolean isDraw()
	{
		return movesPlayed.size() == SIZE * SIZE;
	}
}
