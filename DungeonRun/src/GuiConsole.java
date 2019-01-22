
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;

public final class GuiConsole {

	public final static String newline = System.getProperty("line.separator");

	public static final GUIConsoleIO io = new GUIConsoleIO();

	private GuiConsole() {

	}

	public static class GUIConsoleIO {

		public static final String scanner = null;

		private GUIConsoleIO() {
			
			initComponents();
		}

		private void defaultStyles() {
			// ImageIcon img = new ImageIcon("src/1000.jpg");
			// Orginal windows stil
			// Ändra stil färg via parametrarna
			defaultFont = "Lucida Console";// verkar vara bästa för kartorna
			// defaultFont = "MONOSPACED";//Fungerar
			// defaultFont = "HANGING_BASELINE"; // KARTAN BLIR FEL
			// defaultFont = "SERIF"; // KARTAN BLIR FEL
			// defaultFont = "SANS_SERIF"; // KARTAN BLIR FEL
			// defaultFont = "BOLD"; // KARTAN blir fel
			pane.setBackground(Color.BLACK);
			pane.setForeground(Color.LIGHT_GRAY);
			pane.setCaretColor(Color.WHITE);
			pane.setFont(new Font(defaultFont, Font.BOLD, 14));

			// Music background This must be here start with console and then must be in the
			// main
			// Flyttat till d�r vi starta gui ovanf�r detta
			PlayMusic playmusic = new PlayMusic();

			String backgroundmusic = "/ExternalItems/mysterymusic";
			playmusic.playBackGround(backgroundmusic);
			// playmusic.disposeSound();

			// pane.setBorder(BorderFactory.createEmptyBorder(4, 4, 2, 20));

			pane.setSize(1000, 700);
			pane.setLayout(null);

			// setDefaultCloseOperation(EXIT_ON_CLOSE);

			// pane.background = new JLabel("", img, JLabel.CENTER);
			pane.setBounds(0, 0, 1300, 1000);

			// orginal prompt stil
			// Ändra stil färg via parametrarna
			promptStyle = new SimpleAttributeSet();
			StyleConstants.setFontFamily(promptStyle, defaultFont);
			StyleConstants.setFontSize(promptStyle, 15);// användaren text storleken
			StyleConstants.setForeground(promptStyle, Color.white);
		}

