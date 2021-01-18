package warcraftTD.monsters;

import warcraftTD.towers.Tower;
import warcraftTD.util.ImageMobile;
import warcraftTD.util.Position;

public abstract class Monster extends ImageMobile {
	// Boolean pour savoir si le monstre à atteint le chateau du joueur
	private boolean reached;
	//Recompense en or lorsque l'on tue le montre
	protected int reward;
	//Nombre de vies du monstre
	protected int life;

	
	/**
	 * Classe abstraite qui gèrent les monstres
	 * @param image le chemin l'image dans les fichiers
	 * @param p la position de l'image
	 * @param life le nombre de points de vie du monstre
	 * @param speed la vitesse du monstre
	 * @param reward la récompense en or lorsque l'on tue le monstre
	 */
	public Monster(String image, Position p, int level) {
		super(image, p, p.clone());
		this.setLife(level);
		this.setReward(level);
		super.setSpeed(this.setSpeed(level));
	}
	
	/**
	 * Indique si le monstre est mort
	 * @return vrai si et seulement si la vie du monstre est nulle
	 */
	public boolean isDead(){
		return (life==0);
	}

	/**
	 * Enlève un point de vie au monstre
	 * @param life le nombre de point de vie au début de la vie du monstre
	 */
	public void takeLifePoint(int damage){
		this.life -= damage;
		if (this.life<0) this.life = 0;
	}

	/**
	 * A appeler une fois que le monstre est mort
	 * @return le nombre de point de vies en moins que le boss prend s'il atteint le chateau
	 */
	public int minusLPPlayer(){
		return 1;
	}
	
	@Override
	public String toString() {
		return this.life + " " + this.reward + " " + this.getSpeed();
	}
	
	/*
	 * Getteur et setteur
	 */
	public boolean getReached() {
		return reached;
	}

	public int getReward() {
		return reward;
	}
		
	public void setReached(boolean reached) {
		this.reached = reached;
	}
	
	

	/*
	 * Les méthodes abstraites
	 */

	/**
	 * Indique si le monstre peut être attaqué par la tour t
	 * @param t une tour
	 */
	public abstract boolean canBeAttackBy(Tower t);
	
	/**
	 * Initialise le nombre de points de vies du monstre en fonction de son niveau
	 * @param level le niveau du monstre
	 */
	protected abstract void setLife(int level);

	/**
	 * Initialise la récompense lorsque l'on tue le monstre en fonction de son niveau
	 * @param level le niveau du monstre
	 */
	protected abstract void setReward(int level);

	/**
	 * Initialise la vitesse du monstre en fonction de son niveau
	 * @param level le niveau du monstre
	 * @return la vitesse du monstre
	 */
	protected abstract double setSpeed(int level);

	/**
	 * Donne un bonus de vie lorsque l'on tue le monstre
	 * @return le nombre de points de vie en bonus
	 */
	public abstract int bonusLPPlayer();
}
