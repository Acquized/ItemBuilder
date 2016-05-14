// THIS FILE IS WORK IN PROGRESS AND YOU SHOULD NOT USE IT. THANK YOU.

/* Copyright 2016 Acquized
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.acquized.itembuilder.nms;

import cc.acquized.itembuilder.original.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class WrappedItemBuilder extends ItemBuilder {

    public WrappedItemBuilder(Material material) {
        super(material);
    }

    public WrappedItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public WrappedItemBuilder(Material material, int amount, String displayname) {
        super(material, amount, displayname);
    }

    public WrappedItemBuilder(Material material, String displayname) {
        super(material, displayname);
    }

    public WrappedItemBuilder(ItemStack item) {
        super(item);
    }

    public WrappedItemBuilder(FileConfiguration cfg, String path) {
        super(cfg, path);
    }

    @Deprecated
    public WrappedItemBuilder(ItemBuilder builder) {
        super(builder);
    }

    public AttributeWIP attributeWIP() {
        return new AttributeWIP(this);
    }

    // -------------------------

    public static class AttributeWIP {

        private Object nmsItem;
        private Object modifiers;
        private ReflectionUtils utils = new ReflectionUtils();

        public AttributeWIP(WrappedItemBuilder builder) {
            this.nmsItem = utils.getItemAsNMSStack(builder.build());
            this.modifiers = utils.getNewNBTTagList();
        }

        /* TODO: Maybe move this to a different subclass  */
        public WrappedItemBuilder.AttributeWIP addAttribute(Attribute attribute, String name, Slot slot, int operation, double amount, UUID identifier) {
            try {
                Object data = utils.getNewNBTTagCompound();
                data.getClass().getMethod("setString", new Class[]{ String.class, String.class }).invoke(data, "AttributeName", attribute);
                data.getClass().getMethod("setString", new Class[]{ String.class, String.class }).invoke(data, "Name", name);
                data.getClass().getMethod("setString", new Class[]{ String.class, String.class }).invoke(data, "Slot", slot);
                data.getClass().getMethod("setInt", new Class[]{ String.class, Integer.class }).invoke(data, "Operation", operation);
                data.getClass().getMethod("setDouble", new Class[]{ String.class, Double.class }).invoke(data, "Amount", amount);
                data.getClass().getMethod("setLong", new Class[]{ String.class, Long.class }).invoke(data, "UUIDMost", identifier.getMostSignificantBits());
                data.getClass().getMethod("setLong", new Class[]{ String.class, Long.class }).invoke(data, "UUIDLeast", identifier.getLeastSignificantBits());
                modifiers.getClass().getMethod("add", utils.getNBTTagListClass()).invoke(modifiers, data);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }
            return this;
        }

        public WrappedItemBuilder builder() {
            try {
                nmsItem.getClass().getMethod("a", new Class[]{ String.class, utils.getNBTBaseClass() }).invoke(nmsItem, "AttributeModifiers", modifiers);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
            return new WrappedItemBuilder(utils.getItemAsBukkitStack(nmsItem));
        }

        public enum Attribute {

            MAX_HEALTH("generic.maxHealth"),
            FOLLOW_RANGE("generic.followRange"),
            KNOCKBACK_RESISTANCE("generic.knockbackResistance"),
            MOVEMENT_SPEED("generic.movementSpeed"),
            ATTACK_DAMAGE("generic.attackDamage"),
            ARMOR("generic.armor"),
            ARMOR_THOUGHNESS("generic.armorToughness"),
            ATTACK_SPEED("generic.attackSpeed"),
            LUCK("generic.luck"),
            JUMP_STRENGTH("horse.jumpStrength"),
            SPAWN_REINFORCEMENTS("zombie.spawnReinforcements");

            private String name;

            Attribute(String name) {
                this.name = name;
            }

            public String getIdentifier() {
                return name;
            }
        }

        public enum Slot {

            MAIN_HAND("mainhand"),
            OFF_HAND("offhand"),
            FEET("feet"),
            LEGS("legs"),
            CHEST("chest"),
            HEAD("head");

            private String slot;

            Slot(String slot) {
                this.slot = slot;
            }

            public String getIdentifier() {
                return slot;
            }
        }

        public class ReflectionUtils {

            public Object getNewNBTTagCompound() {
                String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
                try {
                    return Class.forName("net.minecraft.server." + ver + ".NBTTagCompound").newInstance();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Object getNewNBTTagList() {
                String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
                try {
                    return Class.forName("net.minecraft.server." + ver + ".NBTTagList").newInstance();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Object setNBTTag(Object tag, Object item) {
                try {
                    item.getClass().getMethod("setTag", item.getClass()).invoke(item, tag);
                    return item;
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Object getNBTTagCompound(Object nmsStack) {
                try {
                    return nmsStack.getClass().getMethod("getTag").invoke(nmsStack);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Object getItemAsNMSStack(ItemStack item) {
                try {
                    Method m = getCraftItemStackClass().getMethod("asNMSCopy", ItemStack.class);
                    return m.invoke(getCraftItemStackClass(), item);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public ItemStack getItemAsBukkitStack(Object nmsStack) {
                try {
                    Method m = getCraftItemStackClass().getMethod("asCraftMirror", nmsStack.getClass());
                    return (ItemStack) m.invoke(getCraftItemStackClass(), nmsStack);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Class<?> getCraftItemStackClass() {
                String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
                try {
                    return Class.forName("org.bukkit.craftbukkit." + ver + ".inventory.CraftItemStack");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Class<?> getNBTTagListClass() {
                String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
                try {
                    return Class.forName("net.minecraft.server." + ver + ".NBTTagList");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Class<?> getNBTBaseClass() {
                String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
                try {
                    return Class.forName("net.minecraft.server." + ver + ".NBTBase");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

        }

    }

}

