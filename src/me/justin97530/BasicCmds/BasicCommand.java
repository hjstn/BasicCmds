package me.justin97530.BasicCmds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.command.Command;
import org.granitemc.granite.api.command.CommandContainer;
import org.granitemc.granite.api.command.CommandInfo;
import org.granitemc.granite.api.command.CommandSender;
import org.granitemc.granite.api.plugin.PluginContainer;

public class BasicCommand {
	public void syntaxError(CommandSender commandSender, String command) {
		String usage = "<Test>";
		commandSender.sendMessage("Usage: " + command + " " + usage);
	}
	
	@Command(name = "plugins", info = "Lists all plugins, Bukkit style", aliases = {"pl", "plugin"})
	public void onCommandPlugin(CommandInfo i) {
		List<PluginContainer> plugins = Granite.getPlugins();
		List<String> pluginName = new ArrayList<String>();
		for(int in = 0; in < plugins.size(); in++) {
			pluginName.add(plugins.get(in).getName() + " (" + plugins.get(in).getVersion() + ")");
		}
		i.getCommandSender().sendMessage("Plugins (" + pluginName.size() + "): " + StringUtils.join(pluginName, ", "));
	}
	
	@Command(name = "commands", info = "Lists plugin commands", aliases = {"cmds"})
	public void onCommandCommands(CommandInfo i) {
		if(i.args.length <= 0) {
			syntaxError(i.getCommandSender(), i.getUsedCommandName());
			return;
		}
		try {
			Collection<CommandContainer> commands = Granite.getPluginContainer(i.args[0]).getCommands().values();
			List<String> commandName = new ArrayList<String>();
			for(Object c : commands.toArray()) {
				commandName.add(c.toString());
			}
			i.getCommandSender().sendMessage(StringUtils.join(commandName, ", "));
		} catch(NullPointerException ex) {
			i.getCommandSender().sendMessage("Plugin not found!");
		}
	}
}
