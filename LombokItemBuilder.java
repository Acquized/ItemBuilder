/* ItemBuilder
 * Copyright (C) 2016 Acquized
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cc.acquized.itembuilder.lombok;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder(builderClassName = "ItemBuilder")
@Getter
@Setter
public class LombokItemBuilder {

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

    private boolean andSymbol = true;
    private boolean unsafeStackSize = false;

    public LombokItemBuilder(Material material) {
        if(material == null) material = Material.AIR;
        this.item = new ItemStack(material);
        this.material = material;
    }

    /** Initalizes the ItemBuilder with {@link org.bukkit.Material} and Amount */
    public LombokItemBuilder(Material material, int amount) {
        if(material == null) material = Material.AIR;
        if(((amount > material.getMaxStackSize()) || (amount <= 0)) && (!unsafeStackSize)) amount = 1;
        this.amount = amount;
        this.item = new ItemStack(material, amount);
        this.material = material;
    }

    /** Initalizes the ItemBuilder with {@link org.bukkit.Material}, Amount and Displayname */
    public LombokItemBuilder(Material material, int amount, String displayname) {
        if(material == null) material = Material.AIR;
        Validate.notNull(displayname, "The Displayname is null.");
        this.item = new ItemStack(material, amount);
        this.material = material;
        if(((amount > material.getMaxStackSize()) || (amount <= 0)) && (!unsafeStackSize)) amount = 1;
        this.amount = amount;
        this.displayname = displayname;
    }

    /** Initalizes the ItemBuilder with {@link org.bukkit.Material} and Displayname */
    public LombokItemBuilder(Material material, String displayname) {
        if(material == null) material = Material.AIR;
        Validate.notNull(displayname, "The Displayname is null.");
        this.item = new ItemStack(material);
        this.material = material;
        this.displayname = displayname;
    }

    /** Initalizes the ItemBuilder with a {@link org.bukkit.inventory.ItemStack} */
    public LombokItemBuilder(ItemStack item) {
        Validate.notNull(item, "The Item is null.");
        this.item = item;
        if(item.hasItemMeta())
            this.meta = item.getItemMeta();
        this.material = item.getType();
        this.amount = item.getAmount();
        this.data = item.getData();
        this.damage = item.getDurability();
        this.enchantments = item.getEnchantments();
        if(item.hasItemMeta())
            this.displayname = item.getItemMeta().getDisplayName();
        if(item.hasItemMeta())
            this.lore = item.getItemMeta().getLore();
        if(item.hasItemMeta())
            for (ItemFlag f : item.getItemMeta().getItemFlags()) {
                flags.add(f);
            }
    }

    /** Initalizes the ItemBuilder with a {@link org.bukkit.configuration.file.FileConfiguration} ItemStack in Path */
    public LombokItemBuilder(FileConfiguration cfg, String path) {
        this(cfg.getItemStack(path));
    }

    public ItemStack build() {
        item.setType(material);
        item.setAmount(amount);
        item.setDurability(damage);
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
            for (ItemFlag f : flags) {
                meta.addItemFlags(f);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

}
