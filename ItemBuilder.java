package cc.acquized.itembuilder;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;

import java.util.*;

/**
 * ItemBuilder API for creating ItemStacks easy with just 1 line of code
 * @author Acquized
 * @author Kev575
 * @see ItemStack
 */
public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;
    private Material material = Material.STONE;
    private int amount = 1;
    private MaterialData data;
    private short damage = 0;
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private String displayname;
    private List<String> lore = new ArrayList<>();
    private List<ItemFlag> flags = new ArrayList<>();

    /**
     * Initials the ItemBuilder with the Material
     * @see Material
     */
    public ItemBuilder(Material material) {
        Validate.notNull(material, "The Material is null.");
        this.material = material;
    }

    /**
     * Initials the ItemBuilder with the Material and the amount
     * @author Kev575
     * @see Material
     */
    public ItemBuilder(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    /**
     * Initials the ItemBuilder with the Material, the amount and the displaname
     * @author Kev575
     * @see Material
     */
    public ItemBuilder(Material material, int amount, String displayname) {
        this.material = material;
        this.amount = amount;
        this.displayname = displayname;
    }

    /**
     * Initials the ItemBuilder with an already existing ItemStack
     * @see ItemStack
     */
    public ItemBuilder(ItemStack item) {
        Validate.notNull(item, "The Item is null.");
        this.item = item;
        this.meta = item.getItemMeta();
        this.material = item.getType();
        this.amount = item.getAmount();
        this.data = item.getData();
        this.damage = item.getDurability();
        this.enchantments = item.getEnchantments();
        this.displayname = item.getItemMeta().getDisplayName();
        this.lore = item.getItemMeta().getLore();
        for(ItemFlag f : item.getItemMeta().getItemFlags()) {
            flags.add(f);
        }
    }

    /**
     * Sets the amount of the builded ItemStack
     * @param amount (Integer)
     */
    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Sets the MaterialData of the builded ItemStack
     * @param data (MaterialData)
     */
    public ItemBuilder data(MaterialData data) {
        Validate.notNull(data, "The Data is null.");
        this.data = data;
        return this;
    }

    /**
     * Sets the damage (durability) of the builded ItemStack
     * @param damage (Short)
     * @deprecated Use ItemBuilder#durability
     */
    @Deprecated
    public ItemBuilder damage(short damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Sets the durability (damage) of the builded ItemStack
     * @param damage (Short)
     */
    public ItemBuilder durability(short damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Sets the Material of the builded ItemStack
     * @param material (Material)
     */
    public ItemBuilder material(Material material) {
        Validate.notNull(material, "The Mater is null.");
        this.material = material;
        return this;
    }

    /**
     * Sets the ItemMeta of the builded ItemStack
     * @param meta (ItemMeta)
     * @param meta (ItemMeta)
     */
    public ItemBuilder meta(ItemMeta meta) {
        Validate.notNull(meta, "The Meta is null.");
        this.meta = meta;
        return this;
    }

    /**
     * Adds the Enchantment of the builded ItemStack
     * @param enchant (Enchantment)
     */
    public ItemBuilder enchant(Enchantment enchant, int level) {
        Validate.notNull(enchant, "The Enchantment is null.");
        enchantments.put(enchant, level);
        return this;
    }

    /**
     * Sets the Enchantments of the builded ItemStack
     * @param enchantments (Map<Enchantment, Integer> )
     */
    public ItemBuilder enchant(Map<Enchantment, Integer> enchantments) {
        Validate.notNull(enchantments, "The Enchantments is null.");
        this.enchantments = enchantments;
        return this;
    }

    /**
     * Sets the Displayname of the builded ItemStack
     * @param displayname (Displayname)
     */
    public ItemBuilder displayname(String displayname) {
        Validate.notNull(displayname, "The Displayname is null.");
        this.displayname = ChatColor.translateAlternateColorCodes('&', displayname);
        return this;
    }

    /**
     * Adds the line to the Lore of the builded ItemStack
     * @param line (String)
     */
    public ItemBuilder lore(String line) {
        Validate.notNull(line, "The Line is null.");
        lore.add(ChatColor.translateAlternateColorCodes('&', line));
        return this;
    }

    /**
     * Sets the lore of the builded ItemStack
     * @param lore (List<String>)
     */
    public ItemBuilder lore(List<String> lore) {
        Validate.notNull(lore, "The Lore is null.");
        this.lore = lore;
        return this;
    }

    /**
     * Adds the lines to the Lore of the builded ItemStack
     * @param lines (String...)
     * @author Kev575
     */
    public ItemBuilder lores(String... lines) {
        Validate.notNull(lines, "The Lines are null.");
        lore.addAll(Arrays.asList(lines));
        return this;
    }

    /**
     * Adds the line as the counts position as lore
     * @param line (String)
     * @param count (Integer)
     */
    public ItemBuilder lore(String line, int count) {
        Validate.notNull(line, "The Line is null.");
        lore.set(count, ChatColor.translateAlternateColorCodes('&', line));
        return this;
    }

    /**
     * Adds the ItemFlag to the builded ItemStack
     * @param flag (ItemFlag)
     */
    public ItemBuilder flag(ItemFlag flag) {
        Validate.notNull(flag, "The Flag is null.");
        flags.add(flag);
        return this;
    }

    /**
     * Sets the ItemFlags of the builded ItemStack
     * @param flags (List<ItemFlag>)
     */
    public ItemBuilder flag(List<ItemFlag> flags) {
        Validate.notNull(flags, "The Flags are null.");
        this.flags = flags;
        return this;
    }

    /**
     * Makes the Item (un-)breakable
     * @param unbreakable (Boolean)
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        meta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Makes the Item Glow
     */
    public ItemBuilder glow() {
        enchant(Enchantment.ARROW_INFINITE, 10);
        flag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Sets the Owner of the Skull
     * This only affects if the Item is a SKULL_ITEM or a SKULL
     * @param user (String)
     */
    public ItemBuilder owner(String user) {
        Validate.notNull(user, "The Username is null.");
        if((material == Material.SKULL_ITEM) || (material == Material.SKULL)) {
            SkullMeta smeta = (SkullMeta) meta;
            smeta.setOwner(user);
            meta = smeta;
        }
        return this;
    }

    /**
     * Builds the ItemStack and returns it
     * @return (ItemStack)
     */
    public ItemStack build() {
        item = new ItemStack(material, amount, damage);
        meta = item.getItemMeta();
        if(data != null) {
            item.setData(data);
        }
        if(enchantments.size() > 0) {
            item.addUnsafeEnchantments(enchantments);
        }
        if(displayname != null) {
            meta.setDisplayName(displayname);
        }
        if(lore.size() > 0) {
            meta.setLore(lore);
        }
        if(flags.size() > 0) {
            for(ItemFlag f : flags) {
                meta.addItemFlags(f);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

}
