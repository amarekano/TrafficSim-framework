package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import core.endpoints.Destination;
import service.DemandMatrix;
import service.DemandMatrixException;
import service.ReportGenerator;

public class DemandMatrixPanel extends JPanel{

	private static final long serialVersionUID = -7688408801570692394L;
	
	//private	JTable table;
	
	private DemandMatrix dm_cars;
	private DemandMatrix dm_buses;
	
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
	JTable table_cars;
	JTable table_buses;
	JList rowHeader;
	JList rowHeader2;
	
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
					//System.out.println("test");
				}
		    }
		});
		add(petList);		
		
		ListModel lm = new AbstractListModel() {
		      String headers[] = { "aaa", "b", "c", "d", "e", "f", "g", "h", "i" };

		      public int getSize() {
		        return headers.length;
		      }

		      public Object getElementAt(int index) {
		        return headers[index];
		      }
		    };

		    DefaultTableModel dm = new DefaultTableModel();//(lm.getSize(), 10);
		     table_cars = new JTable(dm);
		    
		    table_cars.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		    rowHeader = new JList(lm);
		    ListModel model = new AbstractListModel() {
			      String headers[] = { "hello", "b", "c", "d", "e", "f", "g", "h", "i" };

			      public int getSize() {
			        return headers.length;
			      }

			      public Object getElementAt(int index) {
			        return headers[index];
			      }
			    };
		    rowHeader.setModel(model);
		    rowHeader.setFixedCellWidth(50);

		    rowHeader.setFixedCellHeight(table_cars.getRowHeight()
		        /*+ table.getRowMargin()*/);
		    //                           + table.getIntercellSpacing().height);
		    rowHeader.setCellRenderer(new RowHeaderRenderer(table_cars));

		    JScrollPane scroll = new JScrollPane(table_cars);
		    scroll.setRowHeaderView(rowHeader);
		    //add(scroll, BorderLayout.CENTER);
		    
		    DefaultTableModel dm2 = new DefaultTableModel();//(lm.getSize(), 10);
		     table_buses = new JTable(dm2);
		    table_buses.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		     rowHeader2 = new JList(lm);
		    rowHeader2.setFixedCellWidth(50);

		    rowHeader2.setFixedCellHeight(table_buses.getRowHeight());
		    rowHeader2.setCellRenderer(new RowHeaderRenderer(table_buses));

		    JScrollPane scroll_buses = new JScrollPane(table_buses);
		    scroll_buses.setRowHeaderView(rowHeader2);
		    
		    demandPanel = new JPanel(new CardLayout());
			demandPanel.add(scroll, "Cars");
			demandPanel.add(scroll_buses, "Buses");
			add(demandPanel);
	}
	

	
	public void setDemandMatrixCars(DemandMatrix dm){
		this.dm_cars=dm;
		DefaultTableModel dtm = (DefaultTableModel) table_cars.getModel();		
		List <Destination>destinations=dm_cars.getDestinations();

		String[] test2=new String[destinations.size()];
		for(int i=0;i<destinations.size();i++){
			test2[i]=destinations.get(i).getLabel();
		}
		ListModel model = new AbstractListModel() {
			
		      String headers[] = test2;

		      public int getSize() {
		        return headers.length;
		      }

		      public Object getElementAt(int index) {
		        return headers[index];
		      }
		    };
	    rowHeader.setModel(model);
		
		for(int j=0;j<destinations.size();j++){
			dtm.addColumn(destinations.get(j).getLabel());
		}
		
		for(int j=0;j<destinations.size();j++)
	    {
			Object [] test_array=new Object[destinations.size()];
			for(int i=0;i<destinations.size();i++){
				try {
					double prob=dm_cars.getDemand(destinations.get(j), destinations.get(i));
					test_array[i]=""+prob;
				    
				} catch (DemandMatrixException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    } 
			dtm.addRow(test_array);
			}	
	}
	
	public void setDemandMatrixBuses(DemandMatrix dm){
		this.dm_buses=dm;
		DefaultTableModel dtm = (DefaultTableModel) table_buses.getModel();		
		List <Destination>destinations=dm_buses.getDestinations();

		String[] test2=new String[destinations.size()];
		for(int i=0;i<destinations.size();i++){
			test2[i]=destinations.get(i).getLabel();
		}
		ListModel model = new AbstractListModel() {
			
		      String headers[] = test2;

		      public int getSize() {
		        return headers.length;
		      }

		      public Object getElementAt(int index) {
		        return headers[index];
		      }
		    };
	    rowHeader2.setModel(model);
		
		for(int j=0;j<destinations.size();j++){
			dtm.addColumn(destinations.get(j).getLabel());
		}
		
		for(int j=0;j<destinations.size();j++)
	    {
			Object [] test_array=new Object[destinations.size()];
			for(int i=0;i<destinations.size();i++){
				try {
					double prob=dm_buses.getDemand(destinations.get(j), destinations.get(i));
					test_array[i]=""+prob;
				    
				} catch (DemandMatrixException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    } 
			dtm.addRow(test_array);
			}	
	}
	
	class RowHeaderRenderer extends JLabel implements ListCellRenderer {

		  RowHeaderRenderer(JTable table) {
		    JTableHeader header = table.getTableHeader();
		    setOpaque(true);
		    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		    setHorizontalAlignment(CENTER);
		    setForeground(header.getForeground());
		    setBackground(header.getBackground());
		    setFont(header.getFont());
		  }

		  public Component getListCellRendererComponent(JList list, Object value,
		      int index, boolean isSelected, boolean cellHasFocus) {
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
		}
	
	

}
