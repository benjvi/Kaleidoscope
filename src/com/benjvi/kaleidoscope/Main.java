package com.benjvi.kaleidoscope;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.Frame;

public class Main {
	public static void main(String[] args) {
		final String imagePath = args[0];
		//load in the image from the supplied path
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(imagePath));
		  
		    //initialise the kaleidoscopeImage
		    //BufferedImage kaleidoscope = KaleidoscopeGenerator.createImage(img);
		    //KaleidoscopeGenerator.saveImage(kaleidoscope);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final BufferedImage loadedImg = img;
		KaleidoscopeFactory.updateCurrentInstance(10, 10);


		final KalPanel kPanel = new KalPanel(loadedImg, KaleidoscopeFactory.getCurrentInstance());
		
		final JFrame f = new JFrame("Kaleidoscope");
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run()
            {
				f.setSize(new Dimension(1500, 800));
		        
				f.getContentPane().add(kPanel, BorderLayout.CENTER);
				f.addWindowListener(new WindowAdapter() {
		            public void windowClosing(WindowEvent e) {System.exit(0);}
		        });
		        f.setVisible(true);
				
            }
		});
		KaleidoscopeFactory kalSequence = new KaleidoscopeFactory(kPanel,f);
		Thread kalGenerator = new Thread(kalSequence);
		kalGenerator.start();

		
	}
}
