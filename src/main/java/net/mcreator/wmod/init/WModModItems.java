/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wmod.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import net.minecraft.world.item.Item;

import net.mcreator.wmod.item.GarsItem;
import net.mcreator.wmod.WModMod;

public class WModModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(WModMod.MODID);
	public static final DeferredItem<Item> GARS = REGISTRY.register("gars", GarsItem::new);
	// Start of user code block custom items
	// End of user code block custom items
}