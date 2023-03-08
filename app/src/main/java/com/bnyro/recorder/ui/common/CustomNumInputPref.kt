package com.bnyro.recorder.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bnyro.recorder.R
import com.bnyro.recorder.util.Preferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNumInputPref(
    key: String,
    title: String,
    defValue: Int
) {
    val pref = Preferences.prefs.getInt(key, -1).takeIf { it != -1 }

    var showDialog by remember {
        mutableStateOf(false)
    }
    var isAutoEnabled by remember {
        mutableStateOf(pref == null)
    }
    var input by remember {
        mutableStateOf((pref ?: defValue).toString())
    }

    Button(onClick = { showDialog = true }) {
        Text(text = title)
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = title) },
            confirmButton = {
                DialogButton(stringResource(R.string.okay)) {
                    Preferences.edit {
                        putInt(key, input.takeIf { !isAutoEnabled }?.toIntOrNull() ?: -1)
                    }
                    showDialog = false
                }
            },
            dismissButton = {
                DialogButton(stringResource(R.string.cancel)) {
                    showDialog = false
                }
            },
            text = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = isAutoEnabled, onCheckedChange = { isAutoEnabled = it })
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = stringResource(R.string.auto))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        value = input,
                        onValueChange = { input = it },
                        enabled = !isAutoEnabled,
                        readOnly = isAutoEnabled,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(title) }
                    )
                }
            }
        )
    }
}
