/**
 * This abstract class represents a computer.
 */
public abstract class AbstractAI implements Player
{
	private final char mark ;
	
	/**
	 * Overriden method to show the state of this object as a string.
	 */
	public String toString()
	{
		return getClass().getName() + "[mark = " + mark + "]";
	}
	
	/**
	 * Constructs an AI object with unique mark based on the input mark.
	 * @param mark the mark of the user
	 */
	public AbstractAI(char mark)
	{
		if (mark == 'X')
			this.mark = 'O' ;
		else
			this.mark = 'X' ;
	}
	
	/**
	 * Gets the mark of the computer.
	 */
	public char getMark()
	{
		return mark;
	}
}