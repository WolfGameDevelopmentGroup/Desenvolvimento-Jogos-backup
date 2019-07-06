/*
	 SpriteSheet.java (java)
	 
	 Objetivo: Carregar a imagem do personagem no jogo.
	 
	 Versão 1.0
	 
	 Site: http://www.dirackslounge.online
	 
	 Programador: Rodolfo A. C. Neves (Dirack) 06/07/2019
	 
	 Email: rodolfo_profissional@hotmail.com
	 
	 Licença: Software de uso livre e código aberto.
*/

package Game;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet{

	private BufferedImage spritesheet;

	public SpriteSheet(String path){

		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public BufferedImage getSprite(int x, int y, int width, int height){
		return spritesheet.getSubimage(x,y,width,height);

	}

}
