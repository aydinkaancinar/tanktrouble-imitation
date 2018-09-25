package tanktrouble;

//Kaan cinar && Onder soydal
//Bomb
//Winter 2017 - 2018

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.applet.AudioClip;
import java.applet.Applet;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//class that is producing bombs
public class Bomb
{
int theta; //angle that the bomb will be shoot
protected AffineTransform a; //this lets the shape to move
public Shape e; //that variable stores the information of the location of the ball in initial pos
public Shape newShape; //this stores the altered position
public double xv = 4.3; 
public double yv = 4.3;
private int b = -3; // int b and c are used to check if collusion happens with the same wall again
private int c = -3;
public boolean boom; //boolean that returns if the bomb shoot the tank
public int time = 0; //shows the duration of the presence of a bomb
private Clip clip; //gets the sound that will be palyed
private int alphabomb =255; //opacity information of a bomb
private int tankInfo = 0; //tells which tank shot the bomb

public Bomb(int n, Shape s, int angle) //parameters are the information of the place of the tank, and the angle
{
  tankInfo = n;
  theta = angle;
  e = s;
  newShape = e;
  a = AffineTransform.getTranslateInstance(0, 0);//initialized
}

public void draw(Graphics2D g2) //draws the bomb
{
  if(boom) alphabomb = 0; //in case of explısion opacity is set to 0
  newShape = a.createTransformedShape(e); //gets the changed position of the shape
  g2.setColor(new Color(0,0,0,alphabomb)); //coloring changes according the opacity
  g2.fill(newShape); //draws the shape
}

public void playSound(File sound){ //i got this method from the video in the link: https://www.youtube.com/watch?v=QVrxiJyLTqU
  try{
    clip = AudioSystem.getClip(); //initializes the clip
    clip.open(AudioSystem.getAudioInputStream(sound)); //opens the file
    clip.start(); //starts the file
  }
  catch(Exception e){}
}

public void shoot()
{
  for(int i = 0; i < TankTrouble.component.map.obstacle.rect.length; i++) //looks for the all walls
    for(int j = 0; j < TankTrouble.component.map.obstacle.rect[0].length; j++)
    if(TankTrouble.component.map.obstacle.rect[i][j] !=null && (this.intersects(TankTrouble.component.map.obstacle.rect[i][j]))){
    if((b!=i||c!=j)&&(this.intersects(TankTrouble.component.map.obstacle.rectBounds[i][j][0])||this.intersects(TankTrouble.component.map.obstacle.rectBounds[i][j][1]))){
      xv*=-1;
      b=i; //using this loop will not check for the same wall again (check the if statement)
      c=j;
      break;}
    if((b!=i||c!=j)&&(this.intersects(TankTrouble.component.map.obstacle.rectBounds[i][j][2])||this.intersects(TankTrouble.component.map.obstacle.rectBounds[i][j][3]))){
      yv*=-1;
      b=i;
      c=j;
    break;}
  }
  
  
  a.translate( xv*Math.cos(Math.toRadians(theta)),yv*Math.sin(Math.toRadians(theta))); //positions of the ball gets changed
  time++; //time is incremented
}

public boolean intersects(Shape s) //used to determine if the bomb intersects with a shape
{
  boolean intersect = false; 
  if(s.intersects(newShape.getBounds2D())){//the intersects method used here is a method of shape
    this.playSound(new File("rsrc/Bounce2.wav"));
    intersect = true; //if intersection happens, returns true
  }
  return intersect;
}

public boolean intersects(int x, int y, int w, int h) //used to determine if the bomb intersects with an area
{
  boolean intersect = false;
  if(newShape.intersects(x,y,w,h)){
    this.playSound(new File("rsrc/Bounce2.wav"));
    intersect = true; //if intersection happens, returns true
  }
  return intersect;
}

public int getTime() //returns the time
{
  return time;
}

public int getTankInfo() //returns the tank that shot the bomb
{
  return tankInfo;
}
}