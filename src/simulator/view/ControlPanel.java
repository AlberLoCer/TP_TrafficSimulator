package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;


import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver{
	private static final long serialVersionUID = -9118944526329752975L;
	private Controller controller;
	private RoadMap roadMap;
	private int simTime;
	private boolean stopped;
	private JFileChooser fileChooser;
	private WeatherWindow weatherWindow;
	private CO2Window co2Window;
	private JSpinner ticknum;
	private JButton openButton;
	private JButton co2Button;
	private JButton weatherButton;
	private JButton runButton;
	private JButton stopButton;
	private JButton exitButton;
	
	public ControlPanel(Controller controller) {
		this.controller = controller; 
		controller.addObserver(this);
		initGUI();		
	}
	
	public void initGUI() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		initLoadButton();		
		addSeparation();		
		initCO2Button();		
		initWeatherButton();		
		addSeparation();		
		initRunButton();		
		initStopButton();		
		initTicksSpinner();		
		this.add(Box.createHorizontalGlue());		
		addSeparation();		
		initExitButton();
	}
	
	private void initLoadButton() {
		openButton = addButton("");
		openButton.setIcon(getIcon("open.png"));
		openButton.setToolTipText("Load events from file system");
		openButton.setSize(60, 60);
		openButton.addActionListener( (actionEvent) -> {
			loadButtonClicked();
		});
		this.add(openButton);
	}
	
	private void loadButtonClicked() {

		if(fileChooser == null) {
			// First use of it -> create instance
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("resources/examples"));
			fileChooser.setAcceptAllFileFilterUsed(false);
		}		
		
		fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(ControlPanel.this));
		InputStream fs = null;
		try {
			fs = new FileInputStream(fileChooser.getSelectedFile());
			controller.reset();
			controller.loadEvents(fs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this),
					"Something went wrong while loading the file: " + e.getMessage(), "ERROR",
                    JOptionPane.ERROR_MESSAGE);			
		}
				
	}
	
	private void initCO2Button() {
		co2Window = new CO2Window((Frame) SwingUtilities.getWindowAncestor(ControlPanel.this), controller);
		co2Button = addButton("");
		co2Button.setIcon(getIcon("co2class.png"));
		co2Button.setToolTipText("Change the CO2 class of a vehicle");
		co2Button.setSize(60, 60);		
		co2Button.addActionListener( (actionEvent) -> {
			co2Window.display(roadMap, simTime);		
		});		
		this.add(co2Button);
	}
	
	private void initWeatherButton() {
		weatherWindow = new WeatherWindow((Frame) SwingUtilities.getWindowAncestor(ControlPanel.this), controller);
		weatherButton = addButton("");
		weatherButton.setIcon(getIcon("weather.png"));
		weatherButton.setToolTipText("Change the weather of a road");
		weatherButton.setSize(60, 60);
		weatherButton.addActionListener( (actionEvent) -> {
			weatherWindow.display(roadMap, simTime);		
		});
		this.add(weatherButton);
	}
	
	private void initRunButton() {
		runButton = addButton("");
		runButton.setIcon(getIcon("run.png"));
		runButton.setToolTipText("Run the simulation");
		runButton.setSize(60, 60);
		runButton.addActionListener( (actionEvent) -> {
			stopped = false;
			enableToolBar(false);
			run_sim((Integer)ticknum.getValue());
		});
		this.add(runButton);
	}
	
	private void initStopButton() {
		stopButton = addButton("");
		stopButton.setIcon(getIcon("stop.png"));
		stopButton.setToolTipText("Stop the simulation");
		stopButton.setSize(60, 60);
		stopButton.addActionListener( (actionEvent) -> {
			stop();
		});
		this.add(stopButton);
        stopped = false;
	}
	
	private void initTicksSpinner() {
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JLabel ticks = new JLabel("Ticks:");
		this.add(ticks);		
		
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		int min = 1;
	    int max = 1000;
	    int step = 1;
	    int initValue = 10;
	    SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
		ticknum = new JSpinner(model);
		ticknum.setToolTipText("Simulation ticks to run: 1 - 100000");
		ticknum.setMinimumSize(new Dimension(100, 40));
		ticknum.setPreferredSize(new Dimension(100, 40));
		ticknum.setMaximumSize(new Dimension(100, 40));
		this.add(ticknum);
	}
	
	private void initExitButton() {
		exitButton = addButton("");
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
	
	private void run_sim(int n) {
		if (n > 0 && !stopped) {
			try {
				controller.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this),
						"An exception ocurred: " + e.getMessage(), "ERROR",
	                    JOptionPane.ERROR_MESSAGE);	
				enableToolBar(true);
				stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		}
		
		else {
			enableToolBar(true);
			stopped = true;
		}
	}
	
	private void stop() {
		stopped = true;
	}	

	private void enableToolBar(boolean b) {		
		openButton.setEnabled(b);
		co2Button.setEnabled(b);
		weatherButton.setEnabled(b);
		runButton.setEnabled(b);
		stopButton.setEnabled(!b);
		exitButton.setEnabled(b);		
	}
	
	private void assignObserverData(RoadMap map, int time) {
		this.roadMap = map;		
		this.simTime = time;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		assignObserverData(map, time);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		assignObserverData(map, time);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		assignObserverData(map, time);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		assignObserverData(map, time);
	}

	@Override
	public void onError(String err) {
		
	}

}

