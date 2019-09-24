package mg.template.featurea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_feature_a.*
import mg.template.core.BaseFragment
import mg.template.core.requireBaseActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class FeatureAFragment : BaseFragment(FeatureAFragment::class.java.simpleName) {

    companion object {
        fun newInstance() = FeatureAFragment()
    }

    private val router: FeatureARouter by inject { parametersOf(requireBaseActivity()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_feature_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnGoToFeatureB.setOnClickListener { router.routeToBScreen() }
    }
}