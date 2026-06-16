package com.technicjelle.BlueMapNativeAddonTemplate.render.entityModel;

import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.world.BlockEntity;
import de.bluecolored.bluemap.core.world.mca.blockentity.MCABlockEntity;
import de.bluecolored.bluenbt.NBTName;

public class cableRendererEntity extends MCABlockEntity {
    @NBTName("cable.id") private String cableId;

    public cableRendererEntity() {
        super();
    }
}
