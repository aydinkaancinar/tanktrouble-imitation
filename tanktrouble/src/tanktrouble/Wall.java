package tanktrouble;

//Kaan cinar && Onder soydal
//Class that produces and stores walls
//Winter 2017 - 2018

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Wall
{
public Rectangle2D [][]rect; //these are the walls
public static Rectangle2D [][][]rectBounds; //these are for collusion detection from different sides

public Wall(int i, int j)
{
  rect = new Rectangle2D [i][j];
  rectBounds = new Rectangle2D [i][j][4];
}

public void draw(Graphics2D g){ //draws the walls
  for(int i = 0; i < rect.length; i++)
    for(int j = 0; j < rect[0].length; j++)
    if(TankTrouble.component.map.obstacle.rect[i][j] !=null){
    g.fill(rect[i][j]);
  }
}

public void add(int i, int j, Rectangle2D r, boolean horizontal){ //fills the array, adds the walls to the specified position with specified dimensions
  rect[i][j] = r;
  rectBounds[i][j][0] =new Rectangle2D.Double(r.getX(), r.getY(), 1, r.getHeight());
    rectBounds[i][j][1] =new Rectangle2D.Double(r.getX()+r.getWidth(), r.getY(), 1, r.getHeight());
    rectBounds[i][j][2] =new Rectangle2D.Double(r.getX(), r.getY(), r.getWidth(), 1);
    rectBounds[i][j][3] =new Rectangle2D.Double(r.getX(), r.getY()+r.getHeight(), r.getWidth(), 1);
}
}