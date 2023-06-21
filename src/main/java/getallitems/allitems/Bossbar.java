package getallitems.allitems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Bossbar  {

    static FileConfiguration config = Allitems.getInstance().getConfig();

    public static BossBar bar;

    public static void createBossbar(String title){
        bar = Bukkit.createBossBar(ChatColor.GREEN + title, BarColor.GREEN, BarStyle.SOLID);
        bar.setProgress(0);
        bar.setVisible(true);
        showBossbar();
    }

    public static void restartBossbar(String title){
        List<String> mats = new ArrayList<>(config.getStringList("DONE"));
        double progress = (double) mats.size()  /  1153;

        bar = Bukkit.createBossBar(ChatColor.GREEN + title, BarColor.GREEN, BarStyle.SOLID);
        bar.setProgress(progress);
        bar.setVisible(true);
        showBossbar();
    }

    public static void showBossbar(){
        if (bar != null) {
            removeBossbar();
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(p);
        }
    }

    public static void setBossbar(String title){
        List<String> mats = new ArrayList<>(config.getStringList("DONE"));
        double progress = (double) mats.size()  /  1153;

        bar.setTitle(ChatColor.GREEN + title);
        bar.setProgress(progress);
        showBossbar();
    }

    public static void removeBossbar(){
        for (Player p : Bukkit.getOnlinePlayers()){
            bar.removePlayer(p);
        }
    }

    public static void winGame(){
        bar.setTitle(ChatColor.GOLD + "GAME WON");
        bar.setColor(BarColor.YELLOW);
        bar.setProgress(1);
    }
}
