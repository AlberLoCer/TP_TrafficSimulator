package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JPanel implements TrafficSimObserver {
	
	/* STRATEGY TO FOLLOW:
	 * 
	 * All sizes will be relative to getWidth and getHeight (of the resizable parent)
	 * In particular, the elements of the sides will be placed at a small distance
	 * of the sides (again, using getWidth...) and the road line will spawn taking
	 * all the space between the elements of the left and of the right.
	 * 
	 * The panel is ensured to measure at least _MIN_WIDTH x _MIN_HEIGHT.
	 * If the screen is resized to a smaller dimension, a scrollbar will appear.
	 * 
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	// DIMENSIONS:
	private static final int _MIN_PANEL_WIDTH = 360;
	private static final int _MIN_PANEL_HEIGHT = 200;	
	
	private static final int _HOR_SIDE_MARGINS = 20;	
	private static final int _VERT_SIDE_MARGINS = 50;	
	private static final int _IN_BETWEEN_HOR_MARGINS = 15;	
	private static final int _IN_BETWEEN_VERT_MARGINS = 50;	
	
	private static final int _IMAGE_SIZE = 32;	
	private static final int _JUNC_RADIUS = 10;

	
	private RoadMap _map;

	private Image _car;
	
	private Image _cont0;
	private Image _cont1;
	private Image _cont2;
	private Image _cont3;
	private Image _cont4;
	private Image _cont5;
	
	private Image _rain;
	private Image _cloud;
	private Image _storm;
	private Image _sun;
	private Image _wind;

	MapByRoadComponent(Controller ctrl) {
		//setPreferredSize(new Dimension(300,200) );
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		_car = loadImage("car.png");
		
		_cont0 = loadImage("cont_0.png");
		_cont1 = loadImage("cont_1.png");
		_cont2 = loadImage("cont_2.png");
		_cont3 = loadImage("cont_3.png");
		_cont4 = loadImage("cont_4.png");
		_cont5 = loadImage("cont_5.png");
		
		_rain = loadImage("rain.png");
		_storm = loadImage("storm.png");
		_sun = loadImage("sun.png");
		_wind = loadImage("wind.png");
		_cloud = loadImage("cloud.png");
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {			
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		// Adjust the panel size
		if (getWidth() < _MIN_PANEL_WIDTH || getHeight() < _MIN_PANEL_HEIGHT) {
			setPreferredSize(new Dimension(_MIN_PANEL_WIDTH, _MIN_PANEL_HEIGHT));
			setSize(new Dimension(_MIN_PANEL_WIDTH, _MIN_PANEL_HEIGHT));
		}

		for (int i = 0; i < _map.getRoads().size(); i++) {
			drawRoad(g, _map.getRoads().get(i), i);
		}
		
//		drawRoads(g);
//		drawVehicles(g);
//		drawJunctions(g);
	}
	
	private void drawRoad(Graphics g, Road road, int row) {
		// Calculate specific positions
		int roadNameX = 0 + _HOR_SIDE_MARGINS;
		int roadNameY = 0 + _VERT_SIDE_MARGINS + row * _IN_BETWEEN_VERT_MARGINS;
		
		int leftJuncX = roadNameX + _IN_BETWEEN_HOR_MARGINS * 2; 
		int leftJuncY = 0 + _VERT_SIDE_MARGINS  + row * _IN_BETWEEN_VERT_MARGINS;
		
		int contImX = getWidth() - _IMAGE_SIZE - _HOR_SIDE_MARGINS;
		int contImY = 0 + _VERT_SIDE_MARGINS - _IMAGE_SIZE / 2  + row * _IN_BETWEEN_VERT_MARGINS;
		
		int weatherImX = contImX - _IMAGE_SIZE - _IN_BETWEEN_HOR_MARGINS;
		int weatherImY = 0 + _VERT_SIDE_MARGINS - _IMAGE_SIZE / 2  + row * _IN_BETWEEN_VERT_MARGINS;
		
		int rightJuncX = weatherImX - _IN_BETWEEN_HOR_MARGINS * 2;
		int rightJuncY = 0 + _VERT_SIDE_MARGINS  + row * _IN_BETWEEN_VERT_MARGINS;
		
		// draw the road's identifier
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(road.getId(), roadNameX, roadNameY);
		
		// draw the left junction's circle
		g.setColor(_JUNCTION_COLOR);
		g.fillOval(leftJuncX - _JUNC_RADIUS / 2, leftJuncY - _JUNC_RADIUS / 2,
				_JUNC_RADIUS, _JUNC_RADIUS);

		// draw the left junction's id
		g.setColor(_JUNCTION_LABEL_COLOR);
		String id = road.getSrcJunc().getId();
		g.drawString(id, leftJuncX - _JUNC_RADIUS / 2, leftJuncY - _JUNC_RADIUS);
		
		// draw line for road
		drawLineWithArrow(g, leftJuncX, leftJuncY, rightJuncX, rightJuncY, 
				15, 5, Color.BLUE, Color.ORANGE);	
		
		
		// choose a color for the right junction's circle depending on the traffic light of the road
		Color rightJuncColor = _RED_LIGHT_COLOR;
		int idx = road.getDestJunc().getGreenLightIndex();
		if (idx != -1 && road.equals(road.getDestJunc().getInRoads().get(idx))) {
			rightJuncColor = _GREEN_LIGHT_COLOR;
		}
		
		// draw the right junction's circle
		g.setColor(rightJuncColor);
		g.fillOval(rightJuncX - _JUNC_RADIUS / 2, rightJuncY - _JUNC_RADIUS / 2,
				_JUNC_RADIUS, _JUNC_RADIUS);

		// draw the right junction's id
		g.setColor(_JUNCTION_LABEL_COLOR);
		id = road.getDestJunc().getId();
		g.drawString( id, rightJuncX - _JUNC_RADIUS / 2, rightJuncY - _JUNC_RADIUS);
		
		// draw weather image
		g.drawImage(getWeatherImage(road), weatherImX, weatherImY, _IMAGE_SIZE, _IMAGE_SIZE, this);

		// draw contamination image
		g.drawImage(getContImage(road), contImX, contImY, _IMAGE_SIZE, _IMAGE_SIZE, this);
		
	}
	
	private Image getContImage(Road road) {
		Double aux = Math.min((double) road.getTotalCO2() / (1.0 + (double) road.getCO2Limit()), 1.0);
		int cont = (int) Math.floor( aux / 0.19);
		switch (cont) {
			case 0:
				return _cont0;
			case 1:
				return _cont1;
			case 2:
				return _cont2;
			case 3:
				return _cont3;
			case 4:
				return _cont4;
			case 5:
				return _cont5;
			default:
				return _cont0;
		}
	}
	
	private Image getWeatherImage(Road road) {
		switch (road.getWeather()) {
		case SUNNY:
			return _sun;
		case CLOUDY:
			return _cloud;
		case WINDY:
			return _wind;
		case RAINY:
			return _rain;
		case STORM:
			return _storm;
		default:
			return _sun;
		}
	}
	
	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(//
			Graphics g, //
			int x1, int y1, //
			int x2, int y2, //
			int w, int h, //
			Color lineColor, Color arrowColor) {

		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}

}
