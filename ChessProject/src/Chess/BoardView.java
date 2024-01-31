/**
 * UI
 * The UI extends JFrame, and implements ComponentListener to handle the resize event  
 * MouseListeners are used in chessboard square level, doesn't need here
 */

package Chess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BoardView extends JFrame implements ComponentListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int minSize=3;
	private static final int maxSize=7;
	
	
	JLayeredPane layeredPane;
	JPanel chessBoard;
	JButton save;
	JButton open;
	JLabel label;
	Place place;
	Board board;
	
	int lastSize=6;
	
	
	
	public BoardView(Board board)
	{
		super("Chess");
		place=new Place();
	
		this.board=board;
		 
		//  Use a Layered Pane 
		layeredPane = new JLayeredPane();
		//layeredPane = getLayeredPane(); 
		getContentPane().add(layeredPane);

		layeredPane.addComponentListener(this);
		
		//Create the save and open buttons
		save = new JButton();  

	    save.setText("Save");
	    save.setBounds(10, 20, 70, 20);

	    save.addActionListener(new SaveFile()); 

	    open = new JButton();
	    open.setText("Open");
	    open.setBounds(100, 20, 70, 20);
	    open.addActionListener(new OpenFile()); 

	    
	    layeredPane.add(save, JLayeredPane.DEFAULT_LAYER-1);
	    layeredPane.add(open, JLayeredPane.DEFAULT_LAYER-2);

	    label = new JLabel();
	    label.setBounds(180, 20, 200, 20);
	    layeredPane.add(label, JLayeredPane.DEFAULT_LAYER-3);
	    	    
	    
	    
	    //Add a chess board to the Layered Pane 
		chessBoard = new JPanel();
		//Make a border for the chessboard. Use BorderFactory to create border.
		//BorderFactory actually might not create new instances each time you call it, 
		//but return a reference to an existing one, therefore saving some resources
		
		chessBoard.setBorder(BorderFactory.createRaisedBevelBorder());
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout( new GridLayout(8, 8) );
		
		/*
		 * rank and boardviev row is calculate from each other as x=7-y,
		 * because the first line in a chessboard is the bottom line 
		 */
		for (int i = 0; i < 64; i++)
			chessBoard.add( new FieldView(7 - i / 8, i % 8,this) );
		
		setBoardSize(lastSize*100,lastSize*100+100, true); 
		pack();
		
	}
	
	//save current match position
	class SaveFile implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser jFileChoser = new JFileChooser();
			
			if (jFileChoser.showSaveDialog(BoardView.this)==JFileChooser.APPROVE_OPTION)
			{
				//serialize board
				try {
					FileOutputStream fileOut =
							new FileOutputStream(jFileChoser.getCurrentDirectory().toString()+"\\"+jFileChoser.getSelectedFile().getName());
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(board);
					out.close();
					fileOut.close();
		
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,e1.getMessage(),
							"Save exception, file", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,e1.getMessage(),
							"Save exception, IO", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	//Load an earlier saved party
	class OpenFile implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser jFileChoser = new JFileChooser();
			if (jFileChoser.showOpenDialog(BoardView.this)==JFileChooser.APPROVE_OPTION)
			{
				//deserialize board from file
				try {
					FileInputStream fileIn = new FileInputStream(jFileChoser.getCurrentDirectory().toString()+"\\"+jFileChoser.getSelectedFile().getName());
					ObjectInputStream in = new ObjectInputStream(fileIn);
					board=(Board) in.readObject();
					in.close();
					fileIn.close();
					ReDrawBoard();
		
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,e1.getMessage(),
							"Save exception, file", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,e1.getMessage(),
							"Save exception, IO", JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,e1.getMessage(),
							"Save exception, Class", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	
	
	static private int borderedValue(int value, int min, int max)
	{
		return (value > max? max : (value<min ? min : value));
	}
	
	private void setBoardSize(int width, int height, boolean forced)
	{
	    
	    
	    height-=100;
		int actSize=borderedValue(width/100, minSize, borderedValue(height/100, minSize, maxSize));
		if (forced || actSize!=lastSize)
		{
			lastSize=actSize;
			Dimension windowSize = new Dimension(lastSize*100, lastSize*100+100);
			Dimension boardSize = new Dimension(lastSize*100, lastSize*100);
			layeredPane.setPreferredSize(windowSize);
			chessBoard.setPreferredSize( boardSize );
			chessBoard.setBounds(0, 100, boardSize.width, boardSize.height);
			
			ReDrawBoard();
		}
	}

	public void ReDrawBoard(String additionalInfo)
	{
		for (int i = 0; i < 64; i++)
			((FieldView)chessBoard.getComponent(i)).setFigure(board.getFigure(7-i/8, i%8), lastSize*8);
		if (board.getMatchResult()!=MatchResult.IN_PROCESS)
			label.setText("Game finished, "+board.getMatchResultString());
		label.setText((board.getActualPlayer()==CColor.WHITE ? "White's move" : "Black's move") + additionalInfo);
	}
	private void ReDrawBoard()
	{
		ReDrawBoard("");
	}
	
	public void setField(int rank, int file)
	{
		if (board.isRightPlace(rank, file))
		{
			place.setPos(rank, file);
			((FieldView)chessBoard.getComponent(8*(7-rank)+file)).setHighlighted(true);
		}
	}

	public void clickField(int rank, int file)
	{
		if (!place.onBoard())
			setField(rank, file);
		else
			performMove(rank, file);
		
	}
	public void performMove(int rank, int file)
	{
		if (!place.onBoard())
			return;
		
		if (rank!=place.getRank() || file!=place.getFile())
		{
			((FieldView)chessBoard.getComponent(8*(7-place.getRank())+place.getFile())).setHighlighted(false);
			setField(rank, file);
			return;
		}

		((FieldView)chessBoard.getComponent(8*(7-place.getRank())+place.getFile())).setHighlighted(false);
		String additionalInfo="";
		if (!board.automaticMove(place))
			additionalInfo=" last piece cannot move";
		place.setPos(-1,-1);
		ReDrawBoard(additionalInfo);
		if (board.getMatchResult()!=MatchResult.IN_PROCESS)
			JOptionPane.showMessageDialog(null, board.getMatchResultString(), "Game finished", JOptionPane.INFORMATION_MESSAGE);
	}


	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		setBoardSize(getContentPane().getWidth(), getContentPane().getHeight(), false);

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