		private void initComponents() {
			frame = new JFrame("Dungeon run");
			pane = new JTextPane();
			frame.setLocationRelativeTo(null);
			doc = pane.getStyledDocument();

			// "src/1000.jpg"));
			paneImage = new JTextPane();
			pane.add(paneImage);
			// getClass().getResource("ExternalItems/1000.jpg");
			// String path="ExternalItems/1000.jpg";
			Image icon = new ImageIcon(getClass().getResource("/ExternalItems/1000.jpg")).getImage();
			paneImage.insertIcon(new ImageIcon(icon));
			paneImage.setVisible(true);
			paneImage.setSize(1200, 5);
			paneImage.setLayout(null);
			// setDefaultCloseOperation(EXIT_ON_CLOSE);
			// pane.background = new JLabel("", img, JLabel.CENTER);
			paneImage.setBounds(75, 0, 1005, 450);

			defaultStyles();
			redirectSystemStreams();

			/*
			 * /////////////////////////////////////// Mapen fram ifall det fungerar Map map
			 * = new Map(); JFrame frame2 = new JFrame(); frame2.setSize(400, 400);
			 * frame2.setLocation(frame.getX() + frame.getWidth(), frame.getY());
			 * frame2.setVisible(true);
			 * /////////////////////////////////////////////////////////////////////////////
			 * ///MAPENS FÖNSTER/FRAME
			 */
			// frame.setLocationRelativeTo(null); sätter fönstret i miten

			fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			InputPolicy cp = new InputPolicy();
			pane.addKeyListener(cp);
			pane.setMargin(new Insets(453, 100, 0, 30)); // x,y och Centrera texten eller vart du vill ha texten i rutan
			pane.setEditable(false);
			caret = pane.getCaret();
			caret.setBlinkRate(250);
			caret.addChangeListener(cp);
			maxInput = -1;
			editing = false;

			Action backspace = new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!editing) {
						return;
					}
					int dot = caret.getDot();
					int mark = caret.getMark();
					if (dot < inputStart || mark < inputStart) {
						return;
					}
					if (dot != mark) {
						int start = pane.getSelectionStart();
						int end = pane.getSelectionEnd();
						replaceRange("", start, end);
					} else if (caret.getDot() > inputStart) {
						replaceRange("", dot - 1, dot);
					}
				}
			};

			pane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, false), "backspace");
			pane.getActionMap().put("backspace", backspace);

			Action enter = new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!editing) {

						return;
					}
					try {
						input = doc.getText(inputStart, doc.getLength() - inputStart);
						lastInputStart = inputStart;
						lastInputEnd = doc.getLength();
					} catch (BadLocationException ex) {
						System.err.println(ex.getMessage());
						System.exit(1);
					}
					editing = false;
					promptVal = null;
					selected = false;
					maxInput = -1;

					pane.setEditable(false);
					caret.setVisible(false);
					printText(newline, null);
					latch.countDown();
				}
			};

			pane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "enter");
			pane.getActionMap().put("enter", enter);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension((int) (screenSize.width / 2), (int) (screenSize.height / 2));
			int x = (int) (frameSize.width / 2);
			int y = (int) (frameSize.height / 2);
			frame.setBounds(x, y, frameSize.width, frameSize.height);
			frame.add(new JScrollPane(pane));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.setBounds(0, 0, 1180, 730);// x,y width and height

			// background
			/*
			 * ImageIcon img = new ImageIcon("src/1000.jpg");
			 * 
			 * frame.setBounds(0, 0, 1120, 700); /// ////////// set label, button
			 * add(frame);
			 * 
			 * frame.setContentPane(new JLabel(new ImageIcon("src/1000.jpg")));
			 * frame.setLayout(null); frame.add(pane);
			 */
		}

		@SuppressWarnings("unused")
		private void add(JFrame frame2) {

			// kanske kan lägga background eller mapp ( "10003.jpg"));

		}

		/**
		 * Takes you to the bottom of the window.
		 */
		public void gotoEnd() {
			goTo(doc.getLength());
		}

		private void goTo(final int index) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					caret.setDot(index);
				}

			});
		}

		public void setFont(final String font, final int style, final int size) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					pane.setFont(new Font(font, style, size));
				}

			});
		}

		public SimpleAttributeSet getAttribute(String com) {
			if (com == null) {
				throw new IllegalArgumentException("getAttribute " + "argument can't be null!");
			}
			SimpleAttributeSet returnedAttr = new SimpleAttributeSet();

			// parse for font family name
			{
				String font = defaultFont;
				for (int j = 0; j < fontNames.length; j++) {
					if (com.indexOf(fontNames[j]) != -1) {
						font = fontNames[j];
					}
				}
				StyleConstants.setFontFamily(returnedAttr, font);
				com = com.replace(font, " ");
			}

			Matcher m = Pattern.compile("\\d+").matcher(com);
			if (m.find()) {
				String number = com.substring(m.start(), m.end());
				int size = Integer.parseInt(number);
				size = (size < 8) ? 8 : size;
				size = (size > 72) ? 72 : size;
				StyleConstants.setFontSize(returnedAttr, size);
			}

			String[] cmds = com.split("\\s+");
			for (int j = 0; j < cmds.length; j++) {
				if (cmds[j].equals("bold")) {
					StyleConstants.setBold(returnedAttr, true);
				} else if (cmds[j].equals("underline")) {
					StyleConstants.setUnderline(returnedAttr, true);
				} else if (cmds[j].equals("italic")) {
					StyleConstants.setItalic(returnedAttr, true);
				} else if (cmds[j].equals("strikethrough")) {
					StyleConstants.setStrikeThrough(returnedAttr, true);
				}
				returnedAttr = fgColorApp(returnedAttr, cmds[j]);
				returnedAttr = bgColorApp(returnedAttr, cmds[j]);
			}
			return returnedAttr;
		}

		private SimpleAttributeSet fgColorApp(SimpleAttributeSet attr, String color) {
			if (attr == null) {
				attr = new SimpleAttributeSet();
			}
			if (color.equals("black")) {
				StyleConstants.setForeground(attr, Color.BLACK);
			} else if (color.equals("blue")) {
				StyleConstants.setForeground(attr, Color.BLUE);
			} else if (color.equals("cyan")) {
				StyleConstants.setForeground(attr, Color.CYAN);
			} else if (color.equals("dark_gray")) {
				StyleConstants.setForeground(attr, Color.DARK_GRAY);
			} else if (color.equals("magenta")) {
				StyleConstants.setForeground(attr, Color.MAGENTA);
			} else if (color.equals("orange")) {
				StyleConstants.setForeground(attr, Color.ORANGE);
			} else if (color.equals("pink")) {
				StyleConstants.setForeground(attr, Color.PINK);
			} else if (color.equals("red")) {
				StyleConstants.setForeground(attr, Color.RED);
			} else if (color.equals("white")) {
				StyleConstants.setForeground(attr, Color.WHITE);
			} else if (color.equals("yellow")) {
				StyleConstants.setForeground(attr, Color.YELLOW);
			} else if (color.equals("gray")) {
				StyleConstants.setForeground(attr, Color.GRAY);
			} else if (color.equals("green")) {
				StyleConstants.setForeground(attr, Color.GREEN);
			} else if (color.equals("light_gray")) {
				StyleConstants.setForeground(attr, Color.LIGHT_GRAY);
			}
			return attr;
		}

		private SimpleAttributeSet bgColorApp(SimpleAttributeSet attr, String color) {
			if (attr == null) {
				attr = new SimpleAttributeSet();
			}
			if (color.equals("bg_black")) {
				StyleConstants.setBackground(attr, Color.BLACK);
			} else if (color.equals("bg_blue")) {
				StyleConstants.setBackground(attr, Color.BLUE);
			} else if (color.equals("bg_cyan")) {
				StyleConstants.setBackground(attr, Color.CYAN);
			} else if (color.equals("bg_dark_gray")) {
				StyleConstants.setBackground(attr, Color.DARK_GRAY);
			} else if (color.equals("bg_magenta")) {
				StyleConstants.setBackground(attr, Color.MAGENTA);
			} else if (color.equals("bg_orange")) {
				StyleConstants.setBackground(attr, Color.ORANGE);
			} else if (color.equals("bg_pink")) {
				StyleConstants.setBackground(attr, Color.PINK);
			} else if (color.equals("bg_red")) {
				StyleConstants.setBackground(attr, Color.RED);
			} else if (color.equals("bg_white")) {
				StyleConstants.setBackground(attr, Color.WHITE);
			} else if (color.equals("bg_yellow")) {
				StyleConstants.setBackground(attr, Color.YELLOW);
			} else if (color.equals("bg_gray")) {
				StyleConstants.setBackground(attr, Color.GRAY);
			} else if (color.equals("bg_green")) {
				StyleConstants.setBackground(attr, Color.GREEN);
			} else if (color.equals("bg_light_gray")) {
				StyleConstants.setBackground(attr, Color.LIGHT_GRAY);
			}
			return attr;
		}

		public void clear() {
			pane.setText("");
		}

		public void setPromptColor(Color color) {
			StyleConstants.setForeground(promptStyle, color);
		}

		public void setPromptStyle(SimpleAttributeSet attr) {
			promptStyle = attr;
		}

		public void setCaretColor(final Color color) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					pane.setCaretColor(color);
				}

			});
		}

		// Ändrar färg
		public void setTextColor(final Color color) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					pane.setForeground(color);
				}

			});
		}

		// backgrundsfärg
		public void setBackgroundColor(final Color color) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					pane.setBackground(color);
				}

			});
		}

		// Windows title
		public void setTitle(final String title) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					frame.setTitle(title);
				}

			});
		}

		// Hämtar ovan metoder
		public void print(String txt, SimpleAttributeSet attr) {
			printText(txt, attr);
		}

		// klllar ifall det är string el int
		public void print(String str) {
			printText(str, null);
		}

		// Ändrar string color
		public void print(String str, Color color) {
			printText(str, attr(color));
		}

		// Kunna skriva ut ett object
		public void print(Object obj) {
			printText(String.valueOf(obj), null);
		}

		// Ändra färg på object utskrivften
		public void print(Object obj, Color color) {
			printText(String.valueOf(obj), attr(color));
		}

