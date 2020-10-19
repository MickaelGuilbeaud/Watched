package mg.watched.animes.animesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import mg.watched.core.utils.SchedulerProvider
import mg.watched.core.utils.TrampolineSchedulerProvider
import mg.watched.data.anime.AnimeRepository
import mg.watched.data.anime.AnimeSearchDataSourceFactory
import mg.watched.data.anime.network.models.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AnimeSearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    init {
        // FIXME: Find a way to properly mock the toObservable static method
        //mockkStatic("RxPagedListKt.FileKt")
    }

    private val anime: Anime = Anime(
        1,
        "One Piece",
        AlternativeTitles("One Piece", "One Piece"),
        "Synopsis",
        AnimePicture("url", "url"),
        MediaType.TV,
        null,
        1000,
        AiringStatus.CURRENTLY_AIRING,
        null,
    )
    private val emptyAnimeList: List<Anime> = emptyList()
    private val twoAnimesList: List<Anime> = listOf(anime, anime)
    private val threeAnimesList: List<Anime> = listOf(anime, anime, anime)

    private val mockedPagedList: PagedList<Anime> = mockk()
    private val animeSearchDataSourceFactory: AnimeSearchDataSourceFactory = mockk {
        // FIXME: Find a way to properly mock the toObservable static method
        //every { toObservable(any<PagedList.Config>()) } returns Observable.just(mockedPagedList)
    }
    private val animeRepository: AnimeRepository = mockk {
        every { createAnimeSearchDaTaSourceFactory() } returns animeSearchDataSourceFactory
    }

    private fun createViewModel(
        animeRepository: AnimeRepository = this.animeRepository,
        schedulerProvider: SchedulerProvider = TrampolineSchedulerProvider(),
    ): AnimeSearchViewModel = AnimeSearchViewModel(
        animeRepository,
        schedulerProvider,
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `When screen is opened, then No search view state is shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        // Then
        assertThat(viewModel.viewStates().value!!).isInstanceOf(AnimeSearchViewState.NoSearch::class.java)
    }

    @Test
    fun `When 2 characters are entered, then No search view state is still shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.searchAnimes("ab")
        // Then
        assertThat(viewModel.viewStates().value!!).isInstanceOf(AnimeSearchViewState.NoSearch::class.java)
    }

    @Test
    fun `When 3 characters are entered, then Search results view state is shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.searchAnimes("abc")
        // Then
        // FIXME: Find a way to properly mock the toObservable static method
        assertThat(viewModel.viewStates().value!!).isInstanceOf(AnimeSearchViewState.SearchResults::class.java)
    }
}