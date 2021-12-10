package pl.daneu.simpledrop.guis;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import pl.daneu.simpledrop.SimpleDrop;
import pl.daneu.simpledrop.guis.customgui.Button;
import pl.daneu.simpledrop.guis.customgui.CustomGUI;
import pl.daneu.simpledrop.managers.ConfigManager;
import pl.daneu.simpledrop.managers.DropsManager;
import pl.daneu.simpledrop.objects.Drop;
import pl.daneu.simpledrop.objects.User;
import pl.daneu.simpledrop.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class DropGUI extends CustomGUI {

    private final SimpleDrop plugin;
    private final ConfigManager configManager;
    private final DropsManager dropsManager;

    public DropGUI(SimpleDrop plugin){
        super(plugin.configManager.GUI_DROPS_NAME, 36, true);

        this.plugin = plugin;
        configManager = plugin.configManager;
        dropsManager = plugin.dropsManager;

        registerButtons();
    }

    @Override
    public void registerButtons() {
        int slot = 0;
        for(Drop drop : dropsManager.dropsList){
            setButton(slot, new Button(false) {
                @Override
                public void execute(InventoryClickEvent e) {
                    Player p = (Player) e.getWhoClicked();
                    User user = plugin.usersManager.getUser(p.getUniqueId());

                    boolean dropActiveStatus = user.dropActiveStatusMap().get(drop.nameInConfig());

                    if(dropActiveStatus) user.dropActiveStatusMap().put(drop.nameInConfig(), false);
                    else user.dropActiveStatusMap().put(drop.nameInConfig(), true);

                    open(p);

                }
            });
            slot++;
        }
        setButton(3 * 9, new Button(false) {
            @Override
            public void execute(InventoryClickEvent e) {
                Player p = (Player) e.getWhoClicked();
                User user = plugin.usersManager.getUser(p.getUniqueId());

                for(Drop drop : dropsManager.dropsList){
                    user.dropActiveStatusMap().put(drop.nameInConfig(), true);

                    open(p);
                }
            }
        });

        setButton(3 * 9 + 1, new Button(false) {
            @Override
            public void execute(InventoryClickEvent e) {
                Player p = (Player) e.getWhoClicked();
                User user = plugin.usersManager.getUser(p.getUniqueId());

                for(Drop drop : dropsManager.dropsList){
                    user.dropActiveStatusMap().put(drop.nameInConfig(), false);

                    open(p);
                }
            }
        });

        setButton(3 * 9 + 4, new Button(false) {
            @Override
            public void execute(InventoryClickEvent e) {
                Player p = (Player) e.getWhoClicked();
                User user = plugin.usersManager.getUser(p.getUniqueId());

                if(user.dropStone()) user.setDropStoneStatus(false); else user.setDropStoneStatus(true);

                open(p);
            }
        });
    }

    @Override
    public void open(Player p) {
        int slot = 0;
        User user = plugin.usersManager.getUser(p.getUniqueId());
        for(Drop drop : dropsManager.dropsList){

            boolean dropActiveStatus = user.dropActiveStatusMap().get(drop.nameInConfig());
            String dropActiveStatusString = dropActiveStatus ? "aktywny" : "nieaktywny";

            List<String> lore = new ArrayList<>(configManager.GUI_DROPS_LOREFOREACHGUI);
            for(int il = 0; il < lore.size(); il++){
                String loreLine = lore.get(il);

                for(int iFR = 0; iFR < drop.fortuneReaders().size(); iFR++){
                    Drop.FortuneReader fortuneReader = drop.fortuneReaders().get(iFR);

                    if(loreLine.contains("%minAmount" + iFR + "%")) { loreLine = loreLine.replaceAll("%minAmount" + iFR + "%", String.valueOf(fortuneReader.minAmount())); continue; }
                    if(loreLine.contains("%maxAmount" + iFR + "%")) { loreLine = loreLine.replaceAll("%maxAmount" + iFR + "%", String.valueOf(fortuneReader.maxAmount())); continue; }
                    if(loreLine.contains("%chance" + iFR + "%")) loreLine = loreLine.replaceAll("%chance" + iFR + "%", String.valueOf(fortuneReader.chance()));
                }

                if(loreLine.contains("%minPickaxe%")) loreLine = loreLine.replaceAll("%minPickaxe%", drop.getPickaxeName());
                if(loreLine.contains("%active%")) loreLine = loreLine.replaceAll("%active%", dropActiveStatusString);

                lore.set(il, loreLine);
            }

            ItemBuilder itemBuilder = new ItemBuilder(drop.material())
                    .setName(drop.nameInGUI())
                    .setLore(lore);
            if(dropActiveStatus) itemBuilder.setEnchantment(Enchantment.DAMAGE_ALL, 1)
                            .setFlag(ItemFlag.HIDE_ENCHANTS);

            setItem(slot, itemBuilder.create());
            slot++;
        }

        ItemStack onAllItem = new ItemBuilder(Material.GREEN_CONCRETE)
                .setName("&aNacisnij, aby wlaczyc kazdy drop")
                .create();
        setItem(3 * 9, onAllItem);

        ItemStack offAllItem = new ItemBuilder(Material.RED_CONCRETE)
                .setName("&cNacisnij, aby wylaczyc kazdy drop")
                .create();
        setItem(3 * 9 + 1, offAllItem);

        String dropStatus = user.dropStone() ? "&a&lTAK" : "&c&lNIE";
        ItemStack dropStoneStatus = new ItemBuilder(Material.STONE)
                .setName("&7Czy ma wypada stone do ekwipunku - " + dropStatus)
                .create();
        setItem(3 * 9 + 4, dropStoneStatus);

        p.openInventory(getInventory());
    }
}
