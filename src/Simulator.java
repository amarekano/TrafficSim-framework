import java.awt.EventQueue;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Simulator extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JComboBox comboBox;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simulator frame = new Simulator();
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
	
	public void comboselect(){
		int d =comboBox.getSelectedIndex();
		if (d==1){
			Network2 s = new Network2();
			s.setVisible(true);
		}
		
		if (d==2){
			Network3 l =new Network3();
			l.setVisible(true);
		}
	}
	
	
	public Simulator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Network 1", "Network 2", "Network 3"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(88, 0, 146, 27);
		contentPane.add(comboBox);
		
		JButton btnMaps = new JButton("Maps");
		btnMaps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnMaps.setBounds(227, -1, 117, 29);
		contentPane.add(btnMaps);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.background"));
		panel.setBorder(new LineBorder(new Color(128, 128, 128)));
		panel.setBounds(0, 444, 800, 134);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Traffic Light Interval");
		lblNewLabel.setBounds(45, 25, 144, 16);
		panel.add(lblNewLabel);
		
		JLabel lblTimeOfThe = new JLabel("Time of the Day");
		lblTimeOfThe.setBounds(45, 87, 100, 16);
		lblTimeOfThe.setVerticalAlignment(SwingConstants.BOTTOM);
		panel.add(lblTimeOfThe);
		
		JSlider slider = new JSlider();
		slider.setBounds(180, 19, 190, 29);
		panel.add(slider);
		
		textField = new JTextField();
		textField.setBounds(165, 81, 134, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(534, 15, 231, 109);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblCarh = new JLabel("");
		lblCarh.setBounds(33, 219, 51, 27);
		lblCarh.setIcon(new ImageIcon("images/car2H.png"));
		contentPane.add(lblCarh);
		
		JLabel lblLighth = new JLabel("");
		lblLighth.setBounds(233, 194, 20, 34);
		lblLighth.setIcon(new ImageIcon("images/lightg.png"));
		contentPane.add(lblLighth);
		
		JButton btnClock = new JButton("Clock");
		btnClock.setBounds(650, 6, 117, 29);
		contentPane.add(btnClock);
		
		ScrollPane scrollPane_1 = new ScrollPane();
		scrollPane_1.setBounds(660, 41, 100, 100);
		contentPane.add(scrollPane_1);
		
		JLabel lblA = new JLabel("A");
		lblA.setBounds(6, 219, 33, 16);
		contentPane.add(lblA);
		
		JLabel lblB = new JLabel("B");
		lblB.setBounds(464, 219, 33, 16);
		contentPane.add(lblB);
		
       
        setTitle("Traffic Simulator");
        setVisible(true);
	}
	public void paint(Graphics g) {
	    super.paint(g);
	    g.setColor(Color.lightGray);
	    g.drawLine(230, 60, 230, 450);
	    g.drawLine(30, 245, 450, 245);
	    g.drawLine(30, 280, 450, 280);
	    g.drawLine(260, 60, 260, 450);
	    
	}
}
