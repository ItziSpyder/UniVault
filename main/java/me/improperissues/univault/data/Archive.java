package me.improperissues.univault.data;

import me.improperissues.univault.UniVault;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Archive {

    public static void fillType(Location corner1, Location corner2, Material type) {
        int x = corner1.getBlockX();
        int y = corner1.getBlockY();
        int z = corner1.getBlockZ();
        int x1 = corner2.getBlockX();
        int y1 = corner2.getBlockY();
        int z1 = corner2.getBlockZ();
        int minX = Math.min(x,x1);
        int maxX = Math.max(x,x1);
        int minY = Math.min(y,y1);
        int maxY = Math.max(y,y1);
        int minZ = Math.min(z,z1);
        int maxZ = Math.max(z,z1);
        for (int a = minX; a <= maxX; a ++) {
            for (int b = minY; b <= maxY; b ++) {
                for (int c = minZ; c <= maxZ; c ++) {
                    Location loc = new Location(corner1.getWorld(),a,b,c);
                    Block block = loc.getBlock();
                    block.setType(type);
                }
            }
        }
    }

    public static void setArchivesAir() {
        Location origin = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
        fillType(origin.clone().add(-305,-1,-4),origin.clone().add(305,9,4),Material.AIR);
        fillType(origin.clone().add(-4,-1,-305),origin.clone().add(4,9,305),Material.AIR);
    }

    public static void setOrigin(Player player) {
        Location loc = player.getLocation().getBlock().getLocation();
        Location origin = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
        if (origin != null) {
            player.sendMessage(UniVault.STARTER + "§dAction interrupted: An archive already exists! Deleting it...");
            setArchivesAir();
            player.sendMessage(UniVault.STARTER + "§dArchive deleted!");
        }
        UniVault.getInstance().getConfig().set("config.archive.origin",loc);
        UniVault.getInstance().saveConfig();
        player.sendMessage(UniVault.STARTER + "§dArchive origin set!");
    }

    public static void setArchives(Player player) {
        Location loc = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
        if (loc == null) {
            player.sendMessage(UniVault.STARTER + "§4There is no set origin for the archive!");
            return;
        }
        new BukkitRunnable() {
            int i = 4;
            @Override
            public void run() {
                if (i > 0) {
                    switch (i) {
                        case 4:
                            fillType(loc.clone().add(-304,-1,-4),loc.clone().add(304,7,4),Material.LIGHT);
                            fillType(loc.clone().add(-4,-1,-304),loc.clone().add(4,7,304),Material.LIGHT);
                            fillType(loc.clone().add(-305,-1,-4),loc.clone().add(-305,9,4),Material.CHISELED_NETHER_BRICKS);
                            fillType(loc.clone().add(305,-1,-4),loc.clone().add(305,9,4),Material.CHISELED_NETHER_BRICKS);
                            fillType(loc.clone().add(-4,-1,-305),loc.clone().add(4,9,-305),Material.CHISELED_NETHER_BRICKS);
                            fillType(loc.clone().add(-4,-1,305),loc.clone().add(4,9,305),Material.CHISELED_NETHER_BRICKS);
                            break;
                        case 3:
                            fillType(loc.clone().add(-304,-1,-4),loc.clone().add(304,-1,4),Material.RED_NETHER_BRICKS);
                            fillType(loc.clone().add(-4,-1,-304),loc.clone().add(4,-1,304),Material.RED_NETHER_BRICKS);
                            fillType(loc.clone().add(-304,-1,-2),loc.clone().add(304,-1,2),Material.SHROOMLIGHT);
                            fillType(loc.clone().add(-2,-1,-304),loc.clone().add(2,-1,304),Material.SHROOMLIGHT);
                            fillType(loc.clone().add(-304,-1,-1),loc.clone().add(304,-1,1),Material.CHISELED_NETHER_BRICKS);
                            fillType(loc.clone().add(-1,-1,-304),loc.clone().add(1,-1,304),Material.CHISELED_NETHER_BRICKS);
                            fillType(loc.clone().add(-4,-1,-4),loc.clone().add(4,-1,4),Material.NETHER_BRICKS);
                            fillType(loc.clone().add(-3,-1,-3),loc.clone().add(3,-1,3),Material.RED_NETHER_BRICKS);
                            break;
                        case 2:
                            fillType(loc.clone().add(-304,8,-4),loc.clone().add(304,8,4),Material.RED_NETHER_BRICKS);
                            fillType(loc.clone().add(-4,8,-304),loc.clone().add(4,8,304),Material.RED_NETHER_BRICKS);
                            fillType(loc.clone().add(-304,9,-3),loc.clone().add(304,9,3),Material.RED_NETHER_BRICK_SLAB);
                            fillType(loc.clone().add(-3,9,-304),loc.clone().add(3,9,304),Material.RED_NETHER_BRICK_SLAB);
                            fillType(loc.clone().add(-304,9,-1),loc.clone().add(304,9,1),Material.TINTED_GLASS);
                            fillType(loc.clone().add(-1,9,-304),loc.clone().add(1,9,304),Material.TINTED_GLASS);
                            fillType(loc.clone().add(-304,8,-2),loc.clone().add(304,8,2),Material.AIR);
                            fillType(loc.clone().add(-2,8,-304),loc.clone().add(2,8,304),Material.AIR);
                            break;
                        case 1:
                            Block submissionChest = loc.clone().add(0,0,1).getBlock();
                            submissionChest.setType(Material.CHEST);
                            Container chest = (Container) submissionChest.getState();
                            chest.setCustomName(UniVault.STARTER + "§c#SUBMISSION");
                            chest.update();
                            updateChestIndexes();
                            break;
                    }
                    i --;
                } else this.cancel();
            }
        }.runTaskTimer(UniVault.getInstance(),0,5);
        player.sendMessage(UniVault.STARTER + "§dGenerated the archives!");
    }

    public static Location getArchiveCorner1() {
        Location loc = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
        if (loc == null) return null;
        return loc.clone().add(-305,-1,-4);
    }

    public static Location getArchiveCorner2() {
        Location loc = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
        if (loc == null) return null;
        return loc.clone().add(305,9,4);
    }

    public static Location getArchiveCorner3() {
        Location loc = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
        if (loc == null) return null;
        return loc.clone().add(-4,-1,-305);
    }

    public static Location getArchiveCorner4() {
        Location loc = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
        if (loc == null) return null;
        return loc.clone().add(4,9,305);
    }












    private static void addData(Block block, int index, BlockFace face, String type) {
        block.setType(Material.BARREL);
        Container chest = (Container) block.getState();
        chest.setCustomName("§c#" + type.toUpperCase() + ":" + index);
        chest.update();
        Directional facing = (Directional) block.getBlockData();
        facing.setFacing(face);
        block.setBlockData(facing);
    }

    private static void updateChestIndexes() {
        Location loc = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
        if (loc == null) return;
        int RANDOM_INDEX = 1;
        int SHULKER_INDEX = 1;

        Location RANDOM_TOP_LEFT = loc.clone().add(4,0,5);
        Location RANDOM_TOP_RIGHT = loc.clone().add(-4,0,5);
        for (int l = 0; l < 300; l ++) {
            for (int h = 0; h < 8; h ++) {
                Block block = RANDOM_TOP_LEFT.clone().add(0,h,l).getBlock();
                addData(block,RANDOM_INDEX,BlockFace.WEST,"RANDOM");
                RANDOM_INDEX ++;

                block = RANDOM_TOP_RIGHT.clone().add(0,h,l).getBlock();
                addData(block,RANDOM_INDEX,BlockFace.EAST,"RANDOM");
                RANDOM_INDEX ++;
            }
        }

        Location RANDOM_BOTTOM_LEFT = loc.clone().add(4,0,-5);
        Location RANDOM_BOTTOM_RIGHT = loc.clone().add(-4,0,-5);
        for (int l = 0; l < 300; l ++) {
            for (int h = 0; h < 8; h ++) {
                Block block = RANDOM_BOTTOM_LEFT.clone().add(0,h,-l).getBlock();
                addData(block,RANDOM_INDEX,BlockFace.WEST,"RANDOM");
                RANDOM_INDEX ++;

                block = RANDOM_BOTTOM_RIGHT.clone().add(0,h,-l).getBlock();
                addData(block,RANDOM_INDEX,BlockFace.EAST,"RANDOM");
                RANDOM_INDEX ++;
            }
        }

        Location SHULKER_TOP_LEFT = loc.clone().add(5,0,4);
        Location SHULKER_BOTTOM_LEFT = loc.clone().add(5,0,-4);
        for (int l = 0; l < 300; l ++) {
            for (int h = 0; h < 8; h ++) {
                Block block = SHULKER_TOP_LEFT.clone().add(l,h,0).getBlock();
                addData(block,SHULKER_INDEX,BlockFace.NORTH,"SHULKER");
                SHULKER_INDEX ++;

                block = SHULKER_BOTTOM_LEFT.clone().add(l,h,0).getBlock();
                addData(block,SHULKER_INDEX,BlockFace.SOUTH,"SHULKER");
                SHULKER_INDEX ++;
            }
        }

        Location SHULKER_TOP_RIGHT = loc.clone().add(-5,0,4);
        Location SHULKER_BOTTOM_RIGHT = loc.clone().add(-5,0,-4);
        for (int l = 0; l < 300; l ++) {
            for (int h = 0; h < 8; h ++) {
                Block block = SHULKER_TOP_RIGHT.clone().add(-l,h,0).getBlock();
                addData(block,SHULKER_INDEX,BlockFace.NORTH,"SHULKER");
                SHULKER_INDEX ++;

                block = SHULKER_BOTTOM_RIGHT.clone().add(-l,h,0).getBlock();
                addData(block,SHULKER_INDEX,BlockFace.SOUTH,"SHULKER");
                SHULKER_INDEX ++;
            }
        }
    }
}
