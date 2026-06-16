package com.jwarfield815.Ae2BlueMapNbtFix.render.entityModel;

import de.bluecolored.bluemap.core.world.mca.blockentity.MCABlockEntity;
import de.bluecolored.bluenbt.NBTName;

public class CableRendererEntity extends MCABlockEntity {
    @NBTName("cable.id") private String cableId;

    public CableRendererEntity() {
        super();
    }
}
