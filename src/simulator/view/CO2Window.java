package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.Vehicle;


public class CO2Window extends JDialog{
	
	private Controller controller;			
	private int simTime;
	private Integer contVals[] = {0,1,2,3,4,5,6,7,8,9,10};
	private JComboBox<Vehicle> vehicleBox;
	private JComboBox<Integer> contClassBox;
	private JSpinner ticks;
	
	public CO2Window(Frame f, Controller controller) {
		super(f,true);
		this.controller = controller;
		buildWindow();
	}

	private void buildWindow() {
		JPanel mainPanel = new JPanel();
		setTitle("Change Contamination Class");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JLabel helpMsg = new JLabel("Schedule an event to change the contamination class of a vehicle after a given number of simulation ticks from now");
		helpMsg.setAlignmentX(LEFT_ALIGNMENT);

		mainPanel.add(helpMsg);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		vehicleBox = new JComboBox<Vehicle>();
		contClassBox = new JComboBox<Integer>(contVals);
		ticks = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
		
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.X_AXIS));
		comboPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		comboPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel roadLabel = new JLabel("Vehicles:");
		comboPanel.add(roadLabel);
		comboPanel.add(vehicleBox);
		comboPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		JLabel weatherLabel = new JLabel("Cont Class:");
		comboPanel.add(weatherLabel);
		comboPanel.add(contClassBox);
		comboPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		JLabel ticksLabel = new JLabel("Ticks: ");
		comboPanel.add(ticksLabel);
		comboPanel.add(ticks);
		comboPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		mainPanel.add(comboPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((actionEvent) -> {
			CO2Window.this.setVisible(false);
		});
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener((actionEvent) -> { 
			buildEvent(); 
			CO2Window.this.setVisible(false); 
		}); 
		
		JPanel buttonPanel = new JPanel(); 
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT); 
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));				 
		buttonPanel.add(cancelButton); 
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); 
		buttonPanel.add(okButton); 
		mainPanel.add(buttonPanel); 
		
		setPreferredSize(new Dimension(500, 200));
		setResizable(false);
		setVisible(false);
		//pack(); 
		
	}

	private void buildEvent() {
		Integer CO2 = (Integer) contClassBox.getSelectedItem(); 
		Vehicle v = (Vehicle) vehicleBox.getSelectedItem(); 
		int selTicks = simTime + (Integer) ticks.getValue(); 
		 
		List<Pair<String,Integer>> list = new ArrayList<>(); 
		list.add(new Pair<String, Integer>(v.getId(), CO2)); 
		 
		Event event = new NewSetContClassEvent(selTicks, list); 
		controller.addEvent(event); 
	} 
	
	public void display(RoadMap roadMap, int simTime) { 
		updateData(roadMap, simTime);		 	 
		 
		setLocation(getParent().getLocation().x + getParent().getWidth() / 4, 
				getParent().getLocation().y  + getParent().getHeight() / 4); 
		pack(); 
		setVisible(true);		 
	}

	private void updateData(RoadMap roadMap, int simTime) {
		vehicleBox.removeAllItems(); 
		for(Vehicle v : roadMap.getVehicles()) { 
			vehicleBox.addItem(v); 
		} 
		 
		this.simTime = simTime; 
		 
	} 
}
