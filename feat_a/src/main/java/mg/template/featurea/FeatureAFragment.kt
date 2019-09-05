package mg.template.featurea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_feature_a.*
import mg.template.core.BaseFragment

class FeatureAFragment : BaseFragment() {

    companion object {
        fun newInstance() = FeatureAFragment()
    }

    override val logsTag: String = FeatureAFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_feature_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnGoToFeatureB.setOnClickListener { /* TODO */ }
    }
}