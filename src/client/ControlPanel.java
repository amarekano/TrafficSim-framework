package client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 5379117713281763963L;
	
	private JPanel policy_panel;
	private JPanel demand_matrix_panel;
	private JPanel clock_panel;

	public ControlPanel() {
		super();
		setLayout(new BorderLayout());
		
		policy_panel = new PolicyPanel();
		Dimension dim=getSize();
		policy_panel.setPreferredSize(new Dimension((int)dim.getWidth()/2,(int)dim.getHeight()));
		
		add(policy_panel,BorderLayout.CENTER);
		
		demand_matrix_panel = new DemandMatrixPanel();
		add(demand_matrix_panel,BorderLayout.EAST);
		
		clock_panel= new ClockPanel();
		add(clock_panel,BorderLayout.SOUTH);
	}


}
