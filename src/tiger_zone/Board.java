import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Board extends MouseAdapter{
	public void mouseClicked(MouseEvent event){
		JPanel j = (JPanel)event.getSource();
		j.setBackground(Color.YELLOW);
		
	}
	
	public static void main(String[] args){
		BuildBoard b = new BuildBoard(); 
	}
}

class BuildBoard extends JPanel{
	public BuildBoard(){
		int rows = 5;
		int cols = 5;
		
		MouseAdapter ma = new Board(); 
				
		JPanel[][] panelBoard = new JPanel[rows][cols];
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(rows,cols));
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				panelBoard[i][j] = new JPanel();
				panelBoard[i][j].addMouseListener(ma);
				panelBoard[i][j].setBorder(new LineBorder(Color.BLACK));
				frame.add(panelBoard[i][j]);
			}
		}	

		ImageIcon img = new ImageIcon("C:/Users/Kyle/Desktop/Tiles/Tile1.png");
		JLabel label = new JLabel();
		label.setIcon(img);
		panelBoard[2][2].add(label);
	
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}


