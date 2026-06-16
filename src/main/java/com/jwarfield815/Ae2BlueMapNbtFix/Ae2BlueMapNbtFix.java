package com.jwarfield815.Ae2BlueMapNbtFix;

import com.technicjelle.BMUtils.BMNative.BMNLogger;
import com.technicjelle.BMUtils.BMNative.BMNMetadata;
import com.jwarfield815.Ae2BlueMapNbtFix.render.CableRenderer;
import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import org.jetbrains.annotations.Nullable;



import java.io.IOException;
import java.util.function.Consumer;

public class Ae2BlueMapNbtFix implements Runnable {
	private BMNLogger logger;
	private @Nullable Config config;

	private void addBluemapRegistryValues() {
		logger.logInfo("About to register values");
		BlockRendererType.REGISTRY.register(CableRenderer.TYPE);
	}

	@Override
	public void run() {
		String addonID;
		String addonVersion;
		try {
			addonID = BMNMetadata.getAddonID(this.getClass().getClassLoader());
			addonVersion = BMNMetadata.getKey(this.getClass().getClassLoader(), "version");
			logger = new BMNLogger(this.getClass().getClassLoader());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		logger.logInfo("Starting " + addonID + " " + addonVersion);

		addBluemapRegistryValues();

		BlueMapAPI.onEnable(onEnableListener);
		BlueMapAPI.onDisable(onDisableListener);
	}

	final private Consumer<BlueMapAPI> onEnableListener = api -> {

		try {
			config = Config.load(api);
		} catch (IOException e) {
			config = null;
			throw new RuntimeException(e);
		}

		logger.logInfo("Hello, " + config.getWorld() + "!");
	};

	final private Consumer<BlueMapAPI> onDisableListener = api -> {
		if (config == null) return;
		logger.logInfo("Goodbye, " + config.getWorld() + "!");
	};
}
