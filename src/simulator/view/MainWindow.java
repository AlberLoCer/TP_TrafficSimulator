package simulator.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	
	private Controller ctrl ;
	
	public MainWindow(Controller ctrl ) {
		super ( "Traffic Simulator" );
		this.ctrl = ctrl ;
		initGUI();
	}
	
	private void initGUI() {
		
		JPanel mainPanel = new JPanel( new BorderLayout() );
		this.setContentPane( mainPanel );
		mainPanel.add( new ControlPanel( ctrl ), BorderLayout.PAGE_START );	// Top panel (icons)
		mainPanel.add( new StatusBar( ctrl ), BorderLayout.PAGE_END ); //Bottom panel bar (text, time...)
		
		initPanels(mainPanel);		// Central panel (six tables)
		
		this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		this.pack();
		this.setVisible( true );
	}

    private void initPanels(JPanel mainPanel){
        JPanel viewsPanel = new JPanel( new GridLayout(1, 2)); //Center base panel (6 panels)
		mainPanel.add( viewsPanel , BorderLayout.CENTER );
		
		leftTables(viewsPanel);
		
		rightTables(viewsPanel);		
    }
    
    private void leftTables(JPanel viewsPanel) {
    	
    	JPanel tablesPanel = new JPanel(); //Left panel (Tables for events, vehicles...)
		tablesPanel.setLayout( new BoxLayout( tablesPanel , BoxLayout.Y_AXIS ));
		viewsPanel.add( tablesPanel );
    	
		JPanel eventsView = createViewPanel( new JTable( new EventsTableModel( ctrl )), "Events" );
		eventsView.setPreferredSize( new Dimension(500, 200));
		tablesPanel.add( eventsView );
		
		JPanel vehiclesView = createViewPanel( new JTable( new VehiclesTableModel( ctrl )), "Vehicles" );
		vehiclesView.setPreferredSize( new Dimension(500, 200));
		tablesPanel.add( vehiclesView );
		
		JPanel roadsView = createViewPanel( new JTable( new RoadsTableModel( ctrl )), "Roads" );
		roadsView.setPreferredSize( new Dimension(500, 200));
		tablesPanel.add( roadsView );
		
		JPanel junctionsView = createViewPanel( new JTable( new JunctionsTableModel( ctrl )), "Junctions" );
		junctionsView.setPreferredSize( new Dimension(500, 200));
		tablesPanel.add( junctionsView );
    }
    
    private void rightTables(JPanel viewsPanel) {
    	JPanel mapsPanel = new JPanel(); // Right panel (2 types of maps) 
		mapsPanel.setLayout( new BoxLayout( mapsPanel , BoxLayout.Y_AXIS ));
		viewsPanel.add( mapsPanel );
		
		JPanel mapView = createViewPanel( new MapComponent( ctrl ), "Map" );
		mapView.setPreferredSize( new Dimension(500, 400));			
		mapsPanel.add( mapView );

		JPanel mapByRoadView = createViewPanel( new MapByRoadComponent( ctrl ), "Map by Road" );
		mapByRoadView.setPreferredSize( new Dimension(500, 400));
		mapsPanel.add( mapByRoadView );
    }
	
	
	private JPanel createViewPanel(JComponent c , String title ) {
		JPanel p = new JPanel( new BorderLayout() );
		
		p.add( new JScrollPane( c ));
		
		Border border = BorderFactory.createLineBorder(Color.black, 2);
		p.setBorder(BorderFactory.createTitledBorder(border, title));
		
		return p ;
	}
}
