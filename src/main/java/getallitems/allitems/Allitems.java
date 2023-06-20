package getallitems.allitems;

import getallitems.allitems.commands.ForceSkip;
import getallitems.allitems.commands.Start;
import getallitems.allitems.commands.Win;
import getallitems.allitems.listener.InvListener;
import getallitems.allitems.listener.JoinListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Allitems extends JavaPlugin {

    private static Allitems instance;
    static ArmorStand armorStand;
    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        instance = this;

        PluginManager plm = getServer().getPluginManager();
        plm.registerEvents(new InvListener(), this);
        plm.registerEvents(new JoinListener(), this);
        this.getCommand("start").setExecutor(new Start());
        this.getCommand("win").setExecutor(new Win());
        this.getCommand("forceskip").setExecutor(new ForceSkip());

        System.out.println("PLUGIN LOADED");

        config.options().copyDefaults(true);

        String input = config.getString("CURRENT").replace("_", " ");
        input = input.toLowerCase();
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        Bossbar.restartBossbar(output);

    }

    @Override
    public void onDisable() {
        saveConfig();
        Bossbar.removeBossbar();
    }

    public static Allitems getInstance(){
        return instance;
    }


}
