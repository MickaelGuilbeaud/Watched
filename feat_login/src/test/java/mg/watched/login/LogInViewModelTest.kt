package mg.watched.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import mg.watched.core.utils.SchedulerProvider
import mg.watched.core.utils.TrampolineSchedulerProvider
import mg.watched.data.usecases.LogInUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LogInViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val validUsername: String = "AnimeFan"
    private val emptyUsername: String = ""
    private val emptyPassword: String = ""
    private val wrongPassword: String = "0000"
    private val validPassword: String = "1234"

    private val logInUseCase: LogInUseCase = mockk {
        every { logIn(any(), any()) } returns Completable.complete()
        every { logIn(validUsername, wrongPassword) } returns Completable.error(Exception())
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun createViewModel(
        logInUseCase: LogInUseCase = this.logInUseCase,
        schedulerProvider: SchedulerProvider = TrampolineSchedulerProvider()
    ): LogInViewModel = LogInViewModel(
        logInUseCase = logInUseCase,
        schedulerProvider = schedulerProvider
    )

    @Test
    fun `When screen is opened, then no error is shown`() {
        // Given
        // When
        val viewModel = createViewModel()
        // Then
        assertThat(viewModel.viewStates().value!!.showUsernameIsEmptyError).isFalse()
        assertThat(viewModel.viewStates().value!!.showPasswordIsEmptyError).isFalse()
    }

    @Test
    fun `When username is empty, then username is empty error is shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(emptyUsername, validPassword)
        // Then
        assertThat(viewModel.viewStates().value!!.showUsernameIsEmptyError).isTrue()
    }

    @Test
    fun `When password is empty, then the password is empty error is shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validUsername, emptyPassword)
        // Then
        assertThat(viewModel.viewStates().value!!.showPasswordIsEmptyError).isTrue()
    }

    @Test
    fun `When credentials are wrong, then the wrong credential error is shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validUsername, wrongPassword)
        // Then
        assertThat(viewModel.actionEvents().value!!.peekContent())
            .isInstanceOf(LogInActionEvent.LogInFailed::class.java)
    }

    @Test
    fun `When credentials are valid, then Go to next screen is called`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validUsername, validPassword)
        // Then
        assertThat(viewModel.navigationEvents().value!!.peekContent())
            .isInstanceOf(LogInNavigationEvent.GoToAnimesScreen::class.java)
    }
}