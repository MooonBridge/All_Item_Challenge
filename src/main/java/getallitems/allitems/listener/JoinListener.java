package getallitems.allitems.listener;

import getallitems.allitems.Allitems;
import getallitems.allitems.Bossbar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    FileConfiguration config = Allitems.getInstance().getConfig();

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event){

        Player p = event.getPlayer();
        if (config.contains("CURRENT")) {
            Bossbar.showBossbar();
        }

    }

}
