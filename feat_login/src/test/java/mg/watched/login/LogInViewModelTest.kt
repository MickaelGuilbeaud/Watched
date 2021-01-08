package mg.watched.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import mg.watched.core.utils.WResult
import mg.watched.data.authentication.AuthenticationManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class LogInViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private val validUsername: String = "AnimeFan"
    private val emptyUsername: String = ""
    private val emptyPassword: String = ""
    private val wrongPassword: String = "0000"
    private val validPassword: String = "1234"

    private val authenticationManager: AuthenticationManager = mockk {
        coEvery { authenticateUser(any(), any()) } returns WResult.Success(Unit)
        coEvery { authenticateUser(validUsername, wrongPassword) } returns WResult.Failure(Exception())
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    private fun createViewModel(
        authenticationManager: AuthenticationManager = this.authenticationManager,
        defaultDispatcher: CoroutineDispatcher = this.testDispatcher,
    ): LogInViewModel = LogInViewModel(
        authenticationManager = authenticationManager,
        defaultDispatcher = defaultDispatcher,
    )

    @Test
    fun `When screen is opened, then no error is shown`() = runBlockingTest {
        // Given
        // When
        val viewModel = createViewModel()
        // Then
        assertThat(viewModel.viewStates().value!!.showUsernameIsEmptyError).isFalse()
        assertThat(viewModel.viewStates().value!!.showPasswordIsEmptyError).isFalse()
    }

    @Test
    fun `When username is empty, then username is empty error is shown`() = runBlockingTest {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(emptyUsername, validPassword)
        // Then
        assertThat(viewModel.viewStates().value!!.showUsernameIsEmptyError).isTrue()
    }

    @Test
    fun `When password is empty, then the password is empty error is shown`() = runBlockingTest {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validUsername, emptyPassword)
        // Then
        assertThat(viewModel.viewStates().value!!.showPasswordIsEmptyError).isTrue()
    }

    @Test
    fun `When credentials are wrong, then the wrong credential error is shown`() = runBlockingTest {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validUsername, wrongPassword)
        // Then
        assertThat(viewModel.actionEvents().value!!.peekContent())
            .isInstanceOf(LogInActionEvent.LogInFailed::class.java)
    }

    @Test
    fun `When credentials are valid, then Go to next screen is called`() = runBlockingTest {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validUsername, validPassword)
        // Then
        assertThat(viewModel.navigationEvents().value!!.peekContent())
            .isInstanceOf(LogInNavigationEvent.GoToAnimesScreen::class.java)
    }
}
