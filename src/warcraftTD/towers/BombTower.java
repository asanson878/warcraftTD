package warcraftTD.towers;

import warcraftTD.missiles.Bomb;
import warcraftTD.missiles.Missile;
import warcraftTD.monsters.Monster;
import warcraftTD.util.Position;

public class BombTower extends Tower{
	public static final String IMAGE = "images/BombTowerLevel1.png";
	public static final int SPEEDREACHARGING = 20;
	public static final int PRICE = 60;
	public static final double  REACH = 0.10;
	private static final int NBMISSILES = 150;
	private static final int RECHARGINGPRICE = 50;

	/**
	 * Classe des tours de Bombes
	 * @param p la position de la tour
	 */
	public BombTower(Position p) {
		super(p,IMAGE, SPEEDREACHARGING, NBMISSILES, RECHARGINGPRICE, REACH);
	}
	
	@Override
	protected Missile throwMissile(Monster target) {
		return new Bomb(this.getP().clone(), target);
	}
	
	@Override
	public void updating() {
		this.setLevel(this.getLevel()+1);
		this.setImage(String.format("images/BombTowerLevel%d.png", this.getLevel()));
		this.speedReacharging -= (SPEEDREACHARGING/3)*5;
		this.reach += 0.02;
	}

	@Override
	public void recharge() {
		this.setnBMissile(NBMISSILES);		
	}
	
	
	
}
