package me.justin97530.BasicCmds;

import org.apache.commons.lang3.StringUtils;
import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.chat.ChatColor;
import org.granitemc.granite.api.chat.ChatComponentBuilder;
import org.granitemc.granite.api.chat.TextComponent;
import org.granitemc.granite.api.command.Command;
import org.granitemc.granite.api.command.CommandInfo;
import org.granitemc.granite.api.entity.Entity;
import org.granitemc.granite.api.entity.player.Player;
import org.granitemc.granite.api.plugin.PluginContainer;
import org.granitemc.granite.api.utils.Location;
import org.granitemc.granite.entity.player.GraniteEntityPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
			Player player = Utils.getPlayer(BasicCmds.ReplyList.get(i.getCommandSender().getName()));
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
	
	@Command(name = "ping", info = "Pong!", aliases = {})
	public void onCommandPing(CommandInfo i) {
		i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.YELLOW).text("Pong!").build());
	}
	
	@Command(name = "whois", info = "Who is?", aliases = {})
	public void onCommandWhois(CommandInfo i) {
		if(i.getArgs().length < 1 || i.getArgs().length > 1) {
			i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Syntax: /" + i.getCommand().getName() + " <username>").build());
		} else {
			Player player = Utils.getPlayer(i.args[0]);
			if(player != null) {
				String IP = "No Data";
				int Health = -1;
				Location Loc;
				int LocX = -1;
				int LocY = -1;
				int LocZ = -1;
				String Held = null;
				int ID = -1;
				Location Bed;
				int BedX = -1;
				int BedY = -1;
				int BedZ = -1;
				int Distance = -1;
				
				try {
					if(player.getPlayerIP() != null) {
						IP = player.getPlayerIP();
					}
					Health = (int) player.getHealth();
					Loc = player.getLocation();
					LocX = (int) Loc.getX();
					LocY = (int) Loc.getY();
					LocZ = (int) Loc.getZ();
					Held = player.getHeldItem().getType().getName();
					ID = player.getEntityId();
					Bed = player.getBedLocation();
					BedX = (int) Bed.getX();
					BedY = (int) Bed.getY();
					BedZ = (int) Bed.getZ();
					Distance = (int) player.getDistanceToEntity((Entity) i.getCommandSender());
					
				} catch(NullPointerException e) {
					
				}
				
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("Whois reports for " + player.getName()).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("IP: " + IP).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("Health: " + (Health / 20 * 100) + "% | " + Health).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("Location: " + "X: " + LocX + ", Y: " + LocY + ", Z: " + LocZ).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("Held Item: " + Held).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("Entity ID: " + ID).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("Bed Location: " + "X: " + BedX + ", Y: " + BedY + ", Z: " + BedZ).build());
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.GOLD).text("Distance to you: " + Distance).build());
			} else {
				i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).text("Player not found!").build());
			}
		}
	}
	
	@Command(name = "something", info = "???", aliases = {})
	public void onCommandSomething(CommandInfo i) {
		i.getCommandSender().sendMessage(new ChatComponentBuilder().color(ChatColor.RED).showText(new TextComponent("LOL NOOB")).text("Hi, " + i.getCommandSender().getName()).build());
		Player subject = Utils.getPlayer(i.getCommandSender().getName());
		subject.setAIMoveSpeed(0.0F);
		subject.setAIMoveSpeed(-1.0F);
		subject.setAIMoveSpeed(1.0F);
		subject.setAIMoveSpeed(0.0F);
		subject.setAIMoveSpeed(0.2F);
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

	@Command(name = "pong", info = "DDOS the server!", aliases = {})
	public void onCommandPong(CommandInfo i) {
		for (Player p : Granite.getServer().getPlayers()){
			p.sendMessage(new ChatComponentBuilder().text(i.getCommandSender().getName() + " tries to DDOS the server!").build());
		}
	}
}
