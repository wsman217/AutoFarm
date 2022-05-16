package network.beefmc.autofarm;

import org.bukkit.Location;

import java.util.ArrayList;

public class AutoFarmSelection {

    public ArrayList<Selection> selections = new ArrayList<>();

    public void addSelection(Location loc) {
        addSelection(loc, loc);
    }

    public void addSelection(Location firstCorner, Location secondCorner) {
        selections.add(new Selection(firstCorner, secondCorner));
    }

    public static class Selection {
        public Location firstCorner;
        public Location secondCorner;

        public Selection(Location firstCorner, Location secondCorner) {
            this.firstCorner = firstCorner;
            this.secondCorner = secondCorner;
        }
    }
}
