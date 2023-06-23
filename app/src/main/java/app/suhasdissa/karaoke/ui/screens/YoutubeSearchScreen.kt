package app.suhasdissa.karaoke.ui.screens

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.backend.models.Items
import app.suhasdissa.karaoke.backend.viewmodels.PipedViewModel
import app.suhasdissa.karaoke.ui.components.ErrorScreen
import app.suhasdissa.karaoke.ui.components.InfoScreen
import app.suhasdissa.karaoke.ui.components.LoadingScreen
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun YoutubeSearchScreen(
    modifier: Modifier = Modifier,
    pipedViewModel: PipedViewModel = viewModel(),
    onClickVideoCard: (id: String) -> Unit
) {
    val search = remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        delay(100)
        keyboard?.show()
    }
    Column(modifier.fillMaxSize()) {
        Row(modifier.padding(horizontal = 10.dp), horizontalArrangement = Arrangement.Center) {
            TextField(
                value = search.value,
                onValueChange = {
                    search.value = it
                    if (search.value.text.length >= 3) {
                        pipedViewModel.getSuggestions(search.value.text)
                    }
                },
                modifier
                    .weight(1f)
                    .focusRequester(focusRequester),

                singleLine = true,
                placeholder = { Text("Search Songs") },
                shape = CircleShape
            )
            Button(onClick = {
                keyboard?.hide()
                if (search.value.text.isNotEmpty()) {
                    pipedViewModel.searchPiped(search.value.text)
                }
                pipedViewModel.suggestions = arrayListOf()
            }) {
                Text("Search")
            }
        }

        if (search.value.text.isNotEmpty() && pipedViewModel.suggestions.isNotEmpty()) {
            LazyColumn(Modifier.fillMaxWidth()) {
                items(items = pipedViewModel.suggestions) { suggestion ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clickable { search.value = TextFieldValue(suggestion) }) {
                        Text(text = suggestion, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        when (val searchState = pipedViewModel.state) {
            is PipedViewModel.PipedSearchState.Loading -> {
                LoadingScreen()
            }

            is PipedViewModel.PipedSearchState.Error -> {
                ErrorScreen(error = searchState.error, onRetry = {
                    if (search.value.text.isNotEmpty()) {
                        pipedViewModel.searchPiped(search.value.text)
                    }
                })
            }

            is PipedViewModel.PipedSearchState.Success -> {
                VideoList(items = searchState.items, onClickVideoCard = { onClickVideoCard(it) })
            }

            is PipedViewModel.PipedSearchState.Empty -> {
                InfoScreen(info = "Search For Video")
            }
        }
    }
}

@Composable
fun VideoCard(
    url: String,
    thumbnail: String,
    title: String,
    duration: Int,
    onClickVideoCard: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(Modifier.clickable { onClickVideoCard(url) }) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Box(
                modifier = modifier
                    .weight(1.2f)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    modifier = modifier.fillMaxSize(),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(thumbnail).crossfade(true).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier
                    .weight(2f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, style = MaterialTheme.typography.titleSmall)
                Text(
                    DateUtils.formatElapsedTime(duration.toLong()),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun VideoList(items: ArrayList<Items>, onClickVideoCard: (url: String) -> Unit) {
    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(items = items) { item ->
            VideoCard(
                url = item.url,
                thumbnail = item.thumbnail,
                title = item.title,
                item.duration,
                onClickVideoCard = {
                    onClickVideoCard(it.replace("/watch?v=", ""))
                })
        }
    }
}