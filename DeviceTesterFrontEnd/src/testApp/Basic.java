package testApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Basic extends JPanel{

	private static final long serialVersionUID = 270821511825045112L;
	int i = 0, size = 4, xLargeCircle = 400, yLargeCircle = 300, rLargeCircle = 300, rSmallCircle = 20;
	Circle[] circles;
	long begin = 0;

	public Basic(){
		setBackground(Color.WHITE);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		testGenerator(g);
	}

	public void testGenerator(Graphics g){

		//translate the current figures (out of sinc?)
		circles = CalcCirclesInCircle(g, size, xLargeCircle, yLargeCircle, rLargeCircle, rSmallCircle);

		if(circles != null){
			for(int j=0;j<size;j++){
				circles[j].drawCircle(g, Color.BLACK);
			}
			circles[0].drawCircle(g, Color.RED);
		}
	}

	public void increment(){
		if(i < size){
			i++;
		}
	}

	public MouseAdapter mouseTrigger(Graphics g){
		
		begin = System.currentTimeMillis();

		circles = CalcCirclesInCircle(g, size, xLargeCircle, yLargeCircle, rLargeCircle, rSmallCircle);
		int [] seq = circleSequencer(circles);
		Statistics s = new Statistics();

		MouseAdapter x = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {			
				if(i!=size){
					if(i==0){
						begin = System.currentTimeMillis();
					}
				
					long end = System.currentTimeMillis();
					System.out.println(getPrev());
					s.calcStatistics(circles[i].calcDistance(e.getX(), e.getY(), circles[getPrev()]), circles[i].getR(), end-begin);
					begin = end;
					
					if(circles != null){
						for(int j=0;j<size;j++){
							circles[j].drawCircle(g, Color.BLACK);
						}
						circles[seq[getNext()]].drawCircle(g, Color.RED);
					}		
					increment();
					if(i==size){
						System.out.println("Throughput: "+s.TP);
					}
				}
			}
		};	
		return x;
	}

	private int getPrev(){
		if(i==0){
			return 0;
		}else{
			return i-1;
		}
	}

	private int getNext(){
		if(i>=size-1){
			return 0;
		}else{
			return i+1;
		}
	}

	//Calculates the coordinates of the circles, who form a circle themselves.
	//Works only for modulo 4 number of circles
	//Input: 
	// - nCircles = number of circles
	// - xMain = x-coordinate of main circle
	// - yMain = y-coordinate of main circle
	// - rMain = radius of main circle
	// - rCircle (Optional) = circle of the smaller circle, if set to zero ==> an automatic size will be generated (not optimalized!)

	public Circle[] CalcCirclesInCircle(Graphics g, int nCircles, int xMain, int yMain, int rMain, int rCircle){
		if((nCircles % 4) == 0 & nCircles >= 4){		
			int xCircle, yCircle;
			if(rCircle == 0){
				rCircle = rMain/(nCircles/2);
			}
			Circle[] circles = new Circle[nCircles];
			g.setColor(Color.BLACK);
			g.drawOval(xMain-rMain/2-rCircle/2, yMain-rMain/2-rCircle/2, rMain+rCircle, rMain+rCircle);
			for(int i=0;i<nCircles;i++){
				xCircle = (int) (xMain - rCircle/2 + rMain/2*Math.cos((Math.PI*i/(nCircles/2))-Math.PI/2));
				yCircle = (int) (yMain - rCircle/2 + rMain/2*Math.sin((Math.PI*i/(nCircles/2))-Math.PI/2));
				circles[i] = new Circle(xCircle,yCircle,rCircle);
			}

			return circles;
		}else{
			System.out.println("Invalid number of circles!");
			return null;
		}
	}

	public int[] circleSequencer(Circle[] circles){
		int size = circles.length;
		int[] sequence = new int[size];
		for(int i=0;i<size;i++){
			if(i % 2 == 0){
				sequence[i] = i/2;
			}else{
				sequence[i] = sequence[i-1]+size/2;
			}
		}
		return sequence;
	}

	public void makeTest(String title, int x, int y, int size){
		this.size = size;
		JFrame mainFrame = new JFrame(title);
		mainFrame.add(this);
		mainFrame.setSize(x, y);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);	
		this.addMouseListener(this.mouseTrigger(this.getGraphics()));
	}

	public static void main(String[] args) {	
		Basic a = new Basic();
		a.makeTest("Test", 800, 600, 4);
	}
}

