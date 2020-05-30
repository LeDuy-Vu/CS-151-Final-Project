/**
 * This class represents a user.
 */
public class User implements Player
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
		User otherObject = (User) other ;
		return mark == otherObject.mark;
	}
	
	/**
	 * Constructs a User object with unique mark.
	 * @param mark the mark of the user
	 */
	public User(char mark)
	{
		this.mark = mark ;
	}
	
	/**
	 * Gets the mark of the user.
	 */
	public char getMark()
	{
		return mark;
	}
	
	/**
	 * Fake method and never used, for the sake of the inheritance hierarchy.
	 */
	public int play(char[][] board)
	{
		return -1;
	}
}