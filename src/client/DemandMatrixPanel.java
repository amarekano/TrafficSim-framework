package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import core.endpoints.Destination;
import service.DemandMatrix;
import service.DemandMatrixException;

public class DemandMatrixPanel extends JPanel {

	private static final long serialVersionUID = -7688408801570692394L;
		
	private DemandMatrix dm_cars;
	private DemandMatrix dm_buses;

	JPanel demandPanel;
	JTable table_cars;
	JTable table_buses;
	JList rowHeader;
	JList rowHeader2;
	List <Destination>destinations_cars;
	List <Destination>destinations_buses;
	
	public DemandMatrixPanel() {
		super();
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		String[] demand_matrix_strings = { "Demand Matrix for Cars", "Demand Matrix for Buses" };

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		JComboBox demand_matrix_combo = new JComboBox(demand_matrix_strings);
		demand_matrix_combo.setSelectedIndex(0);
		demand_matrix_combo.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	JComboBox cb = (JComboBox)e.getSource();
		        String myselection = (String)cb.getSelectedItem();
		        //updateLabel(petName);
		        //table.
		        
		        CardLayout cl = (CardLayout)(demandPanel.getLayout());
				
				if(myselection=="Demand Matrix for Cars"){
					cl.show(demandPanel,"Cars");
				}
				else if(myselection=="Demand Matrix for Buses"){
					cl.show(demandPanel, "Buses");
				}
		    }
		});
		add(demand_matrix_combo);		
		
		ListModel lm = new AbstractListModel() {
		      String headers[] = { "aaa", "b", "c", "d", "e", "f", "g", "h", "i" };

		      public int getSize() {
		        return headers.length;
		      }

		      public Object getElementAt(int index) {
		        return headers[index];
		      }
		    };

		    DefaultTableModel dm = new DefaultTableModel(){
		    	public boolean isCellEditable(int row,int cols)
		        {
		    		if(cols==row ){return false;}
		    		return true;
		        }
		    };//(lm.getSize(), 10);
		     table_cars = new JTable(dm);
		     //table_cars.getModel().addTableModelListener(this);
		     table_cars.getModel().addTableModelListener(new TableModelListener(){

				@Override
				public void tableChanged(TableModelEvent e) {
					int row = e.getFirstRow();
			        int column = e.getColumn();
			        if(row==-1 || column==-1){
			        	return;
			        }
			        TableModel model = (TableModel)e.getSource();
			        String columnName = model.getColumnName(column);
			        
			        String data = model.getValueAt(row, column).toString();
			        
			        ListModel listmodel1=rowHeader.getModel();
			        String rowName=listmodel1.getElementAt(row).toString();
			        Destination from=new Destination();
			        Destination to=new Destination();
			        for(Destination des: destinations_cars){
			        	String label=des.getLabel();
			        	if(label.equals(columnName)){
			        		to=des;
			        	}
			        	if(label.equals(rowName)){
			        		from=des;
			        	}
			        }
			        try {
			        	try {
			        		double previous_value=dm_cars.getDemand(from, to);
			        		
			        		double data_double=Double.parseDouble(data);
			        		if(data_double>=0 && data_double<=1){
			        			dm_cars.setDemand(from, to, data_double);
			        		}
			        		else{
			        			dm_cars.setDemand(from, to, previous_value);
			        			model.setValueAt(previous_value, row, column);
			        		}
			        		
			        	} catch (NumberFormatException error) {
			        		model.setValueAt(dm_cars.getDemand(from, to), row, column);
			        	}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (DemandMatrixException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
		    	 
		     });
		    
		    table_cars.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    table_cars.setRowSelectionAllowed(false);

		    rowHeader = new JList(lm);//rowHeader.setBackground(Color.PINK);
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
		    
		    DefaultTableModel dm2 = new DefaultTableModel(){
		    	@Override
		    	public boolean isCellEditable(int row,int cols)
		        {
		    		if(cols==row ){return false;}
		    		
		            return true;                                                                                    
		                               }
		    };//(lm.getSize(), 10);
		     table_buses = new JTable(dm2);
		    //table_buses.getModel().addTableModelListener(this);
		    
		    table_buses.getModel().addTableModelListener(new TableModelListener(){

				@Override
				public void tableChanged(TableModelEvent e) {
					int row = e.getFirstRow();
			        int column = e.getColumn();
			        if(row==-1 || column==-1){
			        	return;
			        }
			        TableModel model = (TableModel)e.getSource();
			        String columnName = model.getColumnName(column);
			        
			        String data = model.getValueAt(row, column).toString();
			        
			        
			        ListModel listmodel1=rowHeader2.getModel();
			        String rowName=listmodel1.getElementAt(row).toString();
			        Destination from=new Destination();
			        Destination to=new Destination();
			        for(Destination des: destinations_buses){
			        	String label=des.getLabel();
			        	if(label.equals(columnName)){
			        		to=des;
			        	}
			        	if(label.equals(rowName)){
			        		from=des;
			        	}
			        }
			        try {
			        	try {
			        		double previous_value=dm_buses.getDemand(from, to);
			        		
			        		double data_double=Double.parseDouble(data);
			        		if(data_double>=0 && data_double<=1){
			        			dm_buses.setDemand(from, to, data_double);
			        		}
			        		else{
			        			dm_buses.setDemand(from, to, previous_value);
			        			model.setValueAt(previous_value, row, column);
			        		}
			        		
			        	} catch (NumberFormatException error) {
			        		model.setValueAt(dm_buses.getDemand(from, to), row, column);
			        	}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (DemandMatrixException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
		    	 
		     });
		     table_buses.setRowSelectionAllowed(false);
		    
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
		destinations_cars=dm_cars.getDestinations();

		final String[] test2=new String[destinations_cars.size()];
		for(int i=0;i<destinations_cars.size();i++){
			test2[i]=destinations_cars.get(i).getLabel();
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
		
		for(int j=0;j<destinations_cars.size();j++){
			dtm.addColumn(destinations_cars.get(j).getLabel());
		}
		
		for(int j=0;j<destinations_cars.size();j++)
	    {
			Object [] test_array=new Object[destinations_cars.size()];
			for(int i=0;i<destinations_cars.size();i++){
				try {
					double prob=dm_cars.getDemand(destinations_cars.get(j), destinations_cars.get(i));
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
		destinations_buses=dm_buses.getDestinations();

		final String[] test2=new String[destinations_buses.size()];
		for(int i=0;i<destinations_buses.size();i++){
			test2[i]=destinations_buses.get(i).getLabel();
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
		
		for(int j=0;j<destinations_buses.size();j++){
			dtm.addColumn(destinations_buses.get(j).getLabel());
		}
		
		for(int j=0;j<destinations_buses.size();j++)
	    {
			Object [] test_array=new Object[destinations_buses.size()];
			for(int i=0;i<destinations_buses.size();i++){
				try {
					double prob=dm_buses.getDemand(destinations_buses.get(j), destinations_buses.get(i));
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
