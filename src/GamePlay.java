import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePlay extends JPanel implements KeyListener, ActionListener {


    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;

    private int delay = 8;

    private int playerX = 310;

    private int ballposX = 12;

    private int ballposY = 350;

    private int ballXDir = -1;

    private int ballYDir = -2;


    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        setPreferredSize(new Dimension(700, 600));



    }


    @Override
    public void paint(Graphics graphic) {
        super.paint(graphic);

        // Debug
        //System.out.println("Painting");
        //background
        graphic.setColor(Color.white);
        graphic.fillRect(1,1,692, 592);

        // border
        graphic.setColor(Color.yellow);
        graphic.fillRect(0, 0, 3, 592);       // Left border
        //System.out.println("left border");
        graphic.fillRect(0, 0, 692, 3);       // Top border
        //System.out.println("top border");
        graphic.fillRect(691, 0, 3, 592);     // Right border
        //System.out.println("right border");
        graphic.fillRect(0, 591, 692, 3);     // Bottom border
        //System.out.println("bottom border");


        // paddle
        graphic.setColor(Color.DARK_GRAY);
        graphic.fillRect(playerX, 550, 100, 8);

        // ball
        graphic.setColor(Color.GREEN);
        graphic.fillOval(ballposX, ballposY, 20, 20);

        // Swing handles disposal automatically so commenting this out for now
        // Dispose to release resources once the game is closed.
        //graphic.dispose();
    }



    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        timer.start();

        if(play) {

            // Ball - paddle interaction
            if(new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }
            ballposX += ballXDir;
            ballposY += ballYDir;
            if(ballposX < 0) {
                ballXDir = -ballXDir;
            }
            if(ballposY < 0) {
                ballYDir = -ballYDir;
            }
            if(ballposX > 670) {
                ballXDir = -ballXDir;
            }

        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            }
            else {
                moveRight();
            }
        }
        if(arg0.getKeyCode() == KeyEvent.VK_LEFT) {

            if(playerX < 10) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }
    }

    // key press functionality
    public void moveRight() {
        play = true;
        playerX += 20;

    }

    public void moveLeft() {
        play = true;
        playerX -= 20;

    }



    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }





}
