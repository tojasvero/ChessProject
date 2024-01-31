package Chess;

import java.util.List;

public class Queen extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Queen(CColor color, int rank, int file)
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
		//check all the diagonal moves
		longStraightMove(1, 1, moves, board);
		longStraightMove(1, -1, moves, board);
		longStraightMove(-1, -1, moves, board);
		longStraightMove(-1, 1, moves, board);
		return moves;
	}
	@Override
	public String getFigure() {
		if (this.getColor()==CColor.WHITE)
			return Character.toString((char)whiteQueen);
		return Character.toString((char)blackQueen);
	}
	@Override
	public float getValue() {
		return 10;
	}

	@Override
	public List<Place> getControlledPlaces(Board board, List<Place> places) {
		// TODO Auto-generated method stub
		//Diagonal
		longStraightPlaceCheck(1, 1, places, board);
		longStraightPlaceCheck(1, -1, places, board);
		longStraightPlaceCheck(-1, -1, places, board);
		longStraightPlaceCheck(-1, 1, places, board);

		longStraightPlaceCheck(1, 0, places, board);
		longStraightPlaceCheck(-1, 0, places, board);
		longStraightPlaceCheck(0, -1, places, board);
		longStraightPlaceCheck(0, 1, places, board);
		
		return places;
	}

}
