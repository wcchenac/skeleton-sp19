package creatures;

import huglife.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
	/**
	 * red color.
	 */
	private int r;
	/**
	 * green color.
	 */
	private int g;
	/**
	 * blue color.
	 */
	private int b;

	/**
	 * creates plip with energy equal to E.
	 */
	public Clorus(double e) {
		super("clorus");
		r = 34;
		g = 0;
		b = 231;
		energy = e;
	}

	public Color color() {
		return color(r, g, b);
	}

	public void attack(Creature c) {
		this.energy += c.energy();
	}

	public void move() {
		energy -= 0.03;
	}

	public void stay() {
		energy -= 0.01;
	}

	public Clorus replicate() {
		energy *= 0.5;
		return new Clorus(energy);
	}

	public Action chooseAction(Map<Direction, Occupant> neighbors) {

		Deque<Direction> emptyNeighbors = new ArrayDeque<>();
		Deque<Direction> plipNeighbors = new ArrayDeque<>();
		boolean anyPlip = false;

		//Use neighbors key set method which build up a set of key, and iterate the key to get empty occupant
		for (Direction d : neighbors.keySet()) {
			if (neighbors.get(d).name().equals("empty")) {
				emptyNeighbors.add(d);
			}
			if (neighbors.get(d).name().equals("plip")) {
				plipNeighbors.add(d);
				anyPlip = true;
			}
		}

		// Clorus action in order of preference
		if (emptyNeighbors.size() == 0) {               // Rule 1
			return new Action(Action.ActionType.STAY);
		} else {
			if (anyPlip) {                           // Rule 2
				return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
			} else {
				if (energy >= 1.0) {                     // Rule 3
					return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
				} else {                                        // Rule 4
					return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
				}
			}
		}
	}
}
