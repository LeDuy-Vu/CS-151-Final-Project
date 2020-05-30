import java.io.* ;
import java.util.Scanner ;
import java.util.Comparator ;

/**
 * This class handles the process of writing data into and loading data from file.
 */
public class Log
{
	private static int fileCount ;
	private static String movesPlayed ;
	private static int index ;
	private static File file ;
	private static PrintWriter output ;
	private static Scanner input ;
	private static final int MAX_LENGTH_OF_MOVESPLAYED = 10 ;
	
	/**
	 * Overriden method to show the state of this object as a string.
	 */
	public String toString()
	{
		return getClass().getName() + "[fileCount = " + fileCount + ", movesPlayed = " + movesPlayed + ", index = " + index + "]";
	}
	
	/**
	 * Creates a Comparator object that compares 2 logs by the number of files in their File's directory.
	 * @return a Comparator object
	 */
	public static Comparator<Log> comparatorByFileCount()
	{
		return new 
			Comparator<Log>()
			{
				public int compare(Log l1, Log l2)
				{
					if (l1.getFileCount() < l2.getFileCount())
						return -1;
					if (l1.getFileCount() > l2.getFileCount())
						return 1;
					return 0;
				}
			};
	}
	
	/**
	 * Constructs a Log object.
	 */
	public Log(String directory)
	{
		file = new File(directory) ;
		if (!file.exists())
			file.mkdir() ;
		fileCount = file.listFiles().length ;
		index = 0 ;
		movesPlayed = "" ;
	}
	
	/**
	 * Gets the number of game files in the database.
	 * @return
	 */
	public int getFileCount()
	{
		return fileCount;
	}
	
	/**
	 * Write the parameter into a new file.
	 * @param content the content of the new file
	 * @throws FileNotFoundException if an error occurs when creating a new file
	 * @precondition content.length() > 0
	 */
	public void writeFile(String content) throws FileNotFoundException
	{
		assert(content.length() > 0): "Data can't be empty" ;
		output = new PrintWriter(file.getPath() + "/Game " + (fileCount++ + 1) + ".txt") ;
		output.print(content) ;
		output.close() ;
	}
	
	/**
	 * Sets the Scanner object to a game file to load data.
	 * @param x the file number in the database
	 * @throws FileNotFoundException if the file is not found
	 * @precondition x >= 1 && x <= fileCount
	 */
	public void setCurrentGame(int x) throws FileNotFoundException
	{
		assert(x >= 1 && x <= fileCount): "File number must be positive" ;
		input = new Scanner(file.listFiles()[x - 1]) ;
	}
	
	/**
	 * Gets the first line of the data file as a string.
	 * @return the first line of the data file
	 * @precondition input.hasNext()
	 */
	public String getMarks()
	{
		assert(input.hasNext()): "There is nothing else to read in the file" ;
		return input.nextLine();
	}
	
	/**
	 * Assigns the second line of the data file to movesPlayed.
	 * @precondition input.hasNext()
	 */
	public void getMoves()
	{
		assert(input.hasNext()): "There is nothing else to read in the file" ;
		try
		{
			index = 0 ;
			movesPlayed = input.nextLine();
		}
		finally
		{
			input.close() ;
		}
	}
	
	/**
	 * Determines if the game ended up draw.
	 * @return true if the game in the file ended up draw.
	 */
	public boolean isDraw()
	{
		return (movesPlayed.length() == MAX_LENGTH_OF_MOVESPLAYED && index == movesPlayed.length() - 1);
	}
	
	/**
	 * Determines if the game ended up with someone winning.
	 * @return true if someone won the game
	 */
	public boolean isEnd()
	{
		return index == movesPlayed.length();
	}
	
	/**
	 * Gets the number of moves.
	 * @return the number of moves
	 */
	public int getNumMoves()
	{
		return movesPlayed.length();
	}
	
	/**
	 * Gets index.
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}
	
	/**
	 * Gets the move where the index is pointing at.
	 * @return the character in the string where the index is pointing
	 */
	public int getCell()
	{
		return Character.getNumericValue(movesPlayed.charAt(index));
	}
	
	/**
	 * Increments the index.
	 */
	public void advanceIndex()
	{
		index++ ;
	}
}
