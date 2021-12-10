package pl.daneu.simpledrop.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import pl.daneu.simpledrop.SimpleDrop;
import pl.daneu.simpledrop.objects.Drop;
import pl.daneu.simpledrop.utils.TextUtil;

import java.util.*;

public class DropsManager {

    private final FileConfiguration dropsConfig;

    public final ArrayList<Drop> dropsList;

    public static Map<Material, Integer> pickaxeOrdering = new LinkedHashMap<>();

    public DropsManager(SimpleDrop plugin){
        dropsConfig = plugin.dropsConfig.get();
        dropsList = new ArrayList<>(27);

        pickaxeOrdering.put(Material.WOODEN_PICKAXE, 0);
        pickaxeOrdering.put(Material.STONE_PICKAXE, 1);
        pickaxeOrdering.put(Material.GOLDEN_PICKAXE, 2);
        pickaxeOrdering.put(Material.IRON_PICKAXE, 3);
        pickaxeOrdering.put(Material.DIAMOND_PICKAXE, 4);
        pickaxeOrdering.put(Material.NETHERITE_PICKAXE, 5);


        for(String dropName : dropsConfig.getConfigurationSection("").getKeys(false)){
            if(dropsList.size() == 27){
                Bukkit.getConsoleSender().sendMessage(TextUtil.getColoredText("&cSimplePlugin &4&l| &cPrzekroczyles maksymalna liczbe dropow! &4&l(27)"));
                Bukkit.getPluginManager().disablePlugin(plugin);
                return;
            }

            dropsList.add(getDrop(dropName));
        }
    }

    public Drop getDrop(String pathToDrop){
        String dropMessage = TextUtil.getColoredText(dropsConfig, pathToDrop + ".drop-message");
        String nameInGUI = TextUtil.getColoredText(dropsConfig, pathToDrop + ".name-in-gui");
        Material dropMaterial = Material.getMaterial(dropsConfig.getString(pathToDrop + ".material"));
        Material minPickaxe = Material.getMaterial(dropsConfig.getString(pathToDrop + ".min-pickaxe"));

        String dropInfoPath = pathToDrop + ".drop-info";

        double bonusDrop = dropsConfig.getDouble(dropInfoPath + ".bonus-drop");

        List<Drop.FortuneReader> fortuneReaders = new ArrayList<>(4);
        for(String fortuneLVL : dropsConfig.getConfigurationSection(dropInfoPath).getKeys(false)){
            if(fortuneLVL.equalsIgnoreCase("bonus-drop")) continue;
            String fortunePath = dropInfoPath + "." + fortuneLVL;

            int minAmount = dropsConfig.getInt(fortunePath + ".min-amount");
            int maxAmount = dropsConfig.getInt(fortunePath + ".max-amount");
            double chance = dropsConfig.getDouble(fortunePath + ".chance");

            Drop.FortuneReader fortuneReader = new Drop.FortuneReader(minAmount, maxAmount, chance);
            fortuneReaders.add(fortuneReader);
        }

        return new Drop(pathToDrop ,dropMaterial, nameInGUI, dropMessage, fortuneReaders, bonusDrop, minPickaxe);
    }
}
