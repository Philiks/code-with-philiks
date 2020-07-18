import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BouncingBall extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final int WIDTH  	= 1000,
					  HEIGHT 	= WIDTH * 9 / 16,
					  DIAMETER 	= 100;
	
	private int myX = WIDTH  / 2 - DIAMETER / 2, // CENTER X 
				myY = HEIGHT / 2 - DIAMETER / 2, // CENTER Y
				xDir = 1, yDir = 1; 
	
	private Color myColor = Color.WHITE;
	
	private BouncingBall() {
		JFrame frame = new JFrame("Bouncing Ball");
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		frame.setContentPane(this);
		frame.revalidate();
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		int delay = 10;
		Timer timer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myX == 0) {
					xDir = 1;
					changeColor();
				} else if(myX + DIAMETER == WIDTH) {
					xDir = -1;
					changeColor();
				}
				
				if(myY == 0) {
					yDir = 1;
					changeColor();
				} else if(myY + DIAMETER == HEIGHT) {
					yDir = -1;
					changeColor();
				}
				
				myX = myX + xDir;
				myY = myY + yDir;
								
				repaint();
			}
		});
		
		timer.start();
	}
	
	private void changeColor() {
		Random rand = new Random();
		myColor = new Color(rand.nextInt(255), // RED 
							rand.nextInt(255), // GREEN
							rand.nextInt(255));// BLUE
		
		// avoids color black since background is already black
		if(myColor.getRed()   == 0 &&
		   myColor.getGreen() == 0 && 
		   myColor.getBlue()  == 0){
			changeColor();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(myColor);
		g.drawOval(myX, myY, DIAMETER, DIAMETER);
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new BouncingBall();
			}
		});
	}
}
