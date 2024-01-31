/**Class for one square on the board. Nothing else, just the position
 * 
 */

package Chess;

import java.security.InvalidParameterException;

public class Place implements java.io.Serializable, Comparable<Place> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rank; //rank: name of the rows on the board
	private int file; //file: name of the columns on the board
	//copy of the parameter, not ref
	public Place(Place place)
	{
		setPos(place.rank, place.file);	
	}
	public Place()
	{
		setPos(-1, -1);
	}	
	public Place(int rank, int file)
	{
		setPos(rank, file);
	}	
	
	boolean equals(Place place)
	{
		if (rank==place.getRank() && file==place.getFile())
			return true;
		return false;
	}
	@Override
	public boolean equals(Object place)
	{
		return equals((Place)place);
	}
	public void setPos(int rank, int file) throws InvalidParameterException
	{
	
		if ((rank < 0 || rank > 7 || file < 0 || file > 7) && (rank!=-1 || file!=-1))
            throw new InvalidParameterException("Rank and file should be between 0 and 7");
		this.rank=rank;
		this.file=file;
	}
	public void addPos(int rank, int file)  throws InvalidParameterException
	{
		if (this.rank < 0 || this.rank > 7 || this.file < 0 || this.file > 7)
            throw new InvalidParameterException("Rank and file should be between 0 and 7");
		this.rank+=rank;
		this.file+=file;
		
		if (this.rank < 0 || this.rank > 7 || this.file < 0 || this.file > 7)
		{
			this.rank-=rank;
			this.file-=file;
            throw new InvalidParameterException("Rank and file should be between 0 and 7");
		}
	}
	
	public boolean onBoard()
	{
		return rank!=-1;
	}
	
	public int getRank() {return rank;} 
	public int getFile() {return file;}
	
	@Override
	public int compareTo(Place o) {
		// TODO Auto-generated method stub
		return rank*8+file-o.rank*8-o.file;
	}

}
