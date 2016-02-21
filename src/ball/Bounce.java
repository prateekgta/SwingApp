package ball;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.security.spec.EllipticCurve;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Bounce extends JFrame implements Runnable, ActionListener
{
	private JButton start;
	private JButton stop;
	private JPanel p1;
	int x,y;
	boolean exit;
	Thread t;
	int  x_shift,y_shift;
	public static void main(String[] args) 
	{
		Bounce b1=new Bounce();
		b1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	Bounce()
	{
		initGui();
	}
	
	public void initGui()
	{
		Random r=new Random();
		x_shift=r.nextInt(10);
		y_shift=r.nextInt(10);
		System.out.println("main invoked by "+Thread.currentThread().getName());
		setSize(400,400);
		getContentPane().setBackground(Color.YELLOW);
		start=new JButton("Start");
		stop=new JButton("Stop ");
		add(start,BorderLayout.NORTH);
		start.addActionListener(this);
		add(stop,BorderLayout.SOUTH);
		stop.addActionListener(this);
		p1=new MyPanel();
		add(p1);
		System.out.println(p1.getBounds().height);//0
		setVisible(true);
		System.out.println(p1.getBounds().height);//330
		x=10;
		y=10;
		exit=false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try 
		{
			if (e.getSource() == start) 
			{
				if(t==null)
				{
				exit=false;
				t=new Thread(this);
				t.start();
			} 
			}
			else
				exit=true;
		} 
		catch (Exception e1) 
		{
			System.out.println("exc in aP " + e1);
		}
	}
	
	@Override
	public void run() 
	{
		System.out.println("thrd started " + Thread.currentThread().getName());
		try {
			
			while (!exit)
			{
				if(x>=(p1.getBounds().width-10))
				{
					x_shift*=-1;
				}
				
				if(y>=(p1.getBounds().height-10))
				{
					y_shift*=-1;
				}
				
				if(x<0)
				{
					x_shift*=-1;
				}
				
				if(y<0)
				{
					y_shift*=-1;
				}				
				x=x+x_shift;
				y=y+y_shift;
				repaint();
				Thread.sleep(50);
			}
		} 
		catch (Exception e) 
		{
			System.out.println("exc in thrd "
					+ Thread.currentThread().getName() + " " + e);
		}
		t = null;
		System.out.println("thrd over " + Thread.currentThread().getName());		
	}

	
	
	private class MyPanel extends JPanel
	{
		public MyPanel() 
		{
			setBackground(Color.CYAN);
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			System.out.println("paint invoked by "+Thread.currentThread().getName());
			Graphics2D g2=(Graphics2D)g;
			g2.setColor(Color.PINK);
			Ellipse2D.Double e1=new Ellipse2D.Double(x,y, 25,25);
			g2.fill(e1);
		}
	}
}
