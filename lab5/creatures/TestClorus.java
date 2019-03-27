package creatures;

import huglife.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TestClorus {

	@Test
	public void testBasic() {
		Clorus cl = new Clorus(2);
		cl.stay();
		assertEquals(1.99, cl.energy(), 0.01);
		cl.move();
		assertEquals(1.96, cl.energy(), 0.01);
	}

	@Test
	public void testReplicate() {
		Clorus cl = new Clorus(2);
		Clorus cl2 = cl.replicate();
		assertEquals(1, cl.energy(), 0.01);
	}

	@Test
	public void testChoose() {

		// No empty adjacent spaces; stay.
		Clorus cl = new Clorus(1.2);
		HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
		surrounded.put(Direction.TOP, new Impassible());
		surrounded.put(Direction.BOTTOM, new Impassible());
		surrounded.put(Direction.LEFT, new Impassible());
		surrounded.put(Direction.RIGHT, new Impassible());

		Action actual = cl.chooseAction(surrounded);
		Action expected = new Action(Action.ActionType.STAY);

		assertEquals(expected, actual);


		// Energy >= 1 with Plip; attack towards an plip space.
		cl = new Clorus(1.2);
		HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
		topPlip.put(Direction.TOP, new Plip());
		topPlip.put(Direction.BOTTOM, new Empty());
		topPlip.put(Direction.LEFT, new Impassible());
		topPlip.put(Direction.RIGHT, new Impassible());

		actual = cl.chooseAction(topPlip);
		expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

		assertEquals(expected, actual);


		// Energy >= 1 w/o Plip; replicate towards an empty space.
		cl = new Clorus(1.2);
		HashMap<Direction, Occupant> noPlip = new HashMap<Direction, Occupant>();
		noPlip.put(Direction.TOP, new Empty());
		noPlip.put(Direction.BOTTOM, new Empty());
		noPlip.put(Direction.LEFT, new Empty());
		noPlip.put(Direction.RIGHT, new Empty());

		actual = cl.chooseAction(noPlip);
		Action unexpected = new Action(Action.ActionType.STAY);

		assertNotEquals(unexpected, actual);


		// Energy < 1; move.
		cl = new Clorus(.99);

		actual = cl.chooseAction(noPlip);
		expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);

		assertEquals(expected, actual);
		}
	}
