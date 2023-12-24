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

/**
 * 
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
    /**
     * 
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        block.draw(g2); 
    }

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
        t = new Timer(DELAY, timeListen); System.out.println("Timer is created");
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
         * 
         */
        public double getX() {
            return x;
        }

        /**
         * 
         */
        public double getY() {
            return y;
        }

        /**
         * 
         */
        public void move(double dx) {
            x += dx;
            rec = new Rectangle2D.Double(x, y, width, height);
            repaint();
        }

        /**
         * 
         */
        public boolean reachedEnd() {
            if ((x + width) >= getWidth())
                return true;
            else return false;
        }

        /**
         * 
         */
        public boolean reachedBeginning() {
            if ((x) <= 0)
                return true;
            else return false;
        }

        /**
         * 
         */
        public void draw(Graphics2D g) {
            g.setColor(Color.BLACK); 
            g.fill(rec);
        }
    }
}