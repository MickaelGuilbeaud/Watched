package mg.template.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_pokedex.*
import mg.template.core.base.BaseFragment
import mg.template.core.utils.addNeedToLoadMoreListener
import mg.template.core.utils.exhaustive
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
        toolbar.title = getString(R.string.pokedex_title)

        rvPokemons.setHasFixedSize(true)
        rvPokemons.adapter = pokemonsAdapter
        rvPokemons.addItemDecoration(SimpleDividerItemDecoration(requireContext()))
        rvPokemons.addNeedToLoadMoreListener { viewModel.loadMorePokemons() }

        //btnGoToFeatureB.setOnClickListener { getPokedexRouter().routeToBScreen() }
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: PokedexViewState) {
        when (viewState) {
            PokedexViewState.Loading -> {
                pbLoading.isVisible = true
                tvError.isVisible = false
                rvPokemons.isVisible = false
            }
            is PokedexViewState.Error -> {
                pbLoading.isVisible = false
                tvError.isVisible = true
                rvPokemons.isVisible = false
            }
            is PokedexViewState.Pokemons -> {
                pbLoading.isVisible = false
                tvError.isVisible = false
                rvPokemons.isVisible = true

                pokemonsAdapter.submitList(viewState.pokemons)
            }
        }.exhaustive
    }

    // endregion
}