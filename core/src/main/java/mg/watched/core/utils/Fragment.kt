package mg.watched.core.utils

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArguments(vararg pairs: Pair<String, Any?>): T {
    val args = arguments
    if (args == null) {
        arguments = bundleOf(*pairs)
    } else {
        args.putAll(bundleOf(*pairs))
    }
    return this
}