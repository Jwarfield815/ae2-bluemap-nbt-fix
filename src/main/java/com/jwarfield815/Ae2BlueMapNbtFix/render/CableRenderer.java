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

//        var black = resourcePack.getTexture(new ResourcePath<>("ae2:part/cable/smart/black"));
//        var lime = resourcePack.getTexture(new ResourcePath<>("ae2:part/cable/smart/lime"));
//        var red = resourcePack.getTexture(new ResourcePath<>("ae2:part/cable/smart/red"));
//
//        logger.logInfo("Black Texture exists? " + black.getResourcePath());
//        logger.logInfo("Lime Texture exists? " + lime.getResourcePath());
//        logger.logInfo("red Texture exists? " + red.getResourcePath());

//        resourcePack.getTextures()
//                .values().stream().filter(t -> t.getResourcePath().toString().contains("part/cable"))
//                .forEach(t -> logger.logInfo(t.getResourcePath().toString()));

        ResourcePath texture = new ResourcePath<>("minecraft:block/warped_planks");

        BlockEntity blockEntity = block.getBlockEntity();
        if (blockEntity instanceof CableRendererEntity cableBus) {
            if (cableBus.getCable() != null) {
                texture = makeCable(cableBus.getCable().id);
            }
        }

        int textureId = textureGallery.get(texture);

        blockModel.initialize();
        blockModel.add(12);
        TileModel model = blockModel.getTileModel();
        int face = blockModel.getStart();

// DOWN
        makeFace(model, face, textureId,
                0,0,0,
                16,0,0,
                16,0,16,
                0,0,16
        );
        face += 2;

// UP
        makeFace(model, face, textureId,
                0,16,0,
                0,16,16,
                16,16,16,
                16,16,0
        );
        face += 2;

// NORTH
        makeFace(model, face, textureId,
                16,0,0,
                0,0,0,
                0,16,0,
                16,16,0
        );
        face += 2;

// SOUTH
        makeFace(model, face, textureId,
                0,0,16,
                16,0,16,
                16,16,16,
                0,16,16
        );
        face += 2;

// WEST
        makeFace(model, face, textureId,
                0,0,0,
                0,0,16,
                0,16,16,
                0,16,0
        );
        face += 2;

// EAST
        makeFace(model, face, textureId,
                16,0,16,
                16,0,0,
                16,16,0,
                16,16,16
        );

        blockModel.scale(1f / 16f, 1f / 16f, 1f / 16f);
    }

    private ResourcePath makeCable(String cableId) {
        if (cableId.contains("fluix")) logger.logInfo(cableId);

        String[] elements = cableId.split(":")[1].split("_");
        Pattern pattern = Pattern.compile("ae2:(black|blue|brown|cyan|fluix|gray|green|light_blue|light_gray|lime|magenta|orange|pink|purple|red|transparent|white|yellow)_(covered|covered_dense|glass|smart|smart_dense)_cable");
        Matcher matcher = pattern.matcher(cableId);
        matcher.matches();

        String cableType = matcher.group(2);
        String cableColor = matcher.group(1);

        if (cableType.equals("smart_dense")) cableType = "dense_smart";
        if (cableType.equals("covered_dense")) cableType = "dense_covered";
        if (cableColor.equals("fluix")) cableColor = "transparent";

        return new ResourcePath<>("ae2:part/cable/" + cableType + "/" + cableColor); //wrap in try catch???
    }

    private void makeFace(
            TileModel model,
            int triangleStart,
            int textureId,

            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3,
            float x4, float y4, float z4
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
                0, 0,
                1, 0,
                1, 1
        );

        model.setUvs(
                tri2,
                0, 0,
                1, 1,
                0, 1
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
}



//bluemap debug world world -23 103 15