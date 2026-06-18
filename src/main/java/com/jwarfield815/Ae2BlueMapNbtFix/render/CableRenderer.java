package com.jwarfield815.Ae2BlueMapNbtFix.render;

import com.jwarfield815.Ae2BlueMapNbtFix.render.entityModel.CableRendererEntity;
import com.technicjelle.BMUtils.BMNative.BMNLogger;
import de.bluecolored.bluemap.core.map.TextureGallery;
import de.bluecolored.bluemap.core.map.hires.RenderSettings;
import de.bluecolored.bluemap.core.map.hires.TileModel;
import de.bluecolored.bluemap.core.map.hires.TileModelView;
import de.bluecolored.bluemap.core.map.hires.block.BlockRenderer;
import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.ResourcePath;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.Variant;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.util.math.Color;
import de.bluecolored.bluemap.core.world.BlockEntity;
import de.bluecolored.bluemap.core.world.block.BlockNeighborhood;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CableRenderer implements BlockRenderer {
    public static final BlockRendererType TYPE = new BlockRendererType.Impl(
            new Key("ae2", "cable_bus"), CableRenderer::new
    );

    private BMNLogger logger;
    private BlockNeighborhood block;
    private TileModelView blockModel;
    private Color blockColor;

    private TextureGallery textureGallery;
    private ResourcePack resourcePack;
    private float from = 0;
    private float to = 16;

    public CableRenderer(ResourcePack resourcePack, TextureGallery textureGallery, RenderSettings renderSettings) {
        try {
            logger = new BMNLogger(this.getClass().getClassLoader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.logInfo("contstructor");
        this.resourcePack = resourcePack;
        this.textureGallery = textureGallery;
    }

    @Override
    public void render(BlockNeighborhood block, Variant variant, TileModelView blockModel, Color color) {
        this.block = block;
        this.blockModel = blockModel;
        this.blockColor = color;
        String texturePath = "minecraft:block/warped_planks";

        BlockEntity blockEntity = block.getBlockEntity();
        if (blockEntity instanceof CableRendererEntity cableBus) {
            if (cableBus.getCable() != null) {
                texturePath = makeCable(cableBus.getCable().id);
            } else {
                texturePath = makeTerminal(cableBus.getUp(), cableBus.getDown(), cableBus.getNorth(), cableBus.getSouth(), cableBus.getEast(), cableBus.getWest());
            }
        }

        ResourcePath texture = new ResourcePath<>(texturePath);

        int textureId = textureGallery.get(texture);

        blockModel.initialize();
        blockModel.add(12);
        TileModel model = blockModel.getTileModel();
        int face = blockModel.getStart();

        makeFacesHelper(model, face, textureId);

        blockModel.scale(1f / 16f, 1f / 16f, 1f / 16f);
    }

    private String makeTerminal(
            CableRendererEntity.Up up, CableRendererEntity.Down down, CableRendererEntity.North north,
            CableRendererEntity.South south, CableRendererEntity.East east, CableRendererEntity.West west
            ) {

        return "ae2:part/pattern_access_terminal_medium";
    }

    private String makeCable(String cableId) {
        if (cableId.contains("fluix")) logger.logInfo(cableId);

        String[] elements = cableId.split(":")[1].split("_");
        Pattern pattern = Pattern.compile("ae2:(black|blue|brown|cyan|fluix|gray|green|light_blue|light_gray|lime|magenta|orange|pink|purple|red|transparent|white|yellow)_(covered|covered_dense|glass|smart|smart_dense)_cable");
        Matcher matcher = pattern.matcher(cableId);
        matcher.matches();

        String cableType = matcher.group(2);
        String cableColor = matcher.group(1);

        switch(cableType) {
            case "covered":
                this.from = 5;
                this.to = 11;
                break;
            case "covered_dense":
                cableType = "dense_covered";
                this.from = 3;
                this.to = 13;
                break;
            case "glass":
                this.from = 6;
                this.to = 10;
                break;
            case "smart":
                this.from = 5;
                this.to = 11;
                break;
            case "smart_dense":
                cableType = "dense_smart";
                this.from = 3;
                this.to = 13;
                break;
        }
        if (cableColor.equals("fluix")) cableColor = "transparent";

        return "ae2:part/cable/" + cableType + "/" + cableColor;
    }

    private void makeFaces(
            TileModel model,
            int triangleStart,
            int textureId,

            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3,
            float x4, float y4, float z4,
            float uvFrom, float uvTo
    ) {

        int tri1 = triangleStart;
        int tri2 = triangleStart + 1;

        model.setPositions(
                tri1,
                x1, y1, z1,
                x2, y2, z2,
                x3, y3, z3
        );

        model.setPositions(
                tri2,
                x1, y1, z1,
                x3, y3, z3,
                x4, y4, z4
        );

        model.setUvs(
                tri1,
                uvFrom, uvFrom,
                uvTo, uvFrom,
                uvTo, uvTo
        );

        model.setUvs(
                tri2,
                uvFrom, uvFrom,
                uvTo, uvTo,
                uvFrom, uvTo
        );

        model.setMaterialIndex(tri1, textureId);
        model.setMaterialIndex(tri2, textureId);

        model.setColor(tri1, 1, 1, 1);
        model.setColor(tri2, 1, 1, 1);

        model.setAOs(tri1, 1, 1, 1);
        model.setAOs(tri2, 1, 1, 1);

        model.setBlocklight(tri1, 15);
        model.setBlocklight(tri2, 15);

        model.setSunlight(tri1, 15);
        model.setSunlight(tri2, 15);
    }
    private void makeFacesHelper(TileModel model, int face, int textureId) {
        // DOWN
        makeFaces(model, face, textureId,
                this.from, this.from, this.from,
                this.to, this.from, this.from,
                this.to, this.from, this.to,
                this.from, this.from, this.to,
                this.from / 16, this.to / 16
        );
        face += 2;

        //UP
        makeFaces(model, face, textureId,
                this.from, this.to, this.from,
                this.from, this.to, this.to,
                this.to, this.to, this.to,
                this.to, this.to, this.from,
                this.from / 16, this.to / 16
        );
        face += 2;

        //NORTH
        makeFaces(model, face, textureId,
                this.to, this.from, this.from,
                this.from, this.from, this.from,
                this.from, this.to, this.from,
                this.to, this.to, this.from,
                this.from / 16, this.to / 16
        );
        face += 2;

        //SOUTH
        makeFaces(model, face, textureId,
                this.from, this.from, this.to,
                this.to, this.from, this.to,
                this.to, this.to, this.to,
                this.from, this.to, this.to,
                this.from / 16, this.to / 16
        );
        face += 2;

        //WEST
        makeFaces(model, face, textureId,
                this.from, this.from, this.from,
                this.from, this.from, this.to,
                this.from, this.to, this.to,
                this.from, this.to, this.from,
                this.from / 16, this.to / 16
        );
        face += 2;

        //EAST
        makeFaces(model, face, textureId,
                this.to, this.from, this.to,
                this.to, this.from, this.from,
                this.to, this.to, this.from,
                this.to, this.to, this.to,
                this.from / 16, this.to / 16
        );
    }

}



//bluemap debug world world -23 103 15