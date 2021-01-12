package warcraftTD;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import warcraftTD.missiles.Missile;
import warcraftTD.monsters.Monster;
import warcraftTD.towers.ArcherTower;
import warcraftTD.towers.BombTower;
import warcraftTD.towers.Tower;
import warcraftTD.util.Position;
import warcraftTD.util.StdDraw;
import warcraftTD.util.buttons.Button;
import warcraftTD.util.buttons.ButtonImage;
import warcraftTD.util.buttons.ButtonText;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Font; 
import java.util.Random;



public class World {
	/*
	 * l'ensemble des monstres, tours, missiles, boutons, chemin suivie par les monstres,
	 * du chemin pour gerer (notamment) l'affichage
	 */
	private LinkedList<Monster> monsters;
	private ArrayList<Tower> towers;
	private List<Missile> missiles;
	private List<Button> buttons;
	private ArrayList<Position> pathMonsters;	
	private Set<Position> path;
	

	// Position par laquelle les monstres vont venir
	private Position spawn;	
	
	/*
	 * Information sur la taille du plateau de jeu
	 * ainsi que la police d'écriture utilisée
	 */
	private int nbSquareX;
	private int nbSquareY;
	private double squareWidth;
	private double squareHeight;
	private Font font;
	
	/*
	 * Informations sur la vie, l'argent du joueur
	 */
	private int life = 20;
	private int money = 100;
	
	
	/*
	 * Gestions des flux (des vagues de monstres, des messages,
	 * des actions effectuées par le joueur, du jeu)
	 */
	private Waves waves;
	private String message;
	private String alertMessage;
	private char action;
	private boolean end = false;
	private boolean start = false;
	private boolean canChangePath = true;
	
	
	
	
	/**
	 * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases données
	 * @param width
	 * @param height
	 * @param nbSquareX
	 * @param nbSquareY
	 * @param startSquareX
	 * @param startSquareY
	 */
	public World(int width, int height, int nbSquareX, int nbSquareY, int nbwaves) {
		StdDraw.setXscale(0, 1.25);
		this.nbSquareX = nbSquareX;
		this.nbSquareY = nbSquareY;
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
		spawn = createPosition(nbSquareX-2, nbSquareY-1);
		message = "";
		font = new Font("TimesNewRoman", Font.BOLD, 20);
		initLists();
		initPath();
		initButton();
		waves = new Waves(nbwaves);
		waves.newWave();
	}
	
	
	
	private void initButton() {
		buttons.add(new ButtonImage(new Position(1.03, 0.65), "images/ArcherTowerLevel3.png", 'a', 0.05, 0.05));
		buttons.add(new ButtonImage(new Position(1.03, 0.55), "images/BombTowerLevel3.png", 'b',  0.05, 0.05));
		buttons.add(new ButtonImage(new Position(1.03, 0.45), "images/up1.png", 'u', 0.02, 0.02));
		buttons.add(new ButtonImage(new Position(1.03, 0.35), "images/box.png", 'r', 0.05, 0.05));
		buttons.add(new ButtonImage(new Position(1.175, 0.025), "images/exit.png", 'e', 0.05, 0.05));
		buttons.add(new ButtonImage(new Position(1.025, 0.025), "images/play.png", '>', 0.05, 0.05));
		buttons.add(new ButtonImage(new Position(1.075, 0.025), "images/pause.png", '=', 0.05, 0.05));
		buttons.add(new ButtonImage(new Position(1.125, 0.025), "images/changePath.png", 'p', 0.05, 0.05));
	}
	
	
	
	private void initLists() {
		missiles = new LinkedList<Missile>();
		towers = new ArrayList <Tower>();
		monsters = new LinkedList<Monster>();
		pathMonsters = new ArrayList<Position>();
		path = new TreeSet<Position>();
		buttons = new LinkedList<Button>();
	}

	
	
