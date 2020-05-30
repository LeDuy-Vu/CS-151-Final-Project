/**
 * This interface represents a Tic Tac Toe player.
 */
public interface Player
{
	/**
	 * Gets the unique mark of the player.
	 * @return the mark of the player
	 */
	char getMark() ;
	
	/**
	 * Return a move made by the player.
	 * @param board the current status of the board
	 * @return the move
	 */
	int play(char[][] board) ;
	
	/**
	 * Overriden method to show the state of this object as a string.
	 * @return the string representing the state
	 */
	String toString() ;
	
	/**
	 * Overriden method to compare this object with another object.
	 * @param other the other object
	 * @return true if both have the same mark
	 */
	boolean equals(Object other) ;
}