package mg.template.featureb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mg.template.core.BaseFragment

class FeatureBFragment : BaseFragment() {

    override val logsTag: String = FeatureBFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_feature_b, container, false)
    }
}