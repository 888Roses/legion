package net.collectively.geode.mc.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/// `AttackAwareItem` is an interface allowing its implementations to be aware of the player attacking with the item. It has
/// only one method, `onAttack`. This method is called both on the **client side** and on the **server side** when any
/// `PlayerEntity` attacks with the item.
///
/// @apiNote This interface will only do something if an `Item` implements it. Once implemented, it will work out of the box
/// without needing anything else.
public interface AttackAwareItem {
    /// Called when any `PlayerEntity` attacks with this item, regardless of what the player is looking at (block, entity,
    /// fluid, or air).
    ///
    /// @param playerEntity Reference to the {@link PlayerEntity} attacking.
    /// @param itemStack    Reference to the {@code item} being used.
    void onAttack(PlayerEntity playerEntity, ItemStack itemStack);
}
