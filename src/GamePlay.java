import java.awt.*;
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

    // Ball speed
    private int ballXDir = -2;

    private int ballYDir = -3;


    private MapGenerator mapObj;


    public GamePlay() {
        mapObj = new MapGenerator(3,7);
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

        // Map class for building bricks
        mapObj.draw((Graphics2D)graphic);

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

        // Paddle
        graphic.setColor(Color.DARK_GRAY);
        graphic.fillRect(playerX, 550, 100, 8);

        // Ball
        graphic.setColor(Color.GREEN);
        graphic.fillOval(ballposX, ballposY, 20, 20);

        // Font
        graphic.setColor(Color.BLACK);
        graphic.setFont(new Font("serif", Font.BOLD, 25));
        graphic.drawString("" + score, 590, 30);

        // Destroyed all bricks
        if(totalBricks <= 0){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            graphic.setColor(Color.green);
            graphic.setFont(new Font("serif", Font.BOLD, 30));
            graphic.drawString("Winner, Score: " + score, 190, 300);

            graphic.setFont(new Font("serif", Font.BOLD, 20));
            graphic.drawString("Press Enter to Restart!", 230, 350);
        }

        // Missed Paddle
        if(ballposY > 570){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            graphic.setColor(Color.red);
            graphic.setFont(new Font("serif", Font.BOLD, 30));
            graphic.drawString("Pfffft. Butter Fingers! Score: " + score, 190, 300);

            graphic.setFont(new Font("serif", Font.BOLD, 20));
            graphic.drawString("Press Enter to Restart!", 230, 350);

        }

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

            // Brick interaction
            for(int i = 0; i<mapObj.map.length; i++){
                for(int j=0; j < mapObj.map[0].length; j++){
                    if(mapObj.map[i][j] > 0){
                        int brickX = j*mapObj.brickWidth + 80;
                        int brickY = i*mapObj.brickHeight + 50;
                        int brickWidth = mapObj.brickWidth;
                        int brickHeight = mapObj.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            mapObj.setBrickValue(0, i, j);
                            totalBricks--;
                            score+=5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXDir = -ballXDir;

                            }
                            else{
                                ballYDir = -ballYDir;
                            }
                        }
                    }
                }
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

        if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXDir = -2;
                ballYDir = -3;
                score = 0;
                totalBricks = 21;
                mapObj = new MapGenerator(3,7);
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
