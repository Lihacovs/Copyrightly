package eu.balticit.copyrightly.utils

import android.content.Intent
import eu.balticit.copyrightly.R
import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

object AppUtils {

    fun openPlayStoreForApp(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        context
                            .resources
                            .getString(R.string.app_market_link) + appPackageName
                    )
                )
            )
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        context
                            .resources
                            .getString(R.string.app_google_play_store_link) + appPackageName
                    )
                )
            )
        }
    }

    fun composeEmail(context: Context, addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            startActivity(context, intent, null)
        }
    }

}