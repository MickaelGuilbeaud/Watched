package mg.template.featurea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_pokedex.*
import mg.template.core.BaseFragment
import mg.template.design.SimpleDividerItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexFragment : BaseFragment(PokedexFragment::class.java.simpleName) {

    companion object {
        fun newInstance() = PokedexFragment()
    }

    private val viewModel: PokedexViewModel by viewModel()

    private val pokemonsAdapter: PokemonAdapter = PokemonAdapter()

    // region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_pokedex, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.viewStates().observe(viewLifecycleOwner) { bindViewState(it) }
    }

    private fun initUI() {
        rvPokemons.setHasFixedSize(true)
        rvPokemons.adapter = pokemonsAdapter
        rvPokemons.addItemDecoration(SimpleDividerItemDecoration(requireContext()))

        //btnGoToFeatureB.setOnClickListener { getFeatureARouter().routeToBScreen() }
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: PokedexViewState) {
        when (viewState) {
            is PokedexViewState.Pokemons -> {
                pokemonsAdapter.submitList(viewState.pokemons)
            }
        }
    }

    // endregion
}