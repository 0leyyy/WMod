/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wmod.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import net.minecraft.world.item.Item;

import net.mcreator.wmod.WModMod;

public class WModModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(WModMod.MODID);
	public static final DeferredItem<Item> BOBS_SPAWN_EGG = REGISTRY.register("bobs_spawn_egg", () -> new DeferredSpawnEggItem(WModModEntities.BOBS, -1, -1, new Item.Properties()));
	// Start of user code block custom items
	// End of user code block custom items
}