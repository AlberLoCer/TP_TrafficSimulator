package simulator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

import simulator.control.Controller;

public class ControlPanel extends JPanel{
	
	//TODO: Use glue for adapting the UI to what it's meant	
	private Controller controller;

	public ControlPanel(Controller controller) {
		this.controller = controller; 
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
		
		addSeparation();
		
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
		
		addSeparation();
		
		JButton runButton = addButton("");
		runButton.setIcon(getIcon("run.png"));
		runButton.setSize(60, 60);
		runButton.addActionListener( (actionEvent) -> {
			// TODO: change, this is only to test
			controller.run(1);
		});
		this.add(runButton);
		
		JButton stopButton = addButton("");
		stopButton.setIcon(getIcon("stop.png"));
		stopButton.setSize(60, 60);
		stopButton.addActionListener( (actionEvent) -> {
			System.out.println("Stop");
		});
		this.add(stopButton);
		
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JLabel ticks = new JLabel("Ticks:");
		this.add(ticks);		
		
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JSpinner ticknum = new JSpinner();
		ticknum.setMinimumSize(new Dimension(100, 40));
		ticknum.setPreferredSize(new Dimension(100, 40));
		ticknum.setMaximumSize(new Dimension(100, 40));
		this.add(ticknum);		
		
		this.add(Box.createHorizontalGlue());
		
		addSeparation();
		
		JButton exitButton = addButton("");
		exitButton.setIcon(getIcon("exit.png"));
		exitButton.setSize(60, 60);
		exitButton.addActionListener( (actionEvent) -> {
			
			Object[] options = {"Yes", "No"};
			int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?"
					, "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if(choice == 0) {
				System.exit(0);
			}		
			
		});
		this.add(exitButton);
	}
	
	private JButton addButton(String text) {
		JButton button = new JButton(text);
		return button;
	}
	
	private void addSeparation() {
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JSeparator sep = new JSeparator(SwingUtilities.VERTICAL);
		sep.setMaximumSize(new Dimension(10, 60));
		this.add(sep);
		
		this.add(Box.createRigidArea(new Dimension(10, 0)));
	}
	
	private ImageIcon getIcon(String filename) {
		return new ImageIcon("resources/icons/" + filename);
	}

}

