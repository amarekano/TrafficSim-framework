package client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.Timer;

import service.DemandMatrix;
import service.ReportGenerator;
import service.SimulationClock;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 5379117713281763963L;
	
	private JPanel policy_panel;
	private DemandMatrixPanel demand_matrix_panel;
	private ClockPanel clock_panel;

	public ControlPanel(Timer tm, SimulationClock simClock) {
		super();
		setLayout(new BorderLayout());
		
		policy_panel = new PolicyPanel();
		Dimension dim=getSize();
		policy_panel.setPreferredSize(new Dimension((int)dim.getWidth()/2,(int)dim.getHeight()));
		
		add(policy_panel,BorderLayout.CENTER);
		
		demand_matrix_panel = new DemandMatrixPanel();
		add(demand_matrix_panel,BorderLayout.EAST);
		
		clock_panel= new ClockPanel();
		clock_panel.setClock(tm, simClock);
		add(clock_panel,BorderLayout.SOUTH);
	}
	
	public void setDemandMatrixCars(DemandMatrix dm){
		demand_matrix_panel.setDemandMatrixCars(dm);
	}
	
	public void setDemandMatrixBuses(DemandMatrix dm){
		demand_matrix_panel.setDemandMatrixBuses(dm);
	}
	
	public void setReportGenerator(ReportGenerator generator)
	{
		clock_panel.setReportGenerator(generator);
	}


}
