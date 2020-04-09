package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.model.Vehicle;


public class CO2Window extends JDialog{
	private JComboBox<Vehicle> vehicleBox;
	private JComboBox<Integer> contClassBox;
	private JSpinner ticks;
	public CO2Window(Frame f) {
		super(f,true);
		buildWindow();
	}

	private void buildWindow() {
		JPanel mainPanel = new JPanel();
		setTitle("Change Road Weather");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JLabel helpMsg = new JLabel("Schedule an event to change the weather of a road after a given number of simulation ticks from now");
		helpMsg.setAlignmentX(LEFT_ALIGNMENT);

		mainPanel.add(helpMsg);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.X_AXIS));
		comboPanel.setAlignmentX(CENTER_ALIGNMENT);

		vehicleBox = new JComboBox<Vehicle>();
		contClassBox = new JComboBox<Integer>();
		ticks = new JSpinner();
		
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
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((actionEvent) -> {
			CO2Window.this.setVisible(false);
		});
		JButton okButton = new JButton("OK");
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(okButton);
		mainPanel.add(buttonPanel);
		
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(true);
		
	}
}
