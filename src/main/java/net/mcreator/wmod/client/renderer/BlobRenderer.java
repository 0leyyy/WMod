package net.mcreator.wmod.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;

import net.mcreator.wmod.entity.BlobEntity;
import net.mcreator.wmod.client.model.animations.houdekloverAnimation;
import net.mcreator.wmod.client.model.Modelhoudeklover;

public class BlobRenderer extends MobRenderer<BlobEntity, Modelhoudeklover<BlobEntity>> {
	public BlobRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(Modelhoudeklover.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(BlobEntity entity) {
		return ResourceLocation.parse("w_mod:textures/entities/houdejlovertexture.png");
	}

	private static final class AnimatedModel extends Modelhoudeklover<BlobEntity> {
		private final ModelPart root;
		private final HierarchicalModel animator = new HierarchicalModel<BlobEntity>() {
			@Override
			public ModelPart root() {
				return root;
			}

			@Override
			public void setupAnim(BlobEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				this.root().getAllParts().forEach(ModelPart::resetPose);
				this.animate(entity.animationState0, houdekloverAnimation.idle, ageInTicks, 1f);
				this.animateWalk(houdekloverAnimation.walking, limbSwing, limbSwingAmount, 1f, 1f);
			}
		};

		public AnimatedModel(ModelPart root) {
			super(root);
			this.root = root;
		}

		@Override
		public void setupAnim(BlobEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}
}