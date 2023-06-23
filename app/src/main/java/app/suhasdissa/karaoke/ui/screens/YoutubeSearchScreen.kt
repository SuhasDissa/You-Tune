package app.suhasdissa.karaoke.ui.screens

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.R
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
    var search by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        delay(100)
        keyboard?.show()
    }
    Column(modifier.fillMaxSize()) {
        var expanded by remember { mutableStateOf(false) }

        TextField(
            value = search,
            onValueChange = {
                search = it
                expanded = true
                if (search.length >= 3) {
                    pipedViewModel.getSuggestions(search)
                }
            },
            modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .focusRequester(focusRequester),

            singleLine = true,
            placeholder = { Text(stringResource(R.string.search)) },
            shape = CircleShape,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                keyboard?.hide()
                expanded = false
                if (search.isNotEmpty()) {
                    pipedViewModel.searchPiped(search)
                }
            }),
            trailingIcon = {
                IconButton(onClick = { search = "" }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear_search)
                    )
                }
            }
        )
        Box {
            if (expanded) {
                Surface(
                    modifier = Modifier.zIndex(1f),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        pipedViewModel.suggestions.forEach { suggestion ->
                            DropdownMenuItem(text = {
                                Text(
                                    text = suggestion,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }, onClick = {
                                search = suggestion
                                keyboard?.hide()
                                expanded = false
                                if (search.isNotEmpty()) {
                                    pipedViewModel.searchPiped(search)
                                }
                            })
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
                        if (search.isNotEmpty()) {
                            pipedViewModel.searchPiped(search)
                        }
                    })
                }

                is PipedViewModel.PipedSearchState.Success -> {
                    VideoList(
                        items = searchState.items,
                        onClickVideoCard = { onClickVideoCard(it) })
                }

                is PipedViewModel.PipedSearchState.Empty -> {
                    InfoScreen(info = "Search For Video")
                }
            }
        }
    }
}

@Composable
fun VideoCard(
    thumbnail: String,
    title: String,
    duration: Int,
    onClickVideoCard: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(Modifier.clickable { onClickVideoCard() }) {
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
                thumbnail = item.thumbnail,
                title = item.title,
                item.duration,
                onClickVideoCard = {
                    onClickVideoCard(item.id)
                })
        }
    }
}