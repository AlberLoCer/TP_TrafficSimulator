package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.Vehicle;


public class InfoWindow extends JDialog{
	private static final long serialVersionUID = 7696803416883376018L;
	private Controller controller;			
	private JSpinner C02LimitSpinner;
	private InfoTableModel tableModel;
	
	public InfoWindow(Frame f, Controller controller) {
		super(f,true);
		this.controller = controller;
		buildWindow();
	}

	private void buildWindow() {
		JPanel mainPanel = new JPanel();
		setTitle("Roads Contamination History");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JLabel helpMsg = new JLabel("Select a contamination limit and press UPDATE to show the roads that exceeded this total CO2 at each tick.");
		helpMsg.setHorizontalAlignment(JLabel.LEFT);

		mainPanel.add(helpMsg);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		

		C02LimitSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.X_AXIS));
		comboPanel.setAlignmentX(CENTER_ALIGNMENT);		

		JLabel C02Label = new JLabel("Contamination Limit: ");
		comboPanel.add(C02Label);
		comboPanel.add(C02LimitSpinner);
		comboPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		mainPanel.add(comboPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener((actionEvent) -> {
			InfoWindow.this.setVisible(false);
		});
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener((actionEvent) -> { 
			tableModel.updateC02Limit( (Integer) C02LimitSpinner.getValue());
		}); 
		
		JPanel buttonPanel = new JPanel(); 
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT); 
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));				 
		buttonPanel.add(closeButton); 
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); 
		buttonPanel.add(updateButton); 
		mainPanel.add(buttonPanel); 
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		tableModel = new InfoTableModel( controller );
		JPanel table = createViewPanel( new JTable( tableModel ));
		table.setPreferredSize( new Dimension(500, 200));
		mainPanel.add( table );
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		setPreferredSize(new Dimension(600, 500));
		setResizable(false);
		setVisible(false);
		
	}
	
	private JPanel createViewPanel(JComponent c) {
		JPanel p = new JPanel( new BorderLayout() );
		
		p.add( new JScrollPane( c ));
		
		return p ;
	}

	
	public void display(RoadMap roadMap, int simTime) { 
		 
		setLocation(getParent().getLocation().x + getParent().getWidth() / 4, 
				getParent().getLocation().y  + getParent().getHeight() / 4); 
		pack(); 
		setVisible(true);		 
	}

}
