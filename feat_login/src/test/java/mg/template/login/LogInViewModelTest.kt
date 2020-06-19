package mg.template.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LogInViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val validEmail: String = "toto@gmail.com"
    private val invalidEmail: String = "toto"
    private val emptyPassword: String = ""
    private val wrongPassword: String = "0000"
    private val validPassword: String = "1234"

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun createViewModel(): LogInViewModel = LogInViewModel()

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
    fun `When email is invalid, then email is invalid error is shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(invalidEmail, validPassword)
        // Then
        assertThat(viewModel.viewStates().value!!.showUsernameIsEmptyError).isTrue()
    }

    @Test
    fun `When password is empty, then the password is empty error is shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validEmail, emptyPassword)
        // Then
        assertThat(viewModel.viewStates().value!!.showPasswordIsEmptyError).isTrue()
    }

    @Test
    fun `When credentials are wrong, then the wrong credential error is shown`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validEmail, wrongPassword)
        // Then
        assert(false)
    }

    @Test
    fun `When credentials are valid, then Go to next screen is called`() {
        // Given
        val viewModel = createViewModel()
        // When
        viewModel.logIn(validEmail, validPassword)
        // Then
        assert(false)
    }
}