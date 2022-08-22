package org.mphasis.states;

import org.mphasis.projectUtils.Reference;

public abstract class ProgramState {
	protected Context context;
	
	protected String errorText = ""; // Used for displaying errors for the user
	protected boolean refresh = true; // Used for continuing the input loop, and broken upon the change state
	
	public ProgramState(Context context) {
		this.context = context;
	}
	
	// Change the context state to the desired state
	protected void changeState(ProgramState state) {
		context.setState(state);
		refresh = false;
	}
	
	// Nabbed from online, used to clear the console (if not on eclipse).
	protected void clearConsole() {
		try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system
              
            if(operatingSystem.contains("Windows")){        
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            } 
        }catch(Exception e){
            System.out.println(e);
        }
	}

	// A set of number parsing methods that return true upon successful parsing, and the data reference is used to return data in the form of a variable for later use
	// These methods use C# equivalent of out variables
	protected boolean parseInt(String error, String input, Reference<Integer> data) {
		try {
			data.set(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			errorText = "This is not a valid pin number!";
			return false;
		}

		return true;
	}
	
	protected boolean parseLong(String error, String input, Reference<Long> data) {
		try {
			data.set(Long.parseLong(input));
		} catch (NumberFormatException e) {
			errorText = "This is not a valid pin number!";
			return false;
		}

		return true;
	}
	
	protected boolean parseDouble(String error, String input, Reference<Double> data) {
		try {
			data.set(Double.parseDouble(input));
		} catch (NumberFormatException e) {
			errorText = "This is not a valid pin number!";
			return false;
		}

		return true;
	}
	
	public void start() {
		while (refresh) {
			refresh();
			updateLoop();
		}
	}

	// Clear the console and display intial GUI
	protected void refresh() {
		clearConsole();
		printGUI();
	}
	
	// Used for the update of indivudal states
	protected abstract void updateLoop();
	// Used to display the intial user GUI
	protected abstract void printGUI();
}
