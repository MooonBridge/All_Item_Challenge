package getallitems.allitems;

import getallitems.allitems.listener.InvListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InvChecker {

    static FileConfiguration config = Allitems.getInstance().getConfig();

    public static void checkInv(Player p){

        if (InvListener.gewonnen){
            return;
        }

        Inventory inv = p.getInventory();
        String item = (String) config.get("CURRENT");

        if (inv.contains(Material.valueOf(item))){

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                player.sendMessage(ChatColor.GREEN + (String) config.get("CURRENT") + ChatColor.AQUA + " gefunden!");
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
                player.sendMessage(ChatColor.AQUA + "Nächstes Item: " + ChatColor.GREEN + output);
            }

            if (config.getStringList("TO-DO").isEmpty()){
                InvListener.gewonnen = true;
                InvListener.won();
            }

            Allitems.getInstance().saveConfig();

            for (Player player : Bukkit.getOnlinePlayers()) {
                checkInv(player);
            }

        }

    }

}
