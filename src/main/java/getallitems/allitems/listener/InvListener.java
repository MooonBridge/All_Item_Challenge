package getallitems.allitems.listener;

import getallitems.allitems.Allitems;
import getallitems.allitems.Bossbar;
import getallitems.allitems.InvChecker;
import getallitems.allitems.commands.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.checkerframework.common.value.qual.EnumVal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class InvListener implements Listener {

    public static boolean gewonnen = false;
    static FileConfiguration config = Allitems.getInstance().getConfig();


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory().equals(Stats.getInv())){
            event.setCancelled(true);
            return;
        }

        if (event.getCurrentItem() == null){
            return;
        }

        String eventItem = event.getCurrentItem().getType().name();

        if (gewonnen){
            return;
        }

        if (eventItem.equalsIgnoreCase((String) config.get("CURRENT"))){

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.GREEN + (String) config.get("CURRENT") + ChatColor.AQUA + " gefunden!");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
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
                gewonnen = true;
                won();
            }

            Allitems.getInstance().saveConfig();

            for (Player player : Bukkit.getOnlinePlayers()) {
                InvChecker.checkInv(player);
            }

        }

    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {

        Player p = (Player) event.getPlayer();
        String eventItem = event.getItem().getItemStack().getType().name();

        if (gewonnen){
            return;
        }

        if (eventItem.equalsIgnoreCase((String) config.get("CURRENT"))){

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
                gewonnen = true;
                won();
            }

            Allitems.getInstance().saveConfig();

            for (Player player : Bukkit.getOnlinePlayers()) {
                InvChecker.checkInv(player);
            }

        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        if (event.getInventory().equals(Stats.getInv())){
            event.setCancelled(true);
        }
    }

    public static void setGewonnen(boolean bool){
        gewonnen = bool;
    }

    public static void won(){
        config.set("WON", true);
        Bossbar.winGame();
        for (Player p : Bukkit.getOnlinePlayers()){
            p.sendTitle("§eVICTORY", "§6You've collected all 1153 items", 20, 40, 20);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 1);
        }
        Allitems.getInstance().saveConfig();
    }

}
