package net.dashsmp.revivesteal.command;

import net.dashsmp.revivesteal.ReviveSteal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ReviveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(p.isOp() || p.getInventory().contains(new ItemStack(Material.TOTEM_OF_UNDYING), 4) && p.getInventory().contains(new ItemStack(Material.DIAMOND_BLOCK), 1) && p.getInventory().contains(new ItemStack(Material.NETHERITE_INGOT), 4)) {
            if(ReviveSteal.ghostPlayers.contains(Bukkit.getPlayer(args[0]).getUniqueId())) {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendTitle(ChatColor.RED + "The Dead Shall Live Again.", ChatColor.GRAY + args[0] + " Has Been Revived", 20, 50, 10);
                    //return true;
                }
                Player rT = Bukkit.getPlayer(args[0]);
                ReviveSteal.ghostPlayers.remove(rT.getUniqueId());
                rT.setGameMode(GameMode.SURVIVAL);
                rT.setAllowFlight(false);
                rT.setInvulnerable(false);
                rT.setInvisible(false);
                if(rT.hasPotionEffect(PotionEffectType.WEAKNESS)){
                    rT.removePotionEffect(PotionEffectType.WEAKNESS);
                }
                rT.getInventory().clear();
                rT.teleport(ReviveSteal.getSpawn());
                p.setDisplayName(p.getName());
                return true;
            } else {
                p.sendMessage(ChatColor.RED + "That Player Is Already Alive!");
                return true;
            }
        } else {
            p.sendMessage(ChatColor.RED + "You Must Have 4 Totems Of Undying,");
            p.sendMessage(ChatColor.RED + "4 Netherite Ingots, And 1 Diamond Block To Revive A Player.");
            return true;
        }
    }
}
