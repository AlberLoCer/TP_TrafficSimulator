package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

import simulator.control.Controller;

public class ControlPanel extends JPanel{
	
	//TODO: Use glue for adapting the UI to what it's meant	

	public ControlPanel(Controller controller) {
		initGUI();
	}
	
	public void initGUI() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JButton openButton = addButton("");
		openButton.setIcon(getIcon("open.png"));
		openButton.setSize(60, 60);
		openButton.addActionListener( (actionEvent) -> {
			System.out.println("Open");
		});
		this.add(openButton);
		
		JSeparator sep1 = new JSeparator(SwingUtilities.VERTICAL);
		this.add(sep1);
		
		JButton co2Button = addButton("");
		co2Button.setIcon(getIcon("co2class.png"));
		co2Button.setSize(60, 60);		
		co2Button.addActionListener( (actionEvent) -> {
			System.out.println("CO2");
		});		
		this.add(co2Button);
		
		JButton weatherButton = addButton("");
		weatherButton.setIcon(getIcon("weather.png"));
		weatherButton.setSize(60, 60);
		weatherButton.addActionListener( (actionEvent) -> {
			System.out.println("You changed weather");
		});
		this.add(weatherButton);
		
		JSeparator sep2 = new JSeparator(SwingUtilities.VERTICAL);
		this.add(sep2);
		
		JButton runButton = addButton("");
		runButton.setIcon(getIcon("run.png"));
		runButton.setSize(60, 60);
		runButton.addActionListener( (actionEvent) -> {
			System.out.println("Run");
		});
		this.add(runButton);
		
		JButton stopButton = addButton("");
		stopButton.setIcon(getIcon("stop.png"));
		stopButton.setSize(60, 60);
		stopButton.addActionListener( (actionEvent) -> {
			System.out.println("Stop");
		});
		this.add(stopButton);
		
		JLabel ticks = new JLabel("Ticks:");
		this.add(ticks);
		JSpinner ticknum = new JSpinner();
		this.add(ticknum);
		
		JPanel space = new JPanel();
		space.setSize(300, 60);
		this.add(space);
		
		JSeparator sep3 = new JSeparator(SwingUtilities.VERTICAL);
		this.add(sep3);
		
		JButton exitButton = addButton("");
		exitButton.setIcon(getIcon("exit.png"));
		exitButton.setSize(60, 60);
		exitButton.addActionListener( (actionEvent) -> {
			System.out.println("Exit");
		});
		this.add(exitButton);
		
	}
	
	private JButton addButton(String text) {
		JButton button = new JButton(text);
		return button;
	}
	
	private ImageIcon getIcon(String filename) {
		return new ImageIcon("resources/icons/" + filename);
	}

}

