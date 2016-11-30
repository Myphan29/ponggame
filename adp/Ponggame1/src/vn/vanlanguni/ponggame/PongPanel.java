/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */
package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -1097341635155021546L;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;
	private int interval = 1000 / 60;
	/** Background. */
	private Color backgroundColor = Color.BLACK;

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	// Random +/-
	private int timeToDisplay, timeToDisplay2, timeToDisplay3;
	private boolean showRandom, showRandomspeedplus, showRandomspeedminus;
	private int xRan, xRanplus, xRanminus;
	private int yRan, yRanplus, yRanminus;
	private int lastHitBall;

	/** The ball: position, diameter */
	private int ballX = 240;
	private int ballY = 240;
	private int diameter = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 200;
	private int playerOneWidth = 10;
	private int playerOneHeight = 60;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 490;
	private int playerTwoY = 200;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 60;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;

	// Secondwindow
	Color buttonColor = Color.BLUE;
	Rectangle rect;

	ImageIcon btnIcon = new ImageIcon("images/button.png");
	boolean hover;
	boolean pressed;
	boolean dragged;
	int x, y, w, h;
	int dx, dy;

	String username1;
	String username2;
	int ballNum = 1;
	Color paddleColor;

	// User name
	JTextField txtUser1 = new JTextField("");
	JTextField txtUser2 = new JTextField("");

	// Set background
	ImageIcon imgBgrStart = new ImageIcon("./Images/Bgr_Start.jpg"), imgBgrPlay = new ImageIcon("./Images/minion1.jpg"),
			imgBgrEnd = new ImageIcon("./Images/Bgr_End.jpg");

	/** Construct a PongPanel. */
	public PongPanel() {
		setBackground(backgroundColor);
		// Second windown
		w = 100;
		h = 30;
		x = WIDTH / 2 - w / 2;
		y = HEIGHT / 2 - h / 2;

		// listen to key presses
		setFocusable(true);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);

		timeToDisplay = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 500;
		timeToDisplay2 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 500;
		timeToDisplay3 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 500;
		// call step() 60 fps

		Timer timer = new Timer(interval, this);
		timer.start();

	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0) { // FIXED #11:
				playerOneY -= paddleSpeed; // Wrong side control
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				Sound.play("sound/bounce.wav");
				ballDeltaY *= -1;
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {
					//Sound.play("sound/ht.wav");
					Sound.play("sound/hehe.wav");
					playerTwoScore++;

					// Player 2 Win, restart the game
					if (playerTwoScore == 5) {
						playing = false;
						gameOver = true;
					}
					ballX = 240;
					ballY = 240;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					
					ballDeltaX *= -1;
					lastHitBall = 1;
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {
					//Sound.play("sound/ht.wav");
					Sound.play("sound/hehe.wav");
					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore == 5) {
						playing = false;
						gameOver = true;
					}
					ballX = 240;
					ballY = 240;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					// Sound.play("sound/hehe.wav");
					ballDeltaX *= -1;
					lastHitBall = 2;
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;

			timeToDisplay -= interval;
			timeToDisplay2 -= interval;
			timeToDisplay3 -= interval;

			// Show bomb

			// System.out.format("%d x: %d - y: %d\n", timeToDisplay, xRan,
			// yRan);
			if (timeToDisplay < 0) {
				if (showRandom == false) {
					showRandom = true;
					xRan = ThreadLocalRandom.current().nextInt(50, 450 + 1);
					yRan = ThreadLocalRandom.current().nextInt(0, 470 + 1);
				} else {
					Point ballCenter = new Point(ballX + diameter / 2, ballY + diameter / 2);
					Point ranCenter = new Point(xRan + 15, yRan + 15);
					double distance = getPointDistance(ballCenter, ranCenter);
					if (distance < diameter / 2 + 15) {
						showRandom = false;
						timeToDisplay = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;
						if (lastHitBall == 1) {
							playerOneHeight -= 20;
						} else if (lastHitBall == 2) {
							playerTwoHeight -= 20;
						}
					}
				}
				if (timeToDisplay < -2500) {
					showRandom = false;
					timeToDisplay = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 500;
				}
				// showPlus
				if (timeToDisplay2 < 0) {
					if (showRandomspeedplus == false) {
						showRandomspeedplus = true;

						xRanplus = ThreadLocalRandom.current().nextInt(50, 450 + 1);
						yRanplus = ThreadLocalRandom.current().nextInt(0, 470 + 1);
					} else {
						Point ballCenter2 = new Point(ballX + diameter / 2, ballY + diameter / 2);
						Point ranPlus = new Point(xRanplus + 15, yRanplus + 15);

						double distancePlus = getPointDistance(ballCenter2, ranPlus);
						if (distancePlus < diameter / 2 + 25) {
							showRandomspeedplus = false;
							timeToDisplay2 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;
							System.out.print("hit plus");
							interval = 1000 / 80;
						}
					}
					if (timeToDisplay2 < -1500) {
						showRandomspeedplus = false;
						timeToDisplay2 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 500;
					}

				}
				// Show Minus

				if (timeToDisplay3 < 0) {
					if (showRandomspeedminus == false) {
						showRandomspeedminus = true;

						xRanminus = ThreadLocalRandom.current().nextInt(50, 450 + 1);
						yRanminus = ThreadLocalRandom.current().nextInt(0, 470 + 1);
					} else {
						Point ballCenter3 = new Point(ballX + diameter / 2, ballY + diameter / 2);
						Point ranMinus = new Point(xRanminus + 15, yRanminus + 15);

						double distanceMinus = getPointDistance(ballCenter3, ranMinus);
						if (distanceMinus < diameter / 2 + 15) {
							showRandomspeedminus = false;
							timeToDisplay3 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;
							System.out.print("hit minus ");
							interval = 5000 / 60;
						}
					}
					if (timeToDisplay3 < -1000) {
						showRandomspeedminus = false;
						timeToDisplay3 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 500;
					}
					// System.out.println(timeToDisplay2);
				}

			}

		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	public double getPointDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {
			g.drawImage(imgBgrStart.getImage(), 0, 0, 500, 500, null);

			// Secondwindow
			rect = new Rectangle(x - 50, y + 182, w, h);
			if (hover) {
				if (pressed) {
					// g.drawImage(btnIcon.getImage(), x, y, x + w, y + h, 0,
					// 214, 371, 214 + 106, null);
					g.setColor(Color.RED);
				} else {
					// g.drawImage(btnIcon.getImage(), x, y, x + w, y + h, 0, 0,
					// 371, 108, null);
					g.setColor(Color.WHITE);
				}
			} else {
				// g.drawImage(btnIcon.getImage(), x, y, x + w, y + h, 0, 108,
				// 371, 108 + 106, null);
				g.drawImage(btnIcon.getImage(), x - 50, y + 182, x - 50 + w, y + 182 + h, 0, 108, 371, 108 + 106, null);
				g.setColor(Color.WHITE);
			}
			g.setFont(new Font("Tahoma", Font.BOLD, 15));
			g.drawString("Setting", x - 25, y + 202);

			/* Show welcome screen */

			// Draw game title and start message
			// g.fillRect(70, 45, 355, 75);
			g.setColor(Color.CYAN);
			g.setFont(new Font("", Font.BOLD, 60));
			g.drawString("Pong Game", 150, 380);

			// FIXME Welcome message below show smaller than game title
			g.setFont(new Font(Font.DIALOG, Font.ITALIC, 28));
			g.drawString("Press 'P' to play", 265, 440);
			Sound.play("Sound/win.wav");

		} else if (playing) {

			/* Game is playing */
			g.drawImage(imgBgrPlay.getImage(), 0, 0, 500, 500, null);

			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.setColor(Color.BLACK);
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			// draw "goal lines" on each side
			// g.setColor(Color.GRAY);
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

			// draw the name
			// g.setColor(Color.BLACK);
			// g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			// g.drawString(String.valueOf(username1), 30, 50);
			// g.drawString(String.valueOf(username2), 370, 50);
			add(txtUser1);
			add(txtUser2);
			Font font1 = new Font(Font.DIALOG, Font.BOLD, 17);
			txtUser1.setFont(font1);
			Font font2 = new Font(Font.DIALOG, Font.BOLD, 17);
			txtUser2.setFont(font2);
			txtUser1.setEditable(false);
			txtUser2.setEditable(false);
//			txtUser1.setBorder(BorderFactory.createEmptyBorder());
//			txtUser2.setBorder(BorderFactory.createEmptyBorder());
			txtUser1.setBounds(75, 10, 120, 30);
			txtUser2.setBounds(325, 10, 120, 30);
			txtUser1.setHorizontalAlignment(txtUser1.CENTER);
			txtUser2.setHorizontalAlignment(txtUser2.CENTER);
			txtUser1.setBackground(Color.ORANGE);
			txtUser2.setBackground(Color.ORANGE);

			// draw the scores
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(playerOneScore), 115, 80); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 365, 80); // Player 2
																	// score

			// draw the ball
			ImageIcon imgBall = new ImageIcon();
			if (ballNum == 1) {
				imgBall = new ImageIcon("./Images/ball.png");

			} else if (ballNum == 2) {
				imgBall = new ImageIcon("./Images/ball2.png");
			} else if (ballNum == 3) {
				imgBall = new ImageIcon("./Images/ball3.png");
			}
			g.drawImage(imgBall.getImage(), ballX, ballY, 30, 30, null);

			// Show random +/-
			if (showRandom) {
				ImageIcon imgBomb = new ImageIcon();
				imgBomb = new ImageIcon("./Images/bomb.png");
				g.drawImage(imgBomb.getImage(), xRan, yRan, 30, 30, null);
			}

			// Show random spech +
			if (showRandomspeedplus) {
				ImageIcon imgPlus = new ImageIcon();
				imgPlus = new ImageIcon("./Images/cong.png");
				g.drawImage(imgPlus.getImage(), xRanplus, yRanplus, 50, 50, null);
			}

			// show random spech-
			if (showRandomspeedminus) {
				ImageIcon imgMinus = new ImageIcon();
				imgMinus = new ImageIcon("./Images/tru.png");
				g.drawImage(imgMinus.getImage(), xRanminus, yRanminus, 30, 30, null);
			}
			// draw the paddles
			g.setColor(paddleColor);
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
		} else if (gameOver) {
			/* Show End game screen with winner name and score */

			g.drawImage(imgBgrEnd.getImage(), 0, 0, 500, 500, null);

			// Draw scores
			// TODO Set Blue color
			g.setColor(Color.RED);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 115, 80); // FIXED #18:
			g.drawString(String.valueOf(playerTwoScore), 365, 80); // Font
																	// Location

			// Draw the winner name
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.drawString(username1+" wins!", 135, 230);
			} else {
				g.drawString(username2+" wins!", 135, 230);
			}

			// Draw Restart message
			// g.setColor(Color.PINK);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			// TODO Draw a restart message
			g.drawString("Game over", 145, 133);
			Sound.play("Sound/win.wav");
			// FIXED #8:
			g.setColor(Color.RED);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 35)); // DON'T SHOW "GAME
																// OVER"
			g.drawString("Press Space to restart", 50, 460);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_P) { // FIXED #19:
				showTitleScreen = false; // P capslock does't work
				playing = true;
				txtUser1.setVisible(true);
				txtUser2.setVisible(true);
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 200;
			playerTwoY = 200;
			ballX = 240;
			ballY = 240;
			playerOneScore = 0; // FIXED #6:
			playerTwoScore = 0; // SCORES DON'T RESET AFTER RESTARTTING
			txtUser1.setVisible(false);
			txtUser2.setVisible(false);
		}

	}

	public void keyReleased(KeyEvent e) {
		if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = false; // FIXED #7:
									// Control buttons of player 2 (button W/S)
									// don't work correctly
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (rect.contains(e.getPoint())) {
			SecondWindow w = new SecondWindow();
			w.setLocationRelativeTo(PongPanel.this);
			w.setVisible(true);
			Settings s = w.getSetings();
			System.out.println("After open window");
			// Stop and wait for user input

			if (w.dialogResult == MyDialogResult.YES) {

				System.out.printf("User settings: \n Username1: %s \n Username2: %s \n", s.getUserName1(),
						s.getUserName2());

				ballNum = s.getBallNumber();

				username1 = s.getUserName1();
				username2 = s.getUserName2();

				paddleColor = s.getPaddleColor();
				System.out.println("ball number " + s.getBallNumber());
				System.out.println("Paddle Color " + s.getPaddleColor());

				txtUser1.setText(username1.toUpperCase());
				txtUser2.setText(username2.toUpperCase());

			} else {
				System.out.println("Player choose to cancel");
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		pressed = true;
		if (rect.contains(e.getX(), e.getY())) {
			dx = e.getX() - x;
			dy = e.getY() - y;
			dragged = true;
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		pressed = false;
		dragged = false;
		repaint();
	}
}
