package com.anangkur.movieku.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anangkur.movieku.R
import com.anangkur.movieku.utils.coil.rememberCoilPainter

@Composable
fun ItemMainHorizontal(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    rating: Float,
    onCLick: () -> Unit,
) {
    Column(modifier = modifier.clickable(onClick = onCLick)) {
        Image(
            modifier = Modifier.width(160.dp).height(240.dp),
            painter = rememberCoilPainter(imageUrl),
            contentDescription = null
        )
        Column(modifier = Modifier.padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
            Text(
                modifier = Modifier.width(128.dp),
                text = title,
                fontSize = 16.sp,
                fontFamily = FontFamily(fonts = listOf(Font(R.font.comfortaa_regular))),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.width(128.dp).padding(top = 10.dp),
                text = "Rating: $rating / 5",
                fontSize = 14.sp,
                fontFamily = FontFamily(fonts = listOf(Font(R.font.comfortaa_regular))),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}