package com.example.examplemod.fabric

import com.example.examplemod.common.Constants
import com.example.examplemod.common.ExampleMod
import com.example.examplemod.common.api.ExampleModPlatform
import net.fabricmc.loader.api.FabricLoader

/**
 * The fabric module entry point
 */
object ExampleModFabric : ExampleModPlatform {
    override fun getPlatformName(): Constants.PLATFORM = Constants.PLATFORM.FABRIC
    override fun isModLoaded(modId: String?): Boolean = FabricLoader.getInstance().isModLoaded(modId)
    override fun isDevelopmentEnvironment(): Boolean = FabricLoader.getInstance().isDevelopmentEnvironment

    fun init() {
        ExampleMod.init(this)
        Constants.LOG.info("Hello Fabric world from Kotlin!")
    }
}