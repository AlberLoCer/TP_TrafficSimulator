package simulator.view;

import java.awt.*;

import javax.swing.*;

import extra.jtable.EventsTableModel;
import simulator.control.Controller;

public class MainWindow extends JFrame{
	
	private Controller ctrl ;
	
	public MainWindow(Controller ctrl ) {
		super ( "Traffic Simulator" );
		this.ctrl = ctrl ;
		initGUI();
	}
	
	private void initGUI() {
		
		JPanel mainPanel = new JPanel( new BorderLayout());
		this.setContentPane( mainPanel );
		mainPanel.add( new ControlPanel( ctrl ), BorderLayout.PAGE_START );
		mainPanel.add( new StatusBar( ctrl ), BorderLayout.PAGE_END );
		
		JPanel viewsPanel = new JPanel( new GridLayout(1, 2));
		mainPanel.add( viewsPanel , BorderLayout.CENTER );
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout( new BoxLayout( tablesPanel , BoxLayout.Y_AXIS ));
		viewsPanel.add( tablesPanel );
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout( new BoxLayout( mapsPanel , BoxLayout.Y_AXIS ));
		viewsPanel.add( mapsPanel );
		
		// tables
		// TODO: fix constructor of given EventsTableModel
		JPanel eventsView = createViewPanel( new JTable( new EventsTableModel( ctrl )), "Events" );
		eventsView.setPreferredSize( new Dimension(500, 200));
		tablesPanel.add( eventsView );
		
		// TODO add other tables
		// ...
		
		// maps
		JPanel mapView = createViewPanel( new MapComponent( ctrl ), "Map" );
		mapView .setPreferredSize( new Dimension(500, 400));
		mapsPanel.add( mapView );
		
		// TODO add a map for MapByRoadComponent
		// ...
		
		this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		this.pack();
		this.setVisible( true );
	}
	
	
	private JPanel createViewPanel(JComponent c , String title ) {
		JPanel p = new JPanel( new BorderLayout() );
		// TODO add a framed border to p with title
		p.add( new JScrollPane( c ));
		return p ;
	}
}
