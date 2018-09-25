package tanktrouble;

//Kaan cinar && Onder soydal
//TankComponent that draws every component of the game panel
//Winter 2017 - 2018

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import javax.swing.JComponent;
import java.util.ArrayList;

public class TankComp extends JComponent //component class for storing the Tanks, moving them at once
{  
public ArrayList<Bomb> bombs;
public Tank tank1;
public Tank tank2;
public Map map;

public TankComp()
{
  map = new Map();
  bombs = new ArrayList<Bomb>();
}

public void addTanks(Tank t1, Tank t2)
{
  tank1 = t1;
  tank2 = t2;
}

public void add(Bomb b)
{
  bombs.add(b); //adds bombs to the arraylist of bombs
} 

public void remove(Bomb b)
{
  bombs.remove(b); //removes the bombs from the arraylist of bombs
} 

public void paintComponent(Graphics g) //draws the Tanks
{
  super.paintComponent(g);
  Graphics2D g2 = (Graphics2D) g; 
  tank1.draw(g2);
  tank2.draw(g2);
  try{
    for(Bomb b:bombs){
      if(b.getTime()>630){ //after a certain time passed the bombs are removed
        if(b.getTankInfo()==1) tank1.bombCount--; //gets the tank that fired the bomb that will be removed, and reduces the bomb count of that tank
        if(b.getTankInfo()==2) tank2.bombCount--;
        bombs.remove(b); //bomb is removed from the array
        b.playSound(new File("rsrc/Disappear.wav")); //disappearing noise of the bomb
      }
    }
  }
  catch (java.util.ConcurrentModificationException e) {}
  for(Bomb b:bombs) //enhenced for loop is used to draw
  {
    super.paintComponent(g);
    b.draw(g2);
  }
  if(tank1.explode) tank1.explode(g2);
  if(tank2.explode) tank2.explode(g2);
  map.draw(g2);
}

public void reDraw() //repaints the tank according to the changes that are made
{
  try{
    for(Bomb b:bombs)
    {
      if(!tank1.explode&&tank1.intersects(b.newShape)){//checks if there is an explosion
        if(!b.boom)tank1.explode = true; //if the tank didnt exploded, and if the statement above is true, than explodes the tank
        b.boom = true; //tells that the explosion happened
        bombs.remove(b); //removes the bomb from the array
        if(b.getTankInfo()==1) tank1.bombCount--; //reduces the bomb count of the tank that fire the bomb
        if(b.getTankInfo()==2) tank2.bombCount--;
      }
      if(!tank2.explode&&tank2.intersects(b.newShape)){
        if(!b.boom)tank2.explode = true;
        b.boom = true;
        bombs.remove(b);
        if(b.getTankInfo()==1) tank1.bombCount--;
        if(b.getTankInfo()==2) tank2.bombCount--;
      }
    }
  }
  catch (java.util.ConcurrentModificationException e) {} //catches the errors happen due to the removal of an array element during the enhanced for loop
  if(!tank1.explode)tank1.drive(); //tank can be driven if the explosion didn't happen 
  if(!tank2.explode)tank2.drive();
  for(Bomb b:bombs) //bombs in the array are moved
  {
    b.shoot();
  }
  repaint(); //repaints...
}

public void newGame(){
  bombs.clear();
  map.setValues();
  map.spawn();
  tank1.setValues(map.x1,map.y1);
  tank2.setValues(map.x2,map.y2);
}
}