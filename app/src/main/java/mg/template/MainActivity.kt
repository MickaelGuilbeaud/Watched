package mg.template

import android.os.Bundle
import mg.template.core.BaseActivity

class MainActivity : BaseActivity() {

    override val logsTag: String = BaseActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
