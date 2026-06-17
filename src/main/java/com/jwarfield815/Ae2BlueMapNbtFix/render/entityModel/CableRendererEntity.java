package com.jwarfield815.Ae2BlueMapNbtFix.render.entityModel;

import de.bluecolored.bluemap.core.world.mca.blockentity.MCABlockEntity;
import de.bluecolored.bluenbt.NBTName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@SuppressWarnings({"FieldMayBeFinal", "unused"})
public class CableRendererEntity extends MCABlockEntity {
    @Nullable String customName;
    @Nullable String noteBlockSound;
    @Nullable Profile profile;

    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Profile {

        @Nullable UUID id;
        @Nullable String name;
        List<Property> properties = List.of();

    }

    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Property {

        String name;
        String value;
        @Nullable String signature;

        private Property(Map<String, Object> data) {
            this.signature = (String) data.get("signature");
            this.value = (String) data.getOrDefault("value", "");
        }

    }
}
