package net.mcreator.wmod.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelhoudeklover<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("w_mod", "modelhoudeklover"), "main");
	public final ModelPart bone;
	public final ModelPart bone2;
	public final ModelPart bone3;
	public final ModelPart bone4;
	public final ModelPart bone5;
	public final ModelPart bone6;
	public final ModelPart bone7;
	public final ModelPart bone8;
	public final ModelPart bone9;
	public final ModelPart bone10;
	public final ModelPart bone11;

	public Modelhoudeklover(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone2 = root.getChild("bone2");
		this.bone3 = root.getChild("bone3");
		this.bone4 = root.getChild("bone4");
		this.bone5 = root.getChild("bone5");
		this.bone6 = root.getChild("bone6");
		this.bone7 = root.getChild("bone7");
		this.bone8 = root.getChild("bone8");
		this.bone9 = root.getChild("bone9");
		this.bone10 = root.getChild("bone10");
		this.bone11 = root.getChild("bone11");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(10, 26).addBox(-1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(26, 11).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone6 = partdefinition.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone7 = partdefinition.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone8 = partdefinition.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone9 = partdefinition.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone10 = partdefinition.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition bone11 = partdefinition.addOrReplaceChild("bone11",
				CubeListBuilder.create().texOffs(26, 15).addBox(-1.0F, -12.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(18, 26).addBox(-1.0F, -12.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-3.0F, -5.0F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(-3.0F, -11.0F, -4.0F, 5.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 21)
						.addBox(-2.0F, -9.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(10, 21).addBox(-2.0F, -9.0F, 2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 21)
						.addBox(-2.0F, -9.0F, -3.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(26, 19).addBox(-1.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone6.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone7.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone8.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone9.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone10.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
		bone11.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
	}
}