package Chess;


import java.util.TreeMap;

public class Fields extends TreeMap<Place, Piece> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CColor getColor(int rank, int file)
	{
		Piece piece=get(new Place(rank, file));
		if (piece==null)
			return CColor.UNDEFINED;
		return piece.getColor();
	}
	public String getFigure(int rank, int file) {
		Piece piece=get(new Place(rank, file));
		if (piece==null)
			return "";
		return piece.getFigure();
	}
	public float getValue(int rank, int file) {
		Piece piece=get(new Place(rank, file));
		if (piece==null)
			return 0;
		return piece.getValue();
	}
	
	
	public boolean getMoved(int rank, int file) {
		Piece piece=get(new Place(rank, file));
		if (piece==null) return true;
		return piece.getMoved();
	}
	public boolean isEmpty(int rank, int file) {
		if (get(new Place(rank, file))==null) return true;
		return false;
	}
	
}
