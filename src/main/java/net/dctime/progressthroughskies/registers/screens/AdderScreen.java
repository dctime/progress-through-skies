package net.dctime.progressthroughskies.registers.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.lib.FluidTankRenderer;
import net.dctime.progressthroughskies.lib.MouseUtil;
import net.dctime.progressthroughskies.registers.ModFluids;
import net.dctime.progressthroughskies.registers.menus.AdderMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

public class AdderScreen extends AbstractContainerScreen<AdderMenu>
{
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ProgressThroughSkies.MODID, "textures/gui/adder_gui.png");
    private FluidTankRenderer renderer;

    public AdderScreen(AdderMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignFluidRenderer();
    }

    private void assignFluidRenderer()
    {
        renderer = new FluidTankRenderer(1000, true, 11, 48);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // DETECT MOUSE HOVER
        if (MouseUtil.isMouseOver(pMouseX, pMouseY, x+13, y+21, renderer.getWidth(), renderer.getHeight()))
        {
            renderTooltip(pPoseStack, renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }



    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        // x, y left top coor
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting())
        {
            blit(pPoseStack, x + 58, y + 40, 176, 0, 12, menu.getScaledProgress());
        }

        renderer.render(pPoseStack, x+13, y+21, menu.getFluidStack());

    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