// ändra layouten size , typ som ovan fast kan ändra attirbuten
		public void print(Object obj, SimpleAttributeSet satt) {
			printText(String.valueOf(obj), satt);
		}

		// koll ifall double
		public void print(double d) {
			printText(String.valueOf(d), null);
		}

		// ändrar println double färg
		public void print(double d, Color color) {
			printText(String.valueOf(d), attr(color));
		}

		// ändrar double layout
		public void print(double d, SimpleAttributeSet satt) {
			printText(String.valueOf(d), satt);
		}

		// kolla float
		public void print(float f) {
			printText(String.valueOf(f), null);
		}

		// float färger
		public void print(float f, Color color) {
			printText(String.valueOf(f), attr(color));
		}

		// float layout
		public void print(float f, SimpleAttributeSet satt) {
			printText(String.valueOf(f), satt);
		}

		// kollar ifalll String är lång och anpassar sig
		public void print(long l) {
			printText(String.valueOf(l), null);
		}

// long färger
		public void print(long l, Color color) {
			printText(String.valueOf(l), attr(color));
		}

		// long layout
		public void print(long l, SimpleAttributeSet satt) {
			printText(String.valueOf(l), satt);
		}

		// int värde och kollar varje char i default
		public void print(int i) {
			printText(String.valueOf(i), null);
		}

		// ovan färger
		public void print(int i, Color color) {
			printText(String.valueOf(i), attr(color));
		}

		// ovan layout
		public void print(int i, SimpleAttributeSet satt) {
			printText(String.valueOf(i), satt);
		}

		public void print(char c) {
			printText(String.valueOf(c), null);
		}

		public void print(char c, Color color) {
			printText(String.valueOf(c), attr(color));
		}

		public void print(char c, SimpleAttributeSet satt) {
			printText(String.valueOf(c), satt);
		}

		// ändrar till newline teminate line
		public void println(String txt, SimpleAttributeSet attr) {
			printText(txt + newline, attr);
		}

		public void println() {
			printText(newline, null);
		}

		public void println(String str) {
			printText(str + newline, null);
		}

		public void println(String str, Color color) {
			printText(str + newline, attr(color));
		}

		public void println(Object obj) {
			printText(String.valueOf(obj) + newline, null);
		}

		public void println(Object obj, Color color) {
			printText(String.valueOf(obj) + newline, attr(color));
		}

		public void println(Object obj, SimpleAttributeSet satt) {
			printText(String.valueOf(obj) + newline, satt);
		}

		public void println(double d) {
			printText(String.valueOf(d) + newline, null);
		}

		public void println(double d, Color color) {
			printText(String.valueOf(d) + newline, attr(color));
		}

		public void println(double d, SimpleAttributeSet satt) {
			printText(String.valueOf(d) + newline, satt);
		}

		public void println(float f) {
			printText(String.valueOf(f) + newline, null);
		}

		public void println(float f, Color color) {
			printText(String.valueOf(f) + newline, attr(color));
		}

		public void println(float f, SimpleAttributeSet satt) {
			printText(String.valueOf(f) + newline, satt);
		}

		public void println(long l) {
			printText(String.valueOf(l) + newline, null);
		}

		public void println(long l, Color color) {
			printText(String.valueOf(l) + newline, attr(color));
		}

		public void println(long l, SimpleAttributeSet satt) {
			printText(String.valueOf(l) + newline, satt);
		}

		public void println(int i) {
			printText(String.valueOf(i) + newline, null);
		}

		public void println(int i, Color color) {
			printText(String.valueOf(i) + newline, attr(color));
		}

		public void println(int i, SimpleAttributeSet satt) {
			printText(String.valueOf(i) + newline, satt);
		}

		public void println(char c) {
			printText(String.valueOf(c) + newline, null);
		}

		public void println(char c, Color color) {
			printText(String.valueOf(c) + newline, attr(color));
		}

		public void println(char c, SimpleAttributeSet satt) {
			printText(String.valueOf(c) + newline, satt);
		}

		public void styleLastInput(SimpleAttributeSet attr) {
			doc.setCharacterAttributes(lastInputStart, lastInputEnd, attr, false);
		}

		public String nextPassword() {
			return passwordPrompt();
		}

		public String nextPassword(int l) {
			maxInput = l;
			return passwordPrompt();
		}

		public String nextLine() {
			return prompt();
		}

		public String nextLine(int l) {
			maxInput = l;
			return prompt();
		}

		public double nextDouble() {
			return Double.valueOf(prompt());
		}

		public double nextDouble(int l) {
			maxInput = l;
			return Double.valueOf(prompt());
		}

		public float nextFloat() {
			return Float.valueOf(prompt());
		}

		public float nextFloat(int l) {
			maxInput = l;
			return Float.valueOf(prompt());
		}

		public long nextLong() {
			return Long.valueOf(prompt());
		}

		public long nextLong(int l) {
			maxInput = l;
			return Long.valueOf(prompt());
		}

		public int nextInt() {
			return Integer.valueOf(prompt());
		}

		public int nextInt(int l) {
			maxInput = l;
			return Integer.valueOf(prompt());
		}

		private SimpleAttributeSet attr(Color color) {
			SimpleAttributeSet s = new SimpleAttributeSet();
			StyleConstants.setForeground(s, color);
			return s;
		}

		private void replaceRange(String str, int start, int end) {
			try {
				if (doc instanceof AbstractDocument) {
					((AbstractDocument) doc).replace(start, end - start, str, null);
				} else {
					doc.remove(start, end - start);
					doc.insertString(start, str, null);
				}
			} catch (BadLocationException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}

		// kolla så att någon är inskrivet
		public void setPromptVal(String val, boolean s) {
			promptVal = val;
			selected = s;
		}

		private String passwordPrompt() {
			String oldFont = StyleConstants.getFontFamily(promptStyle);
			StyleConstants.setFontFamily(promptStyle, "password");
			String inp = prompt();
			StyleConstants.setFontFamily(promptStyle, oldFont);
			return inp;
		}

		private String prompt() {
			printText("\r", promptStyle);
			inputStart = doc.getLength();
			editing = true;
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					pane.setEditable(true);
					caret.setVisible(true);
					caret.setDot(inputStart);
				}

			});
			if (promptVal != null) {
				printText(promptVal, promptStyle);
				if (selected) {
					select(inputStart, doc.getLength());
				}
			}
			try {
				latch = new CountDownLatch(1);
				latch.await();
			} catch (java.lang.InterruptedException ex) {
				System.err.print("The latch failed:" + "\n" + ex.getMessage());
			}
			return input;
		}

		private class InputPolicy implements KeyListener, ChangeListener {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (!editing) {
					return;
				}
				int maxRegion = inputStart + maxInput;
				if (caret.getDot() < inputStart || caret.getMark() < inputStart
						|| (maxInput > -1 && doc.getLength() >= maxRegion)) {
					pane.setEditable(false);
				} else if (!pane.isEditable()) {
					pane.setEditable(true);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (!editing) {
					return;
				}
				if (caret.getDot() < inputStart && editing) {
					caret.setDot(pane.getText().length());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		} // InputPolicy class end ---------------------------------------------

		private void printText(String txt, SimpleAttributeSet attr) {
			try {
				doc.insertString(doc.getLength(), txt, attr);
			} catch (BadLocationException ex) {
				GuiConsole.io.println("Error occurred in printText");
				GuiConsole.io.println(ex.getMessage());
			}
		}

		private void select(final int start, final int end) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					pane.select(start, end);
				}

			});
		}

		private void redirectSystemStreams() {
			OutputStream out = new OutputStream() {
				@Override
				public void write(final int b) throws IOException {
					printText(String.valueOf((char) b), null);
				}

				@Override
				public void write(byte[] b, int off, int len) throws IOException {
					printText(new String(b, off, len), null);
				}

				@Override
				public void write(byte[] b) throws IOException {
					write(b, 0, b.length);
				}
			};
			System.setOut(new PrintStream(out, true));
			System.setErr(new PrintStream(out, true));
		}

		private JFrame frame;
		private JTextPane pane;
		private JTextPane paneImage;

		private CountDownLatch latch;
		private StyledDocument doc;
		private Caret caret;
		private SimpleAttributeSet promptStyle;
		private String[] fontNames;
		private String input;
		private String promptVal;
		private String defaultFont;
		private int inputStart;
		private int lastInputStart;
		private int lastInputEnd;
		private int maxInput;
		private boolean editing;
		private boolean selected;
	} // </editor-fold>
}