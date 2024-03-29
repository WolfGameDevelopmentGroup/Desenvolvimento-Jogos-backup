/*
	 RenderGame.java (java)
	 
	 Objetivo: Estudo sobre game looping e threads em java
	 
	 Versão 1.0
	 
	 Site: http://www.dirackslounge.online
	 
	 Programador: Rodolfo A. C. Neves (Dirack) 23/06/2019
	 
	 Email: rodolfo_profissional@hotmail.com
	 
	 Licença: Software de uso livre e código aberto.
*/

package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import Game.FrameGame;
import Game.FramesPerSecond;
import Game.SpriteSheet;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RenderGame extends FramesPerSecond implements Runnable{

	private boolean isRunning;

	public static FrameGame tela;

	int HEIGHT;
	int WIDTH;
	int SCALE;
	String FrameTitle;

	private BufferedImage image;
	private BufferedImage layer;
	private BufferStrategy bs;
	private SpriteSheet sheet;
	private BufferedImage[] player = new BufferedImage[4];
	private int countGameFrame=0;
	private int maxFrames=10;
	private int curAnimation=0;
	private int maxAnimation=4;

	private int xPlayer=50;
	private int yPlayer=50;

	public RenderGame(int HEIGHT,int WIDTH,int SCALE,String FrameTitle){

		this.HEIGHT = HEIGHT;
		this.WIDTH = WIDTH;
		this.SCALE = SCALE;
		this.FrameTitle = FrameTitle;
		this.tela = new FrameGame(this.HEIGHT,this.WIDTH,this.SCALE, this.FrameTitle);
		this.tela.startGameFrame();
		this.image = new BufferedImage(this.WIDTH,this.HEIGHT,BufferedImage.TYPE_INT_RGB);
		this.layer = new BufferedImage(this.WIDTH,this.HEIGHT,BufferedImage.TYPE_INT_RGB);
		this.sheet = new SpriteSheet("/res/novospritesheet.png");
		this.player[0] = sheet.getSprite(0,0,45,64);
		this.player[1] = sheet.getSprite(45,0,45,64);
		this.player[2] = sheet.getSprite(90,0,45,64);
		this.player[3] = sheet.getSprite(138,0,45,64);
	}

	public void updateGameFrame(){

		this.countGameFrame++;

		if(this.countGameFrame > maxFrames){
			
			this.countGameFrame = 0;
			this.curAnimation++;

			if(this.curAnimation >= this.maxAnimation){
				this.curAnimation = 0;
			}


		}

	}

	public void renderizeGame(){

		this.bs = this.tela.canvas.getBufferStrategy();

		if(this.bs == null){
			this.tela.canvas.createBufferStrategy(3);
			return;
		}

		Graphics g = this.layer.getGraphics();

		g.setColor(Color.WHITE);
		this.tela.drawBackground(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(player[this.curAnimation],this.xPlayer,this.yPlayer,null);
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
		this.bs.show();
		
	}

	public synchronized void startGame(){

		this.isRunning = true;
		this.setStartGameTime(System.nanoTime());
		Thread thread = new Thread(this);
		thread.start();

	}

	public void run(){

		while(this.isRunning){

			if(this.isRunning(System.nanoTime())){
				renderizeGame();				
				updateGameFrame();
			}

		}

	}

}
