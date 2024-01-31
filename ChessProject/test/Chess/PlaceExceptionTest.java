/**
 * Tested class: Place
 * Tested functions: constructor, addPos, setPos
 * test cases: bad parameters (position is out of the board)
 * exception expected
 */
package Chess;


import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PlaceExceptionTest {

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{-1,2},
			{1,8},
			{5,-2},
			{10,2},
			{-11,20}
		});
	}
	
	int rank;
	int file;
	public PlaceExceptionTest(int rank, int file)
	{
		this.rank=rank;
		this.file=file;
	}
	
	@Test
	public void setPos_OutOfBoard() {
		exceptionRule.expect(InvalidParameterException.class);
		exceptionRule.expectMessage("Rank and file should be between 0 and 7");
		Place place=new Place(0,0);
		place.setPos(rank, file);
	}

	@Test
	public void addPos_OutOfBoard() {
		exceptionRule.expect(InvalidParameterException.class);
		exceptionRule.expectMessage("Rank and file should be between 0 and 7");
		Place place=new Place(0,0);
		place.addPos(rank, file);
	}
	
	@Test
	public void Construct_OutOfBoard() {
		exceptionRule.expect(InvalidParameterException.class);
		exceptionRule.expectMessage("Rank and file should be between 0 and 7");
		Place place=new Place(rank,file);
		
	}

}
