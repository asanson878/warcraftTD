package warcraftTD;

public abstract class Tower {
	//position du tour à l'instant t
	Position p;
	//vitesse d'attaque de la tour
	int speed;
	//portée de l'attaque de la tour
	double reach;
	//prix de contruction d'une tour
	int price;
	//vitesse des projectiles
	double speedMissile;
	//chemins des tours à afficher
	String chemin;
	
	public Tower(Position p, String chemin, int speed, int price, double reach) {
		this.p = p;
		this.speed = speed;
		this.price = price;
		this.chemin = chemin;
		this.reach = reach;
	}
	
	public void update(double normalizedX, double normalizedY) {
		draw(normalizedX, normalizedY);
	}
	
	/**
	 * Lance un missile en direction du monster m
	 * @param m le monstre pris en cible
	 */
	protected void attack(Monster m, String chemin) {
		StdDraw.picture(p.x, p.y, chemin);
	}
	
	protected abstract boolean attackTo(Monster m);
	
	public void draw(double normalizedX, double normalizedY) {
		StdDraw.picture(p.x, p.y, chemin, normalizedX, normalizedY);
	}
}
