package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class XPCmd extends Cmd{
	public XPCmd(){
		super("xp");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/xp <show/give/set> <player>'&7.");
		addString("xp-show", "&7Your &aXP &7value: &e{EXP}&7 - Level: &c{LEVEL}.");
		addString("xp-show-of", "&7The &aXP &7value of &c{PLAYER}&7: &e{EXP}&7 - Level: &c{LEVEL}.&7");
		
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "xp")){
				try{
					Player player = Bukkit.getPlayerExact(args[1]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						if (args[0].equalsIgnoreCase("show")){
							DirectMC.sendMessage(p, getString("xp-show-of").replace("{EXP}", String.valueOf(player.getExp())).replace("{PLAYER}", player.getName()).replace("{LEVEL}", String.valueOf(p.getLevel())));
							return true;
						}else if (args[0].equalsIgnoreCase("give")){
							
							return true;
						}else if (args[0].equalsIgnoreCase("set")){
							
							return true;
						}else{
							DirectMC.sendMessage(p, getString("usage"));
							return true;
						}
					}
				}catch(Exception e){
					try{
						if (args[0].equalsIgnoreCase("show")){
							DirectMC.sendMessage(p, getString("xp-show").replace("{EXP}", String.valueOf(p.getExp())).replace("{LEVEL}", String.valueOf(p.getLevel())));
							return true;
						}else if (args[0].equalsIgnoreCase("give")){
							DirectMC.sendMessage(p, getString("xp-give"));
							return true;
						}else if (args[0].equalsIgnoreCase("set")){
							
							return true;
						}else{
							DirectMC.sendMessage(p, getString("usage"));
							return true;
						}
					}catch(Exception e1){
						DirectMC.sendMessage(p, getString("usage"));
						return true;
					}
				}
			}else{
				DirectMC.sendMessage(p, getString("no-permission"));
				return true;
			}
		}else{
			sender.sendMessage(getString("no-player"));
			return true;
		}
	}
}