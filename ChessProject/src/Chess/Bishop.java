package Chess;

import java.util.List;

public class Bishop extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bishop(CColor color, int rank, int file)
	{
		super(color, rank, file);
	}
	
	@Override
	public List<Move> getMoves(Board board, List<Move> moves)
	{
		//check all the diagonal ways
		//++
		longStraightMove(1, 1, moves, board);
		//+-
		longStraightMove(1, -1, moves, board);
		//--
		longStraightMove(-1, -1, moves, board);
		//-+
		longStraightMove(-1, 1, moves, board);
		return moves;
	}

	@Override
	public String getFigure() {
		if (this.getColor()==CColor.WHITE)
			return Character.toString((char)whiteBishop);
		return Character.toString((char)blackBishop);
	}

	@Override
	public float getValue() {
		return 3;
	}

	@Override
	public List<Place> getControlledPlaces(Board board, List<Place> places) {
		// TODO Auto-generated method stub
		//Diagonal
		longStraightPlaceCheck(1, 1, places, board);
		longStraightPlaceCheck(1, -1, places, board);
		longStraightPlaceCheck(-1, -1, places, board);
		longStraightPlaceCheck(-1, 1, places, board);
		
		return places;
	}
}
