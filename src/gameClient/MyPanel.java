package gameClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.security.DomainCombiner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import api.directed_weighted_graph;
import gameClient.util.Range;
import gameClient.util.Range2D;

public class MyPanel extends JPanel{
	
	public MyPanel() 
	{
		
	}
	
	MyPanel()
	{
//		  JFrame f= new JFrame("Panel Example");    
//	        JPanel panel=new JPanel();  
//	        panel.setBounds(40,80,200,200);    
//	        panel.setBackground(Color.gray);  
//	        JButton b1=new JButton("Button 1");     
//	        b1.setBounds(50,100,80,30);    
//	        b1.setBackground(Color.yellow);   
//	        JButton b2=new JButton("Button 2");   
//	        b2.setBounds(100,100,80,30);    
//	        b2.setBackground(Color.green);   
//	        panel.add(b1); panel.add(b2);  
//	        f.add(panel);  
//	                f.setSize(400,400);    
//	                f.setLayout(null);    
//	                f.setVisible(true);    
		JFrame j = new JFrame();
		JPanel p = new JPanel();
		
		this.setBackground(Color.red);
		j.add(this);
		this.setVisible(true);
	}

	
	
//	public void paint(Graphics g) {
//        int w = this.getWidth();
//        int h = this.getHeight();
//        setSize(w,h);
//        updateFrame();
//        Image buffer_image;
//        Graphics buffer_graphics;
//        buffer_image = createImage(w, h);
//        buffer_graphics = buffer_image.getGraphics();
//        buffer_graphics.clearRect(0, 0, w, h);
//        drawGraph(buffer_graphics);
//        drawPokemons(buffer_graphics);
//        drawAgants(buffer_graphics);
//        drawInfo(buffer_graphics);
//        g.drawImage(buffer_image, 0, 0, this);
//        }
//	
//	private void updateFrame() {
//		Range rx = new Range(20,this.getWidth()-20);
//		Range ry = new Range(this.getHeight()-10,150);
//		Range2D frame = new Range2D(rx,ry);
//		directed_weighted_graph g = _ar.getGraph();
//		_w2f = Arena.w2f(g,frame);
//	}
}
