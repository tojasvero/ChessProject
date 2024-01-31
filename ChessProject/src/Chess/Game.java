package Chess;

import javax.swing.*;

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Board board=new Board();
		board.reset();
		
		BoardView view=new BoardView(board);
		
		
		view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE );
		view.pack();
		view.setResizable(true);
		view.setLocationRelativeTo( null );
		view.setVisible(true);

	}

}
