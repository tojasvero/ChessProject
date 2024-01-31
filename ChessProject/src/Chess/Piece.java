/**Abstact class for pieces. A base piece has attributes: its CColor, ...
 * Abstract functions: piece's possible moves in the actual moment, squares which the piece controls. Value of the piece. Figure of the piece (to display on the UI)
 * 
 */
package Chess;

import java.security.InvalidParameterException;
import java.util.List;

enum CColor {
	WHITE,
	BLACK,
	UNDEFINED
}

public abstract class Piece implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6249402586347066077L;
	private CColor color;
	private Place place;
	protected boolean mightkill=true; //every piece might be killed, except the king
	private boolean moved;
	public static final int whiteKing=0x2654;
	public static final int whiteQueen=0x2655;
	public static final int whiteRook=0x2656;
	public static final int whiteBishop=0x2657;
	public static final int whiteKnight=0x2658;
	public static final int whitePawn=0x2659;
	public static final int blackKing=0x265A;
	public static final int blackQueen=0x265B;
	public static final int blackRook=0x265C;
	public static final int blackBishop=0x265D;
	public static final int blackKnight=0x265E;
	public static final int blackPawn=0x265F;
	
	public Piece(CColor color, int rank, int file)
	{
		place=new Place(rank, file);
		this.color=color;
		moved=false;
		//board.
	}
	
	/** test one move to the given way
	 * enabledCapture: is it permitted to capture a different colored piece (when a pawn step forward, it is not enabled)
	 * Same to empty space: the pawn's diagonal move is possible when it captures a piece
	 */
	private boolean oneMove(int c1, int c2, List<Move> moves, Board board, boolean enableCapture, boolean enableEmptyField)
	{
		Place place=new Place(getPlace()); 
		Move move=new Move(place);
		try {
			place.addPos(c1, c2);
			
			Piece piece=board.getPiece(place);
			if (piece==null)
			{
				if (enableEmptyField)
					moves.add(move.setTargetandValue(place, 0));
				else
					return false;
				
			}
			else
			{
				if (piece.getColor()==getColor())
					//Same CColor, cannot go to this field
					return false;
					
				//other piece captured (if it is a king, you may not kill it, 
				//but a king may not be in your way here 
				if (enableCapture)
					moves.add(move.setTargetandValue(place, piece.getValue()));
				//cannot go behind this piece
				return false;
				
			}
		}
		catch (InvalidParameterException e) {
			// We are out of board, continue with the next way
			return false;
		}
		return true;
	}
	/** helper function to test that a place is controlled by the piece 
	 */
	private boolean onePlace(int c1, int c2, List<Place> places, Board board)
	{
		try {
			Place place=new Place(getPlace()); 
			place.addPos(c1, c2);
			Piece piece=board.getPiece(place);
			places.add(place);
			return (piece==null);
		}
		catch (InvalidParameterException e) {
			// We are out of board, continue with the next way
			return false;
		}
	}
	
	//helper functions to gather the moves
	protected void longStraightMove(int c1, int c2, List<Move> moves, Board board)
	{
		int step=1;
		while (oneMove(c1*step, c2*step, moves, board, true, true)) step++;
	}

	protected boolean shortStraightMove(int c1, int c2, List<Move> moves, Board board, boolean enableCapture, boolean enableEmptyField)
	{
		return oneMove(c1, c2, moves, board, enableCapture, enableEmptyField);
	}
	protected boolean shortStraightMove(int c1, int c2, List<Move> moves, Board board)
	{
		return shortStraightMove(c1, c2, moves, board, true, true);	
	}
	//helper functions to gather the controlled places
	protected void longStraightPlaceCheck(int c1, int c2, List<Place> places, Board board)
	{
		int step=1;
		while (onePlace(c1*step, c2*step, places, board)) step++;
	}

	protected boolean shortStraightPlaceCheck(int c1, int c2, List<Place> places, Board board)
	{
		return onePlace(c1, c2, places, board);
	}
	
	public CColor getColor() {
		return color;
	}
	
	public void setColor(CColor color) {
		this.color=color;
	}
	
	public Place getPlace() {
		return place;
	}
	
	public void setPlace(Place place) {
		this.place=place;
	}

	public void setMoved() {
		moved=true;
	}
	public boolean getMoved() {
		return moved;
	}

	public boolean isKing() {
		return !mightkill;
	}
	
	public abstract List<Move> getMoves(Board board, List<Move> moves);
	
	public abstract List<Place> getControlledPlaces(Board board, List<Place> places);
	
	public abstract String getFigure();
	
	public abstract float getValue();
	
}
