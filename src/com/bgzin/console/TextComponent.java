package com.bgzin.console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class TextComponent extends JComponent {
	private static final long serialVersionUID = 248859371302016853L;
	
	private static final int DEFAULT_MAX_COLS = 80;
	private static final int DEFAULT_MAX_LINES = 25;
	
	private static final int DEFAULT_FONT_SIZE = 20;
	
	private static final Color DEFAULT_FOREGROUND_COLOR = Color.GREEN;
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
	
	private int maxCols = DEFAULT_MAX_COLS;
	private int maxLines = DEFAULT_MAX_LINES;
	
	private int charWidth = 0;
	private int charHeight = 0;
	
	private Color foregroundColor = DEFAULT_FOREGROUND_COLOR;
	private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
	
	private char[][]  characters = null;
	
	private Color[][] backgroundColors = null;
	private Color[][] foregroundColors = null;
	
	public TextComponent() {
		this(DEFAULT_FONT_SIZE,
			 DEFAULT_MAX_LINES,
			 DEFAULT_MAX_COLS,
			 DEFAULT_FOREGROUND_COLOR,
			 DEFAULT_BACKGROUND_COLOR);
	}
	
	public TextComponent(int fontSize, int maxLines, int maxCols, Color foregroundColor, Color backgroundColor) {
		this.maxLines = maxLines;
		this.maxCols  = maxCols;
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, fontSize);
		setFont(font);
		
		AffineTransform atrans = font.getTransform();
		FontRenderContext frContext = new FontRenderContext(atrans, true, true);
		
		Rectangle2D stringBounds = font.getStringBounds(Character.toString('M'), frContext);
		
		double width  = stringBounds.getWidth();
		double height = stringBounds.getHeight();
		
		charWidth  = ((int)Math.round(width)) + 2;
		charHeight = (int)height;
		
		characters = new char[maxLines][maxCols];
		
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		
		this.foregroundColors = new Color[maxLines][maxCols];
		this.backgroundColors = new Color[maxLines][maxCols];
		
		Dimension d = new Dimension(charWidth * maxCols, charHeight * maxLines);
		
		setPreferredSize(d);
		
		setForeground(foregroundColor);
		setBackground(backgroundColor);
		
		setFocusable(true);
		setEnabled(true);
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {
				getCharacters()[3][4] = 'M';				
				getCharacters()[3][5] = 'o';				
				getCharacters()[3][6] = 't';				
				getCharacters()[3][7] = 'h';				
				getCharacters()[3][8] = 'e';
				getCharacters()[3][9] = 'r';
				
				getCharacters()[3][11] = e.getKeyChar();
				
				repaint();
				
				if(true) {
					return;
				}
				
				getForegroundColors()[3][5] = Color.YELLOW;
				getBackgroundColors()[3][5] = Color.BLUE;
				
				test(getMaxLines(), getMaxCols(), e.getKeyChar());
								
				repaint();
			}
			
			private void test(final int maxL, final int maxC, char chr) {
				int counter = 0;
				Color[] colors = new Color[] {Color.WHITE, Color.BLUE, Color.YELLOW};
				
				for(int i = 0; i  < maxL; i++) {
					for(int j = 0; j < maxC; j++) {
						characters[i][j] = chr;
						
						backgroundColors[i][j] = colors[counter++ % 3];
						foregroundColors[i][j] = Color.GREEN;
					}
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TextComponent.this.requestFocusInWindow();
			}
		});
		
		clear();
	}
	
	public void clear() {
		for(int i = 0; i  < maxLines; i++) {
			for(int j = 0; j < maxCols; j++) {
				characters[i][j] = ' ';
				backgroundColors[i][j] = backgroundColor;
				foregroundColors[i][j] = foregroundColor;
			}
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(int i = 0; i  < maxLines; i++) {
			for(int j = 0; j < maxCols; j++) {
				char currentChar = characters[i][j];
				Color foreground = foregroundColors[i][j];
				Color background = backgroundColors[i][j];
				
				printChar(g, i, j, currentChar, foreground, background);
			}
		}
		
		g.drawString("Mothe&", 200, 100);
	}
	
	private void printChar(Graphics g, int line, int pos, char chr, Color foreground, Color background) {		
		int startX = posToPixels(pos);
		int startY = lineToPixels(line);
		
		g.setColor(background);
		g.fillRect(startX, startY, charWidth, charHeight);

		g.setColor(foreground);
		g.drawChars(new char[] {chr}, 0, 1, startX + 1, startY + charHeight * 2 / 3);
	}
	
	private void printChar(Graphics g, int line, int pos, char chr) {
		printChar(g, line, pos, chr, foregroundColor, backgroundColor);
	}
	
	private int lineToPixels(int line) {
		return charHeight * line;
	}
	
	private int posToPixels(int pos) {
		return charWidth * pos;
	}
	
	public char[][] getCharacters() {
		return characters;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color[][] getBackgroundColors() {
		return backgroundColors;
	}

	public void setBackgroundColors(Color[][] backgroundColors) {
		this.backgroundColors = backgroundColors;
	}

	public int getMaxCols() {
		return maxCols;
	}

	public int getMaxLines() {
		return maxLines;
	}

	public Color[][] getForegroundColors() {
		return foregroundColors;
	}		
}
