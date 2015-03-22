package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

public class DemandMatrixPanel extends JPanel {

	private static final long serialVersionUID = -7688408801570692394L;
	
	private	JTable table;

	public DemandMatrixPanel() {
		super();
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel title = new JLabel("Demand Matrix");
		add(title);
		
		// Create columns names
		String columnNames[] = { "Column 1", "Column 2", "Column 3" ,"Column 1", "Column 2", "Column 3","Column 1", "Column 2", "Column 3" };

		// Create some data
		String dataValues[][] = {
					{ "12", "234", "67","12", "234", "67","12", "234", "67" },
					{ "-123", "43", "853","12", "234", "67","12", "234", "67" },
					{ "93", "89.2", "109","12", "234", "67","12", "234", "67" },
					{ "279", "9033", "3092","12", "234", "67","12", "234", "67" },
					
		};
		
		// Create a new table instance
		table = new JTable( dataValues, columnNames );
		table.setFont(new Font("Serif", Font.BOLD, 20));
		table.setRowHeight(100);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}
	
	public void setColumns(){
		
	}
	
	

}
