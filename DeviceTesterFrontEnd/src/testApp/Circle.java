package testApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Circle {
	
	private int x,y,r;
	Statistics s;
	
	public Circle(int x, int y, int r){
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setR(int r){
		this.r = r;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getR(){
		return r;
	}
	
	public void drawCircle(Graphics g, Color c){
		g.setColor(c);
		g.fillOval(x, y, r, r);
	}
	
	public boolean checkIfCoordinateIsInsideOfCircle(int eX, int eY){

		if(((eX - x)*(eX - x) + (eY - y)*(eY - y)) <= r*r){
			//System.out.println("Clicked on: ("+eX+","+eY+"), which was in bounds!");
			return true;
		}else{
			//System.out.println("Clicked on: ("+eX+","+eY+"), which was out of bounds!");
			return false;
		}
	}
	
	public double calcDistance(int eX, int eY, Circle circle){
		
			if(circle.checkIfCoordinateIsInsideOfCircle(eX, eY)){
				System.out.println("yes");
				return 0;
			}else{
				double distance = Math.sqrt((circle.x - eX)*(circle.x - eX) + (circle.y - eY)*(circle.y - eY))-circle.r/2;
				System.out.println(distance);
				return distance;
			}
	}	
}
