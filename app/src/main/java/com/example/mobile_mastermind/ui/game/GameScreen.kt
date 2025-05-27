package com.example.mobile_mastermind.ui.game

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mobile_mastermind.Home
import com.example.mobile_mastermind.Review
import com.example.mobile_mastermind.domain.model.game.CategoryModel
import com.example.mobile_mastermind.ui.components.ProgressCircle
import com.example.mobile_mastermind.ui.home.ErrorScreen
import com.example.mobile_mastermind.ui.theme.BackgroundLight
import com.example.mobile_mastermind.ui.theme.Black
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.PlaceholderLight
import com.example.mobile_mastermind.ui.theme.RedLight
import com.example.mobile_mastermind.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
fun GameScreen(
    category: CategoryModel,
    navController: NavController,
    gameViewModel: GameViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        gameViewModel.loadQuestions(category)
    }

    val uiState by gameViewModel.uiState

    when {
        uiState.isLoading -> ProgressCircle()

        uiState.errorMessage != null -> {
            ErrorScreen(message = uiState.errorMessage) {
                navController.navigate(Home.route) {
                    popUpTo(0)
                }
            }
        }

        uiState.questions.isEmpty() -> ProgressCircle()

        else -> {
            GameBody(
                navController = navController, uiState = uiState, gameViewModel = gameViewModel
            )
        }
    }
}

@Composable
private fun GameBody(
    navController: NavController, uiState: GameUiState, gameViewModel: GameViewModel
) {
    val currentQuestion = uiState.questions.getOrNull(uiState.currentQuestionIndex)

    if (currentQuestion == null) {
        val resumeGame = uiState.resumeGame
        navController.previousBackStackEntry?.savedStateHandle?.set("resumeGame", resumeGame)
        navController.popBackStack()
        navController.navigate(Review.route)
        return
    }

    var timeLeft by remember { mutableIntStateOf(0) }
    var isAnswerSelected by remember { mutableStateOf(false) }
    var timeOut by remember { mutableStateOf(false) }
    var selectedAnswerId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp, 40.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() }, modifier = Modifier.size(30.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Black
            )
        }
        Column(
            modifier = Modifier.padding(top = 21.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            TimerCard(
                modifier = Modifier,
                currentQuestionIndex = uiState.currentQuestionIndex,
                totalQuestions = uiState.questions.size,
                onTimeOut = {
                    gameViewModel.timeOut(currentQuestion)
                    timeOut = true
                },
                isAnswerSelected = isAnswerSelected,
                onTimeChanged = { updatedTime -> timeLeft = updatedTime }
            )

            ProgressGame(uiState.infoGame, uiState.questions.size)

            QuestionCard(
                question = currentQuestion.text, imageRes = currentQuestion.questionImg
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(21.dp)
            ) {
                itemsIndexed(items = currentQuestion.options) { index, option ->
                    AnswerButton(
                        answer = option, isCorrect = gameViewModel.checkOption(
                            isAnswerSelected, selectedAnswerId, index, currentQuestion
                        ), onClick = {
                            if (!isAnswerSelected) {
                                selectedAnswerId = index
                                isAnswerSelected = true
                                gameViewModel.selectAnswer(index, timeLeft)
                            }
                        })
                }
            }
        }
    }
    if (isAnswerSelected || timeOut) {
        LaunchedEffect(selectedAnswerId, timeOut) {
            delay(1000)
            gameViewModel.loadNextQuestion()
            isAnswerSelected = false
            timeOut = false
        }
    }
}

@Composable
private fun ProgressGame(
    infoGame: List<Boolean>, totalQuestions: Int, modifier: Modifier = Modifier
) {
    val itemSpacing = 4.dp
    val itemHeight = 12.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalQuestions) { index ->
            val color = when {
                index >= infoGame.size -> PlaceholderLight
                infoGame[index] -> GreenLight
                else -> RedLight
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = itemSpacing / 2)
                    .height(itemHeight)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.height / 2
                    val width = size.width

                    drawPath(
                        path = Path().apply {
                            moveTo(0f, radius)
                            arcTo(Rect(0f, 0f, size.height, size.height), 90f, 180f, false)
                            lineTo(width - radius, 0f)
                            arcTo(
                                Rect(width - size.height, 0f, width, size.height), 270f, 180f, false
                            )
                            lineTo(radius, size.height)
                            close()
                        }, color = color
                    )
                }
            }
        }
    }
}

@Composable
private fun TimerCard(
    modifier: Modifier = Modifier,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    onTimeOut: () -> Unit = {},
    isAnswerSelected: Boolean = false,
    onTimeChanged: (Int) -> Unit = {}
) {
    val initialTime = 15
    val totalMillis = initialTime * 1000L
    var elapsedTime by remember { mutableLongStateOf(0L) }
    val startTime = remember { System.currentTimeMillis() }
    val progress = remember { Animatable(1f) }

    LaunchedEffect(currentQuestionIndex, isAnswerSelected) {
        if (!isAnswerSelected) {
            elapsedTime = 0L
            progress.snapTo(1f)

            val frameDuration = 16L

            while (elapsedTime < totalMillis) {
                val currentTime = System.currentTimeMillis()
                val delta = (currentTime - startTime - elapsedTime).coerceAtMost(frameDuration)
                delay(delta)

                elapsedTime += delta
                val newProgress = 1f - (elapsedTime.toFloat() / totalMillis)

                launch {
                    progress.snapTo(newProgress.coerceIn(0f, 1f))
                }

                if (elapsedTime >= totalMillis) {
                    progress.snapTo(0f)
                    onTimeOut()
                }
            }
        } else {
            progress.snapTo(progress.value)
        }
    }

    val remainingTime = ceil(initialTime * progress.value).toInt().coerceAtLeast(0)
    onTimeChanged(remainingTime)
    Card(
        modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = BackgroundLight,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(2.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        style = Stroke(width = 4.dp.toPx())
                    )

                    drawArc(
                        color = getTimerColor(remainingTime),
                        startAngle = 270f,
                        sweepAngle = -360 * progress.value,
                        useCenter = false,
                        style = Stroke(
                            width = 4.dp.toPx(), cap = StrokeCap.Round
                        )
                    )
                }

                Text(
                    text = "$remainingTime",
                    color = getTimerColor(remainingTime),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = "${currentQuestionIndex + 1}/$totalQuestions",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

private fun getTimerColor(remainingTime: Int): Color {
    return when {
        remainingTime <= 5 -> Color(0xFFD46D77)
        remainingTime <= 10 -> Color(0XFFD49D6D)
        else -> Color(0xFF4F7D79)
    }
}

@Composable
private fun AnswerButton(
    answer: String, isCorrect: Boolean? = null, onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (isCorrect) {
                true -> GreenLight
                false -> RedLight
                else -> White
            }, contentColor = when (isCorrect) {
                true -> White
                false -> White
                else -> Color.Black
            }
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = answer, style = MaterialTheme.typography.titleMedium
            )

            if (isCorrect != null) {
                if (isCorrect) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Correct Answer",
                        tint = White,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Surface(
                        shape = CircleShape, color = White, modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Incorrect Answer",
                            tint = RedLight,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestionCard(
    question: String, imageRes: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (imageRes != "") {
                Box(
                    modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = imageRes,
                        contentDescription = "Question Image",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = question,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}