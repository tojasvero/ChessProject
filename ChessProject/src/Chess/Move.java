
package Chess;

import java.security.InvalidParameterException;

public class Move extends Place {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float value;
	Place to;
	boolean castling;
	
	public Move(int rank, int file, boolean castlingWay) {
		super(rank, file);
		if ((rank != 0 && rank != 7) || file !=4)
            throw new InvalidParameterException("Only an unmoved king can do castling");
		this.value=(float) 0.3;
		this.castling=true;
		to=new Place(rank, file+(castlingWay ? 2 : -2));
	}
	public Move(int rank, int file) {
		super(rank, file);
		this.value=0;
		this.castling=false;
	}
	public Move(int rank, int file, int torank, int tofile) {
		super(rank, file);
		this.value=0;
		this.castling=false;
		to=new Place(torank, tofile);
	}
	public Move(Place place) {
		super(place);
		this.value=0;
		this.castling=false;
		
	}
	//next 3 function are castling related
	boolean isCastling()
	{
		return castling;
	}
	//returns the rook's move when castling is performed
	Move getCastling()
	{
		Move move=null;
		if(castling)
		{
			if (to.getFile()>getFile())
				move=new Move(getRank(), getFile()+3, getRank(), getFile()+1);
			else
				move=new Move(getRank(), getFile()-4, getRank(), getFile()-1);
		}
		return move;
	}
	//returns the rook's move in case of rollback castling
	Move getRollbackCastling()
	{
		Move move=null;
		if(castling)
		{
			if (to.getFile()>getFile()) 
				move=new Move(getRank(), getFile()+1, getRank(), getFile()+3);
			else
				move=new Move(getRank(), getFile()-1, getRank(), getFile()-4);
		}
		return move;
	}
	public Place getTarget()
	{
		return to;
	}
	public void setTarget(Place place)
	{
		to=place;
	}
	public float getValue()
	{
		return value;
	}
	public void setValue(float value)
	{
		this.value=value;
	}
	public void addValue(float value)
	{
		this.value+=value;
	}
	public Move setTargetandValue(Place place, float value)
	{
		setTarget(place);
		setValue(value);
		return this;
	}
	
	public boolean fitPlaces(Place from, Place to)
	{
		if (fitFrom(from) && fitTo(to))
			return true;
		return false;
	}
	public boolean fitFrom(Place from)
	{
		if (from.equals(this))
			return true;
		return false;
	}
	public boolean fitTo(Place to)
	{
		if (to.equals(this.to))
			return true;
		return false;
	}

}
