package tiger_zone.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JFrame;

import org.docopt.Docopt;

import tiger_zone.Board;
import tiger_zone.Game;
import tiger_zone.Protocol;
import tiger_zone.Player;
import tiger_zone.Position;
import tiger_zone.Tile;
import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.CloseAiPlayer;
import tiger_zone.ai.PoorAiPlayer;

public class Main {
	public static void displayGame(Game game) {
		BoardFrame bf = new BoardFrame(game);
		bf.setSize(900, 900);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}

	public static void main(String[] args) {
		final String cliDoc =
			    "Tiger Zone\n"
	    	    + "\n"
	    	    + "Usage:\n"
	    	    + "  tigerzone connect <ip> <port> <tournament-password> <username> <password>\n"
	    	    + "  tigerzone localduel\n"
	    	    + "  tigerzone (-h | --help)\n"
	    	    + "\n"
	    	    + "Options:\n"
	    	    + "  -h --help     Show this screen.\n"
	    	    + "\n";

		Map<String, Object> opts = new Docopt(cliDoc).parse(args);
		
		if ((boolean) opts.get("connect")) {
			long milli = System.currentTimeMillis();
			new Protocol((String) opts.get("<ip>"), Integer.parseInt((String) opts.get("<port>")),
					(String) opts.get("<tournament-password>"), (String) opts.get("<username>"), (String) opts.get("<password>"));
			System.out.println("Elapsed Time: " + (System.currentTimeMillis() - milli));
		}
		else if ((boolean) opts.get("localduel")) {
			Stack<Tile> pile = Board.createDefaultStack();
			Tile t = Board.tileMap.get("tltj-");

			Collections.shuffle(pile);
			Game game = new Game(pile);
			game.getBoard().addTileWithNoValidation(new Position(0, 0), t);

			List<Player> players = new ArrayList<Player>(2);
			players.add(new Player(0, "p1"));
			players.add(new Player(1, "p2"));
			game.setPlayers(players);

			List<AiPlayer> ai = new ArrayList<AiPlayer>();
			ai.add(new CloseAiPlayer(game));
			ai.add(new PoorAiPlayer(game));
			game.setAiPlayers(ai);

			while (!game.isOver()) {
				game.conductTurn();
			}

			Main.displayGame(game);
		}
	}
}
