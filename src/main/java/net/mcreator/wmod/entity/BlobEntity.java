package net.mcreator.wmod.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.EventHooks;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Difficulty;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;

import net.mcreator.wmod.procedures.BlobPlaybackConditionProcedure;
import net.mcreator.wmod.init.WModModEntities;
import net.mcreator.wmod.WModMod;

import javax.annotation.Nullable;

import java.util.function.BiConsumer;

public class BlobEntity extends TamableAnimal implements VibrationSystem {
	public final AnimationState animationState0 = new AnimationState();
	public final AnimationState animationState2 = new AnimationState();
	private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener = new DynamicGameEventListener(new VibrationSystem.Listener(this));
	private final VibrationSystem.User vibrationUser = new VibrationUser();
	private VibrationSystem.Data vibrationData = new VibrationSystem.Data();

	public BlobEntity(EntityType<BlobEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setCustomName(Component.literal("lil monkey"));
		setCustomNameVisible(true);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, (float) 6.7));
		this.goalSelector.addGoal(3, new PanicGoal(this, 1.2));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, (float) 6.7));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.death"));
	}

	@Override
	public boolean hurt(DamageSource damagesource, float amount) {
		if (damagesource.is(DamageTypes.CACTUS))
			return false;
		if (damagesource.is(DamageTypes.DROWN))
			return false;
		if (damagesource.is(DamageTypes.FALLING_ANVIL))
			return false;
		return super.hurt(damagesource, amount);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		VibrationSystem.Data.CODEC.encodeStart(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), this.vibrationData).resultOrPartial(e -> WModMod.LOGGER.error("Failed to encode vibration listener for Blob: '{}'", e))
				.ifPresent(listener -> compound.put("listener", listener));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("listener", Tag.TAG_COMPOUND)) {
			VibrationSystem.Data.CODEC.parse(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), compound.getCompound("listener")).resultOrPartial(e -> WModMod.LOGGER.error("Failed to parse vibration listener for Blob: '{}'", e))
					.ifPresent(data -> this.vibrationData = data);
		}
	}

	@Override
	public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
		if (this.level() instanceof ServerLevel serverLevel) {
			listenerConsumer.accept(this.dynamicGameEventListener, serverLevel);
		}
	}

	@Override
	public VibrationSystem.Data getVibrationData() {
		return this.vibrationData;
	}

	@Override
	public VibrationSystem.User getVibrationUser() {
		return this.vibrationUser;
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		Item item = itemstack.getItem();
		if (itemstack.getItem() instanceof SpawnEggItem) {
			retval = super.mobInteract(sourceentity, hand);
		} else if (this.level().isClientSide()) {
			retval = (this.isTame() && this.isOwnedBy(sourceentity) || this.isFood(itemstack)) ? InteractionResult.sidedSuccess(this.level().isClientSide()) : InteractionResult.PASS;
		} else {
			if (this.isTame()) {
				if (this.isOwnedBy(sourceentity)) {
					if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
						this.usePlayerItem(sourceentity, hand, itemstack);
						FoodProperties foodproperties = itemstack.getFoodProperties(this);
						float nutrition = foodproperties != null ? (float) foodproperties.nutrition() : 1;
						this.heal(nutrition);
						retval = InteractionResult.sidedSuccess(this.level().isClientSide());
					} else if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
						this.usePlayerItem(sourceentity, hand, itemstack);
						this.heal(4);
						retval = InteractionResult.sidedSuccess(this.level().isClientSide());
					} else {
						retval = super.mobInteract(sourceentity, hand);
					}
				}
			} else if (this.isFood(itemstack)) {
				this.usePlayerItem(sourceentity, hand, itemstack);
				if (this.random.nextInt(3) == 0 && !EventHooks.onAnimalTame(this, sourceentity)) {
					this.tame(sourceentity);
					this.level().broadcastEntityEvent(this, (byte) 7);
				} else {
					this.level().broadcastEntityEvent(this, (byte) 6);
				}
				this.setPersistenceRequired();
				retval = InteractionResult.sidedSuccess(this.level().isClientSide());
			} else {
				retval = super.mobInteract(sourceentity, hand);
				if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME)
					this.setPersistenceRequired();
			}
		}
		return retval;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level() instanceof ServerLevel serverLevel) {
			VibrationSystem.Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
		}
		if (this.level().isClientSide()) {
			this.animationState0.animateWhen(true, this.tickCount);
			this.animationState2.animateWhen(BlobPlaybackConditionProcedure.execute(), this.tickCount);
		}
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		BlobEntity retval = WModModEntities.BLOB.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return Ingredient.of(new ItemStack(Items.AMETHYST_SHARD)).test(stack);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(WModModEntities.BLOB.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)),
				RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 3);
		builder = builder.add(Attributes.ARMOR, 0.1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 67);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.4);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
		return builder;
	}

	private class VibrationUser implements VibrationSystem.User {
		private final BlobEntity entity = BlobEntity.this;
		private final PositionSource positionSource = new EntityPositionSource(this.entity, this.entity.getEyeHeight());

		@Override
		public PositionSource getPositionSource() {
			return this.positionSource;
		}

		@Override
		public TagKey<GameEvent> getListenableEvents() {
			return TagKey.create(Registries.GAME_EVENT, ResourceLocation.withDefaultNamespace("blob_can_listen"));
		}

		@Override
		public int getListenerRadius() {
			return 12;
		}

		@Override
		public boolean canReceiveVibration(ServerLevel world, BlockPos vibrationPos, Holder<GameEvent> holder, GameEvent.Context context) {
			return true;
		}

		@Override
		public void onReceiveVibration(ServerLevel world, BlockPos vibrationPos, Holder<GameEvent> holder, @Nullable Entity vibrationSource, @Nullable Entity projectileShooter, float distance) {
			BlobPlaybackConditionProcedure.execute();
		}
	}
}