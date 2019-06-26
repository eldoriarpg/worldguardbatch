package de.eldoria.worldguardbatch;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class RegionLoader {

    private WorldGuard worldGuard;
    private RegionContainer regionContainer;

    /**
     * Creates a new Region Loader object.
     */
    public RegionLoader() {
        worldGuard = WorldGuard.getInstance();
        regionContainer = worldGuard.getPlatform().getRegionContainer();
    }

    private Map<String, ProtectedRegion> getRegionsFromWorld(World world) {

        return regionContainer.get(world).getRegions();
    }

    /**
     * Finds all regions where a specific Player is member or owner in a world.
     *
     * @param world      World in which the regions should be found.
     * @param playerName Name of the player from whom the regions should found.
     * @return List of regions, where the player is a member or owner.
     */
    public List<ProtectedRegion> getRegionsFromPlayerInWorld(org.bukkit.World world, String playerName) {

        var regions = getRegionsFromWorld(BukkitAdapter.adapt(world));

        var p = getLocalPlayerFromName(playerName);


        return filterRegion(regions.values(), (region -> region.isMember(p)));
    }

    /**
     * Finds all regions where a specific Player is owner in a world.
     *
     * @param world      World in which the regions should be found.
     * @param playerName Name of the player from whom the regions should found.
     * @return List of regions, where the player is a member or owner.
     */
    public List<ProtectedRegion> getOwnerRegionsFromPlayerInWorld(org.bukkit.World world, String playerName) {
        List<ProtectedRegion> result = new ArrayList<>();

        var regions = getRegionsFromWorld(BukkitAdapter.adapt(world));

        var p = getLocalPlayerFromName(playerName);

        for (ProtectedRegion region : regions.values()) {
            if (region.isOwner(p)) {
                result.add(region);
            }
        }
        return result;

    }

    /**
     * Finds all regions where a specific Player is member or owner in a world.
     *
     * @param world      World in which the regions should be found.
     * @param playerName Name of the player from whom the regions should found.
     * @return List of regions, where the player is a member or owner.
     */
    public List<ProtectedRegion> getMemberRegionsFromPlayerInWorld(org.bukkit.World world, String playerName) {
        List<ProtectedRegion> result = new ArrayList<>();

        var regions = getRegionsFromWorld(BukkitAdapter.adapt(world));

        var p = getLocalPlayerFromName(playerName);

        for (ProtectedRegion region : regions.values()) {
            if (region.isMemberOnly(p)) {
                result.add(region);
            }
        }
        return result;

    }

    /**
     * Find the regions, where the names match with a regex pattern.
     *
     * @param world World in which the regions should be found
     * @param regex Regex pattern which should match.
     * @return Returns list with regions with matching name pattern.
     */
    public List<ProtectedRegion> getRegionsWithNameRegex(org.bukkit.World world, String regex) {
        var regions = getRegionsFromWorld(BukkitAdapter.adapt(world));

        List<ProtectedRegion> result = new ArrayList<>();

        for (ProtectedRegion region : regions.values()) {
            if (region.getId().matches(regex)) {
                result.add(region);
            }
        }
        return result;
    }

    private List<ProtectedRegion> filterRegion(Collection<ProtectedRegion> regions, Predicate<ProtectedRegion> filter) {
        return regions.stream()
                .filter(filter).collect(Collectors.toList());

    }


    /**
     * Find the regions, where the names match a count up pattern. The counter is inserted at a '*'
     *
     * @param world    World in whoch the regions should be found.
     * @param name     name of the regions with a start for counter
     * @param boundMin start number of counter - inclusive
     * @param boundMax end number of counter - exclusive
     * @return Returns list of regions with matching name pattern.
     */
    public List<ProtectedRegion> getRegionsWithNameCountUp(org.bukkit.World world,
                                                           String name, String boundMin, String boundMax) {
        List<ProtectedRegion> result = new ArrayList<>();

        int min = 0;
        int max;

        try {
            max = Integer.parseInt(boundMin);

        } catch (NumberFormatException e) {
            //TODO: Not a valid number
            return Collections.emptyList();
        }
        if (boundMax != null) {
            min = max;
            try {
                max = Integer.parseInt(boundMax);

            } catch (NumberFormatException e) {
                //TODO: not a valid number
                return Collections.emptyList();
            }
        }

        if (min < 0 || max < 0 || max < min) {
            //TODO: No valid numbers
            return Collections.emptyList();
        }


        var worldContainer = regionContainer.get(BukkitAdapter.adapt(world));
        if (worldContainer == null) {
            //TODO: World not found? But how?
            return Collections.emptyList();
        }

        var regions = worldContainer.getRegions();

        for (int i = min; i < max; i++) {
            var num = String.valueOf(i);

            var regName = name.replace("*", num);

            if (regions.containsKey(regName)) {
                result.add(regions.get(regName));
            }
        }
        return result;
    }

    /**
     * Get all regions which are a child of a region.
     *
     * @param world world for lookup
     * @param name  name of the parent
     * @return list of children of the parent.
     */
    public List<ProtectedRegion> getAllChildsOfRegionInWorld(org.bukkit.World world, String name) {
        List<ProtectedRegion> result = new ArrayList<>();

        var worldContainer = regionContainer.get(BukkitAdapter.adapt(world));
        if (worldContainer == null) {
            //TODO: World not found? But how?
            return Collections.emptyList();
        }

        var regions = worldContainer.getRegions();

        for (ProtectedRegion region : regions.values()) {
            if (region.getParent().getId().equalsIgnoreCase(name)) {
                result.add(region);
            }
        }
        return result;
    }

    /**
     * Get all regions in a world.
     *
     * @param world world for lookup
     * @return List of all regions in the world.
     */
    public List<ProtectedRegion> getRegionsInWorld(org.bukkit.World world) {
        List<ProtectedRegion> result = new ArrayList<>();

        var worldContainer = regionContainer.get(BukkitAdapter.adapt(world));

        if (worldContainer == null) {
            //TODO: World not found? But how?
            return result;
        }

        var regions = worldContainer.getRegions();

        result.addAll(regions.values());

        return result;
    }

    /**
     * Get a local player object from a string.
     *
     * @param name name to lookup
     * @return Local Player object. Null if the player never joined the server.
     */
    public static LocalPlayer getLocalPlayerFromName(String name) {
        if (name == null || name.equalsIgnoreCase("")) {
            //TODO: No player name given.
            return null;
        }

        var player = Bukkit.getPlayer(name);

        if (player == null) {
            var oPlayer = Bukkit.getOfflinePlayers();
            for (OfflinePlayer p : oPlayer) {
                if (p.getName().equalsIgnoreCase(name)) {
                    player = p.getPlayer();
                }
            }
            //TODO: Player does not exist.
        }

        return WorldGuardPlugin.inst().wrapPlayer(player);
    }


}
