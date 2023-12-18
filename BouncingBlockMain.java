/** Import Statements */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class BouncingBlockMain {
    public static final int WIDTH = 500;
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
        frame.setVisible(true);
    }
}

class BouncingBlockComponent extends JComponent {
    public static final int X_POS = 10;
    public static final int Y_POS = 2;   
    public static int heightOffset;
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //Adding the block
        int recWidth = getWidth() / 3;
        int recHeight = getHeight() / 3;
        heightOffset = recHeight / 2;
        int xCoord = getWidth() / X_POS;
        int yCoord = getHeight() / Y_POS - heightOffset;
        Block block = new Block(xCoord, yCoord, recWidth, recHeight);
        block.draw(g2);

        //Adding the button to start the movement of the block
        JButton button = new JButton("Start");
         ActionListener bListen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hey");
            }
        };/**/
        button.addActionListener(bListen);
        //Add te button to the component
    }

    /** This class represents the block entity and manipulates its
     *  position.
     */  
    class Block {           
        private double x;
        private double y;
        private double width;
        private double height;
        private Rectangle2D.Double rec;
        public Block(double x, double y, double w, double h) {
            this.x = x;
            this.y = y;
            width = w;
            height = h;
            rec = new Rectangle2D.Double(x, y, w, h);
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void move(double dx) {
            x += dx;
            repaint();
        }

        public boolean reachedEnd() {
            if ((x + width) >= getWidth())
                return true;
            else return false;
        }

        public void draw(Graphics2D g) {
            g.setColor(Color.BLACK);
            g.fill(rec);
        }
    }
}