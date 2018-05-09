package projectview;

public enum States {

	AUTO_STEPPING {
		@Override
		public void enter() {
			// TODO Auto-generated method stub
			
		}
	}, NOTHING_LOADED {
		@Override
		public void enter() {
			// TODO Auto-generated method stub
			
		}
	}, PROGRAM_HALTED {
		@Override
		public void enter() {
			// TODO Auto-generated method stub
			
		}
	}, PROGRAM_LOADED_NOT_AUTOSTEPPING {
		@Override
		public void enter() {
			// TODO Auto-generated method stub
			
		}
	};
	
	private static final int ASSEMBLE = 0;
	private static final int CLEAR = 1;
	private static final int LOAD = 2; 
	private static final int RELOAD = 3;
	private static final int RUN = 4;
	private static final int RUNNING = 5;
	private static final int STEP = 6;
	private static final int CHANGE_JOB = 7;
	
	boolean[] states = new boolean[8];
	
	public abstract void enter();
	
	public boolean getAssembleFileActive() {
	    return states[ASSEMBLE];
	}
	public boolean getClearActive() {
	    return states[CLEAR];
	}
	public boolean getLoadFileActive() {
	    return states[LOAD];
	}
	public boolean getReloadActive() {
	    return states[RELOAD];
	}
	public boolean getRunningActive() {
	    return states[RUNNING];
	}
	public boolean getRunPauseActive() {
	    return states[RUN];
	}
	public boolean getStepActive() {
	    return states[STEP];
	}
	public boolean getChangeJobActive() {
		return states[CHANGE_JOB];
	}
}
