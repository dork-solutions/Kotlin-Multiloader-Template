package com.example.examplemod.common

import com.example.examplemod.common.api.ExampleModPlatform
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Items

/**
 * This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
 * import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
 * common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
 * however it will be compatible with all supported mod loaders.
 */
object ExampleMod {
    lateinit var platform: ExampleModPlatform

    /**
     * The loader specific projects are able to import and use any code from the common project. This allows you to
     * write the majority of your code here and load it from your loader specific projects. This example has some
     * code that gets invoked by the entry point of the loader specific projects.
     */
    fun init(platform: ExampleModPlatform) {
        this.platform = platform
        Constants.LOG.info(
            "Hello from Common init on {}! we are currently in a {} environment!",
            platform.getPlatformName(),
            platform.getEnvironmentName()
        )
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND))
    }
}