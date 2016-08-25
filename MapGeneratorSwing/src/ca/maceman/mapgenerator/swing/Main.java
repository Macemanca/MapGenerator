package ca.maceman.mapgenerator.swing;

import java.awt.EventQueue;

import ca.maceman.mapgenerator.swing.ui.MapGeneratorFrame;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapGeneratorFrame window = new MapGeneratorFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
