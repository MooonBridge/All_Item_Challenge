package getallitems.allitems.commands;

import getallitems.allitems.Bossbar;
import getallitems.allitems.listener.InvListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Win implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        InvListener.won();
        return true;
    }
}
