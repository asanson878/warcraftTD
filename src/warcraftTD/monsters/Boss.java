package warcraftTD.monsters;

import warcraftTD.towers.ArcherTower;
import warcraftTD.towers.BombTower;
import warcraftTD.towers.Tower;
import warcraftTD.util.Position;

public class Boss extends Monster {
    private static final String IMAGEF = "images/Boss.png";
    private static final String IMAGEW = "images/BossInFire.png";
    private static final long TRANSFORMTIME = 12000; // Le monstre se transforme au bout de 12 secondes
    private long time;
    private char state;

    /**
     * Constructeur du monstre
     * @param p la position initiale du monstre
     * @param level le niveau du monstre
     */
    public Boss(Position p, int level) {
        super(IMAGEF, p, level>3?3:level);
        this.state = 'f';
        this.setImage(IMAGEF);
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
        for (int i=1; i<level; i++) speed +=0.003;
        return speed;
    }
    
    
    @Override
    protected void setLife(int level) {
        switch (level) {
        case 1:
        	life = 250;
        	break;
        case 2:
        	life = 500;
        	break;
        case 3:
        	life = 1000;
        	break;
        default:
        	throw new IllegalArgumentException("Level must be between 1 et 3");
        }
    }
	
    
    @Override
	public boolean canBeAttackBy(Tower t) {
		transform();
		return (this.state=='w' && t instanceof BombTower) || (this.state=='f' && t instanceof ArcherTower);
	}
    
    
    @Override
    public int minusLPPlayer(){
        if (!this.getReached()) return -2; //bonus de point de vie 
        return 5;
    }
    
	/**
	 * Transforme le boss un autre type au bout de TRANSFORMTIME ms :
	 * 		- si le boss est marchant, alors il devient volant
	 * 		- s'il est volant, il devient marchant
	 */
    private void transform(){
    	long t = System.currentTimeMillis();
    	if (t-this.time>TRANSFORMTIME) {
    		this.setImage(this.getImage().equalsIgnoreCase(IMAGEF)?IMAGEW:IMAGEF);
    		this.state = state=='f'? 'w':'f';
    		this.time = t;
         }
    }
    @Override
    public void draw(double normalizedX, double normalizedY) {
    	transform();
        super.draw(normalizedX, normalizedY);
   }
}