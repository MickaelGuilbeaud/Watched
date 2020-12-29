package mg.watched.animes.editanimeliststatus

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import mg.watched.data.anime.network.models.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class EditAnimeListStatusViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val nbEpisodesTotal: Int = 12
    private val nbEpisodesWatched: Int = 5

    private val baseMyListStatus: MyListStatus = MyListStatus(nbEpisodesWatched, 10.0, WatchStatus.WATCHING)

    private val anime: Anime = Anime(
        1,
        "One Piece",
        AlternativeTitles("One Piece", "One Piece"),
        "Synopsis",
        AnimePicture("url", "url"),
        MediaType.TV,
        null,
        nbEpisodesTotal,
        AiringStatus.CURRENTLY_AIRING,
        baseMyListStatus,
    )

    private fun createViewModel(
        anime: Anime = this.anime,
    ): EditAnimeListStatusViewModel = EditAnimeListStatusViewModel(
        anime,
    )

    @Test
    fun `When screen is opened, then nb watched episode is from MyListStatus`() {
        // Given
        // When
        val viewModel = createViewModel()
        // Then
        assertThat(viewModel.viewStates().value!!.nbEpisodesWatched).isEqualTo(nbEpisodesWatched)
    }

    @Test
    fun `Total number of episode is from the anime data`() {
        // Given
        // When
        val viewModel = createViewModel()
        // Then
        assertThat(viewModel.viewStates().value!!.nbEpisodesTotal).isEqualTo(nbEpisodesTotal)
    }

    @Test
    fun `When adding watched episode, then nb watched episode doesn't go above total number of episode`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.addWatchedEpisode(100)
        // Then
        assertThat(viewModel.viewStates().value!!.nbEpisodesWatched).isEqualTo(nbEpisodesTotal)
    }

    @Test
    fun `When removing watched episode, then nb watched episode doesn't go below zero`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.removeWatchedEpisode(100)
        // Then
        assertThat(viewModel.viewStates().value!!.nbEpisodesWatched).isEqualTo(0)
    }

    @Test
    fun `When updating list status, then navigation event contains updated list status`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.addWatchedEpisode(100)
        viewModel.applyChanges(WatchStatus.COMPLETED, 8.0)
        // Then
        assertThat(viewModel.navigationEvents().value!!.peekContent())
            .isInstanceOf(EditAnimeListStatusNavigationEvent.GoToAnimeDetailScreen::class.java)
        val navigationEvent = viewModel.navigationEvents().value!!.peekContent() as
            EditAnimeListStatusNavigationEvent.GoToAnimeDetailScreen
        assertThat(navigationEvent.updatedListStatus.nbEpisodesWatched).isEqualTo(nbEpisodesTotal)
        assertThat(navigationEvent.updatedListStatus.status).isEqualTo(WatchStatus.COMPLETED)
        assertThat(navigationEvent.updatedListStatus.score).isEqualTo(8.0)
    }
}