package com.technicjelle.BlueMapNativeAddonTemplate.render;

import de.bluecolored.bluemap.core.map.TextureGallery;
import de.bluecolored.bluemap.core.map.hires.RenderSettings;
import de.bluecolored.bluemap.core.map.hires.TileModelView;
import de.bluecolored.bluemap.core.map.hires.block.BlockRenderer;
import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.Variant;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.util.math.Color;
import de.bluecolored.bluemap.core.util.math.MatrixM4f;
import de.bluecolored.bluemap.core.util.math.VectorM3f;
import de.bluecolored.bluemap.core.world.block.BlockNeighborhood;
import de.bluecolored.bluemap.core.world.block.ExtendedBlock;

public class cableRenderer implements BlockRenderer {
    public static final BlockRendererType TYPE = new BlockRendererType.Impl(
            new Key("ae2", "cable_bus"), cableRenderer::new
    );

    public cableRenderer(ResourcePack resourcePack, TextureGallery textureGallery, RenderSettings renderSettings) {
        System.out.println("contstructor");
    }

    @Override
    public void render(BlockNeighborhood block, Variant variant, TileModelView blockModel, Color color) {
        System.out.println(block.getClass());

//        ExtendedBlock myBlock = block.getNeighborBlock(-23, 103, 15);
        System.out.println(block.getBlockState().getProperties());
    }
}
//bluemap debug world world -23 103 15