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
import org.granitemc.granite.entity.player.GraniteEntityPlayer;

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
	
	@Command(name = "msg", info = "Message a player", aliases = {"whisper", "tell"})
	public void onCommandMsg(CommandInfo i) {
		if(i.getArgs().length < 2) {
			i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Syntax: /" + i.getCommand().getName() + " <player> <message>").build());
		} else {
			Player player = Utils.getPlayer(i.args[0]);
			if(player != null) {
				String message = StringUtils.join(Arrays.copyOfRange(i.args, 1, i.args.length), " ");
				player.sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("[" + i.getCommandSender().getName() + " -> me] ").color(ChatColor.WHITE).text(message).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("[me" + " -> " + player.getName() + "] ").color(ChatColor.WHITE).text(message).build());
				BasicCmds.ReplyList.put(i.getCommandSender().getName(), player.getName());
				BasicCmds.ReplyList.put(player.getName(), i.getCommandSender().getName());
			} else {
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Player not found!").build());
				BasicCmds.ReplyList.remove(i.getCommandSender().getName());
			}
		}
	}
	
	@Command(name = "reply", info = "Reply to a message", aliases = {"r"})
	public void onCommandReply(CommandInfo i) {
		if(i.getArgs().length < 1) {
			i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Syntax: /" + i.getCommand().getName() + " <message>").build());
		} else if(BasicCmds.ReplyList.get(i.getCommandSender().getName()) != null) {
			Player player = Utils.getPlayer(BasicCmds.ReplyList.get(i.getCommandSender()));
			if(player != null) {
				String message = StringUtils.join(i.args, " ");
				player.sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("[" + i.getCommandSender().getName() + " -> me] ").color(ChatColor.WHITE).text(message).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("[me" + " -> " + player.getName() + "] ").color(ChatColor.WHITE).text(message).build());
				BasicCmds.ReplyList.put(player.getName(), i.getCommandSender().getName());
			} else {
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Player not found!").build());
				BasicCmds.ReplyList.remove(i.getCommandSender().getName());
			}
		} else {
			i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("You don't have anyone to reply to!").build());
		}
	}
	
	@Command(name = "something", info = "???", aliases = {})
	public void onCommandSomething(CommandInfo i) {
		i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).showText(new TextComponent("LOL NOOB")).text("Hi, " + i.getCommandSender().getName()).build());
	}
	
	@Command(name = "tp", info = "Teleportation, advanced", aliases = {"teleport"})
	public void onCommandTp(CommandInfo i) {
		if(i.getArgs().length > 2 || i.getArgs().length < 1) {
			i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Syntax: /" + i.getCommand().getName() + "[From] <To>").build());
		} else if(i.getArgs().length == 1) {
			Player toPlayer = Utils.getPlayer(i.args[0]);
			if(toPlayer != null) {
				GraniteEntityPlayer player = (GraniteEntityPlayer)i.getCommandSender();
				player.setLocation(toPlayer.getLocation());
				player.sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("Teleported to " + toPlayer.getName()).build());
				toPlayer.sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text(player.getName() + " teleported to you").build());
			} else {
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Player not found!").build());
			}
		} else if(i.getArgs().length == 2) {
			Player fromPlayer = Utils.getPlayer(i.args[0]);
			Player toPlayer = Utils.getPlayer(i.args[1]);
			if(fromPlayer != null && toPlayer != null) {
				fromPlayer.setLocation(toPlayer.getLocation());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("You teleported " + fromPlayer.getName() + " to " + toPlayer.getName()).build());
				toPlayer.sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text(i.getCommandSender().getName() + " teleported " + fromPlayer.getName() + " to you").build());
				fromPlayer.sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text(i.getCommandSender().getName() + " teleported you to " + toPlayer.getName()).build());
			} else {
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Player not found!").build());
			}
		}
	}
}
