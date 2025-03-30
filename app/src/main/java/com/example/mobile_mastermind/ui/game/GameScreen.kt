package com.example.mobile_mastermind.ui.game

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile_mastermind.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
fun GameScreen(gameViewModel: GameViewModel = hiltViewModel()) {
    val uiState = gameViewModel.uiState.value
    val currentQuestion = uiState.questions.getOrNull(uiState.currentQuestionIndex)

    if (currentQuestion == null) {
        gameViewModel.finishGame()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp, 40.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Go back",
            tint = colorResource(R.color.black),
            modifier = Modifier.size(30.dp)
        )

        Spacer(modifier = Modifier.height(21.dp))

        TimerCard(
            modifier = Modifier,
            currentQuestionIndex = uiState.currentQuestionIndex,
            totalQuestions = uiState.questions.size,
            onTimeOut = { gameViewModel.timeOut() }
        )

        Spacer(modifier = Modifier.height(30.dp))

        ProgressGame(uiState.infoGame, uiState.questions.size)

        Spacer(modifier = Modifier.height(30.dp))

        QuestionCard(question = currentQuestion.text, imageRes = currentQuestion.questionImg)

        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            items(items = currentQuestion.options) { option ->
                AnswerButton(
                    answer = option.text,
                    onClick = { gameViewModel.selectAnswer(option.id) }
                )
            }
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
                index >= infoGame.size -> colorResource(R.color.color_placeholder)
                infoGame[index] -> colorResource(R.color.color_green)
                else -> colorResource(R.color.color_red)
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
) {
    val initialTime = 15
    val totalMillis = initialTime * 1000L
    var elapsedTime by remember { mutableLongStateOf(0L) }
    val startTime = remember { System.currentTimeMillis() }
    val progress = remember { Animatable(1f) }

    LaunchedEffect(currentQuestionIndex) {
        elapsedTime = 0L
        progress.snapTo(1f)
    }

    LaunchedEffect(progress) {
        val frameDuration = 16L // ~60 FPS

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
    }

    val remainingTime = ceil(initialTime * progress.value).toInt().coerceAtLeast(0)

    Card(
        modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.color_background),
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
                    text = "$remainingTime", style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        color = getTimerColor(remainingTime)
                    )
                )
            }

            Text(
                text = "${currentQuestionIndex + 1}/$totalQuestions",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
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
    answer: String,
    isCorrect: Boolean? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (isCorrect) {
                true -> colorResource(R.color.color_green)
                false -> colorResource(R.color.color_red)
                else -> Color.White
            },
            contentColor = when (isCorrect) {
                true -> Color.White
                false -> Color.White
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
                text = answer,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            if (isCorrect != null) {
                if (isCorrect) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Correct Answer",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Incorrect Answer",
                            tint = colorResource(R.color.color_red),
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
    question: String,
    imageRes: Int? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            imageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Question Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                )
            }
            Text(text = question, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    val gameViewModel = GameViewModel()
    GameScreen(gameViewModel = gameViewModel)
}