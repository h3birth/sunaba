package birth.h3.app.youtubeplayer

import android.app.Application
import birth.h3.app.youtubeplayer.api.YoutubeApiRepository
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainApplication : Application() {

    val appModule: Module = module {
        single { androidApplication() }
        single { YoutubeApiRepository() }
    }
    val viewModelModule: Module = module {
        viewModel { MainViewModel() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, viewModelModule))

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
