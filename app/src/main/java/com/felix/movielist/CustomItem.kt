package com.felix.movielist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.felix.movielist.model.playingNow.Result

const val IMAGE_BASE ="https://image.tmdb.org/t/p/w500"
@Composable
fun CustomItem(movie: Result, navigateToDetail: (Int) -> Unit) {

    Card(modifier = Modifier
        .padding(16.dp, 8.dp)
        .fillMaxWidth()
        .clickable {
            navigateToDetail(movie.id)
        }
        .height(110.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp)
    {
        Surface() {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = IMAGE_BASE+movie.posterPath),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .clip(shape = RoundedCornerShape(10.dp)))
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)) {
                    Text(text = movie.originalTitle +"\n\nRating : "+ movie.voteAverage,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(4.dp))
                }
            }
        }
    }
}