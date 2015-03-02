import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;


public class Network3 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Network3 frame = new Network3();
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
	public Network3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Network 3"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(88, 0, 146, 27);
		contentPane.add(comboBox);
       
        setTitle("Traffic Simulator");
        setVisible(true);
	}
	
	public void paint(Graphics g) {
	    super.paint(g);
	    g.setColor(Color.lightGray);
	    g.drawLine(230, 80, 230, 550);
	    g.drawLine(30, 245, 680, 245);
	    g.drawLine(30, 280, 680, 280);
	    g.drawLine(260, 80, 260, 550);
	    g.drawLine(460, 80, 460, 550);
	    g.drawLine(490, 80, 490, 550);
	    g.drawLine(30, 480, 680, 480);
	    g.drawLine(30, 510, 680, 510);
	    
	}
	

}
