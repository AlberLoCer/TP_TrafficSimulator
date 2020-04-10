package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.model.Road;
import simulator.model.Weather;

public class WeatherWindow extends JDialog {
	
	private JComboBox<Road> roadsBox;
	private JComboBox<Weather> weatherBox;
	private JSpinner ticks;
	
	public WeatherWindow(Frame f) {
		super(f, true);
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

		roadsBox = new JComboBox<Road>();
		weatherBox = new JComboBox<Weather>();
		ticks = new JSpinner();
		
		comboPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel roadLabel = new JLabel("Roads:");
		comboPanel.add(roadLabel);
		comboPanel.add(roadsBox);
		comboPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		JLabel weatherLabel = new JLabel("Weather:");
		comboPanel.add(weatherLabel);
		comboPanel.add(weatherBox);
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
			WeatherWindow.this.setVisible(false);
		});
		JButton okButton = new JButton("OK");
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(okButton);
		mainPanel.add(buttonPanel);
		
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setLocation(getParent().getLocation().x + getParent().getWidth() / 4,
				getParent().getLocation().y  + getParent().getHeight() / 4);
		setVisible(true);		
	}
	

}
