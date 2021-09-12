package com.withsw.common;

public class CommonWorker extends Thread {
	protected boolean work;
	protected boolean stop;
	
	public CommonWorker() {
		init();
	}
	
	public CommonWorker(ThreadGroup threadGroup, String name) {
		super(threadGroup, name);
		init();
	}
	
	private void init() {
		work = true;
		stop = false;		
	}
	
	public boolean isWork() {
		return work;
	}
	
	public void setWork(boolean work) {
		this.work = work;
	}
	
	public boolean isStop() {
		return stop;
	}
	
	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
