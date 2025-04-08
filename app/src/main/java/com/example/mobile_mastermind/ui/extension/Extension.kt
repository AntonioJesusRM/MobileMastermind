package com.example.mobile_mastermind.ui.extension

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

val Any.TAG: String
    get() {
        val tagSimpleName = javaClass.simpleName
        val tagName = javaClass.name
        return when {
            tagSimpleName.isNotBlank() -> {
                if (tagSimpleName.length > 23) {
                    tagSimpleName.takeLast(23)
                } else {
                    tagSimpleName
                }
            }

            tagName.isNotBlank() -> {
                if (tagName.length > 23) {
                    tagName.takeLast(23)
                } else {
                    tagName
                }
            }

            else -> {
                "TAG unknown"
            }
        }
    }

@Composable
fun PutImage(
    imgBackground: Int, img: Int?, color: Color, size: Int
) {
    Box(modifier = Modifier.size(size.dp)) {
        Image(
            painter = painterResource(imgBackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(color, BlendMode.SrcIn)
        )

        img?.let {
            Image(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}