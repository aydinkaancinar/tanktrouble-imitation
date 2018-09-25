package tanktrouble;

//Kaan cinar && Onder soydal
//Tank
//Winter 2017 - 2018

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Tank //the class consists of the informations and atributes of the tank
{
private AffineTransform at; //transforms the positions and the angle of the tank
private boolean up,down,right,left,wall; //checks if these keys are pressed
private Clip clip;
private Color color; //color of the tank
private Ellipse2D bomb; //stores the info of the bomb that will be shot
private Ellipse2D e; //stores the info of the turret of the tank
private int counter = 0;
private int upKey,downKey,rightKey,leftKey,shootKey; //key values for the movement of the tank
private int xCo,yCo,w,h; //these variables are about poistioning
private int xv = 4;
private int backxv = -4;
private int turnR = -5;
private int turnL = 5;
private Polygon exp; //poligon that stores the sahpe of the explosion
private Rectangle2D [] points; //stores the boundry points of the tank, later will be used to detect collision
private Rectangle2D gun; //stores the info of the gun of the tank
private Rectangle2D rect; //stores the info of the hull of the tank
private Shape s; //stores the transformed position of the bomb
private Shape [] boundries; //stores the transformed position of the boundries of the tank
public int alpha =255; //opacity of the tank, in case of explosion set to 0
public int alphaExp =255; //opacity of the tank, in case of explosion decreased to 0 after few seconds of the explosion
public int bombCount = 0;
public int checkAngle = 0; //variable that returns the angle
public int playerNumber = 0;
public boolean explode; //boolean that checks explosion
public Shape tankBody; //stores the transformed position of the hull of the tank

public Tank(int n, int x, int y, int u, int d, int r, int l, int shoot, Color c)
{//at parameters there are: player number, x pos, ypos, up key, down key, right key, left key, shooting key, and the color of the tank are located
  //variables are initialized
  playerNumber = n;
  h = 30;
  w = 40;
  upKey = u;
  downKey = d;
  rightKey = r;
  leftKey = l;
  shootKey = shoot;
  color = c;
  setValues(x,y);
}

public void setColor(Color c)
{
  color = c;
}

public void setValues(int x,int y)
{
  up = false;
  down = false;
  right = false;
  left = false;
  wall = false;
  explode = false;
  xCo = x;
  yCo = y;
  xv = 4;
  backxv = -4;
  turnR = -5;
  turnL = 5;
  counter = 0;
  alpha = 255;
  alphaExp = 255;
  bombCount = 0;
  checkAngle = 0;
  rect = new Rectangle2D.Double(0, 0, w, h);
  tankBody = rect;
  gun = new Rectangle2D.Double(0, 0, w, h);
  e = new Ellipse2D.Double(xCo, yCo, w, h);
  bomb = new Ellipse2D.Double(xCo, yCo, w, h);
  at = AffineTransform.getTranslateInstance(0, 0);
  //these positions are the x and y coordinates of the corners of poligon
  int xpoints[] = {xCo, xCo+10, xCo+20, xCo+40, xCo+30, xCo+50, xCo+40, xCo+60, xCo+40, xCo+40, xCo+20, xCo-10, xCo, xCo-20};
  int ypoints[] = {yCo, yCo-30, yCo, yCo-20, yCo, yCo-10, yCo+10, yCo+30, yCo+30, yCo+50, yCo+30, yCo+40, yCo+20, yCo};
  exp = new Polygon(xpoints, ypoints, 14);
  points = new Rectangle2D[30];
  boundries = new Shape[30];
  for(int j = 0; j<8; j++){ //array of boundries of the tank are filled
    points[j]= new Rectangle2D.Double(xCo+5*j, yCo, 1, 1);
    points[j+8] = new Rectangle2D.Double(xCo+5*j, yCo+29, 1, 1);
  }
  for(int k = 0; k<6; k++){
    points[k+16] = new Rectangle2D.Double(xCo, yCo+5*k, 1, 1);
    points[k+22] = new Rectangle2D.Double(xCo+39, yCo+5*k, 1, 1);
  }
  points[28] = new Rectangle2D.Double(xCo+47, yCo+14, 1, 1);
  points[29] = new Rectangle2D.Double(xCo+44, yCo+14, 1, 1);
  for(int m = 0; m<30; m++) {
    boundries[m] = (Shape) points[m];
  }
}

public void playSound(File sound){ //i got this method from the video in the link: https://www.youtube.com/watch?v=QVrxiJyLTqU
  try{
    clip = AudioSystem.getClip(); //initializes the clip
    clip.open(AudioSystem.getAudioInputStream(sound)); //opens the file
    clip.start(); //starts the file
  }
  catch(Exception e){}
}

public void draw(Graphics2D g2)//method that draws the tank
{
  e = new Ellipse2D.Double(xCo+49, yCo+11, 8, 8);//sets the variables for shape
  g2.setColor(Color.BLACK);
  s = at.createTransformedShape(e); //transforms the shape according to the variables (angle and x, y) set in affinetransform
  rect.setRect(xCo, yCo, w, h);
  g2.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha));
  tankBody = at.createTransformedShape(rect);
  g2.fill(tankBody);
  g2.setColor(new Color(0,0,0,alpha));
  g2.draw(at.createTransformedShape(rect));
  e = new Ellipse2D.Double(xCo+7, yCo+2, 26, 26);
  g2.setColor(new Color(0,0,0,alpha));
  g2.draw(at.createTransformedShape(e));
  gun.setRect(xCo+30, yCo+11, 18, 8);
  g2.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha));
  g2.fill(at.createTransformedShape(gun));
  gun.setRect(xCo+33, yCo+11, 15, 8);
  g2.setColor(new Color(0,0,0,alpha));
  g2.draw(at.createTransformedShape(gun));
  for(int c = 0; c<30; c++) {
    boundries[c] = at.createTransformedShape(points[c]);
  }
}

