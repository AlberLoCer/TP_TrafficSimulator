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
	
	//TODO: Change directories for icons
	//TODO: Use glue for adapting the UI to what it's meant
	

		public ControlPanel(Controller controller) {
			initGUI();
		}
		
		public void initGUI() {
			
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			JButton openButton = addButton("");
			openButton.setIcon(new ImageIcon("resources/icons/open.png"));
			openButton.setSize(60, 60);
			openButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO: add correct action
					System.out.println("You exited");
					
				}
			});
			this.add(openButton);
			
			JSeparator sep1 = new JSeparator(SwingUtilities.VERTICAL);
			this.add(sep1);
			
			JButton co2Button = addButton("");
			co2Button.setIcon(new ImageIcon("resources/icons/co2class.png"));
			co2Button.setSize(60, 60);
			co2Button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO: add correct action
					System.out.println("You loaded");
					
				}
			});
			this.add(co2Button);
			
			JButton weatherButton = addButton("");
			weatherButton.setIcon(new ImageIcon("resources/icons/weather.png"));
			weatherButton.setSize(60, 60);
			weatherButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO: add correct action
					System.out.println("You changed weather");
					
				}
			});
			this.add(weatherButton);
			
			JSeparator sep2 = new JSeparator(SwingUtilities.VERTICAL);
			this.add(sep2);
			
			JButton runButton = addButton("");
			runButton.setIcon(new ImageIcon("resources/icons/run.png"));
			runButton.setSize(60, 60);
			runButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO: add correct action
					System.out.println("Run");
					
				}
			});
			this.add(runButton);
			
			JButton stopButton = addButton("");
			stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
			stopButton.setSize(60, 60);
			stopButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO: add correct action
					System.out.println("Stop");
					
				}
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
			exitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
			exitButton.setSize(60, 60);
			exitButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO: add correct action
					System.out.println("Stop");
					
				}
			});
			this.add(exitButton);
			
			
			
			
		}
		
		public JButton addButton(String text) {
			JButton button = new JButton(text);
			return button;
		}

	}

