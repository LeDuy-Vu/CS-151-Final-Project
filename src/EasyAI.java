import java.util.Random ;

/**
 * This class represents a computer at difficulty level easy.
 */
public class EasyAI extends AbstractAI
{
	private static final int NUMBER_OF_CELL = 9 ;
	
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
		EasyAI otherObject = (EasyAI) other ;
		return getMark() == otherObject.getMark();
	}
	
	/**
	 * Constructs an EasyAI object.
	 * @param mark the input mark to pass to super constructor
	 */
	public EasyAI(char mark)
	{
		super(mark) ;
	}
	
	/**
	 * Generates a random move.
	 */
	public int play(char[][] board)
	{
		return 1 + new Random().nextInt(NUMBER_OF_CELL);
	}
}