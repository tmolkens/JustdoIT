package testApp;

import java.awt.event.MouseEvent;

public class Statistics {

	double WE, TP, IDE;
	private double time;

	public Statistics(){

	}

	public double calcThroughput(double IDE, double MT){
		if(MT!=0){
			return IDE/MT;
		}else{
			return 0;
		}
	}

	public double calcIDE(double d, double we){
			
		if(d>0){
			double x = 1 + (d/we);
			return Math.log10(x)/Math.log10(2);
		}else{
			return 0;
		}
	}

	public double calcWE(int r){
		return r;
	}

	public void calcStatistics(double d, int r, double t){
		savePartialStatistic(calcWE(r), calcIDE(d, WE), t);
	}

	public void savePartialStatistic(double WE, double IDE, double time){
		System.out.println(WE+","+IDE+","+time);
		this.WE = (WE + this.WE)/2;
		if(IDE != 0){
			this.IDE = (IDE + this.IDE)/2;
		}
		if(time != 0){
			this.time = (time + this.time)/2;
		}
		this.TP = calcThroughput(this.IDE, this.time/1000);
	}
}
