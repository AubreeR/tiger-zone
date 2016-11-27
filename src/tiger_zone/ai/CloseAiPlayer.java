package tiger_zone.ai;

import java.util.List;
import java.util.Map;

import tiger_zone.Game;
import tiger_zone.Player;
import tiger_zone.Position;
import tiger_zone.Tiger;
import tiger_zone.Tile;

/**
 * A sample AiPlayer which places tiles as close to the origin as possible.
 */
public class CloseAiPlayer extends AiPlayer {
	public CloseAiPlayer(Game game) {
		super(game);
	}
	
	public final void makeMove() {
		final Tile current = this.game.getCurrentTile();
		
		final Map<Position, List<Integer>> validTilePlacements = this.game.getBoard().getValidTilePlacements(current);

		// no possible move; simply pass our turn (not what's actually supposed to happen)
		if (validTilePlacements.isEmpty()) {
			System.out.println("no moves, skipping :(");
			return;
		}

		// Get closest placement to origin
		double minDistance = Double.MAX_VALUE;
		Position closest = null;
		Position origin = new Position(0, 0);
		for (Position p : validTilePlacements.keySet()) {
			if (origin.distance(p) < minDistance) {
				minDistance = origin.distance(p);
				closest = p;
			}
		}
		
		final int rotation = validTilePlacements.get(closest).get(0);

		// Rotate tile until we get desired rotation
		while (current.getRotation() != rotation) {
			current.rotate();
		}
		
		game.getBoard().addTile(closest, current);
		
		final Player currentPlayer = this.getPlayer();
		
		int i = -1;
		
		// If this tile has a den, place a tiger on it if we have one available.
		if (current.hasDen() && currentPlayer.getTigers().size() > 0) {
			Tiger tiger = currentPlayer.getTigers().pop();
			current.addTiger(5, tiger);
			i = 5;
		}
		else if(current.hasAnimal()) {
			for (i = 1; i < 10; i++) {
				if (current.getZone(i) == 'l') {
					boolean isValid = this.game.getBoard().validTigerPlacement(closest, i, false);
					if (isValid && currentPlayer.getTigers().size() > 0) {
						Tiger tiger = currentPlayer.getTigers().pop();
						current.addTiger(i, tiger);
						break;
					}
				}
			}
			i = (i == 10) ? -1 : i;
		}
	}
}
