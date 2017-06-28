package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Pawel on 16.06.2017.
 */
public class Gamplay extends JPanel implements KeyListener, ActionListener{
    private boolean play=false;

    private int score = 0;
    private int totalScore=score;

    private  int row=1;
    private  int col=2;
    private final int rowStart=row;
    private final int colStart=col;
    private int totalBricks = row*col;
    private final int tot=totalBricks;

    private int lvl=1;

    private Timer timer;
    private int delay = 3;

    private int playerX = 300;

    private int ballposX = playerX+43;
    private int ballposY = 535;
    private int ballXdir=-1;
    private int ballYdir=-2;

    private MapGenerator map;


    /*
    start gry
     */
    public Gamplay(){
        map = new MapGenerator(row,col);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();

    }

/*
rysowanie czego kolwiek
 */
    public void paint(Graphics g){
        //background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,1,592);
        g.fillRect(0,0,692,1);
        g.fillRect(691,0,1,592);

        // score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);


        //the paddle
        g.setColor(Color.white);
        g.fillRect(playerX,550,100,8);

        //the ball
        g.setColor(Color.red);
        g.fillOval(ballposX,ballposY,15,15);

       // jezeli wygrana
        if (totalBricks==0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("YOU WON Scores:" + totalScore,210,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Pres SPACE to next level",250,340);

            g.setFont(new Font("serif",Font.BOLD,15));
            g.drawString("You win "+lvl+" level",300,360);

        }

        // jezeli przegrana
        if (ballposY>570 && totalBricks>0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over Scores:" + totalScore,210,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Pres ENTER to Restart",250,340);

            g.setFont(new Font("serif",Font.BOLD,15));
            g.drawString("You died on " + lvl + " level",287,360);
        }
        // jezeli pauza
        if ( score>0 && play==false && ballposY<570 && totalBricks>0){
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game is PAUSED" ,250,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Pres Left or Right to Play",255,340);

        }

        g.dispose();
    }

/*
ta klasa odpowiada za accje w programie
 */
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play){
            // interacja z obiektami tzn z graczem
            if (new Rectangle(ballposX,ballposY,15,15)
                    .intersects(new Rectangle(playerX,550,95,8))){
                ballYdir = -ballYdir;
            }

            /*
             map.map -> pierwsza map to private MapGenerator map
             druga map to mapa z klasy map generator tablica
             to jest interakcja z klockami
              */
            A:for (int i=0;i<map.map.length;i++){
                for (int j=0;j<map.map[0].length;j++){
                    if (map.map[i][j]>0){
                        int brickX =j*map.brickWidth+50;
                        int brickY = i*map.brickHeight +50;
                        int brickWidth = map.brickWidth;
                        int brickHeight=map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;
                        // zderzenie pileczki z klockiem
                        if (ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score +=5;
                            totalScore+=5;

                            if(ballposX+19 <=brickRect.x || ballposX+1 >= brickRect.x+brickRect.width){
                                ballXdir=-ballXdir;
                            } else {
                                ballYdir=-ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }


            ballposX+=ballXdir;
            ballposY+=ballYdir;
            // odbijanie sie od scian
            if (ballposX<0){        //left
                ballXdir=-ballXdir;
            }
             if (ballposY<0){       //top
                ballYdir=-ballYdir;
            }
             if (ballposX>670){       //right
                ballXdir=-ballXdir;
            }

        }
        repaint();   // to powoduje ze cala clasa paint powtarza sie, i na nowo rysuje, dzieki temu mamy ruch

    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}


    /*
    odpowiada za wydarzenie wcisnięcia klawisza
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D){
            if (playerX>=600){
                playerX=600;
            } else {
                moveRight();
            }

        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A){
            if (playerX<=0){ //było 10
                playerX=0;
            } else {
                moveLeft();
            }

        }
        // akcja na klikniecie spacji po wygranej
        if (e.getKeyCode()==KeyEvent.VK_SPACE && totalBricks==0  ){
            if (!play){
                play=true;
                playerX=300;
                ballposX=playerX;
                ballposY=535;
                ballXdir=-1;
                ballYdir=-2;
                totalScore+=score;
                row+=2;
                col+=2;
                totalBricks=row*col;

                score=0;
                lvl++;

                map = new MapGenerator(row,col);

                repaint();


            }
        }
        // akcja na klikniecie entera po smierci
        if (e.getKeyCode()==KeyEvent.VK_ENTER && ballposY>570){
            if (!play){
                play=true;
                playerX=300;
                ballposX=playerX;
                ballposY=535;
                ballXdir=-1;
                ballYdir=-2;

                row=rowStart;
                col=colStart;
                score=0;
                totalScore=score;
                lvl=1;
                totalBricks=tot;
                map = new MapGenerator(row,col);
                repaint();
            }
        }
        //pauza
        if (e.getKeyCode()==KeyEvent.VK_P){ play=false;}

        //wyjscie
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE){System.exit(0);}

    }

    // poruszanie sie
public void moveRight(){
        play=true;
        playerX+=75;
}
public void moveLeft(){
        play=true;
        playerX-=75;
}


}
