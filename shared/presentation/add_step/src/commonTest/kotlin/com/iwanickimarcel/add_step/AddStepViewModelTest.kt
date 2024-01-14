package com.iwanickimarcel.add_step

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.iwanickimarcel.test.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddStepViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var validateStep: ValidateStep
    private lateinit var viewModel: AddStepViewModel

    @Before
    fun setUp() {
        validateStep = ValidateStep()
        viewModel = AddStepViewModel(validateStep)
    }

    @Test
    fun `initially state with default values should be emitted`() = runTest {
        viewModel.state.test {
            val expectedState = AddStepState()
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on step changed called, state with step should be emitted`() = runTest {
        viewModel.state.test {
            viewModel.onEvent(
                AddStepEvent.OnStepChanged(TextFieldValue("Do something"))
            )

            val expectedState = AddStepState(
                step = TextFieldValue("Do something"),
                stepError = null
            )

            skipItems(1)
            assertThat(awaitItem()).isEqualTo(expectedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when on add step click called and data is correct, state with success should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddStepEvent.OnStepChanged(TextFieldValue("Do something"))
                )
                viewModel.onEvent(
                    AddStepEvent.OnAddStepClick(
                        stepsCount = 1,
                        onStepAdded = {}
                    )
                )

                val expectedState = AddStepState(
                    step = TextFieldValue("Do something"),
                    stepError = null,
                    success = true
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when on add step click called and data is incorrect, state with step error should be emitted`() =
        runTest {
            viewModel.state.test {
                viewModel.onEvent(
                    AddStepEvent.OnStepChanged(TextFieldValue(""))
                )
                viewModel.onEvent(
                    AddStepEvent.OnAddStepClick(
                        stepsCount = 1,
                        onStepAdded = {}
                    )
                )

                val expectedState = AddStepState(
                    step = TextFieldValue(""),
                    stepError = "Fill step",
                )

                skipItems(2)
                assertThat(awaitItem()).isEqualTo(expectedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

}