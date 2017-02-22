//T Harvey
//based loosely on http://www.java2s.com/Code/JavaAPI/java.awt/GraphicsdrawImageImageimgintxintyImageObserverob.htm
 

// Ryan Gray, Vincent Mangubat

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Animation extends JPanel {

    final int frameCount = 10;
    int picNum = 0;
    BufferedImage[] pics;
    ArrayList<BufferedImage[]> picsOrc;
    int xloc = 0;
    int yloc = 0;
    final int xIncr = 8;
    final int yIncr = 2;
    final static int frameWidth = 500;
    final static int frameHeight = 300;
    final static int imgWidth = 165;
    final static int imgHeight = 165;
	int NE = 0;
	int SE = 1;
	int SW = 0;
	int NW = 0;


    //Override this JPanel's paint method to cycle through picture array and draw images
    public void paint(Graphics g) {
    	picNum = (picNum + 1) % frameCount;
    	
		if (SE == 1 && (yloc + imgHeight > frameHeight)) {
			SE = 0;
			NE = 1;
		} else if (SW == 1 && (yloc + imgHeight > frameHeight)) {
			SW = 0;
			NW = 1;
		} else if (NE == 1 && (yloc < 0)) {
			NE = 0;
			SE = 1;
		} else if (NW == 1 && (yloc < 0)) {
			NW = 0;
			NE = 1;
		} else if (SE == 1 && (xloc + imgWidth > frameWidth)) {
			SE = 0;
			SW = 1;
		} else if (SW == 1 && (xloc < 0)) {
			SW = 0;
			SE = 1;
		} else if (NE == 1 && (xloc + imgWidth > frameWidth)) {
			NE = 0;
			NW = 1;
		} else if (NW == 1 && (xloc < 0)) {
			NW = 0;
			NE = 1;
		}
		if (SE == 1) {
			g.drawImage(picsOrc.get(0)[picNum], xloc+=xIncr, yloc+=yIncr, Color.gray, this);
		} else if (SW == 1) {
			g.drawImage(picsOrc.get(1)[picNum], xloc-=xIncr, yloc+=yIncr, Color.gray, this);
		} else if (NE == 1) {
			g.drawImage(picsOrc.get(2)[picNum], xloc+=xIncr, yloc-=yIncr, Color.gray, this);
		} else if (NW == 1) {
			g.drawImage(picsOrc.get(3)[picNum], xloc-=xIncr, yloc-=yIncr, Color.gray, this);
		}
    	
    	
    	// TODO: Keep the orc from walking off-screen, turn around when bouncing off walls.
		//Be sure that animation picture direction matches what is happening on screen.
    }

    //Make frame, loop on repaint and wait
    public static void main(String[] args) {
    	JFrame frame = new JFrame();
    	frame.getContentPane().add(new Animation());
    	frame.setBackground(Color.gray);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameWidth, frameHeight);
    	frame.setVisible(true);
    	for(int i = 0; i < 1000; i++){
    		frame.repaint();
    		try {
    			Thread.sleep(100);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    }

    //Constructor: get image, segment and store in array
    public Animation(){
    	BufferedImage[] imgs = createImage();
    	picsOrc = new ArrayList<>(8);
    	for (BufferedImage img : imgs) {
    		pics = new BufferedImage[10];
    		for(int i = 0; i < frameCount; i++) {
    			pics[i] = img.getSubimage(imgWidth*i, 0, imgWidth, imgHeight);
    		}
    		picsOrc.add(pics);
    	}
    	
    	
    	
    	// TODO: Change this constructor so that at least eight orc animation pngs are loaded
    }  
    
    //Read image from file and return
    private BufferedImage[] createImage(){
    	BufferedImage[] bufferedImage = new BufferedImage[4];
    	try {
    		bufferedImage[0] = ImageIO.read(new File("orc_animation/orc_forward_southeast.png"));
    		bufferedImage[1] = ImageIO.read(new File("orc_animation/orc_forward_southwest.png"));
    		bufferedImage[2] = ImageIO.read(new File("orc_animation/orc_forward_northeast.png"));
    		bufferedImage[3] = ImageIO.read(new File("orc_animation/orc_forward_northwest.png"));
    		return bufferedImage;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    	
    	// TODO: Change this method so you can load other orc animation bitmaps
    }
}