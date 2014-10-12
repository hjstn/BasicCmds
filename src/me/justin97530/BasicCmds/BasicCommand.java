package me.justin97530.BasicCmds;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.command.Command;
import org.granitemc.granite.api.command.CommandInfo;
import org.granitemc.granite.api.plugin.PluginContainer;

public class BasicCommand {
	@Command(name = "plugins", info = "Lists all plugins, Bukkit style", aliases = {"pl", "plugin"})
	public void onCommandPlugin(CommandInfo i) {
		List<PluginContainer> plugins = Granite.getPlugins();
		List<String> pluginName = new ArrayList<String>();
		for(int in = 0; in < plugins.size(); in++) {
			pluginName.add(plugins.get(in).getName() + " (" + plugins.get(in).getVersion() + ")");
		}
		i.getCommandSender().sendMessage("Plugins (" + pluginName.size() + "): " + StringUtils.join(pluginName, ", "));
	}
}
