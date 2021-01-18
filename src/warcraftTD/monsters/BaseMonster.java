package warcraftTD.monsters;

import warcraftTD.towers.Tower;
import warcraftTD.util.Position;


public class BaseMonster extends Monster {
	private static final int LIFE = 5;
	public static final int REWARD = 5;
	public static final double SPEED = 0.005;
	public static final String IMAGE = "images/BaseMonster.png";
	
	/**
	 * Classe qui gère les monstres qui marchent
	 * @param p une position
	 * @param level un entier compris entre 1 et 3
	 */
	public BaseMonster(Position p, int level) {
		super(IMAGE, p, (level>3?3:level));
	}

	@Override
	protected void setLife(int level) {
		this.life = 0;
		for (int i=1; i<=level; i++) this.life += LIFE;
		
	}

	@Override
	protected void setReward(int level) {
		this.reward = 0;
		for (int i=1; i<=level; i++) this.reward += REWARD;
	}

	@Override
	protected double setSpeed(int level) {
		double speed = SPEED;
		for (int i=2; i<=level; i++) speed += 0.004;
		return speed;
	}
	
	@Override
	public boolean canBeAttackBy(Tower t) {
		return true;
	}
	
	@Override
	public int minusLPPlayer() {
		return 2;
	}

	@Override
	public int bonusLPPlayer() {
		return 0; //pas de bonus de vie
	}
}
