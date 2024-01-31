package Chess;

import java.util.List;

public class Rook extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Rook(CColor color, int rank, int file)
	{
		super(color, rank, file);
	}

	@Override
	public List<Move> getMoves(Board board, List<Move> moves) {
		// TODO Auto-generated method stub
		//check all the straight moves
		longStraightMove(1, 0, moves, board);
		longStraightMove(-1, 0, moves, board);
		longStraightMove(0, -1, moves, board);
		longStraightMove(0, 1, moves, board);
		return moves;
	}
	@Override
	public String getFigure() {
		if (this.getColor()==CColor.WHITE)
			return Character.toString((char)whiteRook);
		return Character.toString((char)blackRook);
	}
	@Override
	public float getValue() {
		return 5;
	}

	@Override
	public List<Place> getControlledPlaces(Board board, List<Place> places) {
		// TODO Auto-generated method stub
		//Straight
		longStraightPlaceCheck(1, 0, places, board);
		longStraightPlaceCheck(-1, 0, places, board);
		longStraightPlaceCheck(0, -1, places, board);
		longStraightPlaceCheck(0, 1, places, board);
		
		return places;
	}

}
