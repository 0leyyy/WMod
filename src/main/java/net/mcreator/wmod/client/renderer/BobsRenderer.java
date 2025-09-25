package net.mcreator.wmod.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;

import net.mcreator.wmod.entity.BobsEntity;
import net.mcreator.wmod.client.model.animations.BobsAnimation;
import net.mcreator.wmod.client.model.ModelBobs;

public class BobsRenderer extends MobRenderer<BobsEntity, ModelBobs<BobsEntity>> {
	public BobsRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(ModelBobs.LAYER_LOCATION)), 0.1f);
	}

	@Override
	public ResourceLocation getTextureLocation(BobsEntity entity) {
		return ResourceLocation.parse("w_mod:textures/entities/bobstexture.png");
	}

	private static final class AnimatedModel extends ModelBobs<BobsEntity> {
		private final ModelPart root;
		private final HierarchicalModel animator = new HierarchicalModel<BobsEntity>() {
			@Override
			public ModelPart root() {
				return root;
			}

			@Override
			public void setupAnim(BobsEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				this.root().getAllParts().forEach(ModelPart::resetPose);
				this.animate(entity.animationState0, BobsAnimation.idle, ageInTicks, 1f);
				this.animate(entity.animationState1, BobsAnimation.dance, ageInTicks, 1f);
				this.animateWalk(BobsAnimation.Walking, limbSwing, limbSwingAmount, 1f, 1f);
				this.animate(entity.animationState3, BobsAnimation.Jump, ageInTicks, 1f);
			}
		};

		public AnimatedModel(ModelPart root) {
			super(root);
			this.root = root;
		}

		@Override
		public void setupAnim(BobsEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}
}