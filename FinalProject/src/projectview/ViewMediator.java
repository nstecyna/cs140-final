package projectview;

import java.util.Observable;

import javax.swing.JFrame;

import project.MachineModel;

public class ViewMediator extends Observable {

	private MachineModel model;
	private JFrame frame;
	
	public void step() { };
	
	public MachineModel getModel() {
		return model;
	}
	
	public void setModel(MachineModel m) {
		model = m;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void clearJob() {
		model.clearJob();
	}
	
	void makeReady(String s) {
		//TODO
	}
	
}
