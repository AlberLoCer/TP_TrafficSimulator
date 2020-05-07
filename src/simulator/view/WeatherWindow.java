package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
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
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class WeatherWindow extends JDialog {
	private static final long serialVersionUID = -2236894090169871300L;
	private Controller controller;			// To add new events 
	private int simTime;
	
	private JComboBox<Road> roadsBox;
	private JComboBox<Weather> weatherBox;
	private JSpinner ticks;
	
	public WeatherWindow(Frame f, Controller controller) {
		super(f, true);
		this.controller = controller;
		buildWindow();
	}

	private void buildWindow() {
		JPanel mainPanel = new JPanel();
		setTitle("Change Road Weather");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		// Description
		JLabel helpMsg = new JLabel("Schedule an event to change the weather of a road after a given number of simulation ticks from now");
		helpMsg.setAlignmentX(LEFT_ALIGNMENT);
		mainPanel.add(helpMsg);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		// ComboBoxes and spinner		
		roadsBox = new JComboBox<Road>();		// Filled with data on display()
		weatherBox = new JComboBox<Weather>(Weather.values());
		ticks = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));		// Min: 1 Max:999
		
		// Panel to hold the comboBoxes and spinner
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.X_AXIS));
		comboPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		// Add roads comboBox
		comboPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel roadLabel = new JLabel("Roads:");
		comboPanel.add(roadLabel);
		comboPanel.add(roadsBox);
		
		// Add weather comboBox
		comboPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		JLabel weatherLabel = new JLabel("Weather:");
		comboPanel.add(weatherLabel);
		comboPanel.add(weatherBox);
		
		// Add ticks spinner
		comboPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		JLabel ticksLabel = new JLabel("Ticks: ");
		comboPanel.add(ticksLabel);
		comboPanel.add(ticks);
		comboPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		
		// Add whole comboPanel
		mainPanel.add(comboPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		
		// Cancel button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((actionEvent) -> {
			WeatherWindow.this.setVisible(false);
		});
		
		// Accept button
		JButton okButton = new JButton("OK");
		okButton.addActionListener((actionEvent) -> {
			buildEvent();
			WeatherWindow.this.setVisible(false);
		});
		
		// Panel to hold buttons
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
	}
	
	private void buildEvent() {
		Weather selWeather = (Weather) weatherBox.getSelectedItem();
		Road selRoad = (Road) roadsBox.getSelectedItem();
		int selTicks = simTime + (Integer) ticks.getValue();
		
		List<Pair<String,Weather>> list = new ArrayList<>();
		list.add(new Pair<String, Weather>(selRoad.getId(), selWeather));
		
		Event event = new SetWeatherEvent(selTicks, list);
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
		//Refresh comboBoxes options based on current roadMap	
		roadsBox.removeAllItems();
		for(Road road : roadMap.getRoads()) {
			roadsBox.addItem(road);
		}
		
		this.simTime = simTime;
		
		// WeatherBox always has the same values
	}

}
