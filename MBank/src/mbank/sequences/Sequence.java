package mbank.sequences;

public abstract class Sequence {
	private static long id = 1;
	
	public synchronized static long getNext()
	{
		return(id++);
	}
}
