package tiger_zone.ai;

import java.util.List;
import java.util.Map;

import tiger_zone.Game;
import tiger_zone.Player;
import tiger_zone.Position;
import tiger_zone.Tiger;
import tiger_zone.Tile;
import tiger_zone.action.Action;
import tiger_zone.action.PassAction;
import tiger_zone.action.TigerPlacementAction;
import tiger_zone.action.TilePlacementAction;

/**
 * A sample AiPlayer which places tiles as close to the origin as possible.
 */
public class CloseAiPlayer extends AiPlayer {
	private Tile latestTile;
	private Position latestPosition; 
	private int latestRot;
	private int latestZone; 
	
	public CloseAiPlayer(Game game) {
		super(game);
	}
	
	public final Action makeMove() {
		final Tile current = this.game.getCurrentTile();
		
		final Map<Position, List<Integer>> validTilePlacements = this.game.getBoard().getValidTilePlacements(current);

		// no possible move; simply pass our turn (not what's actually supposed to happen)
		if (validTilePlacements.isEmpty()) {
			return new PassAction();
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
		setLatestTile(current);
		setLatestPosition(closest);
		setLatestRot(current.getRotation()); 
		
		final Player currentPlayer = this.getPlayer();
		
		int i = -1;
		
		// If this tile has a den, place a tiger on it if we have one available.
		if (current.hasDen() && currentPlayer.getTigers().size() > 0) {
			Tiger tiger = currentPlayer.getTigers().pop();
			current.addTiger(5, tiger);
			setLatestZone(5);
			i = 5;
		}
		else if (current.hasAnimal()) {
			for (i = 1; i < 10; i++) {
				boolean isValid = this.game.getBoard().validTigerPlacement(closest, i, false);
				if (isValid && currentPlayer.getTigers().size() > 0) {
					Tiger tiger = currentPlayer.getTigers().pop();
					current.addTiger(i, tiger);
					setLatestZone(i); 
					break;
				}
			}
			i = (i == 10) ? -1 : i;
		}
		
		if (i >= 1 && i <= 9) {
			return new TigerPlacementAction(closest, current, i);
		}
		return new TilePlacementAction(closest, current);
	}

	public Tile getLatestTile() {
		return latestTile;
	}

	public void setLatestTile(Tile latestTile) {
		this.latestTile = latestTile;
	}

	public Position getLatestPosition() {
		return latestPosition;
	}

	public void setLatestPosition(Position latestPosition) {
		this.latestPosition = latestPosition;
	}

	public int getLatestRot() {
		return latestRot;
	}

	public void setLatestRot(int latestRot) {
		this.latestRot = latestRot;
	}

	public int getLatestZone() {
		return latestZone;
	}

	public void setLatestZone(int latestZone) {
		this.latestZone = latestZone;
	}
}
