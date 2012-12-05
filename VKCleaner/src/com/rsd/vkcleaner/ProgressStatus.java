package com.rsd.vkcleaner;

public class ProgressStatus 
{
	public int max;
	public int min;
	public int current;
	private int currentProgressInPercent;
	
	public static final int WORK_STARTED = 0;
	public static final int PROGRESS_UPDATED = 1;
	public static final int WORK_DONE = 2;
	
	public ProgressStatus()
	{
		this.max = 0;
		this.min = 0;
		this.current = 0;
		this.currentProgressInPercent = 0;
	}
	
	public int getCurrentProgressInPercent()
	{
		if(this.max > 0)
		{
			this.currentProgressInPercent = (current * 100) / this.max;
		}
		return this.currentProgressInPercent;
	}
}

