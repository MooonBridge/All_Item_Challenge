package getallitems.allitems.commands;

import getallitems.allitems.Allitems;
import getallitems.allitems.Bossbar;
import getallitems.allitems.InvChecker;
import getallitems.allitems.listener.InvListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ForceSkip implements CommandExecutor {

    FileConfiguration config = Allitems.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (InvListener.gewonnen){
            return true;
        }

        if (config.getStringList("TO-DO").isEmpty()){
            Bossbar.winGame();
            for (Player p : Bukkit.getOnlinePlayers()){
                p.sendTitle("§eVICTORY", "§6You've collected all 1153 items", 20, 40, 20);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 1);
            }
        }

        List<String> mats1 = new ArrayList<>(config.getStringList("DONE"));
        mats1.add((String) config.get("CURRENT"));
        config.set("DONE", mats1);

        List<String> mats = new ArrayList<>(config.getStringList("TO-DO"));
        mats.remove((String) config.get("CURRENT"));
        config.set("TO-DO", mats);

        int randomNum = ThreadLocalRandom.current().nextInt(0, mats.size());
        String current = mats.get(randomNum);
        config.set("CURRENT", current);

        String input = current.replace("_", " ");
        input = input.toLowerCase();
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);

        Bossbar.setBossbar(output);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.RED + "ITEM GESKIPPED!");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 10, 1);
            player.sendMessage(ChatColor.AQUA + "Nächstes Item: " + ChatColor.GREEN + output);
        }

        Allitems.getInstance().saveConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            InvChecker.checkInv(player);
        }

        return true;
    }
}
