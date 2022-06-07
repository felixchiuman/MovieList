package com.felix.movielist.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.felix.movielist.CustomItem
import com.felix.movielist.model.playingNow.GetAllPlayingNowResponse
import com.felix.movielist.network.Resource
import com.felix.movielist.network.Status
import com.felix.movielist.ui.theme.MovieListTheme
import com.felix.movielist.viewModel.MainPageViewModel
import org.koin.androidx.compose.getViewModel


class MainPageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.primary
                ) {
                    MovieScreen()
                }
            }
        }
    }
}
@Composable
fun MovieScreen() {
    val vm = getViewModel<MainPageViewModel>()
    val resource by vm.playingNow.observeAsState()
    vm.getAllPlayingNow()
    MovieList(resource)
}

@Composable
fun MovieList(
    resource: Resource<GetAllPlayingNowResponse>?
) {
    when (resource?.status){
        Status.LOADING -> {
            var showDialog by remember { mutableStateOf(false) }

            if (showDialog) {
                Dialog(
                    onDismissRequest = { showDialog = false },
                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        contentAlignment= Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
        Status.SUCCESS -> {
            val data = resource.data?.results ?: emptyList()
            LazyColumn{
                items(items = data){ movie ->
                    CustomItem(movie = movie)
                }
            }
        }
        Status.ERROR -> {
            val context = LocalContext.current
            Toast.makeText(context, "Load Error", Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    MovieListTheme {
        Column {
        }
    }
}