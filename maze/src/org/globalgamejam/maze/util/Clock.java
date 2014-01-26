package org.globalgamejam.maze.util;

public class Clock {

	private NumberRange hours, minutes, seconds;
	
	public Clock(int h, int m, int s) {
		hours = new NumberRange(0, 23, h);
		minutes = new NumberRange(0, 59, m);
		seconds = new NumberRange(0, 59, s);
	}
	
	public void tick() {
		
		if (seconds.decrement()) {
			if (minutes.decrement()) {
				hours.decrement();
			}
		}
	}
	
	public void reset() {
		hours.reset();
		minutes.reset();
		seconds.reset();
	}
	
	@Override
	public String toString() {
		return hours + ":" + minutes + ":" + seconds;
	}
	
	
	class NumberRange {
		
		private int number;
		
		private int min, max;
		
		public NumberRange(int min, int max, int number) {
			this.min = min;
			this.max = max;
			this.number = number;
		}
		
		@Override
		public String toString() {			
			return (number > 9) ? String.valueOf(number) : "0" + number;
			
		}
		
		public void reset() {
			number = min;
		}
		
		public boolean decrement() {
			number--;
			
			if (number < min) {
				number = this.max;
				return true;
			}
			
			return false;
		}
		
	}
}
