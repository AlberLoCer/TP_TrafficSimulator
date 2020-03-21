package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.*;
import simulator.view.MainWindow;

public class Main {
	
	private enum Mode{
		Console("console"), GUI("gui");
		String name;
		private Mode(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}

	private static Mode _mode = null;					// Default mode
	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static Integer _ticks;
	
	

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			
			parseModeOption(line);
			if(_mode == Mode.GUI) {
				parseInFileOption(line, true);
				parseTicksOption(line);
			}
			else {
				parseInFileOption(line, false);
				parseOutFileOption(line);
				parseTicksOption(line);
			}
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new UnknownError(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		
		cmdLineOptions.addOption(Option.builder("t").hasArg().longOpt("ticks")
				.desc("Ticks to the simulator’s main loop (defaultvalue is 10).").build());
		cmdLineOptions.addOption(Option.builder("m").hasArg().longOpt("mode")
				.desc("Output mode: <console> or <gui> (default)").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line, boolean isOptional) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (!isOptional && _inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseTicksOption(CommandLine line) throws ParseException {
		String aux = line.getOptionValue("t");
		if (aux != null) {
			try {
				_ticks = Integer.parseInt(aux);
			}
			catch (NumberFormatException e) {
				throw new ParseException("Ticks must be a number");
			}
		}
		else {
			_ticks = _timeLimitDefaultValue;
		}
	} 
	
	private static void parseModeOption(CommandLine line) throws ParseException {
		String aux = line.getOptionValue("m");
		if (aux != null) {
			if(aux.toLowerCase().equals(Mode.Console.getName())) {
				_mode = Mode.Console;
			}
			else if(aux.toLowerCase().equals(Mode.GUI.getName())) {
				_mode = Mode.GUI;
			}
			else {
				throw new ParseException("Mode should be either <console> or <gui>");
			}
		}
		else {
			_mode = Mode.GUI;		// Default mode
		}
	} 
	
	private static void initFactories() {

		List<Builder<LightSwitchingStrategy>> lssBuilders = new ArrayList<>();
		lssBuilders.add(new RoundRobinStrategyBuilder());
		lssBuilders.add( new MostCrowdedStrategyBuilder());		
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lssBuilders);
		
		List<Builder<DequeingStrategy>> dqBuilders = new ArrayList<>();
		dqBuilders.add( new MoveFirstStrategyBuilder());
		dqBuilders.add( new MoveAllStrategyBuilder());
		Factory<DequeingStrategy> dqsFactory = new BuilderBasedFactory<>(dqBuilders);
		
		List<Builder<Event>> builders  = new ArrayList<Builder<Event>>();
		builders.add( new NewJunctionEventBuilder(lssFactory, dqsFactory));
		builders.add(new SetContClassEventBuilder());
		builders.add(new SetWeatherEventBuilder());
		builders.add(new NewVehicleEventBuilder());
		builders.add(new NewCityRoadEventBuilder());
		builders.add(new NewInterCityRoadEventBuilder());
		
		_eventsFactory = new BuilderBasedFactory<Event>(builders);

	}

	private static void startBatchMode() throws IOException {
		Controller controller = new Controller(new TrafficSimulator(), _eventsFactory);
		InputStream is = new FileInputStream(new File(_inFile));
		controller.loadEvents(is);
		if(_outFile != null) {			
			controller.run(_ticks, new FileOutputStream(_outFile));		
		}
		else {
			controller.run(_ticks, System.out);
		}
		
	}
	
	private static void startGUIMode() throws IOException {
		Controller controller = new Controller(new TrafficSimulator(), _eventsFactory);
		if (_inFile != null) {		//In GUI mode it is optional
			InputStream is = new FileInputStream(new File(_inFile));
			controller.loadEvents(is);
		}
		
		SwingUtilities.invokeLater( () -> new MainWindow(controller));
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		if(_mode == Mode.Console) {
			startBatchMode();
		}
		else {
			startGUIMode();
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
