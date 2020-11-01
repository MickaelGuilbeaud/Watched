package mg.watched.data.anime

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import mg.watched.core.utils.TrampolineSchedulerProvider
import mg.watched.data.anime.db.AnimeDao
import mg.watched.data.anime.db.models.Anime
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.AnimesEntity
import org.junit.Before
import org.junit.Test

class AnimeRepositoryTest {

    @MockK
    private lateinit var animeDao: AnimeDao

    @MockK
    private lateinit var animeService: AnimeService

    private val anime1 = Anime(
        0,
        "One Piece",
        "",
        "Pirates",
        600,
        listOf("Shounen"),
        listOf("Tohei"),
        5.0,
        0
    )
    private val anime2 = Anime(
        0,
        "Bakemonogatari",
        "",
        "Monsters",
        14,
        listOf("Seinen"),
        listOf("Shaft"),
        5.0,
        1
    )
    private val animes: List<Anime> = listOf(anime1, anime2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `When there is no data and first subscription is made, then data is loaded from network`() {
        // Given
        every { animeDao.getAllStream() } returns Observable.just(emptyList())
        every { animeService.getSeasonAnimes(any(), any()) } returns Single.just(AnimesEntity())
        val repository = AnimeRepository(animeDao, animeService, TrampolineSchedulerProvider())

        // When
        repository.getSeasonAnimesStream()
            .test()

        // Then
        verify { animeService.getSeasonAnimes(any(), any()) }
    }
}
