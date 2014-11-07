package me.justin97530.BasicCmds;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.plugin.Plugin;
import org.granitemc.granite.api.plugin.PluginContainer;

@Plugin(name = "BasicCmds", id = "basiccmds", version = "0.0.2")
public class BasicCmds {
	public static Map<String, String> ReplyList = new HashMap<String, String>();
	Logger logger = Granite.getLogger();
	
	public BasicCmds() {
		PluginContainer plugin = Granite.getPluginContainer(this);
		plugin.registerCommandHandler(new BasicCommand());
		plugin.registerEventHandler(new BasicEvents());
	}
}
