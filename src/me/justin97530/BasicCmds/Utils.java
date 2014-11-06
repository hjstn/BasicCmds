package me.justin97530.BasicCmds;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.entity.player.Player;

public class Utils {
	public static Player getPlayer(String username) {
		Player player = null;
		for(Iterator<Player> it = Granite.getServer().getPlayers().iterator(); it.hasNext();) {
			Player currentPlayer = it.next();
			String name = currentPlayer.getName();
			int bestMatch = -1;
			int levenshtein = StringUtils.getLevenshteinDistance(username, name);
			if(levenshtein < 7 && (bestMatch == -1 || bestMatch > levenshtein)) {
				bestMatch = levenshtein;
				player = currentPlayer;
			}
		}
		return player;
	}
}
