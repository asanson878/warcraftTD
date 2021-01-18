package warcraftTD.monsters;

import warcraftTD.towers.ArcherTower;
import warcraftTD.towers.Tower;
import warcraftTD.util.Position;

public class FlyingMonster extends Monster {
	public static final int LIFE = 3;
	public static final int REWARD = 8;
	public static final double SPEED = 0.01;
	public static final String IMAGE = "images/FlyingMonster.png";
	
	/**
	 * Classe qui gère les monstres volants
	 * @param p
	 */
	public FlyingMonster(Position p, int level) {
		super(IMAGE, p, (level>3?3:level));
	}

	@Override
	protected void setLife(int level) {
		this.life = LIFE;
		for (int i=2; i<=level; i++)
			this.life += LIFE;
	}

	@Override
	protected void setReward(int level) {
		this.reward = REWARD;
		for (int i=2; i<=level; i++) {
			this.reward += REWARD;
			i++;
		}
	}
	
	@Override
	protected double setSpeed(int level) {
		double speed = SPEED;
		for (int i=2; i<=level; i++)
			speed +=0.002;
		return speed;
	}
	
	@Override
	public boolean canBeAttackBy(Tower t) {
		return t instanceof ArcherTower;
	}

	@Override
	public int bonusLPPlayer() {
		return 0; //pas de bonus de vie
	}
}
