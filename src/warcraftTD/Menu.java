package warcraftTD;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.LinkedList;

import warcraftTD.util.Position;
import warcraftTD.util.StdDraw;
import warcraftTD.util.buttons.Button;
import warcraftTD.util.buttons.ButtonText;

final public class Menu {
	private int width;
	private int height;
	private int nbSquareX;
	private int nbSquareY;
	private LinkedList<Button> buttons;
	private Font f;
	
	/**
	 * Constructeur du menu
	 */
	public Menu() {
		width = 1500;
		height = 800;
		nbSquareX = 16;
		nbSquareY = 16;
		menu();

		StdDraw.setCanvasSize(width, height);
		StdDraw.enableDoubleBuffering();
	}
	
	/**
	 * Crée
	 */
	public void menu() {
		StdDraw.setScale();
		f = new Font("TimesNewRoman", Font.BOLD, 60);
		buttons = new LinkedList<Button>();
		buttons.add(new ButtonText(new Position(0.5, 0.6), "Play", 'p' , f, width, height));
		buttons.add(new ButtonText(new Position(0.5, 0.45), "Rules", 'r', f, width, height));
		buttons.add(new ButtonText(new Position(0.5, 0.3), "Exit", 'q', f, width, height));
	}
	

	/**
	 * Met à jour les boutons
	 * @return l'action que doit réalisé le bouton cliqué, ' ' si aucun bouton cliqué
	 */
	public char updateButton() {
		Iterator<Button> ib = buttons.iterator();
		char c = ' ';
		while (ib.hasNext()) {
			Button b = ib.next();
			if (b.isClicked() && c==' ') c = b.getAction();
			b.draw();
		}
		return c;
	}
	
	/**
	 * Lance la fontion correspondant à l'action
	 * @param c l'action sélectionnée par le bouton
	 */
	public void selected(char c) {
		switch (c){
		case 'p':
			playGame();
			break;
		case 'r':
			rules();
			break;
		case 'q':
			System.exit(0);
		}
	}
	
	/**
	 * Lance le jeu (world)
	 */
	public void playGame() {
		int nbWaves = 15;
		World w = new World(width, height, nbSquareX, nbSquareY, nbWaves);
		w.run();
		//une fois que joue est fini, on revient au menu
		menu();
	}
	

	/**
	 * Affiche les règles à l'écran
	 */
	public void rules() {
		Font f = new Font("TimesNewRoman", Font.BOLD, 13);
		Button b = new ButtonText(new Position(0.9, 0.05), "Back", 'b', f, width, height);
		boolean end = false;
		while (!end) {
			StdDraw.clear();
			end = b.isClicked();
			rulesText();
			b.draw();
			StdDraw.show();
			StdDraw.pause(100);
		}
		
	}
	
	/**
	 * Affiche les règles du jeu
	 */
	public void rulesText() {
		Font f;
		try {
			f = new Font("Papyrus", Font.ITALIC, 40);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.err.println("Pour avoir un résultat plus joli, merci d'intaller la font \"Papyrus\"");
			f = new Font("TimesNewRoman", Font.ITALIC, 13);
		}
		StdDraw.setFont(f);
		StdDraw.setPenColor(new Color(0, 30, 115));
		StdDraw.text(0.5, 0.8, "Several monsters try to destroy Gangson's Castel !");
		StdDraw.text(0.5, 0.75, "Help the Gangsoners to eliminate the monsters before the castel will be over.");
		StdDraw.text(0.5, 0.7, "You will be the chief of our army : contruct towers to kill the monsters");
		StdDraw.text(0.5, 0.65, "Two differents types of tower : the Archer Tower and the Bomb Tower");
		StdDraw.text(0.5, 0.60, "The first one is very powerfull with its arrows.");
		StdDraw.text(0.5, 0.55, "But we are in a woodless area : product arrow is very expensive");
		StdDraw.text(0.5, 0.50, "The second one is cheaper, but the bomb can't touch the flying monsters.");
		StdDraw.text(0.5, 0.45, "Select a tower you want to build and click on the grass to build it !");
		StdDraw.text(0.5, 0.40, "Very easy ! But pay attention to the boss : he can fly and walk...");
		StdDraw.text(0.5, 0.35, "Remember you can change the path the monster will follow.");
		StdDraw.text(0.8, 0.25, "Prince Ecinrph");
		StdDraw.picture(0.8, 0.1, "images/seal.png", 0.1, 0.1);
	}
	
	/**
	 * Dessin le fond d'écran du menu
	 */
	public void drawBackGround() {
		StdDraw.picture(0.5, 0.5, "images/menuBackground.png", 1, 1);
		StdDraw.picture(0.5, 0.88, "images/titre.png");
		StdDraw.setFont();
		StdDraw.setPenColor();
		StdDraw.text(0.84, 0.01, "Prince KOUGANG, Alexis SANSON");
	}
	

	/**
	 * Boucle principale du menu
	 */
	public void run() {
		boolean quitter = false;
		while (!quitter) {
			StdDraw.clear();
			drawBackGround();
			selected(updateButton());
			StdDraw.show();
			StdDraw.pause(100);
		}
	}
	
	
	
	
}
