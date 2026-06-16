package com.jwarfield815.Ae2BlueMapNbtFix.render;

import de.bluecolored.bluemap.core.map.TextureGallery;
import de.bluecolored.bluemap.core.map.hires.RenderSettings;
import de.bluecolored.bluemap.core.map.hires.TileModel;
import de.bluecolored.bluemap.core.map.hires.TileModelView;
import de.bluecolored.bluemap.core.map.hires.block.BlockRenderer;
import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.ResourcePath;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.Variant;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Element;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Face;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.util.math.Color;
import de.bluecolored.bluemap.core.world.block.BlockNeighborhood;
import de.bluecolored.bluemap.core.world.block.ExtendedBlock;
import de.bluecolored.bluenbt.BlueNBT;

public class cableRenderer implements BlockRenderer {
    public static final BlockRendererType TYPE = new BlockRendererType.Impl(
            new Key("ae2", "cable_bus"), cableRenderer::new
    );
    private BlockNeighborhood block;
    private TileModelView blockModel;
    private Color blockColor;

    private TextureGallery textureGallery;
    private ResourcePack resourcePack;

    public cableRenderer(ResourcePack resourcePack, TextureGallery textureGallery, RenderSettings renderSettings) {
        System.out.println("contstructor");
        this.resourcePack = resourcePack;
        this.textureGallery = textureGallery;
    }

    @Override
    public void render(BlockNeighborhood block, Variant variant, TileModelView blockModel, Color color) {
//        ExtendedBlock myBlock = block.getNeighborBlock(-23, 103, 15);

        this.block = block;
        this.blockModel = blockModel;
        this.blockColor = color;

        System.out.println("rendering");

//        Model testModel = this.resourcePack.getModels().get(new ResourcePath<>("minecraft:block/oak_planks"));

        int textureId = textureGallery.get(
                new ResourcePath<>("minecraft:block/oak_planks")
        );

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