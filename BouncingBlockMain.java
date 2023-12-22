/** Import Statements */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class BouncingBlockMain {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 300;
    public static void main(String[] args) {
        /*
         * 1. Create a frame
         * 2. Create a component
         * 3. Create a block object class so that the block can move
         * 4. Draw the block first
         */
        JFrame frame = new JFrame(); 
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("BouncingBlock");
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
    public static final int INCREMENT = 20;
    public static final int DELAY = 10;
    public static final int X_POS = 10;
    public static final int Y_POS = 2; 
    private Block block;
    private Timer t;
    /**
     * The constructor, which creates the block
     */
    public BouncingBlockComponent() {
        block = new Block(); System.out.println("Construct a blcok");
    }

    @Override
    public void paintComponent(Graphics g) {System.out.println("Painted");
        Graphics2D g2 = (Graphics2D) g;
        //Adding the block
        block.draw(g2);
    }
    /**
     * 
     */
    public void addButton(JFrame frame) {
        //Adding the button to start the movement of the block
        JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Hey");
                motionMover();
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(button);
        frame.add(btnPanel, BorderLayout.SOUTH);
    }

    public void motionMover() {
        class TimerListener implements ActionListener {
            private Block b;
            public TimerListener(Block bl) { System.out.println("TimerListened");
                b = bl;
            }

            @Override
            public void actionPerformed(ActionEvent event) {
                b.move(INCREMENT); 
            }
        }
        ActionListener timeListen = new TimerListener(block);
        t = new Timer(DELAY, timeListen);
        t.start(); System.out.println("Timer started");
        
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
        private double x;
        private double y;
        private Rectangle2D.Double rec;
        public Block() {
            width = getWidth() / REC_WIDTH; //System.out.println("Block constructor");
            height = getHeight() / REC_HEIGHT;
            heightOffset = height / 2;
            x = getWidth() / X_POS;
            y = getHeight() / Y_POS - heightOffset;
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
            x += dx; System.out.println("supoposed to move");
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
        public void draw(Graphics2D g) {
            g.setColor(Color.BLACK); 
            g.fill(rec);System.out.println("Draw");
        }
    }
}