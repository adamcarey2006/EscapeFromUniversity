package io.github.some_example_name;

import com.badlogic.gdx.Game;

/**
 * <code> MyGame </code> handles creating and presenting the game from
 * the different screens.
 * @see com.badlogic.gdx.Game Game
 * @see com.badlogic.gdx.Screen Screen
 */
//leaderboard system imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;

//leaderboard class
public class MyGame extends Game {

	private List<Map.Entry<String, Integer>> leaderboard;// leaderboard represented by list
	private String username = "";

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	/** Create game, starting at the menu score. */
	@Override
	public void create() {
		leaderboard = new ArrayList<>();
		int[] achLog = { 0, 0, 0, 0 };
		setScreen(new MenuScreen(this, achLog));
	}

	/**
	 * Add a score to the leaderboard & sort.
	 */
	public void addScore(String username, int score) {
		leaderboard.add(new AbstractMap.SimpleEntry<>(username, score));
		Collections.sort(leaderboard, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue()); // Sort by descending order
			}
		});
	}

	public List<Map.Entry<String, Integer>> getLeaderboard() {
		return leaderboard;
	}
}
