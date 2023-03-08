package com.bnyro.recorder.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bnyro.recorder.R
import com.bnyro.recorder.ui.dialogs.ConfirmationDialog
import com.bnyro.recorder.ui.models.PlayerModel

@Composable
fun PlayerView(
    showDeleteAllDialog: Boolean,
    onDeleteAllDialogDismissed: () -> Unit
) {
    val playerModel: PlayerModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        playerModel.loadFiles(context)
    }

    val files = playerModel.files

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        if (files.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(files) {
                    RecordingItem(recordingFile = it)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(120.dp),
                        imageVector = Icons.Default.AudioFile,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = stringResource(R.string.nothing_here))
                }
            }
        }
    }

    if (showDeleteAllDialog) {
        ConfirmationDialog(
            title = R.string.delete_all,
            onDismissRequest = onDeleteAllDialogDismissed
        ) {
            files.forEach {
                it.delete()
                playerModel.files.remove(it)
            }
        }
    }
}