public void explode(Graphics2D g2) //draws the explosion of the tank
{
  if(alpha>20) alpha-=20; //this reduces the opacity as time goes by
  else if(alphaExp>1) {alpha = 0; alphaExp-=2;}
  g2.setColor(new Color(Color.ORANGE.getRed(),Color.ORANGE.getGreen(),Color.ORANGE.getBlue(),alphaExp));
  g2.fill(at.createTransformedShape(exp));
  if(counter == 0)
  {
    this.playSound(new File("Exlosion.wav"));
    counter=1;
  }
}

public void takeInput(int i) //checks if the keys are pressed
{
  if(i == upKey){
    up = true;
  }
  if(i == downKey){
    down = true;
  }
  if(i == leftKey){
    left = true;
  }
  if(i == rightKey){
    right = true;
  }
  if(i == shootKey)
  {
    bombala(); //when the shooting key is pressed a bomb is shooted
  }
}

public void release(int i) //checks if the keys are released
{
  if(i == upKey){
    up = false;
  }
  if(i == downKey){
    down = false;
  }
  if(i == leftKey){
    left = false;
  }
  if(i == rightKey){
    right = false;
  }
}

public void drive() //drives the tank according the conditions of the booleans: up, down, right, left
{
  if(up){
    wall = false;
    for(int i = 0; i < TankTrouble.component.map.obstacle.rect.length; i++)
      for(int j = 0; j < TankTrouble.component.map.obstacle.rect[0].length; j++)
      if(TankTrouble.component.map.obstacle.rect[i][j] !=null)
      if(this.intersectsForw(TankTrouble.component.map.obstacle.rect[i][j])) at.translate(-5,0); //translates the object backwards if the front of the tank crashes
    //checks if the front of the tank
    at.translate(xv,0); //this translates the position of the tank
    if(left){ //meanwhile if the left is pressed...
      at.rotate(Math.toRadians(turnR),xCo+w/2,yCo+h/2); //this rotates the shapes that create the tank
      checkAngle +=turnR; //angles are checked, so it can be used when the tank shoots a bomb
    }
    if(right){
      at.rotate(Math.toRadians(turnL),xCo+w/2,yCo+h/2);
      checkAngle +=turnL;
    } 
  }
  
  if(down){ 
    wall =false;
    for(int i = 0; i < TankTrouble.component.map.obstacle.rect.length; i++)
      for(int j = 0; j < TankTrouble.component.map.obstacle.rect[0].length; j++)
      if(TankTrouble.component.map.obstacle.rect[i][j] !=null)
      if(this.intersectsBack(TankTrouble.component.map.obstacle.rect[i][j])) at.translate(5,0); //translates the object forwrd if the back of the tank crashes
    //checks if the back of the tank
    at.translate(backxv,0);
    if(left){ 
      at.rotate(Math.toRadians(turnR),xCo+w/2,yCo+h/2);
      checkAngle +=turnR;
    }
    if(right){
      at.rotate(Math.toRadians(turnL),xCo+w/2,yCo+h/2);
      checkAngle +=turnL;
    }
  }
  
  if(!(down||up)){
    if(left){
      at.rotate(Math.toRadians(turnR),xCo+w/2,yCo+h/2);
      checkAngle +=turnR;
    }
    if(right){
      at.rotate(Math.toRadians(turnL),xCo+w/2,yCo+h/2);
      checkAngle +=turnL;
    }
  }
}

public boolean intersects(Shape s) //returns true if tank intersects with a certain shape
{
  boolean intersect = false;
  for(Shape bound:boundries){
    if(s.intersects(bound.getBounds2D())) intersect = true;
  }
  return intersect;
}

public boolean intersectsForw(Shape s) //returns true if tank intersects with a certain shape
{
  boolean intersect = false;
  for(int i = 22; i<30;i++){
    if(s.intersects(boundries[i].getBounds2D())) intersect = true; //checks the boundries in the front
  }
  return intersect;
}

public boolean intersectsBack(Shape s) //returns true if tank intersects with a certain shape
{
  boolean intersect = false;
  for(int i = 16; i<22;i++){
    if(s.intersects(boundries[i].getBounds2D())) intersect = true; //checks the boundries in the back
  }
  return intersect;
}

public boolean intersects(int x, int y, int w, int h) //used to determine if the bomb intersects with an area
{
  boolean intersect = false;
  for(Shape bound:boundries){
    if(bound.intersects(x,y,w,h)) intersect = true;
  }
  return intersect;
}

public int getAngle() //returns the angle of the tank
{
  return checkAngle;
}

public void bombala() //allows tank to fire a bomb
{
  if((bombCount<5)&&!explode){ //bomb is fired if tank fired less than 5 bombs and if it didn't explode

    int degree = checkAngle;
    TankTrouble.component.add(new Bomb(playerNumber,s, checkAngle)); //a new bomb is added to the arraylist in the TankComp
    this.playSound(new File("Bomba2.wav"));
    bombCount++;
  }
}
}