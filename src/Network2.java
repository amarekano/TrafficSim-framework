
import java.awt.Color;
import java.awt.Graphics;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;



public class Network2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Network2 frame = new Network2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Network2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Network 2", "Network 3"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(88, 0, 146, 27);
		contentPane.add(comboBox);

		
       
        setTitle("Traffic Simulator");
        setVisible(true);
	}
	
	public void paint(Graphics g) {
	    super.paint(g);
	    g.setColor(Color.lightGray);
	    g.drawLine(230, 60, 230, 450);
	    g.drawLine(30, 245, 680, 245);
	    g.drawLine(30, 280, 680, 280);
	    g.drawLine(260, 60, 260, 450);
	    g.drawLine(460, 60, 460, 450);
	    g.drawLine(490, 60, 490, 450);
	    
	}
}
