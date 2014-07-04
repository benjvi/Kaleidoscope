package com.benjvi.kaleidoscope;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class KaleidoscopeFactory implements Runnable {
	
	private static Kaleidoscope kal;
	private KalPanel kPanel;
	private final JFrame f;
	
	KaleidoscopeFactory(KalPanel kPanel, JFrame frame) {
		this.kPanel = kPanel;
		this.f = frame;
	}
	public static Kaleidoscope getNewInstance(int boundsx, int boundsy) {
		return new Kaleidoscope(boundsx, boundsy);
	}
	
	public static synchronized Kaleidoscope getCurrentInstance() {
		return kal;
	}
	
	public static synchronized void updateCurrentInstance(int boundsx, int boundsy) {
		kal = new Kaleidoscope(boundsx, boundsy);
	}
	
	public static synchronized void updateCurrentInstance(int boundsx, int boundsy, int zoom) {
		kal = new Kaleidoscope(boundsx, boundsy, zoom);
	}
	
	public void run() {
		while(true) {
		int zoom = 3;
			for (int i=0; i<20; i++) {
				zoom = (int)Math.round(3+ (Math.random() * 20));
				updateCurrentInstance(1500, 800, zoom);
				kPanel.setKaleidoscope(getCurrentInstance());
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						f.revalidate();
						f.repaint();
					}
				});
				try {
					Thread.sleep(Math.round(100+Math.random()*500));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
