package com.jwarfield815.Ae2BlueMapNbtFix.render;

import com.jwarfield815.Ae2BlueMapNbtFix.render.entityModel.CableRendererEntity;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.world.BlockEntity;
import de.bluecolored.bluemap.core.world.mca.blockentity.BlockEntityType;

public class CableRendererType implements BlockEntityType {
    @Override
    public Class<? extends BlockEntity> getBlockEntityClass() {
        return CableRendererEntity.class;
    }

    @Override
    public Key getKey() {
        return null;
    }
}
