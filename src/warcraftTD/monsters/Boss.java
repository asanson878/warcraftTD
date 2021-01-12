package warcraftTD.monsters;

import warcraftTD.towers.ArcherTower;
import warcraftTD.towers.Tower;
import warcraftTD.util.Position;
import warcraftTD.util.StdDraw;

public class Boss extends Monster {
    private static final String IMAGEF = "images/Boss.png";
    private static final String IMAGEW = "images/Bomb.png";

    public String image;
    protected long time;
    public char state;

    public Boss(Position p, int level) {
        super(IMAGEF, p, level>3?3:level);
        this.state = 'f';
        this.time = System.currentTimeMillis();
    }
    //TODO : faire des javadoc
    @Override
    protected void setReward(int level) {
        this.reward = 0;
        for (int i=1; i<=level; i++) this.reward +=60;
    }

    @Override
    protected double setSpeed(int level) {
        double speed = 0.005;
        for (int i=1; i<level; i++) speed+=0.003;
        return speed;
    }
    
    @Override
    protected void setLife(int level) {
        switch (level) {
        case 1:
        	life = 100;
        	break;
        case 2:
        	life = 150;
        	break;
        case 3:
        	life = 200;
        	break;
        default:
        	throw new IllegalArgumentException("Level must be between 1 et 3");
        }
    }
	@Override
	public boolean canBeAttackBy(Tower t) {
		return (this.state=='w') || (this.state=='f' && t instanceof ArcherTower);
	}
    
    private String transform(){
            image = IMAGEW;
            this.state = 'w';
        return image;
    }
    @Override
    public void draw(double normalizedX, double normalizedY) {
        StdDraw.picture(getP().getX(), getP().getY(), transform(), normalizedX, normalizedY);
   }
}