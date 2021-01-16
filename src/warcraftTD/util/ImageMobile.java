package warcraftTD.util;

abstract public class ImageMobile {
	//la position de l'image
	private Position p;
	//la position suivante (temporellement)  de l'image
	private Position nextP;
	//le chemin de l'image
	private String image;
	//vitesse de déplacement de l'image
	private double speed;
	
	/**
	 * Classe qui va permettre de gérer les images mobiles (comme les monstres, les projectiles)
	 * @param image url de l'image
	 * @param p la position de l'image
	 * @param nextP la position suivante (temporellement) de l'image
	 */
	public ImageMobile(String image, Position p,  Position nextP) {
		this.setP(p);
		this.setImage(image);
		this.setNextP(nextP);
	}
	
	/**
	 * Classe qui va permettre de gérer les images mobiles (comme les monstres, les projectiles)
	 * @param image url de l'image
	 * @param p la position de l'image
	 * @param nextP la position suivante (temporellement) de l'image 
	 * @param speed la vitesse de déplacement de l'image
	 */
	public ImageMobile(String image, Position p,  Position nextP, double speed) {
		this(image, p, nextP);
		this.setSpeed(speed);
	}
	
	
	/**
	 * Déplace l'image en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
	 */
	public void move() {
		double pX = p.getX(); double pY = p.getY();
		double nextpX = nextP.getX() ; double nextpY = nextP.getY();
		// Mesure sur quel axe l'image se dirige.
		double dx = nextpX - pX;
		double dy = nextpY - pY;
		if (dy + dx != 0){
			// Mesure la distance à laquelle l'image à pu se déplacer.
			double ratioX = dx/(Math.abs(dx) + Math.abs(dy));
			double ratioY = dy/(Math.abs(dx) + Math.abs(dy));
			double px = ratioX * speed + pX;
			double py = ratioY * speed + pY;
			
			//conditions pour voir si on a dépassé nextP
			if (dx>0 && px>nextpX) px = nextpX;
			else if (dy>0 && py>nextpY) py = nextpY;
			else if (dx<0 && px<nextpX) px = nextpX;
			else if (dy<0 && py<nextpY) py = nextpY;
			p.setX(px);
			p.setY(py);
		}	
	}
	
	/**
	 * Dessine une image dans le canevas à échelle des autres images
	 * @param normalizedX l'échelle des x
	 * @param normalizedY l'échelle des y
	 * @param run indique si l'image peut bouger
	 */
	public void update(double normalizedX, double normalizedY, boolean run) {
		if (run) move();
		draw(normalizedX, normalizedY);
	}
	
	/**
	 * Affiche une image normalisée en fonction de la taille de la fenetre
	 */
	public void draw(double normalizedX, double normalizedY) {
		StdDraw.picture(p.getX(), p.getY(), this.image, normalizedX, normalizedY);
	}
	

	/**
	 * Calcul l'angle du mouvement de l'image en fonction de l'axe des abscisses
	 * @param pInit la position initiale
	 * @param pFinal la position finale
	 * @return l'angle entre la droite passant par pInit et pFinal et entre l'axe des
	 * abscisses.
	 */
	public static double calculateAngle(Position pInit, Position pFinal) {
		double dx = pFinal.getX() - pInit.getX();
		double dy = pFinal.getY() - pInit.getY();
		double angle;
		if (dx==0) angle=dy>0?90:270;
		else {
			angle = (double) Math.toDegrees(Math.atan(dy/Math.abs(dx)));
			angle = (dx<0)? -(angle+180):angle;
		}
		return angle;
	}
	
	/**
	 * Calcul l'angle du mouvement de l'image en fonction de l'axe des abscisses
	 * @return l'angle entre la droite passant par this.p et this.pNext et entre
	 * l'axe des abscisses.
	 */
	public double calculateAngle() {
		return calculateAngle(this.p, this.nextP);
	}
	
	/**
	 * Getter et setter
	 */
	public Position getP() {
		return p;
	}

	public void setP(Position p) {
		this.p = p;
	}

	public Position getNextP() {
		return nextP;
	}

	public void setNextP(Position nextP) {
		this.nextP = nextP;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
