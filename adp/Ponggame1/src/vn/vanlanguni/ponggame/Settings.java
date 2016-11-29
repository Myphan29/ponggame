package vn.vanlanguni.ponggame;

import java.awt.Color;

public class Settings {
	private String Player1, Player2;
	private Color backgroundColor, paddleColor, ballColor;
	private int ballNumber;

	public Settings(){	}
	
	public Settings(String Player1, String Player2, Color backgroundColor, Color paddleColor, Color ballColor) {
		super();
		this.Player1 = Player1;
		this.Player2 = Player2;
		this.backgroundColor = backgroundColor;
		this.paddleColor = paddleColor;
		this.ballColor = ballColor;
	}

	public Settings(String u1, String u2) {
		Player1 = u1;
		Player2 = u2;
	}

	public int getBallNumber() {
		return ballNumber;
	}

	public void setBallNumber(int ballNumber) {
		this.ballNumber = ballNumber;
	}

	public String getPlayer2() {
		return Player2;
	}

	public void setPlayer2(String Player) {
		this.Player2 = Player;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getPaddleColor() {
		return paddleColor;
	}

	public void setPaddleColor(Color paddleColor) {
		this.paddleColor = paddleColor;
	}

	public Color getBallColor() {
		return ballColor;
	}

	public void setBallColor(Color ballColor) {
		this.ballColor = ballColor;
	}

	public String getPlayer1() {
		return Player1;
	}

	public void setPlayer1(String Player) {
		Player1 = Player;
	}

}
