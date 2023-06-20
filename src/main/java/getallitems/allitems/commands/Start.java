package getallitems.allitems.commands;

import getallitems.allitems.Allitems;
import getallitems.allitems.Bossbar;
import getallitems.allitems.listener.InvListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Start implements CommandExecutor {

    FileConfiguration config = Allitems.getInstance().getConfig();
    List<String> allmats;
    String current;



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            allmats = getAllMats();

            InvListener.setGewonnen(false);

            int randomNum = ThreadLocalRandom.current().nextInt(0, allmats.size() + 1);
            current = allmats.get(randomNum);

            config.set("TO-DO", allmats);
            config.set("DONE", null);
            config.set("CURRENT", current);

            String input = current.replace("_", " ");
            input = input.toLowerCase();
            String output = input.substring(0, 1).toUpperCase() + input.substring(1);

            Bossbar.createBossbar(output);

            for (Player player : Bukkit.getOnlinePlayers()){
                    player.sendTitle("§aGAME STARTED!", "§3Only 1153 to go!", 15, 25, 15);
                    player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 10, 1);
                    player.sendMessage(ChatColor.AQUA + "Erstes Item: " + ChatColor.GREEN + output);
            }

            Allitems.getInstance().saveConfig();

        }

        return true;

    }

    public static List<String> getAllMats(){
        List<Material> materials = Arrays.stream(Material.values()).filter(Material::isItem).collect(Collectors.toList());
        List<String> remainingMaterials = new ArrayList<>();
        for (Material mat : materials) {
            List<Material> forbidden = new ArrayList<>();
            forbidden.addAll(Arrays.asList(Material.AIR, Material.SPAWNER, Material.SUSPICIOUS_GRAVEL, Material.SUSPICIOUS_SAND, Material.FARMLAND, Material.BARRIER, Material.LIGHT, Material.STRUCTURE_VOID, Material.STRUCTURE_BLOCK, Material.JIGSAW, Material.PLAYER_HEAD, Material.DEBUG_STICK, Material.BUDDING_AMETHYST, Material.FROGSPAWN));
            if (!forbidden.contains(mat) && !mat.name().contains("SPAWN_EGG") && !mat.name().contains("INFESTED") && !mat.name().contains("COMMAND")) {
                remainingMaterials.add(mat.name());
            }
        }
        return remainingMaterials;

    }

}
