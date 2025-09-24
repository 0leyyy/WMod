/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.wmod.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import net.mcreator.wmod.block.RBlock;
import net.mcreator.wmod.WModMod;

public class WModModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(WModMod.MODID);
	public static final DeferredBlock<Block> R = REGISTRY.register("r", RBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}