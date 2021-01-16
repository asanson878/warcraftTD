package warcraftTD;
import java.util.Random;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.monsters.Boss;
import warcraftTD.monsters.FlyingMonster;
import warcraftTD.monsters.Monster;
import warcraftTD.util.Position;

public class Waves {
	private class Wave{
		// comteur de temps d'apparition entre chaque monstres;
		long timeMonsters;
		//temps pour générer un monstre
		int timesGenerateMonsters;
		//miveaux des monstres
		int levels;
		//nombre de monstres à créer;
		int nbMonsters;
		//
		boolean premier;

		/*
		 * Classe privée qui s'occupe d'une vague de monstres
		 */
		public Wave(int levels, int nbMonsters){
			this.levels = levels;
			this.nbMonsters = nbMonsters;
			this.timeMonsters = System.currentTimeMillis();
			this.timesGenerateMonsters = (new Random()).nextInt(1000)+500;
			this.premier = nbMonsters>1;
		}
		
		/*
		 * Créer un montres de type type à la position beginMonster
		 */
		public Monster createMonster(char type, Position beginMonster) {
			Monster m = null;
			long time = System.currentTimeMillis();
			if (!isEmpty() && (time-this.timeMonsters>this.timesGenerateMonsters || this.premier)) {
				if (this.premier) this.premier = false;
				switch (type) {
				case 'w':
					m = new BaseMonster(beginMonster, this.levels);
					break;
				case 'f':
					m = new FlyingMonster(beginMonster, this.levels);
					break;
				case 'b':
					m = new Boss(beginMonster, this.levels);
					break;
				}
				int mintimes = 500-50*(waveCounter-1)<=5? 5 : 500-50*(waveCounter-1);
				this.timesGenerateMonsters = mintimes;
				this.timeMonsters = time;
				this.nbMonsters--;
			}
			return m;
		}
		 /*
		  * Indique si la vague est termniée
		  */
		public boolean isEmpty() {
			return this.nbMonsters<=0;
		}
	}

	// nombre de vagues
	int nbWaves;
	// nombre de monstres
	int nbMonster;
	int waveCounter;
	//vague courant
	Wave wave;
	//niveau des monstres
	int level;
	//indique si la vague est une vague de boss
	boolean boss;
	

	/**
	 * Classe qui gèrent les vagues du jeu
	 * @param nbWaves le nombre de vagues dans le jeu
	 */
	public Waves(int nbWaves) {
		this.nbWaves = nbWaves + 1;
		this.nbMonster = 3;
		this.waveCounter = 0;
		this.level = 1;
	}

	/**
	 * Créer une nouvelle vague
	 */
	public void newWave() {
		this.boss = this.waveCounter!=0 && this.waveCounter%5==0;
		int nbMonster = this.nbMonster;
		if (this.boss) nbMonster = 1;
		this.wave = new Wave(level, nbMonster);
		if (this.boss) level++;
		this.waveCounter++;
		this.nbMonster += 10*this.level;
	}
	
	/**
	 * Créer un nouveau monstre (un boss au bout de 5 vagues et un monstre marchant ou volant aléatoirement)
	 * @param beginMonster la position de départ du monstre
	 * @return le monstre créer
	 */
	public Monster createMonster(Position beginMonster) {
		char c;
		if (!this.boss){
			Random rd = new Random();
			c = "wf".charAt(rd.nextInt("wf".length()));
		}
		else c = 'b';
		return this.wave.createMonster(c, beginMonster);
	}
	
	/**
	 * Indique si les vagues de monstres sont finis (fin du jeu)
	 * @return true si et seuelement toutes les vagues sont terminées
	 */
	public boolean end() {
		return this.waveCounter == this.nbWaves && this.wave.isEmpty();
	}
	
	/**
	 * Indique si une vague de monstre est terminée
	 * @return true si et seulement si la vague en cours est terminée
	 */
	public boolean endWave() {
		return wave.isEmpty();
	}
	
}