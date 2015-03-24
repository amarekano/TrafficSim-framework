package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import service.ReportGenerator;

public class DemandMatrixPanel extends JPanel{

	private static final long serialVersionUID = -7688408801570692394L;
	
	private	JTable table;
	private	JTable table_buses;
	
	// Create columns names
	String columnNames[] = { "Column 1", "Column 2", "Column 3" ,"Column 1", "Column 2", "Column 3","Column 1", "Column 2", "Column 3" };

	// Create some data
	String dataValues[][] = {
						{ "12", "234", "67","12", "234", "67","12", "234", "67" },
						{ "-123", "43", "853","12", "234", "67","12", "234", "67" },
						{ "93", "89.2", "109","12", "234", "67","12", "234", "67" },
						{ "279", "9033", "3092","12", "234", "67","12", "234", "67" },
						
			};

	JPanel demandPanel;
	public DemandMatrixPanel() {
		super();
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		String[] petStrings = { "Demand Matrix for Cars", "Demand Matrix for Buses" };

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		JComboBox petList = new JComboBox(petStrings);
		petList.setSelectedIndex(0);
		petList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	JComboBox cb = (JComboBox)e.getSource();
		        String petName = (String)cb.getSelectedItem();
		        //updateLabel(petName);
		        //table.
		        
		        CardLayout cl = (CardLayout)(demandPanel.getLayout());
				
				if(petName=="Demand Matrix for Cars"){
					cl.show(demandPanel,"Cars");
				}
				else if(petName=="Demand Matrix for Buses"){
					cl.show(demandPanel, "Buses");
					System.out.println("test");
				}
		    }
		});
		add(petList);
		
		
		//JLabel title = new JLabel("Demand Matrix");
		//add(title);
		
		// Create columns names
		String columnNames[] = { "Column 1", "Column 2", "Column 3" ,"Column 1", "Column 2", "Column 3","Column 1", "Column 2", "Column 3" };

		// Create some data
		String carValus[][] = {
					{ "car 12", "234", "67","12", "234", "67","12", "234", "67" },
					{ "-123", "43", "853","12", "234", "67","12", "234", "67" },
					{ "93", "89.2", "109","12", "234", "67","12", "234", "67" },
					{ "279", "9033", "3092","12", "234", "67","12", "234", "67" },
					
		};
		
		String busValues[][] = {
				{ "bus 1", "234", "67","12", "234", "67","12", "234", "67" },
				{ "-bbbb", "43", "853","12", "234", "67","12", "234", "67" },
				{ "93", "89.2", "109","12", "234", "67","12", "234", "67" },
				{ "279", "9033", "3092","12", "234", "67","12", "234", "67" },
				
	};
		
		// Create a new table instance
		table = new JTable( carValus, columnNames );
		table.setFont(new Font("Serif", Font.BOLD, 20));
		table.setRowHeight(100);
		JScrollPane scrollPane_cars = new JScrollPane(table);
		
		
		table_buses=new JTable( busValues, columnNames );
		table_buses.setFont(new Font("Serif", Font.BOLD, 20));
		table_buses.setRowHeight(100);
		JScrollPane scrollPane_buses = new JScrollPane(table_buses);
		
		demandPanel = new JPanel(new CardLayout());
		demandPanel.add(scrollPane_cars, "Cars");
		demandPanel.add(scrollPane_buses, "Buses");
		
		
		//JScrollPane scrollPane = new JScrollPane(demandPanel);
		
		
		
		
		add(demandPanel);
	}
	
	public void setColumns(){
		
	}
	
	
	
	

}
