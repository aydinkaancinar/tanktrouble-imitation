package tanktrouble;

//Onder soydal & Kaan cinar
//Map
//Winter 2017 - 2018

import javax.swing.JComponent;
import java.util.Random;
import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Map extends JComponent{

public int x1,x2,y1,y2;
final int WIDTH = 810;
final int HEIGHT = 610;
final int SQUARE = 98;
final int RECT = 6;
private int ARR_FIRST = 2*((WIDTH-RECT)/(SQUARE+RECT))+1;
private int ARR_SECOND = 2*((HEIGHT-RECT)/(SQUARE+RECT))+1;
Random rgen = new Random();

public Wall obstacle= new Wall(ARR_FIRST,ARR_SECOND);
//ArrayList<Shape> obstacle = new ArrayList<Shape>();
//constructor
public Map(){  
  setValues();
} 

public void setValues()
{
  for(int i = 0; i < ARR_FIRST; i++){
    for(int j = 0; j < ARR_SECOND; j++){
      obstacle.rect[i][j] =null;}}
  ARR_FIRST = 2*((WIDTH-RECT)/(SQUARE+RECT))+1;
  ARR_SECOND = 2*((HEIGHT-RECT)/(SQUARE+RECT))+1;
  this.setSize(new Dimension(WIDTH,HEIGHT));
  for(int i = 0; i < ARR_FIRST; i++){
    for(int j = 0; j < ARR_SECOND; j++){
      int x;
      int y;
      if(i%2==0){x =10+ (i/2)*(SQUARE+RECT);}
      else{x =10+ (i/2)*SQUARE + ((i/2)+1)*RECT;}
      if(j%2==0){y =10+ (j/2)*(SQUARE+RECT);}
      else{y =10+ (j/2)*SQUARE + ((j/2)+1)*RECT;}
      
      if( i%2!=0 && j%2!=0){  
        //obstacle[i][j] = new Rectangle(x,y,SQUARE,SQUARE);
      }
      else if( i%2==0 && j%2==0){
        //obstacle[i][j] = new Rectangle(x+33,y+21,4,4);
      }
      else if(i%2!=0 && j%2==0){
        if((j!=0 && j!= ARR_SECOND-1)&& rgen.nextBoolean()){}
        else{
          obstacle.add(i,j,new Rectangle(x+29,y+20,SQUARE+6,RECT),true);
        }
      }
      else if(i%2==0 && j%2!=0){
        if(( i!=0 && i!= ARR_FIRST-1) && rgen.nextBoolean()){}
        else{
          obstacle.add(i,j,new Rectangle(x+32,y+17,RECT,SQUARE+6),false);
        }
      }   
    }
  }
}
          
          
public void draw(Graphics g){
  Graphics2D g2 = (Graphics2D) g;
  //DRAW TANKS
  Rectangle a = new Rectangle(10,10);
  Rectangle b = new Rectangle(10,10);
  //spawn(a,b);
  //draw map
  this.setBackground(new Color(228,228,228));
  g2.setColor(new Color(78,78,78));
  obstacle.draw(g2);
  
}

//set tanks location in grid
public void spawn(){
  x1 = rgen.nextInt(ARR_FIRST-2)+1;
  while(x1%2==0){
    x1 = rgen.nextInt(ARR_FIRST-2)+1;
  }
  y1 = rgen.nextInt(ARR_SECOND-2)+1;
  while(y1%2==0){
    y1 = rgen.nextInt(ARR_SECOND-2)+1;
  }
  //determine second tank place
  x2 = rgen.nextInt(ARR_FIRST-2)+1;
  while(x2%2==0 || x2 == x1){
    x2 = rgen.nextInt(ARR_FIRST-2)+1;
  }
  y2 = rgen.nextInt(ARR_SECOND-2)+1;
  while(y2%2==0 || y2 == y1){
    y2 = rgen.nextInt(ARR_SECOND-2)+1;
  }
  //create path between tanks
  findPlace(x1,y1,x2,y2);
  //find relative place
  /* Point tank1P = setRelativeLoc(x1,y1);
   Point tank2P = setRelativeLoc(x2,y2);
   */
  //find relative place
  if(x1%2==0){x1 =(x1/2)*(SQUARE+RECT);}
  else{x1 =(x1/2)*SQUARE + ((x1/2)+1)*RECT;}
  if(y1%2==0){y1 =(y1/2)*(SQUARE+RECT);}
  else{y1 =(y1/2)*SQUARE + ((y1/2)+1)*RECT;} 
  if(x2%2==0){x2 =(x2/2)*(SQUARE+RECT);}
  else{x2 =(x2/2)*SQUARE + ((x2/2)+1)*RECT;}
  if(y2%2==0){y2 =(y2/2)*(SQUARE+RECT);}
  else{y2 =(y2/2)*SQUARE + ((y2/2)+1)*RECT;}
  //set tanks location
  x1 = x1+SQUARE/2;
  y1 = y1+SQUARE/2;
  x2 = x2+SQUARE/2;
  y2 = y2+SQUARE/2;
}

//method to create a path between tanks
public void findPlace(int x1, int y1, int x2, int y2){
  //clear path
  obstacle.rect[x2][y2] = null;
  //base case
  if(x1==x2 && y1==y2){}
  //recursive case
  else{
    if(x2 < x1){
      findPlace(x1,y1,x2+1,y2);
    }
    else if(x2 > x1){
      findPlace(x1,y1,x2-1,y2);
    }
    else{
      if(y2 < y1){
        findPlace(x1,y1,x2,y2+1);
      }
      else if(y2 > y1){
        findPlace(x1,y1,x2,y2-1);
      }  
    }
  }
}

public Point setRelativeLoc(int locX, int locY){
  if(locX%2==0){locX =(locX/2)*(SQUARE+RECT);}
  else{locX =(locX/2)*SQUARE + ((locX/2)+1)*RECT;}
  if(locY%2==0){locY =(locY/2)*(SQUARE+RECT);}
  else{locY =(locY/2)*SQUARE + ((locY/2)+1)*RECT;}
  return (new Point (locX, locY));
} 
}