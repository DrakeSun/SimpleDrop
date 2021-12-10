package pl.daneu.simpledrop.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import pl.daneu.simpledrop.CustomConfig;
import pl.daneu.simpledrop.SimpleDrop;
import pl.daneu.simpledrop.objects.Drop;
import pl.daneu.simpledrop.objects.User;
import pl.daneu.simpledrop.utils.TextUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private final SimpleDrop plugin;
    private final CustomConfig usersConfig;

    public final Map<UUID, User> usersMap;

    public UserManager(SimpleDrop plugin){
        this.plugin = plugin;
        usersConfig = plugin.usersConfig;

        usersMap = new HashMap<>();
    }

    public User getUser(UUID uuid){ return usersMap.get(uuid); }

    public void registerUser(UUID uuid){
        if(usersMap.containsKey(uuid)) return;

        FileConfiguration config = usersConfig.get();
        String sUUID = uuid.toString();

        if(config.get(sUUID + ".drop-stone") == null) config.set(sUUID + ".drop-stone", true);
        for(Drop drop : plugin.dropsManager.dropsList){

            if(config.get(sUUID + ".drops-status." + drop.nameInConfig()) != null) continue;

            config.set(sUUID + ".drops-status." + drop.nameInConfig(), true);
        }

        usersConfig.save();
        createUser(sUUID);
    }

    private void createUser(String pathToUser){
        FileConfiguration config = usersConfig.get();

        Map<String, Boolean> dropActiveStatus = new HashMap<>();
        for(String dropName : config.getConfigurationSection(pathToUser + ".drops-status").getKeys(false)){
            dropActiveStatus.put(dropName, config.getBoolean(pathToUser + ".drops-status." + dropName));
        }
        boolean dropStone = config.getBoolean(pathToUser + ".drop-stone");

        UUID uuid = UUID.fromString(pathToUser);
        usersMap.put(uuid, new User(uuid, dropActiveStatus, dropStone));
    }

    public void saveAllUsers(){
        FileConfiguration config = usersConfig.get();

        for(User user : usersMap.values()){
            for(Drop drop : plugin.dropsManager.dropsList){
                if(config.getBoolean(user.uuid() + ".drops-status." + drop.nameInConfig())
                        == user.dropActiveStatusMap().get(drop.nameInConfig())) continue;
                config.set(user.uuid() + ".drops-status." + drop.nameInConfig(), user.dropActiveStatusMap().get(drop.nameInConfig()));
            }

            if(config.getBoolean(user.uuid() + ".drop-stone") != user.dropStone())
                config.set(user.uuid() + ".drop-stone", user.dropStone());

        }
        usersConfig.save();
    }

}
