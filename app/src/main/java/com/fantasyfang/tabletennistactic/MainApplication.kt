package com.fantasyfang.tabletennistactic

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.fantasyfang.tabletennistactic.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initFlipper()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    private fun initFlipper() {
        if (FlipperUtils.shouldEnableFlipper(
                this,
                true
            )
        ) {
            SoLoader.init(this, false)

            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(
                    InspectorFlipperPlugin(
                        this@MainApplication,
                        DescriptorMapping.withDefaults()
                    )
                )
                addPlugin(NetworkFlipperPlugin())
                addPlugin(DatabasesFlipperPlugin(this@MainApplication))
                addPlugin(
                    SharedPreferencesFlipperPlugin(
                        this@MainApplication,
                        "my_shared_preference_file"
                    )
                )
                start()
            }
        }
    }
}

