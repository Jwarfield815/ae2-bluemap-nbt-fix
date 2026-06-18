package com.jwarfield815.Ae2BlueMapNbtFix.render.entityModel;

import de.bluecolored.bluemap.core.util.Registry;
import de.bluecolored.bluemap.core.world.mca.blockentity.BlockEntityType;
import de.bluecolored.bluemap.core.world.mca.blockentity.MCABlockEntity;
import de.bluecolored.bluenbt.NBTName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class CableRendererEntity extends MCABlockEntity {
    @NBTName("cable") private Cable cable;
    @NBTName("up") private Up up;
    @NBTName("down") private Down down;
    @NBTName("north") private North north;
    @NBTName("south") private South south;
    @NBTName("east") private East east;
    @NBTName("west") private West west;

    public static class Cable {
        @NBTName("id") public String id;
    }

    public static class Up {
        @NBTName("id") public String upId;
    }

    public static class Down {
        @NBTName("id") public String downId;
    }

    public static class North {
        @NBTName("id") public String northId;
    }

    public static class South {
        @NBTName("id") public String southId;
    }

    public static class East {
        @NBTName("id") public String eastId;
    }

    public static class West {
        @NBTName("id") public String westId;
    }

    public CableRendererEntity() {
        super();
    }
}
