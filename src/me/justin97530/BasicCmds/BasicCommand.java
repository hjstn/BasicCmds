package me.justin97530.BasicCmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.chat.ChatColor;
import org.granitemc.granite.api.chat.ChatComponentBuilder;
import org.granitemc.granite.api.chat.TextComponent;
import org.granitemc.granite.api.command.Command;
import org.granitemc.granite.api.command.CommandInfo;
import org.granitemc.granite.api.entity.player.Player;
import org.granitemc.granite.api.plugin.PluginContainer;

public class BasicCommand {
	@Command(name = "plugins", info = "Lists all plugins, Bukkit style", aliases = {"pl", "plugin"})
	public void onCommandPlugin(CommandInfo i) {
		List<PluginContainer> plugins = Granite.getPlugins();
		List<String> pluginNames = new ArrayList<String>();
		for(Iterator<PluginContainer> it = plugins.iterator(); it.hasNext();) {
			pluginNames.add(it.next().getName());
		}
		i.getCommandSender().sendMessage("Plugins (" + pluginNames.size() + "): " + StringUtils.join(pluginNames, ", "));
	}
	
	@Command(name = "msg", info = "???", aliases = {"whisper", "tell"})
	public void onCommandMsg(CommandInfo i) {
		if(i.getArgs().length < 2) {
			i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Syntax: /" + i.getCommand() + " <player> <message>").build());
		} else {
			Player player = Utils.getPlayer(i.args[0]);
			if(player != null) {
				String message = StringUtils.join(Arrays.copyOfRange(i.args, 1, i.args.length), " ");
				player.sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("[" + i.getCommandSender().getName() + " -> me] ").color(ChatColor.WHITE).text(message).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("[me" + " -> " + player.getName() + "] ").color(ChatColor.WHITE).text(message).build());
			} else {
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Player not found!").build());
			}
		}
	}
	
	@Command(name = "something", info = "???", aliases = {})
	public void onCommandSomething(CommandInfo i) {
		i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).showText(new TextComponent("LOL NOOB")).text("Hi, " + i.getCommandSender().getName()).build());
	}
}
