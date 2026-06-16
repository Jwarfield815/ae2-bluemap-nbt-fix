package com.technicjelle.BlueMapNativeAddonTemplate.render;

import com.technicjelle.BlueMapNativeAddonTemplate.render.entityModel.cableRendererEntity;
import de.bluecolored.bluemap.core.map.TextureGallery;
import de.bluecolored.bluemap.core.map.hires.RenderSettings;
import de.bluecolored.bluemap.core.map.hires.block.BlockRenderer;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.world.BlockEntity;
import de.bluecolored.bluemap.core.world.block.Block;
import com.technicjelle.BlueMapNativeAddonTemplate.render.entityModel.cableRendererEntity;
import de.bluecolored.bluemap.core.world.mca.blockentity.BlockEntityType;

public class cableRendererType implements BlockEntityType {
    @Override
    public Class<? extends BlockEntity> getBlockEntityClass() {
        return cableRendererEntity.class;
    }

    @Override
    public Key getKey() {
        return null;
    }
}
