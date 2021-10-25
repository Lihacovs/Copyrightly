package eu.balticit.copyrightly

import android.app.Application
import eu.balticit.copyrightly.data.AppRepositoryManager

class MyApp : Application() {

    companion object {
        var repositoryManager: AppRepositoryManager = AppRepositoryManager()
    }
}