	/**
	 * Initialise le chemin sur lequel passera les monstres
	 */
	public void initPath(){
		//Prend des points au hasard dans le cadrillage
		int[][] chemin = new int[5][2]; //on prend 5 points sur le cadrillage
		chemin[0][0] = nbSquareX-2; chemin[0][1] = nbSquareY-1;
		Random rd = new Random();
		int index = 1;
		while (index!=4) {
			int x = rd.nextInt(nbSquareX-2);
			int y = rd.nextInt(nbSquareY);
			int j = 0;
			//vérfie certaines conditions sur les points
			//les points doivent être sur des lignes et des colonnes différentes
			while  (j!=index && !(equalsTo(chemin[j][0], x, 1) || equalsTo(chemin[j][1], y, 1))) j++;
			if (j==index) {
				chemin[index][0]=x; chemin[index][1]=y;
				index++;
			}
		}
		//ajout du point d'arrivé
		chemin[4][0] = nbSquareX-1;
		chemin[4][1] = nbSquareY-1;
		
		//on transforme les points en Position dans le canevas
		//on relie les points
		//on parcourt la liste de point créer précédement
		for (int i=1; i<chemin.length; i++) {
			int x = chemin[i-1][0]; int y = chemin[i-1][1];
			int nextx = chemin[i][0]; int nexty = chemin[i][1];
			int dx = nextx - x;
			int dy = nexty - y;
			pathMonsters.add(createPosition(x,y));
			//on relie horizontalement
			for (int n=x; n!=nextx; n=n+signe(dx))
				path.add(createPosition(n, y));
			pathMonsters.add(createPosition(nextx, y));
			//on relie verticalement
			for (int n=y; n!=nexty; n=n+signe(dy))
				path.add(createPosition(nextx, n));
		}
		Position p = createPosition(chemin[chemin.length-1][0], chemin[chemin.length-1][1]);
		path.add(p);
		pathMonsters.add(p);
	}
	private int signe(int n) {
		return (n>0)?1:-1;
	}
	private boolean equalsTo(int a, int x, int epsilon) {
		return a-epsilon<=x && x<=a+epsilon;
	}
	private Position createPosition(int x, int y) {
		return new Position(x * squareWidth + squareWidth / 2, y * squareHeight + squareHeight / 2);
	}
	
	
	
	/**
	 * Définit le décors du plateau de jeu.
	 */
	 public void drawBackground() {
		 for (int i = 0; i < nbSquareX; i++)
			 for (int j = 0; j < nbSquareY; j++)
				 StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/Grass.png", squareWidth, squareHeight);
	 }
	 
	 
	 
	 /**
	  * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décor.
	  */
	 public void drawPath() {
		 for (Position p: path)
			 StdDraw.picture(p.getX(), p.getY(), "images/Path.png", squareWidth, squareHeight);
		 Position sp = spawn.clone();
		 //TODO : modifier l'image de départ
		 StdDraw.setPenColor(StdDraw.YELLOW);
		 StdDraw.filledRectangle(sp.getX(), sp.getY(), squareWidth / 2, squareHeight / 2);
		 StdDraw.picture((nbSquareX-1) * squareWidth + squareWidth / 2, (nbSquareY-1) * squareHeight + squareHeight / 2, "images/Castel.png", squareWidth, squareHeight);
		  
	 }
	 
	 
	 
	 /**
	  * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
	  */
	 public void drawInfos() {
		 //Configuration de la police
		 StdDraw.setFont(font);
		 StdDraw.setPenColor(StdDraw.BLACK);
		 //Affichage de l'argent :
		 StdDraw.setPenRadius(0.01);
		 StdDraw.picture(1.03, 0.85, "images/gold.png");
		 StdDraw.text(1.07, 0.85, Integer.toString(money));
		 //Affichage des vies
		 StdDraw.picture(1.03, 0.80, "images/heart.png");
		 StdDraw.text(1.07, 0.80, Integer.toString(life));
		 //Affichage du numéro de la vague en cours
		 StdDraw.picture(1.03, 0.75, "images/wave.png");
		 StdDraw.text(1.07, 0.75, Integer.toString(waves.nbWaves-waves.waveCounter));
	 }
	 
	 
	 
	 /**
	  * Affichage des boutons au niveau du menu
	  */
	 public void drawButtons() {
		 for (Button b : buttons)
			 b.draw();
	 }
	 
	 
	 /**
	  * Affichage du texte du menu
	  */
	 public void drawMenuText() {
		 StdDraw.setFont(font);
		 StdDraw.setPenColor(StdDraw.BLACK);
		 StdDraw.picture(1.125, 0.95, "images/menu.png", 0.2, 0.07);
		 StdDraw.text(1.15, 0.65, "Archer Tower (cost 50g)");
		 StdDraw.text(1.15, 0.55, "Bomb Tower (cost 60g)");
		 StdDraw.text(1.15, 0.45, "Update Tower (cost 40g)");
		 StdDraw.text(1.15, 0.35, "Top up a tower");
		 StdDraw.text(1.13, 0.3, "Cost : 100g (Archer Tower)");
		 StdDraw.text(1.13, 0.25, "Cost : 50g (Bomb  Tower) ");
		 StdDraw.text(1.05, 0.17, "Message :");
		 StdDraw.text(1.125, 0.12, message);
	 }
	 
	 
	 /**
	  * Affichage du menu
	  */
	 public void drawMenu() {
		 actionRealized();
		 StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
		 StdDraw.filledRectangle(1.125, 0.5, 0.125, 0.5);
		 drawInfos();
		 drawButtons();
		 drawMenuText();
	 }
	 
	 
	 
