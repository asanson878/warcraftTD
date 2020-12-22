package warcraftTD;

public class BombTower extends Tower{
	public BombTower(Position p) {
		super(p, "images/BombTower.png", 20, 60, 0.2);
	}
	
	protected boolean attackTo(Monster m) {
		return m.p.equals(this.p, 0.1);
	}
}
