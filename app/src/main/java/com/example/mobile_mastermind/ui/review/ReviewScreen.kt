package com.example.mobile_mastermind.ui.review

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.components.PrimaryButton
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.MOBILEMASTERMINDTheme
import com.example.mobile_mastermind.ui.theme.RedLight

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    onDoneClick: () -> Unit = {}
) {
    val uiState = reviewViewModel.uiState.value

    Scaffold(
        modifier = modifier, bottomBar = {
            PrimaryButton(
                text = stringResource(R.string.review_button_done),
                onClick = onDoneClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.review_title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            ResultCard(
                category = uiState.category,
                score = uiState.pointsEarned,
                correctAnswers = uiState.answerCorrect,
                incorrectAnswers = uiState.answerIncorrect
            )

            Text(
                text = stringResource(R.string.review_title_answers),
                style = MaterialTheme.typography.titleMedium
            )

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
            containerColor = MaterialTheme.colorScheme.onPrimary,
        )
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = questions) { question ->
                QuestionItem(
                    question = question, questionNumber = questions.indexOf(question) + 1
                )
            }
        }
    }
}

@Composable
private fun QuestionItem(
    question: QuestionResult, questionNumber: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (question.isCorrect) GreenLight
                    else RedLight, shape = CircleShape
                )
        ) {
            Text(
                text = stringResource(R.string.review_number_question, questionNumber),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = question.userAnswer,
                style = MaterialTheme.typography.bodyMedium,
                color = if (question.isCorrect) GreenLight
                else RedLight
            )
        }

        AnswerIndicator(
            isCorrect = question.isCorrect, size = IndicatorSize.MEDIUM
        )
    }
}

@Composable
fun ResultCard(
    category: String, score: Int, correctAnswers: Int, incorrectAnswers: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
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
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = category, style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.result_score, score),
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScoreItem(
                    count = correctAnswers, isCorrect = true
                )

                ScoreItem(
                    count = incorrectAnswers, isCorrect = false
                )
            }
        }
    }
}

@Composable
private fun ScoreItem(
    count: Int, isCorrect: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$count", style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.width(8.dp))
        AnswerIndicator(
            isCorrect = isCorrect, size = IndicatorSize.LARGE
        )
    }
}

@Composable
private fun AnswerIndicator(
    isCorrect: Boolean, size: IndicatorSize
) {
    when (size) {
        IndicatorSize.LARGE -> {
            if (isCorrect) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.review_correct_content_description),
                    tint = GreenLight,
                    modifier = Modifier.size(40.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.error_icon),
                    contentDescription = stringResource(R.string.review_incorrect_content_description),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        IndicatorSize.MEDIUM -> {
            if (isCorrect) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.review_correct_content_description),
                    tint = GreenLight,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.error_icon),
                    contentDescription = stringResource(R.string.review_incorrect_content_description),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

private enum class IndicatorSize {
    MEDIUM, LARGE
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    MOBILEMASTERMINDTheme {
        val viewModel = ReviewViewModel()
        ReviewScreen(reviewViewModel = viewModel)
    }
}