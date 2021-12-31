package net.dashsmp.revivesteal.command;

import net.dashsmp.revivesteal.ReviveSteal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SacrificeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(args[0].equals("")) {
            p.sendMessage(ChatColor.RED + "/sacrifice <playername>");
            return true;
        }
        if(p.getName().equalsIgnoreCase(args[0])) {
            p.sendMessage(ChatColor.RED + "You Can't Sacrifice Yourself For You!");
            return true;
        }
        if(Bukkit.getPlayer(args[0]).isOnline()) {
            Player rT = Bukkit.getPlayer(args[0]);
            p.setHealth(0);
            ReviveSteal.ghostPlayers.remove(Bukkit.getPlayer(args[0]).getUniqueId());
            ReviveSteal.ghostPlayers.add(p.getUniqueId());
            rT.setGameMode(GameMode.SURVIVAL);
            rT.setAllowFlight(false);
            if(rT.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                rT.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
            }
            rT.getPlayer().teleport(ReviveSteal.getSpawn());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.sendTitle(ChatColor.RED + "One Shall Live And One Shall Die.", ChatColor.GRAY + p.getName() + " Has Scarificed Themself For " + args[0], 20, 50, 10);
                return true;
            }
            return true;
        } else {
            p.sendMessage(ChatColor.RED + "That Player Is Offline!");
            return true;
        }
    }
}
