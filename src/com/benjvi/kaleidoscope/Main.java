package com.benjvi.kaleidoscope;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Frame;

public class Main {
	public static void main(String[] args) {
		//load in the image from the supplied path
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(args[0]));
		  
		    //initialise the kaleidoscopeImage
		    //BufferedImage kaleidoscope = KaleidoscopeGenerator.createImage(img);
		    //KaleidoscopeGenerator.saveImage(kaleidoscope);
		} catch (IOException e) {
		}
		
		KalPanel kPanel = new KalPanel(img);
		 
        JFrame f = new JFrame("Kaleidoscope");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
 
        f.getContentPane().add(kPanel, BorderLayout.CENTER);
        f.setSize(new Dimension(550, 200));
        f.setVisible(true);
       
		
	}
}
