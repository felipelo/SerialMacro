package com.bgzin.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TextComponentDemo {
	public static void main(String[] args) {
		TextComponentDemo demo = new TextComponentDemo();
		
		demo.createUI();
	}
	
	private void createUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Custom Component Demo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				
				final TextComponent textComponent = new TextComponent(12, 25, 80, Color.GREEN, Color.BLACK);
				
				JButton button = new JButton("Test");
				
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						textComponent.getCharacters()[0][0] = 'A';
						textComponent.getCharacters()[0][textComponent.getMaxCols() - 1] = 'B';
						textComponent.getCharacters()[textComponent.getMaxLines() - 1][0] = 'C';
						textComponent.getCharacters()[textComponent.getMaxLines() - 1][textComponent.getMaxCols() - 1] = 'D';
						
						textComponent.repaint();
					}
				});
				
				frame.add(textComponent, BorderLayout.CENTER);
				frame.add(button, BorderLayout.SOUTH);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
