package cc.acquized.itembuilder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginAwareness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItemBuilder API for creating ItemStack easy with just 1 line of code
 * @author Acquized
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
        this.material = material;
    }

    /**
     * Initials the ItemBuilder with an already existing ItemStack
     * @see ItemStack
     */
    public ItemBuilder(ItemStack item) {
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
        this.material = material;
        return this;
    }

    /**
     * Sets the ItemMeta of the builded ItemStack
     * @param meta (ItemMeta)
     */
    public ItemBuilder meta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Adds the Enchantment of the builded ItemStack
     * @param enchant (Enchantment)
     */
    public ItemBuilder enchant(Enchantment enchant, int level) {
        enchantments.put(enchant, level);
        return this;
    }

    /**
     * Sets the Enchantments of the builded ItemStack
     * @param enchantments (Map<Enchantment, Integer> )
     */
    public ItemBuilder enchant(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    /**
     * Sets the Displayname of the builded ItemStack
     * @param displayname (Displayname)
     */
    public ItemBuilder displayname(String displayname) {
        this.displayname = displayname;
        return this;
    }

    /**
     * Adds the line to the Lore of the builded ItemStack
     * @param line (String)
     */
    public ItemBuilder lore(String line) {
        lore.add(line);
        return this;
    }

    /**
     * Sets the lore of the builded ItemStack
     * @param lore (List<String>)
     */
    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Adds the line as the counts position as lore
     * @param line (String)
     * @param count (Integer)
     */
    public ItemBuilder lore(String line, int count) {
        lore.set(count, line);
        return this;
    }

    /**
     * Adds the ItemFlag to the builded ItemStack
     * @param flag (ItemFlag)
     */
    public ItemBuilder flag(ItemFlag flag) {
        flags.add(flag);
        return this;
    }

    /**
     * Sets the ItemFlags of the builded ItemStack
     * @param flags (List<ItemFlag>)
     */
    public ItemBuilder flag(List<ItemFlag> flags) {
        this.flags = flags;
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
