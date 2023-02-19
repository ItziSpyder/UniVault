package io.github.itzispyder.universalvaults.server.plugin.misc;

import org.bukkit.Material;

/**
 * Different types of glass pane types
 */
public enum PaneType {

    DEFAULT("default",Material.GLASS_PANE),
    WHITE("white",Material.WHITE_STAINED_GLASS_PANE),
    ORANGE("orange",Material.ORANGE_STAINED_GLASS_PANE),
    MAGENTA("magenta",Material.MAGENTA_STAINED_GLASS_PANE),
    LIGHT_BLUE("light_blue",Material.LIGHT_BLUE_STAINED_GLASS_PANE),
    YELLOW("yellow",Material.YELLOW_STAINED_GLASS_PANE),
    LIME("lime",Material.LIME_STAINED_GLASS_PANE),
    PINK("pink",Material.PINK_STAINED_GLASS_PANE),
    GRAY("gray",Material.GRAY_STAINED_GLASS_PANE),
    SILVER("silver",Material.LIGHT_GRAY_STAINED_GLASS_PANE),
    CYAN("cyan",Material.CYAN_STAINED_GLASS_PANE),
    PURPLE("purple",Material.PURPLE_STAINED_GLASS_PANE),
    BLUE("blue",Material.BLUE_STAINED_GLASS_PANE),
    BROWN("brown",Material.BROWN_STAINED_GLASS_PANE),
    GREEN("green",Material.GREEN_STAINED_GLASS_PANE),
    RED("red",Material.RED_STAINED_GLASS_PANE),
    BLACK("black",Material.BLACK_STAINED_GLASS_PANE);

    private final String name;
    private final Material type;

    /**
     * Constructs a new enum pane type
     * @param name name
     * @param type material
     */
    PaneType(String name, Material type) {
        this.name = name;
        this.type = type;
    }

    public Material getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
