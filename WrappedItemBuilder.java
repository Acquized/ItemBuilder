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
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

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

    @SuppressWarnings("deprecation")
    @Deprecated
    public WrappedItemBuilder(ItemBuilder builder) {
        super(builder);
    }

    public AttributeWIP attributeWIP() {
        return new AttributeWIP(this);
    }

    // -------------------------

    public static class AttributeWIP {

        private WrappedItemBuilder builder;

        public AttributeWIP(WrappedItemBuilder builder) {
            this.builder = builder;
        }

        /* TODO: Maybe move this to a different subclass  */
        public WrappedItemBuilder.AttributeWIP addAttribute(Attribute attribute, String name, Slot slot, int operation, double amount, UUID identifier) {

            return this;
        }

        public WrappedItemBuilder builder() {
            return builder;
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

    }

}
