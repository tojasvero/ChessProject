/**
 * Board represents the actual standing
 * 
 */
package Chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


enum MatchResult {
	WHITE_WON,
	BLACK_WON,
	DRAW,
	IN_PROCESS
}
public class Board implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -18562585162373104L;
	
	private Fields fields;
	private CColor nextplayer=CColor.WHITE;
	
	

	private MatchResult matchResult=MatchResult.IN_PROCESS;
	
	public Board() {
		fields=new Fields();
	}
	
	public Piece getPiece(Place place)
	{
		return fields.get(place);
	}
	public void setPiece(Piece piece)
	{
		if (piece==null)
			throw new InvalidParameterException("setPiece need a valid Piece");
		fields.put(piece.getPlace(), piece);
	}
	public void setEmpty(int rank, int file)
	{
		fields.remove(new Place(rank, file));
	}
	

	public CColor getActualPlayer()
	{
		return nextplayer;
	}
	public void setActualPlayer(CColor color)
	{
		nextplayer=color;;
	}

	public boolean isRightPlace(int rank, int file)
	{
		return fields.getColor(rank, file)==nextplayer;
	}

	
	public MatchResult getMatchResult()
	{
		return matchResult;
	}
	public String getMatchResultString()
	{
		switch (matchResult)
		{
		case BLACK_WON:
			return "Black won!";
		case WHITE_WON:
			return "White won!";
		case DRAW:
			return "Draw!";
		default:
			return "In process";
		}
	}
	
	public String getFigure(int rank, int file) {
		return fields.getFigure(rank, file);
	}	
	
	public void reset() {
		setPiece(new Rook(CColor.WHITE,0,0));
		setPiece(new Knight(CColor.WHITE,0,1));
		setPiece(new Bishop(CColor.WHITE,0,2));
		setPiece(new Queen(CColor.WHITE,0,3));
		setPiece(new King(CColor.WHITE,0,4));
		setPiece(new Bishop(CColor.WHITE,0,5));
		setPiece(new Knight(CColor.WHITE,0,6));
		setPiece(new Rook(CColor.WHITE,0,7));
		
		for (int i=0;i<8;i++)
		{
			setPiece(new Pawn(CColor.WHITE,1,i));	
			setPiece(new Pawn(CColor.BLACK,6,i));
			for (int j=2;j<6;j++)
				setEmpty(j, i);
		}

		setPiece(new Rook(CColor.BLACK,7,0));
		setPiece(new Knight(CColor.BLACK,7,1));
		setPiece(new Bishop(CColor.BLACK,7,2));
		setPiece(new Queen(CColor.BLACK,7,3));
		setPiece(new King(CColor.BLACK,7,4));
		setPiece(new Bishop(CColor.BLACK,7,5));
		setPiece(new Knight(CColor.BLACK,7,6));
		setPiece(new Rook(CColor.BLACK,7,7));
		
	}
	
	//Collects all the possible moves of the current player
	public List<Move> getMoves(Piece piece)
	{
		ArrayList<Move> moves= new ArrayList<Move>();
		if (piece==null)
		{
			for (Piece tempPiece: fields.values())
			{
				if (tempPiece.getColor()==nextplayer)
					tempPiece.getMoves(this, moves);
			}
		}
		else
		{
			piece.getMoves(this, moves);
		}
		return moves;
	}
	private CColor opponentColor()
	{
		return (nextplayer==CColor.WHITE ? CColor.BLACK : CColor.WHITE);
	}
	
	public void getCastlingMoves(Piece piece, List<Place> places, List<Move> moves)
	{
		if (piece==null || !piece.isKing() || piece.getMoved())
			return;
		Place place=new Place(piece.getPlace()); //copy, not reference, so piece is not changed
		int rank=place.getRank();
		int file=place.getFile();
		int i;
		//short castling
		if (!fields.getMoved(rank, file+3)) {
			for(i=0;i<4;i++)
			{
				place.setPos(rank, file+i);
				if(places.contains(place) || (i>0 && i<3 && !fields.isEmpty(rank, file+i)))
					break;
			}
			if(i==4)
				moves.add(new Move(rank, file, true));
				
		}
		//long (queen side) castling
		if (!fields.getMoved(rank, file-4)) {
			for(i=0;i<5;i++)
			{
				place.setPos(rank, file-i);
				if(places.contains(place) || (i>0 && i<4 && !fields.isEmpty(rank, file-i)))
					break;
			}
			if(i==5)
				moves.add(new Move(rank, file, false));
		}	
	}
	
	//Collects all the controlled places of the current player's opponent
	//Place considered to be controlled: there is a piece which attacks the mentioned place. If there is a piece with the same color, 
	//it is considered to be controlled too (however it is not attacked, but defended 
	public List<Place> getControlledPlaces()
	{
		ArrayList<Place> places= new ArrayList<Place>();;
		for (Piece piece: fields.values())
		{
			if (piece.getColor()==opponentColor())
				piece.getControlledPlaces(this, places);			
		}
		return places;

	}
	
	//Place of the king
	private Place getKingPlace(CColor color)
	{
		for (Piece piece: fields.values())
			if (piece.isKing() && piece.getColor()==color)
				return piece.getPlace();
		return new Place(-1, -1);
		
	}
	
	//check if the king is under attack
	private boolean checkChess()
	{
		List<Place> places= getControlledPlaces();
		if(places.contains(getKingPlace(nextplayer)))
			return true;
		return false;
	}
	
	
	
	private void rollbackMove(Move move, Piece piece, Piece savedPiece)
	{
		piece.setPlace(move);
		setPiece(piece);
		if (savedPiece==null)
			setEmpty(move.getTarget().getRank(), move.getTarget().getFile());
		else
			setPiece(savedPiece);
		
		Move cmove=move.getRollbackCastling();
		if (cmove!=null)
		{
			Piece rookpiece=getPiece(cmove);
			rookpiece.setPlace(cmove.getTarget());
			setPiece(rookpiece);
			setEmpty(cmove.getRank(), cmove.getFile());
			
		}
	}
	
	
	private boolean performMove(Move move, boolean needCheck, boolean testOnly)
	{
		Piece piece=getPiece(move);
		if (piece==null || piece.getColor()!=nextplayer)
			return false;
		
		Piece savedPiece=getPiece(move.getTarget());
		
		Move cmove=move.getCastling();
		if (cmove!=null)
		{
			Piece rookpiece=getPiece(cmove);
			rookpiece.setPlace(cmove.getTarget());
			setPiece(rookpiece);
			setEmpty(cmove.getRank(), cmove.getFile());
		}
		piece.setPlace(move.getTarget());
		setPiece(piece);
		setEmpty(move.getRank(), move.getFile());
		if (needCheck && checkChess())
		{
			//chess! king is captured, this is bad move. roll back and return false
			rollbackMove(move, piece, savedPiece);
			return false;
		}
		if (testOnly)
			rollbackMove(move, piece, savedPiece);
		else
			piece.setMoved();
		return true;
		
	}
	
	//if nextplayer has not got valid move the game finished, but it can be draw or lost party, need to check
	//if the king is under attack, it is a lost
	private void setResult()
	{
		if (checkChess())
			matchResult=(nextplayer==CColor.WHITE ? MatchResult.BLACK_WON : MatchResult.WHITE_WON);
		else
			matchResult=MatchResult.DRAW;
	}

	//At the end of move change the active player
	//at the end of an automatic move the program has to check if the game is finished
	//Before the automatic move it is not important, the automatic move function checks it
	private void swapPlayer()
	{
		nextplayer=opponentColor();
		checkEndOfGame();
	}
	

	
	private boolean checkEndOfGame()
	{
		List<Move> moves=getMoves(null);
		for(Move move: moves)
		{
			if (performMove(move, true, true))
				return false;
		}
		setResult();
		return true;
		
		
	}

	//Very simple game plan: the program search the move with the highest value. 
	//Move value= captured piece's value + the moved pieces value if it steps out from the way of an attack - the moved pieces value if it steps in the way of an attack  
	public boolean automaticMove(Place from)
	{
		Piece piece=getPiece(from);
		if (piece.getColor()!=nextplayer)
			return false;

		List<Move> moves=getMoves(piece);
		if (moves.isEmpty()) //end of match...Check if it is a draw or the other side won 
		{
			checkEndOfGame();
			return false;
		}
		
		List<Place> places= getControlledPlaces();
		
		if (piece.isKing())
			getCastlingMoves(piece, places, moves);			

		
		float maxrank=-10;

		ArrayList<Move> bestMoves= new ArrayList<Move>();
		
		//opponentColor()
		Place opponentKingPlace=getKingPlace(opponentColor());
		
		for(Move move: moves)
		{
			//check move
			if (performMove(move, true, true))
			{
				if (!move.isCastling()) //castling's value is always 0.3
				{
					if(move.getValue()>0) //reward for agressive behaviour
						move.addValue((float) 0.1);
					//escape from a captured place. But if the piece is a king there shouldn't be a reward for the escape, because every valid move eliminate the danger
					//if (!piece.isKing() && !places.stream().filter(item -> item.equals(move)).collect(Collectors.toList()).isEmpty())
					if (!piece.isKing() && places.contains(move))
						move.addValue(fields.getValue(move.getRank(), move.getFile()));
					//entering to a captured place
					//if (!places.stream().filter(item -> item.equals(move.getTarget())).collect(Collectors.toList()).isEmpty())
					if (places.contains(move.getTarget()))
						move.addValue(fields.getValue(move.getRank(), move.getFile())*-1);
					if(move.getValue()+0.5>=maxrank)
					{
						//check, if the move cause chess, and there is a reward for it
						Piece savedPiece=getPiece(move.getTarget());
						piece.setPlace(move.getTarget());
						setPiece(piece);
						setEmpty(move.getRank(), move.getFile());

						nextplayer=opponentColor();
						
						List<Place> tempControlledPlaces=getControlledPlaces();
						if (tempControlledPlaces.contains(opponentKingPlace))
						{
							if (checkEndOfGame())
							{
								return false;
							}
							move.addValue((float) 0.5);
						}
						nextplayer=opponentColor();
						rollbackMove(move, piece, savedPiece);
					}
				}
				if (move.getValue()>maxrank)
				{
					bestMoves.clear();
					maxrank=move.getValue();
				}
				if(move.getValue()>=maxrank)
					bestMoves.add(move);
			}
		}
		if (bestMoves.isEmpty()) //end of match...Check if it is a draw or the other side won 
		{
			checkEndOfGame();
			return false;
		}
		
		Random random = new Random();
		int index=random.nextInt(bestMoves.size());
		performMove(bestMoves.get(index), false, false);
		swapPlayer();
		return true;
	}
}
