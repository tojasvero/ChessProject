package Chess;

import java.util.List;

public class Knight extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Knight(CColor color, int rank, int file)
	{
		super(color, rank, file);
	}

	@Override
	public List<Move> getMoves(Board board, List<Move> moves) {
		// TODO Auto-generated method stub
		//check all the straight moves
		shortStraightMove(1, 2, moves, board);
		shortStraightMove(1, -2, moves, board);
		shortStraightMove(-1, 2, moves, board);
		shortStraightMove(-1, -2, moves, board);
		//check all the diagonal moves
		shortStraightMove(2, 1, moves, board);
		shortStraightMove(2, -1, moves, board);
		shortStraightMove(-2, 1, moves, board);
		shortStraightMove(-2, -1, moves, board);
		return moves;
	}
	@Override
	public String getFigure() {
		if (this.getColor()==CColor.WHITE)
			return Character.toString((char)whiteKnight);
		return Character.toString((char)blackKnight);
	}
	@Override
	public float getValue() {
		return 3;
	}

	@Override
	public List<Place> getControlledPlaces(Board board, List<Place> places) {
		// TODO Auto-generated method stub
		shortStraightPlaceCheck(1, 2, places, board);
		shortStraightPlaceCheck(1, -2, places, board);
		shortStraightPlaceCheck(-1, 2, places, board);
		shortStraightPlaceCheck(-1, -2, places, board);
		
		shortStraightPlaceCheck(2, 1, places, board);
		shortStraightPlaceCheck(2, -1, places, board);
		shortStraightPlaceCheck(-2, 1, places, board);
		shortStraightPlaceCheck(-2, -1, places, board);
		return places;	
	}

}
