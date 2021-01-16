package warcraftTD.towers;

import warcraftTD.missiles.Missile;
import warcraftTD.monsters.Monster;
import warcraftTD.util.Position;
import warcraftTD.util.StdDraw;

public abstract class Tower {
	//position du tour à l'instant t
	private final Position p;
	//niveau de la tour
	private int level;
	//chemins des tours à afficher
	private String image;
	//vitesse d'attaque de la tour
	protected int speedReacharging;
	//portée de l'attaque de la tour
	protected double reach;
	//vitesse des projectiles
	protected double speedMissile;
	//temps de rechargement des projectiles
	protected long time;
	//nombre de projectiles dans la tour
	protected int nbMissile;
	//prix d'un rechargement de projectiles
	private int rechargingPrice;
	//prix amélioration d'une tour
	public final static int UPDATEPRICE = 40;

	/**
	 * Classe abstraite qui permet de gérer les différentes types de tours 
	 * @param p la Position de la tour
	 * @param image l'url de l'image de la tour
	 * @param speed la vitesse de rechargement de la tour
	 * @param price le prix de production de la tour
	 * @param reach la distance de visée de la tour
	 */
	public Tower(Position p, String image, int speedReacharging, int nbMissiles, int rechargingPrice, double reach) {
		this.p = p;
		this.speedReacharging = speedReacharging*25;
		this.setImage(image);
		this.reach = reach;
		this.time = System.currentTimeMillis();
		this.setLevel(1);
		this.nbMissile = nbMissiles;
		this.rechargingPrice = rechargingPrice;
	}
	
	/**
	 * Dessine une image de la tour dans le cadrillage
	 * @param normalizedX la taille en largeur d'un carré du cadrillage
	 * @param normalizedY la taille en longeur d'un carré du cadrillage
	 */
	public void update(double normalizedX, double normalizedY) {
		draw(normalizedX, normalizedY);
	}
	
	/**
	 * Attaque le monstre m
	 * @param m le monstre pris en cible
	 * @return un projectile en direction du montre
	 * @note le projectile est celui défini dans throwMissile
	 */
	public Missile attack(Monster m) {
		if (m.canBeAttackBy(this) && this.nbMissile>0) {
			long tps = System.currentTimeMillis();
			if (tps-this.time>this.speedReacharging) {
					if (m.getP().dist(this.getP())<=this.reach){
						this.time = tps;
						this.nbMissile--;
						return throwMissile(m);
					}
				}
		}
		return null;
	}
	
	/**
	 * Dessin la tour dans le canevas à l'échelle normalizedX et normalizedY
	 * @param normalizedX 
	 * @param normalizedY
	 */
	public void draw(double normalizedX, double normalizedY) {
		StdDraw.picture(p.getX(), p.getY(), image, normalizedX, normalizedY);
		if (this.nbMissile<=0) StdDraw.picture(p.getX(), p.getY(), "images/noMoreMunitions.png", normalizedX/4, normalizedY/4);
	}
	
	/**
	 * Indique si la tour est améliorable ou non
	 * @return vrai si la tour est améliorable
	 */
	public boolean isUpdatable() {
		return level<3;
	}
	
	/**
	 * Augmente d'un niveau le niveau de la tour
	 */
	public abstract void updating();
	
	/**
	 * Recharge la tour en munitions
	 */
	public abstract void recharge();
	
	
	
	
	/**
	 * Lance un projectile en direction du monstre depuis la position de la tour
	 * @param m un montre
	 * @return un projectile en direction du monstre
	 */
	protected abstract Missile throwMissile(Monster target);

	/*
	 * Getteur et setteur
	 */
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Position getP() {
		return p;
	}
	
	protected void setnBMissile(int nbMissile) {
		this.nbMissile = nbMissile;
	}

	public int getRechargingPrice() {
		return this.rechargingPrice;
	}
}
