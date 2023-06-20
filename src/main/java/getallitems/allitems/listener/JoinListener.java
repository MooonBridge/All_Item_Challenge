package getallitems.allitems.listener;

import getallitems.allitems.Bossbar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event){

        Player p = event.getPlayer();
        Bossbar.showBossbar();

    }

}
