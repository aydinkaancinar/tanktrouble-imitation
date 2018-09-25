package tanktrouble;

//Onder soydal & Kaan cinar
//Color Picking Panel
//Winter 2017 - 2018

//packages
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class ColorWheel extends JPanel{

//slider array
JSlider [] slider = new JSlider[6];
//constants
final int WIDTH = 820;
final int HEIGHT = 740;
//color variables
int rV1 = 0;
int gV1 = 0;
int bV1 = 0;
int rV2 = 0;
int gV2 = 0;
int bV2 = 0;
//tanks
private Tank tank1;
private Tank tank2;

private JButton player1Bt;
private JButton player2Bt;
private JPanel north;
private JPanel pickPanel;

//sub panels//////////////
private JPanel pickPanelT1;
private JPanel pickPanelT1T;
private JPanel pickPanelT1R;
private JPanel pickPanelT1G;
private JPanel pickPanelT1B;
private JPanel pickPanelT2;
private JPanel pickPanelT2T;
private JPanel pickPanelT2R;
private JPanel pickPanelT2G;
private JPanel pickPanelT2B;
///////////////////////////

private JLabel tankPhoto;//gif

//tank panels//////////////
private ShowTank tankShow1;
private ShowTank tankShow2;
///////////////////////////

//colors///////////////////
private Color c1 = Color.GREEN;
private Color c2 = Color.RED;
///////////////////////////

//flags when ready/////////
private boolean flag1 = false;
private boolean flag2 = false;
///////////////////////////


public ColorWheel(){ //constructor
  this.setLayout(new BorderLayout());
  
  //north panel which holds pick panel
  north = new JPanel();
  
  //pick panel values
  pickPanel = new JPanel();
  pickPanel.setPreferredSize(new Dimension(410,400));
  pickPanel.setLayout(new GridLayout(1,2));
  pickPanel.setBackground(Color.WHITE);
  
  //slider listener
  SliderListener listener = new SliderListener();
  
  //initialize sliders and set values
  for(int i = 0; i<6;i++){
    int val = 0;
    if(i==1||i==3){val=255;}
    slider[i] = new JSlider(SwingConstants.VERTICAL,0,255,val);
    slider[i].addChangeListener(listener);
    slider[i].setMajorTickSpacing(255);
    slider[i].setMinorTickSpacing(15);
    slider[i].setPaintTicks(true);
    slider[i].setPaintLabels(true);
    slider[i].setOpaque(true);
    slider[i].setBackground(Color.WHITE);
  }
  //set slider colors
  slider[0].setForeground(Color.RED);
  slider[1].setForeground(new Color(24,118,55));
  slider[2].setForeground(Color.BLUE);
  slider[3].setForeground(Color.RED);
  slider[4].setForeground(new Color(24,118,55));
  slider[5].setForeground(Color.BLUE);
  
  //initialize tanks
  tank1 = new Tank(2,10,10,84,71,72,70,81, new Color(0,255,0)); 
  tank2 = new Tank(2,10,10,84,71,72,70,81, new Color(255,0,0));
  
  //initialize tank panels
  tankShow1 = new ShowTank(tank1);
  tankShow2 = new ShowTank(tank2);
  
  //PLAYER 1 CONTROL PANEL
  //pick panel player 1 sub-panels
  pickPanelT1 = new JPanel();
  pickPanelT1.setBackground(Color.WHITE);
  pickPanelT1.setLayout(new GridLayout(1,4));
  pickPanelT1T = new JPanel();
  pickPanelT1T.setBackground(Color.WHITE);
  pickPanelT1T.setLayout(new GridLayout(2,1));
  pickPanelT1R = new JPanel();
  pickPanelT1R.setBackground(Color.WHITE);
  pickPanelT1R.setLayout(new GridLayout(2,1));
  pickPanelT1G = new JPanel();
  pickPanelT1G.setBackground(Color.WHITE);
  pickPanelT1G.setLayout(new GridLayout(2,1));
  pickPanelT1B = new JPanel();
  pickPanelT1B.setBackground(Color.WHITE);
  pickPanelT1B.setLayout(new GridLayout(2,1));
  
  //player 1 font edit
  JLabel labelR1 = new JLabel("RED", SwingConstants.CENTER);
  labelR1.setFont(new Font("Chalkduster", Font.BOLD, 15));
  labelR1.setForeground(Color.RED);
  JLabel labelG1 = new JLabel("GREEN", SwingConstants.CENTER);
  labelG1.setFont(new Font("Chalkduster", Font.BOLD, 15));
  labelG1.setForeground(new Color(24,118,55));
  JLabel labelB1 = new JLabel("BLUE", SwingConstants.CENTER);
  labelB1.setFont(new Font("Chalkduster", Font.BOLD, 15));
  labelB1.setForeground(Color.BLUE);
  
  //pick panel player 1 sub-panels edit
  
  //red panel
  pickPanelT1R.add(slider[0]);
  pickPanelT1R.add(labelR1);
  pickPanelT1.add(pickPanelT1R);
  
  //green panel
  pickPanelT1G.add(slider[1]);
  pickPanelT1G.add(labelG1);
  pickPanelT1.add(pickPanelT1G);
  
  //blue panel
  pickPanelT1B.add(slider[2]);
  pickPanelT1B.add(labelB1);
  pickPanelT1.add(pickPanelT1B);
  
  //tank panel
  pickPanelT1T.add(tankShow1);
  JLabel label1 = new JLabel("Player 1", SwingConstants.CENTER);
  label1.setFont(new Font("Chalkduster", Font.BOLD, 15));
  pickPanelT1T.add(label1);
  pickPanelT1.add(pickPanelT1T);
  //////////////////////////////
  ///////////////////////////////
  
  //PLAYER 2 CONTROL PANEL
  //pick panel player 2 sub-panels
  pickPanelT2 = new JPanel();
  pickPanelT2.setBackground(Color.WHITE);
  pickPanelT2.setLayout(new GridLayout(1,4));
  pickPanelT2T = new JPanel();
  pickPanelT2T.setBackground(Color.WHITE);
  pickPanelT2T.setLayout(new GridLayout(2,1));
  pickPanelT2R = new JPanel();
  pickPanelT2R.setBackground(Color.WHITE);
  pickPanelT2R.setLayout(new GridLayout(2,1));
  pickPanelT2G = new JPanel();
  pickPanelT2G.setBackground(Color.WHITE);
  pickPanelT2G.setLayout(new GridLayout(2,1));
  pickPanelT2B = new JPanel();
  pickPanelT2B.setBackground(Color.WHITE);
  pickPanelT2B.setLayout(new GridLayout(2,1));
  
  //player 2 font edit
  JLabel labelR2 = new JLabel("RED", SwingConstants.CENTER);
  labelR2.setFont(new Font("Chalkduster", Font.BOLD, 15));
  labelR2.setForeground(Color.RED);
  JLabel labelG2 = new JLabel("GREEN", SwingConstants.CENTER);
  labelG2.setFont(new Font("Chalkduster", Font.BOLD, 15));
  labelG2.setForeground(new Color(24,118,55));
  JLabel labelB2 = new JLabel("BLUE", SwingConstants.CENTER);
  labelB2.setFont(new Font("Chalkduster", Font.BOLD, 15));
  labelB2.setForeground(Color.BLUE);
  
  //pick panel player 2 sub-panels edit
  
  //tank panel
  pickPanelT2T.add(tankShow2);
  JLabel label2 = new JLabel("Player 2", SwingConstants.CENTER);
  label2.setFont(new Font("Chalkduster", Font.BOLD, 15));
  pickPanelT2T.add(label2);
  pickPanelT2.add(pickPanelT2T);
  
  //red panel
  pickPanelT2R.add(slider[3]);
  pickPanelT2R.add(labelR2);
  pickPanelT2.add(pickPanelT2R);
  
  //green panel
  pickPanelT2G.add(slider[4]);
  pickPanelT2G.add(labelG2);
  pickPanelT2.add(pickPanelT2G);
  
  //blue panel
  pickPanelT2B.add(slider[5]);
  pickPanelT2B.add(labelB2);
  pickPanelT2.add(pickPanelT2B);
  /////////////////////////////
  
  //add player panels to pick panel
  pickPanel.add(pickPanelT1);
  pickPanel.add(pickPanelT2);
  ///////////////////////////
  
  
  
  
  //add pick panel to main panel
  this.add(pickPanel, BorderLayout.CENTER);
  
  //upload gif to panel
  tankPhoto = new JLabel(new ImageIcon("rsrc/tank.gif"));
  tankPhoto.setPreferredSize(new Dimension(820,300));
  this.add(tankPhoto, BorderLayout.SOUTH);
  
  //player ready buttons 
  player1Bt = new JButton(new ImageIcon("rsrc/ready1.png"));
  player1Bt.setBackground(Color.GREEN);
  player1Bt.setOpaque(true);
  player1Bt.addActionListener(new buttonL1());
  player2Bt = new JButton(new ImageIcon("rsrc/ready2.png"));
  player2Bt.setBackground(Color.RED);
  player2Bt.setOpaque(true);
  player2Bt.addActionListener(new buttonL2());
  this.add(player1Bt, BorderLayout.WEST);
  this.add(player2Bt, BorderLayout.EAST);
   
  //upload title
  JLabel title = new JLabel(new ImageIcon("rsrc/title.png"));
  title.setPreferredSize(new Dimension(820,100));
  this.add(title, BorderLayout.NORTH);
  
  //set background size
  pickPanel.setBackground(Color.WHITE);
  this.setBackground(Color.LIGHT_GRAY);
  
  //set panel size
  this.setSize(new Dimension(WIDTH,HEIGHT));
}

//understand if players are ready
public boolean isReady(){
  return flag1 && flag2;
}
//get player 1 color
public Color getColor1(){
  return c1;
}
//get player 2 color
public Color getColor2(){
  return c2;
}
//slider listener
class SliderListener implements ChangeListener {
  public void stateChanged(ChangeEvent e) {
    //get slider
    JSlider source = (JSlider)e.getSource();
    String sT = source.getName();
    //get color values
    rV1 = slider[0].getValue();
    gV1 = slider[1].getValue();
    bV1 = slider[2].getValue();
    rV2 = slider[3].getValue();
    gV2 = slider[4].getValue();
    bV2 = slider[5].getValue();
    
    //create colors
    c1 = new Color(rV1,gV1,bV1);
    c2 = new Color(rV2,gV2,bV2);
    
    //set button colors
    player1Bt.setBackground(c1);
    player2Bt.setBackground(c2);
    
    //update tanks
    tank1 = new Tank(2,10,10,84,71,72,70,81,c1); 
    tank2 = new Tank(2,10,10,84,71,72,70,81,c2);
    
    //update tank panel
    tankShow1.changeTank(tank1);
    tankShow2.changeTank(tank2);
    
    //repaint all
    pickPanel.repaint();
  }
}

//tank panel
class ShowTank extends JPanel{
  Tank t;
  //constructor
  public ShowTank(Object tank){
    t = (Tank) tank;
    this.setPreferredSize(new Dimension(60,50));
  }
  //draw tank
  public  void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    t.draw(g2);
  }
  //tank update method
  public void changeTank(Tank t2) {
    t = t2;
  }
}
//Ready button listeners

// Player 1 listener
class buttonL1 implements ActionListener { 
  public void actionPerformed(ActionEvent e) { 
    JButton button = (JButton) e.getSource();
    button.setIcon(new ImageIcon("rsrc/player1.png"));
    //set button and sliders disabled
    button.setEnabled(false);
    slider[0].setEnabled(false);
    slider[1].setEnabled(false);
    slider[2].setEnabled(false);
    flag1 = true;
  }
}

//Player 2 Listener
class buttonL2 implements ActionListener { 
  public void actionPerformed(ActionEvent e) { 
    JButton button = (JButton) e.getSource();
    button.setIcon(new ImageIcon("rsrc/player2.png"));
    //set button and sliders disabled
    button.setEnabled(false);
    slider[3].setEnabled(false);
    slider[4].setEnabled(false);
    slider[5].setEnabled(false);
    flag2 = true;
  }
}
}