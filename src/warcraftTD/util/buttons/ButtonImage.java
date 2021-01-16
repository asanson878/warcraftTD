package warcraftTD.util.buttons;

import java.awt.Color;

import warcraftTD.util.Position;
import warcraftTD.util.StdDraw;

public class ButtonImage extends Button{
	private String image;
	

	/**
	 * Classe qui gère le bouton composé d'une image
	 * @param p la position de l'image
	 * @param image chemin de l'image dans le répertoire courant
	 * @param action action que va réaliser le bouton si on clique dessus
	 * @param width du bouton
	 * @param height du bouton
	 */
	public ButtonImage(Position p, String image, char action, double width, double height) {
		super(p, action, width, height);
		this.image = image;
	}


	@Override
	public void draw() {
		StdDraw.picture(p.getX(), p.getY(), image, width, heigth);
		if (mouseIn()) {
			StdDraw.setPenColor(new Color(255, 255, 200, 50));
			StdDraw.filledRectangle(p.getX(), p.getY(), width/2, heigth/2);
		}	
	}
}
