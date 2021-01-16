package warcraftTD.util.buttons;
import warcraftTD.util.Position;
import warcraftTD.util.StdDraw;


public abstract class Button {
	protected Position p;
	protected double width;
	protected double heigth;
	private char action;
	
	/**
	 * Classe abstraite qui permet de gérer les boutons
	 * @param p la position du bouton
	 * @param action l'action que doit réalisé le bouton
	 * @param width la largeur du bouton
	 * @param height la longueur
	 */
	public Button(Position p, char action, double width, double height) {
		this.p = p;
		this.width = width;
		this.heigth = height;
		this.action = action;
	}
	
	/**
	 * Indique si la souris a cliqué sur le bouton
	 * @return true si et seulement si la souris a cliqué sur le bouton
	 */
	public boolean isClicked() {
		return StdDraw.isMousePressed() && mouseIn();
	}
	
	/**
	 * Indique si la souris est sur le bouton
	 * @return true si et seulement si la souris se trouve au niveau du bouton
	 */
	public boolean mouseIn() {
		return isBetween(StdDraw.mouseX(), p.getX(), width/2) && isBetween(StdDraw.mouseY(), p.getY(), heigth/2);
	}
	
	private boolean isBetween(double x, double a, double epsilon) {
		return a-epsilon<=x && x<=a+epsilon;
	}
	
	/**
	 * Dessin le bouton
	 */
	public abstract void draw();

	/**
	 * Getteur
	 * @return l'action que doit réaliser le bouton
	 */
	public char getAction() {
		return action;
	}
}
