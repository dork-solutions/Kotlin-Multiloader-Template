package com.example.examplemod.neoforge

import com.example.examplemod.common.Constants
import com.example.examplemod.common.ExampleMod
import com.example.examplemod.common.api.ExampleModPlatform
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import net.neoforged.fml.common.Mod
import net.neoforged.fml.loading.FMLLoader

@Mod(Constants.MOD_ID)
class ExampleModNeoForge(eventBus: IEventBus, modContainer: ModContainer): ExampleModPlatform {
    override fun getPlatformName(): Constants.PLATFORM = Constants.PLATFORM.NEOFORGE
    override fun isModLoaded(modId: String?): Boolean = ModList.get().isLoaded(modId)
    override fun isDevelopmentEnvironment(): Boolean = !FMLLoader.isProduction()

    init {
        ExampleMod.init(this)
        Constants.LOG.info("Hello NeoForge world from Kotlin!")
    }
}