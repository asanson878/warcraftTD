package warcraftTD.util.buttons;
import java.awt.Font;

import warcraftTD.util.Position;
import warcraftTD.util.StdDraw;


public class ButtonText extends Button{
	private String text;
	private Font f;
	
	/**
	 * Classe qui gère les bouton avec du texte
	 * @param p la position du bouton
	 * @param text le texte à fichier
	 * @param action action que va réaliser le bouton
	 * @param f la font utilisée pour le texte
	 * @param width la largeur de la fenêtre
	 * @param height la hauteur de la fenêtre
	 */
	public ButtonText(Position p, String text, char action, Font f, double width, double height) {
		super(p, action, text.length()*f.getSize()/(width), f.getSize()/(height));
		this.text = text;
		this.f = f;
	}

	@Override
	public void draw() {
		StdDraw.setFont(f);
		if (mouseIn()) StdDraw.setPenColor(StdDraw.RED);
		else StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(p.getX(), p.getY(), text);
		
	}
}