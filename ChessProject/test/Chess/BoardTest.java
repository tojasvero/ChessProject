/**
 * Set up a position, where some move are determined by the automaticMove's supposed algorithm, and then check them
 * The tested function is the automaticMove, but it means that all functions are tested through this
 */

package Chess;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	Board board;
	
	@Before
	public void setUp() throws Exception {
		board=new Board();
		board.setPiece(new Knight(CColor.BLACK,4,5));
		board.setPiece(new King(CColor.WHITE,0,4));
		board.setPiece(new Rook(CColor.BLACK,1,0));
		board.setPiece(new Rook(CColor.BLACK,2,1));
		board.setPiece(new Rook(CColor.WHITE,0,7));
		board.setPiece(new Pawn(CColor.WHITE,3,3));
		board.setPiece(new Pawn(CColor.BLACK,4,4));
		board.setPiece(new Pawn(CColor.BLACK,5,5));
		board.setPiece(new King(CColor.BLACK,7,3));
		board.setPiece(new Bishop(CColor.WHITE,6,0));
		
	}
	@Test
	public void testBishopMove() {
		//bishop should move 6,0->4,2 to escape from captures
		board.setActualPlayer(CColor.WHITE);
		board.automaticMove(new Place(6,0));
		Assert.assertEquals(board.getFigure(4, 2),Character.toString((char)Piece.whiteBishop));
		Assert.assertNull(board.getPiece(new Place(6,0)));
	}
	@Test
	public void testCheckMove() {
		//white rook should give a chess (cannot capture opponent, but for a check there is a reward
		board.setActualPlayer(CColor.WHITE);
		board.automaticMove(new Place(0,7));
		Assert.assertEquals(board.getFigure(7, 7),Character.toString((char)Piece.whiteRook));
		Assert.assertNull(board.getPiece(new Place(0,7)));
	}

	@Test
	public void testCheckmateMove() {
		//black rook 2,1 would prefer checkmate (it is an ultimate goal)
		board.setActualPlayer(CColor.BLACK);
		board.automaticMove(new Place(2,1));
		Assert.assertEquals(board.getMatchResult(),MatchResult.BLACK_WON);
	}
	@Test
	public void testBlackRookMove() {
		//black rook 1,2 should capture white bishop, it is better, then a check
		board.setActualPlayer(CColor.BLACK);
		board.automaticMove(new Place(1,0));
		Assert.assertEquals(board.getFigure(6, 0),Character.toString((char)Piece.blackRook));

	}
	@Test
	public void testWhiteCastling() {
		//White king should choose the castling, there is a reward for it (versus 0 value moves)
		board.setActualPlayer(CColor.WHITE);
		board.automaticMove(new Place(0,4));
		Assert.assertEquals(board.getFigure(0, 5),Character.toString((char)Piece.whiteRook));
		Assert.assertEquals(board.getFigure(0, 6),Character.toString((char)Piece.whiteKing));
	}
	@Test
	public void testWhiteCastlingAfterMove() {
		//White king after move cannot choose the castling
		board.setActualPlayer(CColor.WHITE);
		board.getPiece(new Place(0,7)).setMoved();
		board.automaticMove(new Place(0,4));
		Assert.assertNull(board.getPiece(new Place(0,6)));
		Assert.assertEquals(board.getFigure(0, 7),Character.toString((char)Piece.whiteRook));
	}
	@Test
	public void testBlackKnightMove() {
		//black knight shouldn't capture white pawn, because white bishop saves it
		board.setActualPlayer(CColor.BLACK);
		board.automaticMove(new Place(4,5));
		Assert.assertEquals(board.getFigure(3, 3),Character.toString((char)Piece.whitePawn));
		Assert.assertNull(board.getPiece(new Place(4,5)));
	}
	@Test
	public void testBlackPawnMove() {
		//black pawn should capture white pawn, despite it will be captured by white bishop
		//+1 for the captur, -1 for the being in danger, that is 0, 
		//but there is an agressive behaviour reward
		board.setActualPlayer(CColor.BLACK);
		board.automaticMove(new Place(4,4));
		Assert.assertEquals(board.getFigure(3, 3),Character.toString((char)Piece.blackPawn));
		Assert.assertNull(board.getPiece(new Place(4,4)));
	}
}