	 /**
	  * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
	  *	lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
	  */
	 public void drawMouse() {
		double normalizedX = (int)(StdDraw.mouseX() / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(StdDraw.mouseY() / squareHeight) * squareHeight + squareHeight / 2;
		Position mouse = new Position(normalizedX, normalizedY);
		StdDraw.setPenColor(255, 255, 255);
		StdDraw.setPenRadius(0.002);
		
		//Set des positions où l'on ne peut pas construire de tours
		TreeSet<Position> positionst = new TreeSet<Position>();
		for (Tower t : towers) {
			positionst.add(t.getP());
		}
		switch (action) {
		case 'a' :
			if (!path.contains(mouse) && !positionst.contains(mouse) && mouse.getX()<=1) {
				StdDraw.circle(normalizedX, normalizedY, ArcherTower.REACH);
				StdDraw.picture(normalizedX, normalizedY,  "images/ArcherTowerLevel1.png", squareWidth, squareHeight);
			}
			break;
		case 'b' :
			if (!path.contains(mouse) && !positionst.contains(mouse) && mouse.getX()<=1) {
				StdDraw.circle(normalizedX, normalizedY, BombTower.REACH);
				StdDraw.picture(normalizedX, normalizedY,  "images/BombTowerLevel1.png", squareWidth, squareHeight);
			}
			break;
		case 'u': 
			//indique par une flèche les tours qui peuvent être évoluées
			for (Tower t: towers)
				if (t.getLevel()==1 || t.getLevel()==2)
					StdDraw.picture(t.getP().getX(), t.getP().getY(), String.format("images/up%d.png", t.getLevel()));
			break;
		}
	 }
	
	 
	 
	 /**
	  * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction update() qui appelle les fonctions run() et draw() de Monster.
	  * Modifie la position du monstre au cours du temps à l'aide du paramètre nextP.
	  */
	 public void updateMonsters() {
		Iterator<Monster> it = monsters.iterator();
		Monster m;
		while (it.hasNext()) {
			 m = it.next();
			 //on cherche la prochaine position du monstre dans le chemin
			 if (pathMonsters.contains(m.getP())) {
				 int i = pathMonsters.indexOf(m.getP()) + 1;
				 if (i<pathMonsters.size())
					 m.setNextP(pathMonsters.get(i));
				 else {
					 m.setReached(true);
				 }
			 }
			 if (!m.getReached() && !m.isDead()) m.update(squareWidth, squareHeight);
			 else {
				 //suppression du monstre
				 if (m.isDead()) this.money += m.getReward();
				 if (m.getReached()) life--;
				 it.remove();
			 }
		 }
	 }
	 
	 
	 
	 /**
	  * Pour chaque tour, on vérifie si la tour doit attaquer ou pas
	  */
	 public void updateTowers() {
		 Iterator<Tower> it = towers.iterator();
		 Tower t;
		 while (it.hasNext()) {
			 t = it.next();
			 //cherche un monstre qui est dans sa zone de tir
			 Iterator<Monster> itm = monsters.iterator();
			 boolean find = false;
			 Missile missile = null;
			 while (itm.hasNext() && !find){
				 Monster m = itm.next();
				 missile = t.attack(m);
				 find = missile!=null;
			 }
			 if (find) missiles.add(missile);
			 t.update(squareWidth, squareHeight);
		 }
	 }
	 
	 
	 
	 /**
	  * Met à jour les projectiles : les faits avancer et les détruits si besoin
	  */
	 public void updateMissiles(){
		 Iterator<Missile> it = missiles.iterator();
		 Missile msl;
		 while (it.hasNext()) {
			 msl = it.next();
			 if (msl.getP().equals(msl.getTarget().getP(), 0.01)) {
				 msl.hit();
				 it.remove();
			 }
			 else msl.update(squareWidth, squareHeight);
		 }
	 }
	 
	 
	 
	 /**
	  * Met à jour les boutons
	  */
	 public void updateButtons() {
		 for (Button b : buttons) {
			 if (b.isClicked()) action = b.getAction();
		 }
	 }
	 
	 
	 
	 /**
	  * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
	  */
	 public void updateGame() {
		drawBackground();
		drawMenu();
		drawPath();
		updateButtons();
	 }
	 
	 
	 public int updateCharacter() {
		updateMonsters();
		updateTowers();
		updateMissiles();
		drawMouse();
		return life;
	 }

	 
	 
	/**
	 * Récupère l'action choisie par l'utilisateur et affiche les informations pour la touche séléctionnée
	 */
	public void actionRealized() {
		action = Character.toLowerCase(action);
		switch (action) {
		case 'a':
			message = "Arrow Tower selected";
			break;
		case 'b':
			message = "Bomb Tower selected";
			break;
		case 'u':
			message = "Evolution selected";
			break;
		case 'r':
			message = "Recharging";
			break;
		case 's':
			message = "Starting game!";
		case 'e':
			message = "Exiting";
		}
	}
	
	
	
	/**
	 * Vérifie lorsque l'utilisateur clique sur sa souris qu'il peut: 
	 * 		- Ajouter une tour à la position indiquée par la souris.
	 * 		- Améliorer une tour existante.
	 * Puis l'ajouter à la liste des tours
	 * @param x
	 * @param y
	 */
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		Position mouse = new Position(normalizedX, normalizedY);
		
		//Set des positions où l'on ne peut pas construire de tours
		TreeMap<Position, Tower> positions = new TreeMap<Position, Tower>();
		for (Tower t : towers) {
			positions.put(t.getP(), t);
		}
		switch (action) {
		case 'a':
			if (!path.contains(mouse) && !positions.containsKey(mouse) && mouse.getX()<1) {
				if (this.money>=ArcherTower.PRICE) {
					towers.add(new ArcherTower(mouse));
					this.money-=ArcherTower.PRICE;
				}
				else alertMessage = "You haven't got enought money!";
			}
			break;
		case 'b':
			if (!path.contains(mouse) && !positions.containsKey(mouse) && mouse.getX()<1) {
				if (this.money>=BombTower.PRICE) {
					towers.add(new BombTower(mouse));
					this.money-=BombTower.PRICE;
				}
				else alertMessage = "You haven't got enought money!";
				
			}
			break;
		case 'u':
			if (positions.containsKey(mouse) && mouse.getX()<1) {
				Tower t = positions.get(mouse);
				if (this.money>=Tower.UPDATEPRICE && t.isUpdatable()){
					t.updating();
					this.money -= Tower.UPDATEPRICE;
				}
				else alertMessage = "The tower can't be updated!";
			}
			break;
		case 'r':
			if (positions.containsKey(mouse)) {
				Tower t = positions.get(mouse);
				if (this.money>=t.getRechargingPrice()) {
					t.recharge();
					this.money -=t.getRechargingPrice();
				}
				else alertMessage = "You haven't got enought money!";
			}
			break;
		}
		StdDraw.pause(50);
	}
	
	
	
