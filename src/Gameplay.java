import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];

    private boolean left = false, right = false, up = false, down = false;

    private ImageIcon rightMouth, leftMouth, upMouth, downMouth;

    private int lengthOfSnake = 3;

    private int [] enemyXPos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int [] enemyYPos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    private ImageIcon enemyImage;

    private Random random = new Random();
    private int xPos = random.nextInt(34); //34 = total number of elements in the array enemyXPos
    private int yPos = random.nextInt(23);

    private int score = 0;

    private int moves = 0;

    private Timer timer;
    private int delay = 100;

    private ImageIcon snakeImage;

    private ImageIcon titleImage;

    public Gameplay()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics)
    {
        if(moves == 0)
        {
            snakeXLength[2] = 50;
            snakeXLength[1] = 75;
            snakeXLength[0] = 100;

            snakeYLength[2] = 100;
            snakeYLength[1] = 100;
            snakeYLength[0] = 100;
        }

        //draw title image border
        graphics.setColor(Color.white);
        graphics.drawRect(24, 10, 851, 55);

        //draw the title image
        titleImage = new ImageIcon("snaketitle.PNG");
        titleImage.paintIcon(this, graphics, 25, 11);

        //draw border for gameplay
        graphics.setColor(Color.cyan);
        graphics.drawRect(24, 74, 851, 577);

        //draw background for gameplay
        graphics.setColor(Color.green);
        graphics.fillRect(25, 75, 850, 575);

        //draw scores
        graphics.setColor(Color.white);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Scores: " + score, 780, 30);

        //draw length of snake
        graphics.setColor(Color.white);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Length: " + lengthOfSnake, 780, 50);

        rightMouth = new ImageIcon("rightmouth.png");
        rightMouth.paintIcon(this, graphics, snakeXLength[0], snakeYLength[0]);

        for(int i = 0; i < lengthOfSnake; i++)
        {
            if(i == 0 && right)
            {
                rightMouth = new ImageIcon("rightmouth.png");
                rightMouth.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && left)
            {
                leftMouth = new ImageIcon("leftmouth.png");
                leftMouth.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && up)
            {
                upMouth = new ImageIcon("upmouth.png");
                upMouth.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && down)
            {
                downMouth = new ImageIcon("downmouth.png");
                downMouth.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i != 0)
            {
                snakeImage = new ImageIcon("snakeimage.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
        }

        enemyImage = new ImageIcon("enemy.png");

        if((enemyXPos[xPos] == snakeXLength[0]) && (enemyYPos[yPos] == snakeYLength[0]))
        {
            score++;
            lengthOfSnake++;
            xPos = random.nextInt(34);
            yPos = random.nextInt(23);
        }

        enemyImage.paintIcon(this, graphics, enemyXPos[xPos], enemyYPos[yPos]);

        //if the snake collide with itself then game over
        for(int k = 1; k < lengthOfSnake; k++)
        {
            if((snakeXLength[k] == snakeXLength[0]) && (snakeYLength[k] == snakeYLength[0]))
            {
                right = false; left = false; up = false; down = false;

                graphics.setColor(Color.white);
                graphics.setFont(new Font("arial", Font.BOLD, 50));
                graphics.drawString("Game Over!", 300, 300);

                graphics.setFont(new Font("arial", Font.BOLD, 20));
                graphics.drawString("Press Space to RESTART", 350, 340);

            }
        }

        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        timer.start();
        if(right)
        {
            for(int j = lengthOfSnake; j >= 0; j--)
            {
                snakeYLength[j+1] = snakeYLength[j];
            }
            for(int j = lengthOfSnake; j >=0; j--)
            {
                if(j == 0)
                {
                    snakeXLength[j] = snakeXLength[j] + 25;
                }
                else
                {
                    snakeXLength[j] = snakeXLength[j-1];
                }
                if(snakeXLength[j] > 850) //if snake touches the the right border then it should come from left side.
                {
                    snakeXLength[j] = 25;
                }
            }
            repaint();
        }

        if(left)
        {
            for(int j = lengthOfSnake; j >= 0; j--)
            {
                snakeYLength[j+1] = snakeYLength[j];
            }
            for(int j = lengthOfSnake; j >=0; j--)
            {
                if(j == 0)
                {
                    snakeXLength[j] = snakeXLength[j] - 25;
                }
                else
                {
                    snakeXLength[j] = snakeXLength[j-1];
                }
                if(snakeXLength[j] < 25) //if snake touches the the right border then it should come from left side.
                {
                    snakeXLength[j] = 850;
                }
            }
            repaint();
        }

        if(up)
        {
            for(int j = lengthOfSnake; j >= 0; j--)
            {
                snakeXLength[j+1] = snakeXLength[j];
            }
            for(int j = lengthOfSnake; j >=0; j--)
            {
                if(j == 0)
                {
                    snakeYLength[j] = snakeYLength[j] + 25;
                }
                else
                {
                    snakeYLength[j] = snakeYLength[j-1];
                }
                if(snakeYLength[j] > 625) //if snake touches the the right border then it should come from left side.
                {
                    snakeYLength[j] = 75;
                }
            }
            repaint();
        }

        if(down)
        {
            for(int j = lengthOfSnake; j >= 0; j--)
            {
                snakeXLength[j+1] = snakeXLength[j];
            }
            for(int j = lengthOfSnake; j >=0; j--)
            {
                if(j == 0)
                {
                    snakeYLength[j] = snakeYLength[j] - 25;
                }
                else
                {
                    snakeYLength[j] = snakeYLength[j-1];
                }
                if(snakeYLength[j] < 75) //if snake touches the the right border then it should come from left side.
                {
                    snakeYLength[j] = 625;
                }
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            moves =0;
            score = 0;
            lengthOfSnake = 3;
            repaint();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            moves++;
            right = true;
            if(! left)
            {
                right = true;
            }
            else
            {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            moves++;
            left = true;
            if(! right)
            {
                left = true;
            }
            else
            {
                left = false;
                right = true;
            }
            up = false;
            down = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            moves++;
            up = true;
            if(! down)
            {
                up = true;
            }
            else
            {
                up = false;
                down = true;
            }
            right = false;
            left = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            moves++;
            down = true;
            if(! up)
            {
                down = true;
            }
            else
            {
                up = true;
                down = false;
            }
            right = false;
            left = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}