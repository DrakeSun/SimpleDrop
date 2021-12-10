package pl.daneu.simpledrop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.daneu.simpledrop.SimpleDrop;

import java.util.UUID;

public class JoinListener implements Listener {

    private final SimpleDrop plugin;

    public JoinListener(SimpleDrop plugin){ this.plugin = plugin; }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        UUID uuid = e.getPlayer().getUniqueId();

        plugin.usersManager.registerUser(uuid);
    }

}
