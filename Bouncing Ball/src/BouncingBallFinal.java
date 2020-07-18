import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BouncingBallFinal extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final int WIDTH  	= 1000,
					  HEIGHT 	= WIDTH * 9 / 16,
					  DIAMETER 	= 100;
	
	private int myX = WIDTH  / 2 - DIAMETER / 2, // CENTER X 
				myY = HEIGHT / 2 - DIAMETER / 2, // CENTER Y
				xDir = 1, yDir = 1; 
	private boolean showSubscribe = false;
	private int showSubsCtr;
	
	private Color myColor = Color.WHITE;
	
	private BouncingBallFinal() {
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
		
		int delay = 15;
		Timer timer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int oldXDir = xDir;
				int oldYDir = yDir;
				
				if(myX == 0)
					xDir = 1;
				else if(myX + DIAMETER == WIDTH)
					xDir = -1;
				
				if(myY == 0)
					yDir = 1; 
				else if(myY + DIAMETER == HEIGHT)
					yDir = -1;
				
				if(xDir != oldXDir || yDir != oldYDir)
					changeColor();
				
				myX = myX + xDir;
				myY = myY + yDir;
				
				if(!(showSubscribe && --showSubsCtr > 0))
					showSubscribe = false;
				
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
		showSubscribe = true;
		showSubsCtr = 100;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(myColor.darker());
		g2d.fillOval(myX, myY, DIAMETER, DIAMETER);
		
		Stroke oldStroke = g2d.getStroke();
		
		g2d.setStroke(new BasicStroke(5f));
		g2d.setColor(myColor.brighter());
		
		int leftCurve  = myX - (DIAMETER / 2 + 15);
		int middleLine = myX + DIAMETER / 2;
		int rightCurve = myX + (DIAMETER / 2 + 15);
		
		g2d.drawOval(myX, myY, DIAMETER, DIAMETER);
		g2d.drawArc(leftCurve, myY, DIAMETER, DIAMETER, 315, 90);
		g2d.drawLine(middleLine, myY + 5, middleLine, myY + DIAMETER - 5);
		g2d.drawArc(rightCurve, myY, DIAMETER, DIAMETER, 225, -90);
		
		g2d.setStroke(oldStroke);
		
		if(showSubscribe) {
			g2d.setFont(new Font("Arial", Font.BOLD, 25));
			g2d.drawString("SUBSCRIBE NOW!", WIDTH / 2 - 100, HEIGHT / 2);
		}
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new BouncingBallFinal();
			}
		});
	}
}
