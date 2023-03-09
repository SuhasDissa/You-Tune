package app.suhasdissa.karaoke.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.R
import app.suhasdissa.karaoke.enums.ThemeMode
import app.suhasdissa.karaoke.obj.AudioFormat
import app.suhasdissa.karaoke.ui.common.ChipSelector
import app.suhasdissa.karaoke.ui.common.ClickableIcon
import app.suhasdissa.karaoke.ui.common.CustomNumInputPref
import app.suhasdissa.karaoke.ui.common.SelectionDialog
import app.suhasdissa.karaoke.ui.dialogs.AboutDialog
import app.suhasdissa.karaoke.ui.models.ThemeModel
import app.suhasdissa.karaoke.util.PickFolderContract
import app.suhasdissa.karaoke.util.Preferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    onDismissRequest: () -> Unit
) {
    val themeModel: ThemeModel = viewModel()

    var audioFormat by remember {
        mutableStateOf(AudioFormat.getCurrent())
    }

    val directoryPicker = rememberLauncherForActivityResult(PickFolderContract()) {
        it ?: return@rememberLauncherForActivityResult
        Preferences.edit { putString(Preferences.targetFolderKey, it.toString()) }
    }
    var showAbout by remember {
        mutableStateOf(false)
    }
    var showThemePref by remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest.invoke()
        }
    ) {
        Box {
            Row(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                ClickableIcon(
                    imageVector = Icons.Default.DarkMode,
                    contentDescription = stringResource(R.string.theme)
                ) {
                    showThemePref = true
                }
                ClickableIcon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.about)
                ) {
                    showAbout = true
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.directory),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    onClick = {
                        val lastDir = Preferences.prefs.getString(Preferences.targetFolderKey, "")
                            .takeIf { !it.isNullOrBlank() }
                        directoryPicker.launch(lastDir?.let { Uri.parse(it) })
                    }
                ) {
                    Text(stringResource(R.string.choose_dir))
                }
                Spacer(modifier = Modifier.height(10.dp))
                ChipSelector(
                    title = stringResource(R.string.audio_format),
                    entries = AudioFormat.formats.map { it.name },
                    values = AudioFormat.formats.map { it.format },
                    selections = listOf(audioFormat.format)
                ) { index, newValue ->
                    if (newValue) {
                        audioFormat = AudioFormat.formats[index]
                        Preferences.edit { putString(Preferences.audioFormatKey, audioFormat.name) }
                    }
                }
                CustomNumInputPref(
                    key = Preferences.audioSampleRateKey,
                    title = stringResource(R.string.sample_rate),
                    defValue = 44_100
                )
            }
        }
    }

    if (showThemePref) {
        SelectionDialog(
            onDismissRequest = { showThemePref = false },
            title = stringResource(R.string.theme),
            entries = listOf(R.string.system, R.string.light, R.string.dark).map {
                stringResource(it)
            }
        ) {
            themeModel.themeMode = ThemeMode.values()[it]
            Preferences.edit { putString(Preferences.themeModeKey, themeModel.themeMode.name) }
        }
    }

    if (showAbout) {
        AboutDialog {
            showAbout = false
        }
    }
}
