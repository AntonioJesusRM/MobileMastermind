package com.example.mobile_mastermind.ui.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.components.PrimaryButton

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    onDoneClick: () -> Unit = {}
) {
    val uiState = reviewViewModel.uiState.value

    Scaffold(
        modifier = modifier,
        bottomBar = {
            PrimaryButton(
                text = stringResource(R.string.review_button_done),
                onClick = onDoneClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = stringResource(R.string.review_title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp
            )

            // Results Card
            ResultCard(
                category = uiState.category,
                score = uiState.pointsEarned,
                correctAnswers = uiState.answerCorrect,
                incorrectAnswers = uiState.answerIncorrect
            )

            // Answers Section
            Text(
                text = stringResource(R.string.review_title_answers),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )

            // Answers List
            AnswersCard(questions = uiState.questions)
        }
    }
}

@Composable
private fun AnswersCard(questions: List<QuestionResult>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.6f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white),
        )
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = questions) { question ->
                QuestionItem(
                    question = question,
                    questionNumber = questions.indexOf(question) + 1
                )
            }
        }
    }
}

@Composable
private fun QuestionItem(
    question: QuestionResult,
    questionNumber: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Question Number
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (question.isCorrect) colorResource(R.color.color_green)
                    else colorResource(R.color.color_red),
                    shape = CircleShape
                )
        ) {
            Text(
                text = "Q$questionNumber",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        // Question Content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = question.questionText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = question.userAnswer,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = if (question.isCorrect) colorResource(R.color.color_green)
                else colorResource(R.color.color_red)
            )
        }

        // Correct/Incorrect Indicator
        AnswerIndicator(
            isCorrect = question.isCorrect,
            size = IndicatorSize.MEDIUM
        )
    }
}

@Composable
fun ResultCard(
    category: String,
    score: Int,
    correctAnswers: Int,
    incorrectAnswers: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white),
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
                Text(
                    text = stringResource(R.string.result_category),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = category,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.result_score, score),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScoreItem(
                    count = correctAnswers,
                    isCorrect = true
                )

                ScoreItem(
                    count = incorrectAnswers,
                    isCorrect = false
                )
            }
        }
    }
}

@Composable
private fun ScoreItem(
    count: Int,
    isCorrect: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$count",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        AnswerIndicator(
            isCorrect = isCorrect,
            size = IndicatorSize.LARGE
        )
    }
}

@Composable
private fun AnswerIndicator(
    isCorrect: Boolean,
    size: IndicatorSize
) {
    when (size) {
        IndicatorSize.LARGE -> {
            if (isCorrect) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.review_correct_content_description),
                    tint = colorResource(R.color.color_green),
                    modifier = Modifier.size(40.dp)
                )
            } else {
                Surface(
                    shape = CircleShape,
                    color = colorResource(R.color.color_red),
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.review_incorrect_content_description),
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        IndicatorSize.MEDIUM -> {
            if (isCorrect) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.review_correct_content_description),
                    tint = colorResource(R.color.color_green),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Surface(
                    shape = CircleShape,
                    color = colorResource(R.color.color_red),
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.review_incorrect_content_description),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

private enum class IndicatorSize {
    MEDIUM,
    LARGE
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    val viewModel = ReviewViewModel()
    ReviewScreen(reviewViewModel = viewModel)
}