/** This program consists of a block in a JFrame, along with two buttons, 
 *  one intended to start the movement of the block, and the other intended to
 *  stop it. When the start button is pressed, the block moves to the right until
 *  it touches the rightmost edge of the frame, and then reveses directions, 
 *  meaning it oscillates back and forth until the user clicks on the stop button.
 *  @author Charles Doan
 *  Completion Date: 12-23-2023
 */
/** Import Statements */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class BouncingBlockMain {
    public static final int FRAME_WIDTH = 700;
    public static final int FRAME_HEIGHT = 300;
    public static void main(String[] args) {
        JFrame frame = new JFrame(); 
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Bouncing Block");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BouncingBlockComponent component = new BouncingBlockComponent();
        frame.add(component);
        component.addButton(frame);
        frame.setVisible(true);
    }
}

/** This is the JComponent class that holds the features of the graphic, 
 *  the block, its listeners, and the buttons that turn its movements
 *  on or off.
 */
class BouncingBlockComponent extends JComponent {
    public static final int INCREMENT = 10;
    public static final int DELAY = 10;
    public static final int X_POS = 10;
    public static final int Y_POS = 2; 
    private Block block;
    private Timer t;
    /**
     * The constructor, which creates the block
     */
    public BouncingBlockComponent() {
        block = new Block(BouncingBlockMain.FRAME_WIDTH, 
                          BouncingBlockMain.FRAME_HEIGHT); 
         
        motionMover();
    }

    /** Paints the component's features onto the frame
     *  @param Graphics g the visual "paintbrush" that draws the block
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        block.draw(g2); 
    }

    /** Creates the start/stop buttons, adds ActionListeners to
     *  them, and adds the buttons to the frame. The buttons 
     *  start/stop the Timer that animates the movement of the block
     *  @param JFrame frame the JFrame
     */ 
    public void addButton(JFrame frame) {
        //Adding the button to start the movement of the block
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.start();
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                t.stop();
            }
        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(startButton);
        btnPanel.add(stopButton);
        frame.add(btnPanel, BorderLayout.SOUTH);
    }

    /** Creates the TimerListener, which is responsible for animating the 
     *  block and reversing its movement if it determines that it has touched
     *  or moved beyond the borders of the frame.
     */ 
    public void motionMover() {
        class TimerListener implements ActionListener {
            private Block b;
            private boolean movingRight = true;
            public TimerListener(Block bl) {
                b = bl;
            }

            @Override
            public void actionPerformed(ActionEvent event) {
                if (movingRight) {
                    b.move(INCREMENT);
                    if (b.reachedEnd()) movingRight = false;
                } else {
                    b.move(INCREMENT * -1);
                    if (b.reachedBeginning()) movingRight = true;
                }
            }
        }
        ActionListener timeListen = new TimerListener(block);
        t = new Timer(DELAY, timeListen); 
    }

    /** This class represents the block entity and manipulates its
     *  position.
     */  
    class Block { 
        public static final int REC_WIDTH = 4;
        public static final int REC_HEIGHT = 3;   
        public static double heightOffset;     
        private double width;
        private double height;    
        public double x;
        private double y;
        private Rectangle2D.Double rec;
        public Block(double frameWidth, double frameHeight) {
            width = frameWidth / REC_WIDTH; 
            height = frameHeight / REC_HEIGHT;
            heightOffset = height / 2;
            x = frameWidth / X_POS;
            y = frameHeight / Y_POS - heightOffset;
            rec = new Rectangle2D.Double(x, y, width, height);
        }

        /**
         * @return the x position of the Block
         */
        public double getX() {
            return x;
        }

        /**
         * @return the y position of the Block (constant)
         */
        public double getY() {
            return y;
        }

        /** Changes the x position of the block by increment dx.
         * 
         */
        public void move(double dx) {
            x += dx;
            rec = new Rectangle2D.Double(x, y, width, height);
            repaint();
        }

        /** Determines if the block has touched the rightmost edge of the 
         *  frame.
         *  @return if the block has touched the rightmost edge of the frame.
         */
        public boolean reachedEnd() {
            if ((x + width) >= getWidth())
                return true;
            else return false;
        }

        /** Determines if the block has touched the leftmost edge of the 
         *  frame.
         *  @return if the block has touched the leftmost edge of the frame.
         */
        public boolean reachedBeginning() {
            if ((x) <= 0)
                return true;
            else return false;
        }

        /** Draws the block with a black color
         */
        public void draw(Graphics2D g) {
            g.setColor(Color.BLACK); 
            g.fill(rec);
        }
    }
}