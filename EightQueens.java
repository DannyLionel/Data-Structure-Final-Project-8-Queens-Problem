import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EightQueens implements ListSelectionListener {
	private JLabel picture;
    @SuppressWarnings("rawtypes")
	private JList list;
    private JSplitPane splitPane;
    private String[] solutionNames = new String[92];
    private int[][][] solutions = new int[92][8][8];
    private int x[]=new int[20];
	private int count=0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new EightQueens();         
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EightQueens(){
		JFrame frame = new JFrame("Eight Queens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        list = new JList(solutionNames);
        list.setFixedCellHeight(15);
        list.setFixedCellWidth(65);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        
        JScrollPane listScrollPane = new JScrollPane(list);
        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.CENTER);
         
        JScrollPane pictureScrollPane = new JScrollPane(picture);
 
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   listScrollPane, pictureScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(100);
        splitPane.setPreferredSize(new Dimension(750, 625));
        
        for (int i = 1; i <= 92; i++){
        	solutionNames[i-1] = "Solution " + String.valueOf(i);
        }
        
        Queen(1,8);
        
        updateSolution(list.getSelectedIndex());
        
        frame.getContentPane().add(splitPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}

	public boolean place(int row,int column)
	{
		int i;
		for(i=1;i<=row-1;i++)
		{
			if(x[i] == column)
				return false;
			else
				if(Math.abs(x[i] -  column) == Math.abs(i - row))
					return false;
		}
		return true;
	}
	
	public void Queen(int row,int n)
	{
		int column;
		for(column=1;column<=n;column++)
		{
			if(place(row,column))
			{
				x[row] = column;
				if(row==n){
					create_array(n);}
				else
					Queen(row+1,n);
			}
		}
	}
	
	public void create_array(int n)
	{
		count++;
		for(int i=1;i<=n;i++)
		{
			for(int j=1;j<=n;j++)// for nXn board
			{
				if(x[i]==j)
					solutions[count-1][i-1][j-1] = 1;
				else
					solutions[count-1][i-1][j-1] = 0;
			}
		}
	}
	
	@Override
    public void valueChanged(ListSelectionEvent e) {
        @SuppressWarnings("rawtypes")
		JList list = (JList)e.getSource();
        updateSolution(list.getSelectedIndex());
    }
     
    protected void updateSolution (int index) {
    	BufferedImage board = null;
		try {
			board = ImageIO.read(new File("bin/Chess_Board.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	BufferedImage queen = null;
		try {
			queen = ImageIO.read(new File("bin/Chess_Queen.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	BufferedImage combined = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
    	Graphics g = combined.getGraphics();
    	g.drawImage(board, 0, 0, null);
    	
    	for (int x = 0; x < 8; x++){
        	for (int y = 0; y < 8; y++){
        		if (solutions[index][x][y] == 1){
        			g.drawImage(queen, x*75+10, y*75+15, null);
        		}
        	}
        }
    	
    	ImageIcon icon = new ImageIcon(combined);
        picture.setIcon(icon);
        picture.setText(null);
    }
}
