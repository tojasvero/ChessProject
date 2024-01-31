package Chess;

import java.util.List;

public class King extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public King(CColor color, int rank, int file)
	{
		super(color, rank, file);
		mightkill=false;
	}
	

	@Override
	public List<Move> getMoves(Board board, List<Move> moves) {
		// TODO Auto-generated method stub
		//check all the straight moves
		shortStraightMove(1, 0, moves, board);
		shortStraightMove(-1, 0, moves, board);
		shortStraightMove(0, -1, moves, board);
		shortStraightMove(0, 1, moves, board);
		//check all the diagonal moves
		shortStraightMove(1, 1, moves, board);
		shortStraightMove(1, -1, moves, board);
		shortStraightMove(-1, -1, moves, board);
		shortStraightMove(-1, 1, moves, board);
		return moves;
	}
	@Override
	public String getFigure() {
		if (this.getColor()==CColor.WHITE)
			return Character.toString((char)whiteKing);
		return Character.toString((char)blackKing);
	}
	@Override
	public float getValue() {
		return 100;
	}

	@Override
	public List<Place> getControlledPlaces(Board board, List<Place> places) {
		// TODO Auto-generated method stub
		shortStraightPlaceCheck(1, 0, places, board);
		shortStraightPlaceCheck(-1, 0, places, board);
		shortStraightPlaceCheck(0, 1, places, board);
		shortStraightPlaceCheck(0, -1, places, board);
		
		shortStraightPlaceCheck(1, 1, places, board);
		shortStraightPlaceCheck(1, -1, places, board);
		shortStraightPlaceCheck(-1, -1, places, board);
		shortStraightPlaceCheck(-1, 1, places, board);
		return places;
	}

}
