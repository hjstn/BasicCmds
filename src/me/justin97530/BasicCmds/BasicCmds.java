package me.justin97530.BasicCmds;

import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.plugin.Plugin;
import org.granitemc.granite.api.plugin.PluginContainer;

@Plugin(name = "BasicCmds", id = "basiccmds", version = "1.0")
public class BasicCmds {
	public BasicCmds() {
		PluginContainer plugin = Granite.getPluginContainer(this);
		plugin.registerCommandHandler(new BasicCommand());
	}
}
