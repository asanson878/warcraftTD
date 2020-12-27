package warcraftTD.missiles;

import warcraftTD.monsters.Monster;
import warcraftTD.util.Position;

public class Arrow extends Missile {
    public Arrow(Position p, Monster target){
        super(p, target,  0.04, "images/Arrow.png", 2);
    }
}

