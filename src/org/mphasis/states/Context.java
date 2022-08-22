package org.mphasis.states;

// Holds the current state of the program
public class Context {
	private ProgramState state;
	
	public Context() {
		
	}
	
	public void setState(ProgramState state) {
		this.state = state;
		this.state.start();
	}
}
