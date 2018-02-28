
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import javax.swing.border.BevelBorder;


 class ImageSplitTest {

    public static String[] cropimage(int wh,int no) throws IOException {
      
 
        
       

BufferedImage image = ImageIO.read( new File("C:\\Users\\dell\\Desktop\\images"+wh+""+no+".jpg")); 

        int rows =wh; 
        int cols = wh;
        int chunks = rows * cols; //

        int chunkWidth = image.getWidth() / cols;

      int chunkHeight = image.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; 

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

               
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0,0,chunkWidth, chunkHeight,chunkWidth*y, chunkHeight*x,chunkWidth*y+chunkWidth , chunkHeight*x+chunkHeight, null);
             
            }
        }
        
String imagechunks[]=new String[chunks];
     
        for (int i = 0; i < imgs.length; i++){ 
            ImageIO.write(imgs[i], "jpg", new File("C:\\Users\\dell\\Desktop\\picturepuzzle\\"+i+""+wh+""+no+".jpg"));

       imagechunks[i]="C:\\Users\\dell\\Desktop\\picturepuzzle\\"+i+""+wh+""+no+".jpg";

   
}

return(imagechunks);    
    
 }
}



        
        
 
      
        
        
        
        
 






 class picpuzzle extends JFrame implements ActionListener,Runnable 
 {
 JPanel statusbar=new JPanel();
JMenuBar menubar=new JMenuBar();
JMenuItem exit,level,newgame,view_help;
 JButton[][] t;
 Icon cl[]; 
JMenu help, menu;
JLabel statuspane,movesstatus,spane;
String player;
String imagepath[];
 int prstlvl=0,no=0,moves,level_no1=3,min,sec;
final String cfix="C:\\Users\\dell\\Desktop\\45.jpg";
picpuzzle()
{
super("puzzlepicpuzzle");

    
    setResizable(false);
  setBounds(200,20,900,980);  
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}
void createstatusbar(picpuzzle mainframe)
 {

 statusbar.setLayout(new FlowLayout(FlowLayout.LEFT,15,3));
   statusbar.setBackground(Color.LIGHT_GRAY);
     statusbar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
     statuspane =new JLabel();
       
      statuspane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
     statuspane.setPreferredSize(new Dimension(100,20));
     statuspane.setText(player);
     movesstatus=new JLabel();
    
    movesstatus.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
     movesstatus.setPreferredSize(new Dimension(50,20));
    
    
     
        JLabel label=new JLabel("PLAYER:");
         statusbar.add(label);
          statusbar.add(statuspane);
         JLabel label2=new JLabel("MOVES:");
         label2.setLocation(800, 940);
           statusbar.add(label2);
           statusbar.add(movesstatus);
           
        JLabel label3=new JLabel("TIME:");
         statusbar.add(label3);
           spane =new JLabel();
       statusbar.add(spane);
      spane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
     spane.setPreferredSize(new Dimension(100,20));
     spane.setText("00:00");
     mainframe.add(statusbar,BorderLayout.SOUTH);
    
 }

void menubarcreator(picpuzzle mainframe){
      


mainframe.setJMenuBar(menubar);
menu=new JMenu("MENU");
help=new JMenu("HELP");

view_help=help.add("View Help");
view_help.addActionListener((ActionEvent actionEvent) -> {
    String h="To move a tile,click it and it will swapped\n"
            + " with the empty tile next to it.If there is no\n"
            + "empty tile next to it,clicked tile will not move.\n"
            + "You win when all the scattered tile are organized \n"
            + " according to the actual picture";
    JOptionPane.showMessageDialog(mainframe,h,"How to play",JOptionPane.PLAIN_MESSAGE);
    
});
  newgame=menu.add("New game");
  newgame.addActionListener((ActionEvent ae) -> {
      int c=level_no1;
      if(no==3)
              {
               no=0;   
              }
      else
      {
      no++;
      }
    
     
      movesstatus.setText("");
      moves=0;
     reset(this,c);
    
         timage(this);
    try {
      createtiles(this);
    } catch (IOException ex ) {
        
    System.out.println("ex");
    }
    
    

});




level=menu.add("LEVEL");
level.addActionListener((ActionEvent ae) -> {
    int c=level_no1;
    no=0;
    level_option_box(this);
    if(c!=level_no1)
    {
    reset(this,c);
     movesstatus.setText("");
     moves=0;
     timage(this);
    try {
        createtiles(this);
   } catch (IOException ex) {
       
      
       System.out.println(ex);
   }
    }
});


exit=menu.add("EXIT");
exit.addActionListener((ActionEvent ae) -> {
 
        System.exit(0);
        
  
});
menubar.add(menu);
menubar.add(help);
}

void reset(picpuzzle mainframe,int d)
{
    
mainframe.setVisible(false);
  

  for(int a=0;a<d;a++)
  {
      
      for(int b=0;b<d;b++)
      {
        mainframe.remove(t[a][b]);    
      }
  }

 mainframe.setVisible(true);
 min=0;
 sec=0;
 }

    

    public void run() {
     try
     {       
   for( min=0;min<60;min++)  
   {
       for( sec=0;sec<60;sec++)
               {
                if((min>=10)&&(sec>=10)) 
                {
                     spane.setText(""+min+":"+sec+"");
                    
                }
                else if(sec<10&&min>=10)
                {
                 spane.setText(""+min+":0"+sec+""); 
                 
                  
                }
               else if(sec>=10&&min<10)
                {
                 spane.setText("0"+min+":"+sec+"");
                  
                }
                else
                {
                 spane.setText("0"+min+":0"+sec+"");
                  
                }
                 Thread.sleep(1000);
               }
   }
     }
   catch (InterruptedException interruptedException) {
         }
    }
        
    
    
    
    
    
    

void level_option_box(picpuzzle mainframe)
{
  
   
    Object[] option={"esay","medium","advance"};
    
  String l="Threr are three level in this picpuzzle:\n"
          + "Easy:-Consist of 9 tiles \n"
          + "Medium:-Consist of 16 tiles \n"
          + "advance:-Consist of 25 tiles";
          
 int choice=JOptionPane.showOptionDialog(mainframe,l,"picpuzzle level",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,option,option[level_no1-3]);
    
   if(choice==JOptionPane.YES_OPTION) 
   {
  
              level_no1=3;
   }  
    
   if(choice==JOptionPane.NO_OPTION) 
   {
         
        
       level_no1=4;
   }   
    
   if(choice==JOptionPane.CANCEL_OPTION) 
   {
      
      level_no1=5;
   }   
   
    
    
 }

void timage(picpuzzle mainframe){

JButton picture=new JButton(new ImageIcon("C:\\Users\\dell\\Desktop\\images"+ level_no1+""+no+".jpg"));
  
         picture.setBounds(0,0,900,900);
        
      mainframe.add(picture);
         mainframe.setVisible(true);
         
     
         
         
         JOptionPane.showMessageDialog(mainframe,"    Prsss OK to start the picpuzzle",null,JOptionPane.PLAIN_MESSAGE);
        
     
              
     
               mainframe.remove(picture);
                  mainframe.setVisible(true); 
 
   }





 void createtiles(picpuzzle mainframe) throws IOException{
            
    mainframe.setLayout(null);
    
           t=new JButton[level_no1][level_no1];
  
int a,b;

int ff=900/level_no1;

int y=0;
for(a=0;a<level_no1;a++)
{
    int x=0;
    for(b=0;b<level_no1;b++)
    {
   t[a][b]=new JButton();
   t[a][b].addActionListener(this);
   t[a][b].setBounds(x,y,ff,ff);
   mainframe.add(t[a][b]);
   x+=ff;
    }
  y+=ff;  
}

displayimages(mainframe);
       
     
 }
 
 

 void displayimages(picpuzzle mainframe ) throws IOException
 {
    

      

   imagepath= ImageSplitTest.cropimage(level_no1,no);
   
   int size=imagepath.length;

   String randomimages[]=new String[size];
   int n=0;
   while(n<size-1)
   {
       int z;
     
      z=(int)((size-1)*Math.random());
      
      if(n==0)
       {
        randomimages[n]=imagepath[z];
       
        n++;
       }
     else
      {
        boolean flag=true;  
       for(int f=0;f<n;f++)
        {
           if(randomimages[f].compareTo(imagepath[z])==0)
            {
             flag=false;
             break;
            } 
        } 
       if(flag)
       {
               randomimages[n]=imagepath[z];
              
                 n++;
       }
            
        }
 
      }
   cl=new ImageIcon[level_no1*level_no1];
     randomimages[size-1]=cfix;
  
   int c=0;
   for(int a=0;a<level_no1;a++)
{
   
    for( int b=0;b<level_no1;b++)
    {
   t[a][b].setIcon(cl[c]=new ImageIcon(randomimages[c]));
 c++;
}
}
 

   
 
}
 
 
 
 

 
public static void main(String abc[]) throws IOException 
{
   
picpuzzle mainframe=new picpuzzle();
mainframe.menubarcreator(mainframe);
mainframe.setVisible(true);

Thread time=new Thread(mainframe);
 
time.setDaemon(true); 

mainframe.player=JOptionPane.showInputDialog(mainframe,"enter your name", "user name", JOptionPane.PLAIN_MESSAGE);
if(("".equals(mainframe.player))||(mainframe.player==null))
{
   mainframe.player="player1";
}
mainframe.createstatusbar(mainframe);
mainframe.setVisible(true);
mainframe.timage(mainframe);
    time.setName("sdf");            
mainframe.createtiles(mainframe);
time.start();
}


 
    
    public void actionPerformed(ActionEvent ae) {
        int a,b,check=0;
        for(a=0;a<level_no1;a++)
        {
         for(b=0;b<level_no1;b++)
             
         {
              if(ae.getSource()==t[a][b])
              {
                 Icon cc=t[a][b].getIcon();
                 
                  int ga1=a-1; 
                  int ga2=a+1;
                   int gb1=b-1;
                    int gb2=b+1;
                  
              if(ga1>=0&&(t[ga1][b].getIcon()==cl[level_no1*level_no1-1])) 
              {
              
                   Icon dd=t[ga1][b].getIcon();
               t[a][b].setIcon(dd);
               t[ga1][b].setIcon(cc);
               
          moves++;
           movesstatus.setText(""+moves+"");    
                   
              
             }
              
              else if(ga2<=level_no1-1&&(t[ga2][b].getIcon()==cl[level_no1*level_no1-1])) 
              {
               
                       
                   Icon dd=t[ga2][b].getIcon();
               t[a][b].setIcon(dd);
               t[ga2][b].setIcon(cc);
             
          moves++;
           movesstatus.setText(""+moves+"");    
                   
              
             
              }
              
           else if(gb1>=0&&(t[a][gb1].getIcon()==cl[level_no1*level_no1-1])) 
              {
              
                   Icon dd=t[a][gb1].getIcon();
               t[a][b].setIcon(dd);
               t[a][gb1].setIcon(cc);
               
          moves++;
           movesstatus.setText(""+moves+"");    
                   
              
             }   
          
            else if(gb2<=level_no1-1&&(t[a][gb2].getIcon()==cl[level_no1*level_no1-1])) 
              {
               
                   Icon dd=t[a][gb2].getIcon();
               t[a][b].setIcon(dd);
               t[a][gb2].setIcon(cc);
                
          moves++;
           movesstatus.setText(""+moves+"");    
                   
              
             }
         
           
for(int s=0;s<level_no1;s++)
        {
         for(int t2=0;t2<level_no1;t2++)
             
         {
            
   if(t[s][t2].getIcon().toString().equals(imagepath[check]))
     check++;
     else
         break;
         }
         
        } 

if(check==level_no1*level_no1-1)
{
    Object[] option={"play again","exit"};
    

   

int choice=JOptionPane.showOptionDialog(this,"cogratulation "+player+" won the game","Game won",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,option,option[0]);


if(choice==JOptionPane.YES_OPTION) 
   {
       playerlist.maker(player, moves, level_no1,this);
       no++;
 moves=0;
        try {
            reset(this,level_no1) ;
            movesstatus.setText("");
            moves=0;
            timage(this);
            createtiles(this);
        } catch (IOException ex) {
            Logger.getLogger(picpuzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
   }  
    
   else 
   {
         
      System.exit(0);
   } 

        
    }
  }
 }
        }
    
}
 }
 

 
class playerlist {
    public static void maker(String name,int mv,int tb,picpuzzle frame) {
          try
      {
       
          Connection databaseconnection=DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\dell\\Documents\\access\\playerlist.accdb");
          Statement statement=databaseconnection.createStatement();
          
         
          ResultSet player_name=statement.executeQuery("SELECT * FROM level_"+tb+" WHERE playername='"+name+"'");
         
          if(player_name.next())
          {
        if((player_name.getInt("moves"))>mv)
        {
           statement.executeUpdate("UPDATE level_"+tb+" SET moves="+mv+" WHERE playername='"+name+"'");  
        
        }
          }
                
         
          else
          {
           statement.executeUpdate("INSERT INTO level_"+tb+""+"(playername,moves)"+ "VALUES('"+name+"',"+mv+")");   
          }
          
         
          table(tb,frame);     

         
          
      }
      catch(SQLException cnfe)
              {
                  System.err.println(cnfe);   
              }
    }
    
    static void table(int tb,picpuzzle frame) throws SQLException
    {
        
    Connection databaseconnection=DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\dell\\Documents\\access\\playerlist.accdb");
          Statement statement=databaseconnection.createStatement();   
      ResultSet player_name=statement.executeQuery("SELECT * FROM level_"+tb+"");  
        
        
       String name; 
       int   move;
      int rows=0;      	    	
    	while(player_name.next())
    	{
    	 
    	  
    	   move = player_name.getInt(2);
    	name= player_name.getString(1);
    	  	
    	  rows++;
    	
    	} 
        
            		
				Object data1[][]=new Object[rows][2];
				
				Object[] Colheads={"name","moves"};
				 player_name=statement.executeQuery("SELECT * FROM level_"+tb+"");
				
				for(int i1=0;i1<rows;i1++)
				{
						 player_name.next();
						for(int j1=0;j1<2;j1++)
						{
							data1[i1][j1]= player_name.getString(j1+1);
						}
				}
				JTable table=new JTable(data1,Colheads);
				int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
				int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
				
				JScrollPane jsp=new JScrollPane(table,v,h);
				
	JOptionPane.showMessageDialog(frame,jsp,"list of players",JOptionPane.PLAIN_MESSAGE,null);
        	
			
    		
    
        
    }   
    }
    

    
