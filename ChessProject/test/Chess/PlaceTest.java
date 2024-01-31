/**
 * Tested class: Place
 * Tested functions: constructor, addPos, setPos, equals (override), compareTo (override)
 * test cases: good parameters (position is on the board or special case (-1,-1)
 */
package Chess;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Assert;

@RunWith(Parameterized.class)
public class PlaceTest {
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{-1,-1, 0, 0},
			{1,2,3,4},
			{0,0,1,1},
			{2,5,5,2},
			{6,5,-4,-3}
		});
	}
	
	int rank;
	int file;
	int addrank; 
	int addfile;
	
	public PlaceTest(int rank, int file, int addrank, int addfile)
	{
		this.rank=rank;
		this.file=file;
		this.addrank=addrank;
		this.addfile=addfile;
	}

	@Test
	public void ConstructInsideBoard() {
		Place place=new Place(rank+addrank,file+addfile);
		Assert.assertEquals(place.getRank(), rank+addrank);
		Assert.assertEquals(place.getFile(), file+addfile);
		
	}
	@Test
	public void addPosInsideBoard() {
		Place place=new Place(rank,file);
		//-1,-1 special case, forbidden to use in addPos!!!
		if (rank==-1 && file==-1) 
		{
			exceptionRule.expect(InvalidParameterException.class);
			exceptionRule.expectMessage("Rank and file should be between 0 and 7");
			place.addPos(addrank, addfile);
		}
		else
		{
			place.addPos(addrank, addfile);
			Assert.assertEquals(place.getRank(), rank+addrank);
			Assert.assertEquals(place.getFile(), file+addfile);
			Assert.assertEquals(place, new Place(rank+addrank,file+addfile));
		}
	}
	@Test
	public void setPosInsideBoard() {
		Place place=new Place(rank,file);
		place.setPos(rank+addrank,file+addfile);
		Assert.assertEquals(place.getRank(), rank+addrank);
		Assert.assertEquals(place.getFile(), file+addfile);
		Assert.assertEquals(place, new Place(rank+addrank,file+addfile));
	}
	@Test
	public void testCompareTo() {
		Place place=new Place(rank,file);
		Place otherPlace=new Place(place);
		if (rank==-1 && file==-1) 
		{
			exceptionRule.expect(InvalidParameterException.class);
			exceptionRule.expectMessage("Rank and file should be between 0 and 7");
			otherPlace.addPos(addrank, addfile);
		}
		otherPlace.addPos(addrank, addfile);
		if (addrank>0 || (addrank==0 && addfile>0)) //otherPlace>place
			Assert.assertTrue(otherPlace.compareTo(place)>0);
		else if(addrank<0 || (addrank==0 && addfile<0))
			Assert.assertTrue(otherPlace.compareTo(place)<0);
		else
			Assert.assertTrue(place.compareTo(otherPlace)==0);
	}
	
	
	
}
