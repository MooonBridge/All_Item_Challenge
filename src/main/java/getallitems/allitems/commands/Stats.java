package getallitems.allitems.commands;

import getallitems.allitems.Allitems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Stats implements CommandExecutor, Listener {

    static FileConfiguration config = Allitems.getInstance().getConfig();
    private static Inventory inv;
    String[] mats;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){

            Player p = (Player) sender;
            if (config.contains("CURRENT")){

                mats = getItemArray();
                int mats_length = mats.length;

                inv = Bukkit.createInventory(null, 54, "§c§lStats");

                initializeItems();
                p.openInventory(inv);

            }

        }

        return true;
    }

    public String[] getItemArray(){
        String[] materials = {};
        if (config.contains("DONE")){
            String mats_unsorted = config.getString("DONE");
            mats_unsorted = mats_unsorted.replace("[", "");
            mats_unsorted = mats_unsorted.replace("]", "");
            mats_unsorted = mats_unsorted.replaceAll(" ", "");
            materials = mats_unsorted.split(",");
        }

        return materials;

    }

    public void initializeItems(){
        String currentItem = config.getString("CURRENT");
        for (int i = 0; i < 9; i++){
            inv.setItem(i, createItem(Material.GRAY_STAINED_GLASS_PANE, "§0-"));
            inv.setItem(i+45, createItem(Material.GRAY_STAINED_GLASS_PANE, "§0-"));
        }
        inv.setItem(46, createItem(Material.ARROW, "§f§lLast Page"));
        inv.setItem(52, createItem(Material.ARROW, "§f§lNext Page"));
        inv.setItem(4, new ItemStack(Material.getMaterial(currentItem)));
        for (int i = 0; i < mats.length; i++){
            inv.addItem(new ItemStack(Material.getMaterial(mats[i])));
        }

    }

    public ItemStack createItem(Material material, String name){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;
    }

    public static Inventory getInv(){
        return inv;
    }

}
