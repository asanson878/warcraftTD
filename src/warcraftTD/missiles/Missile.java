package warcraftTD.missiles;

import warcraftTD.monsters.Monster;
import warcraftTD.util.ImageMobile;
import warcraftTD.util.Position;
import warcraftTD.util.StdDraw;


public abstract class Missile extends ImageMobile{
	//cible visée par le projectil
	private Monster target;
	//dommages créés par le projectile lorsqu'il atteint la cible
	private int damage;
	
	
	/**
	 * Classe abstraite qui permet de gérer les projectiles
	 * @param p la position du projectile
	 * @param target le monstre visé par le projectile
	 * @param speed la vitesse du projectile
	 * @param image le chemin du projectile
	 * @param damage les dommages infligée par le projectile
	 */
	public Missile(Position p, Monster target, double speed, String image, int damage) {
		super(image, p, target.getP(), speed);
		this.target = target;
		this.damage = damage;
	}

	
	@Override
	public void draw(double normalizedX, double normalizedY){
		Position p = this.getP();
		double angle = super.calculateAngle(p, this.getNextP());
		StdDraw.picture(p.getX(), p.getY(), this.getImage(), normalizedX, normalizedY, angle);
	}
	
	
	/**
	 * Enlève des points de vies au monstre touché
	 */
	public void hit() {
		this.target.takeLifePoint(damage);
	}
	/**
	 * retourn le monstre à attaquer (cible)
	 */
	public Monster getTarget() {
		return target;
	}

	/*
	 * Getteurs et Setteurs
	 */
	public void setTarget(Monster target) {
		this.target = target;
	}

	
	public int getDamage() {
		return damage;
	}


	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
