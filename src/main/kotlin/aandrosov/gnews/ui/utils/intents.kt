package aandrosov.gnews.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.openWebPage(url: String) {
    val webpage: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}