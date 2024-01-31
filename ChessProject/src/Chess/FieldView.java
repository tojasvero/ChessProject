/**Class to visualize a square on the board, and handle click on it. 
 * Inherited from JButton, the handler is an ActionListener (defined by lambda expression)
 * 
 */

package Chess;


import javax.swing.JButton;

import java.awt.Color;
import java.awt.Insets;

public class FieldView extends JButton {
	private static final long serialVersionUID = 1L;

	//Square colors (normal and highlighted)

	private static final Color LIGHT_GRAY = new Color(238,238,238);
	private static final Color WHITE = new Color(255,255,255);
	private static final Color LIGHT_GRAY_HL = new Color(138,238,238);
	private static final Color WHITE_HL = new Color(155,255,255);

	private BoardView boardView;
	//The field's color, calculated from it's place (rank, file)
	private boolean isWhite;
	
	public FieldView(int rank, int file, BoardView theBoardView)
	{
		this.boardView=theBoardView;
		addActionListener(e->boardView.clickField(rank, file));
		
		//setMargin: without margin there is enough space for button text
		setMargin(new Insets(0,0,0,0));
		//don't want to see the border
		setBorderPainted(false);
		isWhite=(((7-rank) + file) % 2 == 0);
		setHighlighted(false);
	}
	
	public void setHighlighted(boolean highlighted){
		setBackground(isWhite ? (highlighted? WHITE_HL : WHITE) : (highlighted? LIGHT_GRAY_HL : LIGHT_GRAY));
	}
	
	public void setFigure(String figureText, int size) {
		setFont(getFont().deriveFont((float)size));
		setText(figureText);
	}
}
