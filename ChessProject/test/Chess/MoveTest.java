/**
 * Move test: checks Move constructors, fitPlaces, isCastling, getCastling, getRollbackCastling functions
 */

package Chess;

import java.security.InvalidParameterException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MoveTest {
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	Move shortCastling;
	Move longCastling;
	Move sampleMove;
	
	@Before
	public void setUp() throws Exception {
		shortCastling=new Move(0, 4, true);
		longCastling=new Move(7, 4, false);
		sampleMove=new Move(1,5,2,6);
	}
	
	//simple move construction test + some getter, setter test, 
	@Test
	public void testMove() {
		Move move=new Move(1,5);
		Assert.assertNull(move.getTarget());
		Assert.assertEquals(move.getValue(), 0, 0.0001);
		Assert.assertEquals(move.getFile(), 5);
		move.setTargetandValue(new Place(2,6), 10);
		Assert.assertNotNull(move.getTarget());
		Assert.assertEquals(move.getTarget().getRank(), 2);
		Assert.assertEquals(move.getValue(), 10, 0.0001);
		Assert.assertTrue(move.fitPlaces(sampleMove, sampleMove.getTarget()));
	}

	//short castling
	@Test
	public void testBadCastling() {
		exceptionRule.expect(InvalidParameterException.class);
		exceptionRule.expectMessage("Only an unmoved king can do castling");
		Move move=new Move(3,4,true);
	}
	
	//bad move
	@Test
	public void testBadMove() {
		exceptionRule.expect(InvalidParameterException.class);
		exceptionRule.expectMessage("Rank and file should be between 0 and 7");
		Move move=new Move(1,8,2,6);
	}
	
	//isCastling test
	@Test
	public void testCastling() {
		Assert.assertTrue(longCastling.isCastling());
		Assert.assertTrue(shortCastling.isCastling());
		Assert.assertFalse(sampleMove.isCastling());
	}
	
	//getCastling test
	@Test
	public void testGetCastling() {
		Assert.assertTrue(longCastling.getCastling().fitPlaces(new Place(7,0), new Place(7,3)));
		Assert.assertTrue(shortCastling.getCastling().fitPlaces(new Place(0,7), new Place(0,5)));
		Assert.assertNull(sampleMove.getCastling());
	}
	
	//getRollbackCastling test
	@Test
	public void testGetRollbackCastling() {
		Assert.assertTrue(longCastling.getRollbackCastling().fitPlaces(new Place(7,3), new Place(7,0)));
		Assert.assertTrue(shortCastling.getRollbackCastling().fitPlaces(new Place(0,5), new Place(0,7)));
		Assert.assertNull(sampleMove.getRollbackCastling());
	}

}
