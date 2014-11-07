package me.justin97530.BasicCmds;

import org.granitemc.granite.api.event.On;
import org.granitemc.granite.api.event.player.EventPlayerQuit;

public class BasicEvents {
	@On(event = EventPlayerQuit.class)
	public void event(EventPlayerQuit e) {
		BasicCmds.ReplyList.remove(e.getPlayer().getName());
	}
}
