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
	 * Met à jour le monstre à l'échelle vis-à-vis des autres images
	 * @param normalizedX l'échelle des x
	 * @param normalizedY l'échelle des y
	 */
	@Override
	public void update(double normalizedX, double normalizedY, boolean run) {
		super.update(normalizedX, normalizedY, run);
	}
	
	//TODO : faire la javadoc !
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
	
	
	public abstract boolean canBeAttackBy(Tower t);
	/*
	 * Les méthodes abstraites
	 */
	protected abstract void setLife(int level);
	protected abstract void setReward(int level);
	protected abstract double setSpeed(int level);
}
