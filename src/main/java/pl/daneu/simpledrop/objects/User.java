package pl.daneu.simpledrop.objects;

import java.util.Map;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private boolean dropStone;
    private final Map<String, Boolean> dropActiveStatusMap;

    public User(UUID uuid, Map<String, Boolean> dropActiveStatusMap, boolean dropStone){
        this.uuid = uuid;
        this.dropStone = dropStone;
        this.dropActiveStatusMap = dropActiveStatusMap;
    }

    public UUID uuid(){ return uuid; }
    public Map<String, Boolean> dropActiveStatusMap(){ return dropActiveStatusMap; }
    public boolean dropStone(){ return dropStone; }

    public void setDropStoneStatus(boolean status){ dropStone = status; }
}