	/**
	 * Fonction qui permet de gérer les différentes vagues de monstres
	 */
	public void controleWaves() {
		Monster m = waves.createMonster(spawn.clone());
		if (m!=null) {
			monsters.add(m);
		}
		else {
			if (waves.endWave() && monsters.size()==0) {
				waves.newWave();
			}
		}
	}
	
	
	/**
	 * 
	 */
	public void start() {
		if (!start) {
			waves.newWave();
			start = false;
			canChangePath = false;
		}
	}
	
	public void changePath() {
		if (canChangePath)
			initPath();
	}
	
	public void pause() {
		//TODO : à finir
	}
	
	/**
	 * Récupère la touche entrée au clavier ainsi que la position de la souris et met à jour le plateau en fonction de ces interractions
	 */
	public void run() {
		while(!end) {	
			StdDraw.clear();
//			if (start) {
				if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				}
				controleWaves();
				updateGame();
				end = updateCharacter()==0 || action=='e'|| (waves.end() && monsters.size()==0);
//			}
//			updateGame();
			StdDraw.show();
			StdDraw.pause(20);
		}
		//fin du jeu
		boolean victory = waves.end() && monsters.size()==0;
		end(victory);
	}
	
	/**
	 * Affiche une image en fin de partie
	 * @param victory un boolean : vrai si on gagne, faux sinon
	 */
	public void end(boolean victory) {
		String image = "defeat";
		if (victory) image = "victory";
		StdDraw.picture(0.5, 0.5, "/images/"+image+".png");
		StdDraw.show();
		StdDraw.pause(3000);
	}
}
