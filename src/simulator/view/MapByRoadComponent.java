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
	private static final int _IN_BETWEEN_HOR_MARGINS = 20;	
	private static final int _IN_BETWEEN_VERT_MARGINS = 20;	
	
	private static final int _IMAGE_SIZE = 40;	
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
	private Image _storm;
	private Image _sun;
	private Image _wind;

	MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		_car = loadImage("car.png");
		
		_cont0 = loadImage("cont_0.png");
		_cont1 = loadImage("cont_1.png");
		_cont2 = loadImage("cont_2.png");
		_cont3 = loadImage("cont_3.png");
		_cont4 = loadImage("cont_4t.png");
		_cont5 = loadImage("cont_5.png");
		
		_rain = loadImage("rain.png");
		_storm = loadImage("storm.png");
		_sun = loadImage("sun.png");
		_wind = loadImage("wind.png");
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
		
		// Calculate horizontal positions
		int roadNameX = 0 + _HOR_SIDE_MARGINS;
		int roadNameY = 0 + _VERT_SIDE_MARGINS;
		int leftJuncX = roadNameX + _IN_BETWEEN_HOR_MARGINS * 2; 
		int leftJuncY = 0 + _VERT_SIDE_MARGINS;
		int contImX = getWidth() - _IMAGE_SIZE - _HOR_SIDE_MARGINS;
		int contImY = 0 + _VERT_SIDE_MARGINS - _IMAGE_SIZE / 2;
		int weatherImX = contImX - _IMAGE_SIZE - _IN_BETWEEN_HOR_MARGINS;
		int weatherImY = 0 + _VERT_SIDE_MARGINS - _IMAGE_SIZE / 2;
		int rightJuncX = weatherImX - _IN_BETWEEN_HOR_MARGINS * 2;
		int rightJuncY = 0 + _VERT_SIDE_MARGINS;
		
		g.drawImage(_cont0, contImX, contImY, _IMAGE_SIZE, _IMAGE_SIZE, this);
		g.drawImage(_rain, weatherImX, weatherImY, _IMAGE_SIZE, _IMAGE_SIZE, this);
		
		// draw the road's identifier
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString("r1", roadNameX, roadNameY);
		
		// draw the left junction's circle
		g.setColor(_JUNCTION_COLOR);
		g.fillOval(leftJuncX - _JUNC_RADIUS / 2, leftJuncY - _JUNC_RADIUS / 2,
				_JUNC_RADIUS, _JUNC_RADIUS);

		// draw the left junction's id
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString("temp", leftJuncX - _JUNC_RADIUS / 2, leftJuncY - _JUNC_RADIUS);
		
		// draw the right junction's circle
		g.setColor(_JUNCTION_COLOR);
		g.fillOval(rightJuncX - _JUNC_RADIUS / 2, rightJuncY - _JUNC_RADIUS / 2,
				_JUNC_RADIUS, _JUNC_RADIUS);

		// draw the left junction's id
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString("temp", rightJuncX - _JUNC_RADIUS / 2, rightJuncY - _JUNC_RADIUS);
		
		// draw line for road
		drawLineWithArrow(g, leftJuncX, leftJuncY, rightJuncX, rightJuncY, 
				15, 5, Color.BLUE, Color.ORANGE);
		
//		drawRoads(g);
//		drawVehicles(g);
//		drawJunctions(g);
	}
	
	// TODO: delete, just for testing 
//	private JPanel createPanel(Color color, int x, int y) {
//		JPanel panel = new JPanel();
//		panel.setBackground(color);
//		panel.setPreferredSize(new Dimension(x, y));
//		panel.setMaximumSize(new Dimension(x, y));
//		panel.setMinimumSize(new Dimension(x, y));
//		return panel;
//	}

	private void drawRoads(Graphics g) {
		for (Road r : _map.getRoads()) {

			// the road goes from (x1,y1) to (x2,y2)
			int x1 = r.getSrcJunc().getX();
			int y1 = r.getSrcJunc().getY();
			int x2 = r.getDestJunc().getX();
			int y2 = r.getDestJunc().getY();

			// choose a color for the arrow depending on the traffic light of the road
			Color arrowColor = _RED_LIGHT_COLOR;
			int idx = r.getDestJunc().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDestJunc().getInRoads().get(idx))) {
				arrowColor = _GREEN_LIGHT_COLOR;
			}

			// choose a color for the road depending on the total contamination, the darker
			// the
			// more contaminated (wrt its co2 limit)
			int roadColorValue = 200 - (int) (200.0 * Math.min(1.0, (double) r.getTotalCO2() / (1.0 + (double) r.getCO2Limit())));
			Color roadColor = new Color(roadColorValue, roadColorValue, roadColorValue);

			// draw line from (x1,y1) to (x2,y2) with arrow of color arrowColor and line of
			// color roadColor. The size of the arrow is 15px length and 5 px width
			drawLineWithArrow(g, x1, y1, x2, y2, 15, 5, roadColor, arrowColor);
		}

	}

	private void drawVehicles(Graphics g) {
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relativly to the length of the road, and
				// the location on the vehicle.
				Road r = v.getRoad();
				int x1 = r.getSrcJunc().getX();
				int y1 = r.getSrcJunc().getY();
				int x2 = r.getDestJunc().getX();
				int y2 = r.getDestJunc().getY();
				double roadLength = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
				double alpha = Math.atan(((double) Math.abs(x1 - x2)) / ((double) Math.abs(y1 - y2)));
				double relLoc = roadLength * ((double) v.getLocation()) / ((double) r.getLength());
				double x = Math.sin(alpha) * relLoc;
				double y = Math.cos(alpha) * relLoc;
				int xDir = x1 < x2 ? 1 : -1;
				int yDir = y1 < y2 ? 1 : -1;

				int vX = x1 + xDir * ((int) x);
				int vY = y1 + yDir * ((int) y);

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car (with circle as background) and it identifier
				g.fillOval(vX - 1, vY - 6, 14, 14);
				g.drawImage(_car, vX, vY - 6, 12, 12, this);
				g.drawString(v.getId(), vX, vY - 6);
			}
		}
	}

	private void drawJunctions(Graphics g) {
		for (Junction j : _map.getJunctions()) {

			// (x,y) are the coordinates of the junction
			int x = j.getX();
			int y = j.getY();

			// draw a circle with center at (x,y) with radius _JRADIUS
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x - _JUNC_RADIUS / 2, y - _JUNC_RADIUS / 2, _JUNC_RADIUS, _JUNC_RADIUS);

			// draw the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(j.getId(), x, y);
		}
	}

	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		setPreferredSize(new Dimension(maxW, maxH));
		setSize(new Dimension(maxW, maxH));
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
