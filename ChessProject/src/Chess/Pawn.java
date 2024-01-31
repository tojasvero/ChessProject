package Chess;

import java.util.List;

public class Pawn extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//If a pawn didn't move it can step 2 forward
	
	public Pawn(CColor color, int rank, int file)
	{
		super(color, rank, file);
	}

	@Override
	public List<Move> getMoves(Board board, List<Move> moves) {
		// TODO Need to work out
		//check all the straight moves
		int forward=(getColor()==CColor.WHITE ? 1:-1);
		
		if (shortStraightMove(forward*1, 0, moves, board,false, true) && !getMoved())
			shortStraightMove(forward*2, 0, moves, board,false, true);
		
		shortStraightMove(forward*1, 1, moves, board,true, false);
		shortStraightMove(forward*1, -1, moves, board,true, false);
		return moves;
	}
	@Override
	public String getFigure() {
		if (this.getColor()==CColor.WHITE)
			return Character.toString((char)whitePawn);
		return Character.toString((char)blackPawn);
	}
	@Override
	public float getValue() {
		return 1;
	}

	@Override
	public List<Place> getControlledPlaces(Board board, List<Place> places) {
		// TODO Auto-generated method stub
		int forward=(getColor()==CColor.WHITE ? 1:-1);
		//just the diagonal!
		shortStraightPlaceCheck(forward*1, 1, places, board);
		shortStraightPlaceCheck(forward*1, -1, places, board);
		return places;	

	}

}
