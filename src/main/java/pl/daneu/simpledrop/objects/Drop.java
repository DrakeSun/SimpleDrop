package pl.daneu.simpledrop.objects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.daneu.simpledrop.managers.DropsManager;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public record Drop(String nameInConfig, Material material, String nameInGUI, String dropMessage, List<FortuneReader> fortuneReaders, double bonusDrop, Material minPickaxe) {

    public record FortuneReader(int minAmount, int maxAmount, double chance){}

    public boolean canDrop(Player p, int fortuneLevel){
        double rDouble = ThreadLocalRandom.current().nextDouble(100) + 0.01;

        if(p.hasPermission("simpledrop.bonusdrop") && isPickaxeEnough(p)) return rDouble <= fortuneReaders.get(fortuneLevel).chance + bonusDrop;
        if(!isPickaxeEnough(p)) return false;

        return rDouble <= fortuneReaders.get(fortuneLevel).chance;
    }

    public int dropRandomAmount(int fortuneLevel){
        int minAmount = fortuneReaders.get(fortuneLevel).minAmount;
        int maxAmount = fortuneReaders.get(fortuneLevel).maxAmount;

        return ThreadLocalRandom.current().nextInt(minAmount, maxAmount + 1);

    }

    private boolean isPickaxeEnough(Player p){
        Material mPickaxe = p.getItemInHand().getType();
        return DropsManager.pickaxeOrdering.get(mPickaxe) >= DropsManager.pickaxeOrdering.get(minPickaxe);
    }

    public String getPickaxeName(){
        return switch (minPickaxe) {
            case NETHERITE_PICKAXE -> "netherytowy";
            case DIAMOND_PICKAXE -> "diamentowy";
            case IRON_PICKAXE -> "zelazny";
            case GOLDEN_PICKAXE -> "zloty";
            case STONE_PICKAXE -> "kamienny";
            case WOODEN_PICKAXE -> "drewniany";
            default -> "NIE PODANO PRAWIDLOWEJ NAZWY KILOFU";
        };
    }

}
