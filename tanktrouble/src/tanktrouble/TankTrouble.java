package tanktrouble;

//Onder soydal & Kaan cinar
//Classs that manages the frames, keylistener and timer
//Winter 2017 - 2018

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class TankTrouble //main class
{
private static ColorWheel colorSelection;
private static int a = 0;
private static int counter = 0;
private static JFrame frame;
private static Timer t;
private static JPanel devs;
public static TankComp component;
private static Tank tank1;
private static Tank tank2;
private static JPanel scorePanel;
private static JLabel redImg;
private static JLabel greenImg;
private static JLabel p1;
private static JLabel p2;
private static JLabel redScore;
private static JLabel greenScore;
private static int score1 = -1;
private static int score2 = -1;
private static KeyAdapter ka;
private static JFrame intro;

public TankTrouble(){
  //Intro Frame
  intro = new JFrame();//initialize frame
  colorSelection = new ColorWheel();//initialize color selection
  intro.setSize(new Dimension(820, 720)); //dimentions of the frame are set
  intro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close operation
  intro.getContentPane().add(colorSelection, BorderLayout.CENTER);// add color selection to frame
  intro.setResizable(false);//make it not resizable
  intro.setLocationRelativeTo(null);//set location
  intro.setVisible(true);//show it 
  ///////
  
  
  ka = new KeyAdapter() {//used to override only the methods that will be used
    @Override
    public void keyPressed(KeyEvent e) {
      tank1.takeInput(e.getKeyCode()); //takes input for tank1
      tank2.takeInput(e.getKeyCode()); //takes input for tank2
    }
    public void keyReleased(KeyEvent e) {
      tank1.release(e.getKeyCode()); //notifies that the key is released for tank1
      tank2.release(e.getKeyCode()); //notifies that the key is released for tank1
    }
  };
  
  Font font = new Font("Arial", Font.BOLD, 30);
  scorePanel = new JPanel();
  redImg = new JLabel();
  greenImg = new JLabel();
  p1 = new JLabel();
  p2 = new JLabel();
  redScore = new JLabel("0");
  greenScore = new JLabel("0");
  ImageIcon red = new ImageIcon("rsrc/green.png");
  ImageIcon green = new ImageIcon("rsrc/red.png");
  ImageIcon p1controls = new ImageIcon("rsrc/p1controls.png");
  ImageIcon p2controls = new ImageIcon("rsrc/p2controls.png");
  p1.setIcon(p1controls);
  p2.setIcon(p2controls);
  redImg.setIcon(red);
  greenImg.setIcon(green);
  redScore.setFont(font);
  greenScore.setFont(font);
  scorePanel.add(p1);
  scorePanel.add(redScore);
  scorePanel.add(redImg);
  scorePanel.add(greenImg);
  scorePanel.add(greenScore);
  scorePanel.add(p2);
  
  final int FRAME_WIDTH = 820; //frame width
  final int FRAME_HEIGHT = 760; //frame height
  frame = new JFrame(); 
  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //gets screen size
  int xCo = (dim.width-820)/2;
  int yCo = (dim.height-760)/2;
  frame.setLocation(xCo, yCo); //sets location of the frame to the middle of the screen
  frame.setLayout(new BorderLayout());
  frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); //dimentions of the frame are set
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  frame.setTitle("Tanks"); //title 
  frame.setResizable(false);
  frame.addKeyListener(ka); //key listener is added to the frame
  
  component = new TankComp(); //component initialized and tanks are added 
  component.map.spawn();
  tank1 = new Tank(1,component.map.x1,component.map.y1,KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_RIGHT,KeyEvent.VK_LEFT,77,Color.GREEN); //tank1 properties
  tank2 = new Tank(2,component.map.x2,component.map.y2,84,71,72,70,81,Color.RED); //tank1 properties
  component.addTanks(tank1,tank2);
  component.setPreferredSize(new Dimension(820,640)); //dimesions of component is set
  component.setLocation(0,0); 
  
  
  devs = new JPanel(); //panel for animation
  devs.add(component); 
  
  class TimerListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      if(colorSelection.isReady()&&a==0){
        tank1.setColor(colorSelection.getColor2());
        tank2.setColor(colorSelection.getColor1());
        intro.dispose();//close intro frame
        frame.add(devs);
        frame.getContentPane().add(devs, BorderLayout.CENTER); 
        frame.getContentPane().add(scorePanel, BorderLayout.SOUTH); 
        frame.validate();
        frame.setVisible(true);//make game frame visible
        a = 1;
      }
      component.reDraw(); //repaint method
      if(tank1.explode||tank2.explode){
        counter++;
      }
      if(counter == 220||a ==1){
        counter = 0;
        if(!tank1.explode) score1++;
        if(!tank2.explode) score2++;
        component.newGame();
        greenScore.setText(score1+"");
        redScore.setText(score2+"");
        a =2;
      }
    }
  }
  
  
  ActionListener listener = new TimerListener();
  final int DELAY = 15; 
  t = new Timer(DELAY, listener);
  t.start();
}
}