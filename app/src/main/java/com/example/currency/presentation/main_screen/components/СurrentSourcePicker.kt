package com.example.currency.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currency.data.countryFlags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentSourcePicker(
    modifier: Modifier = Modifier,
    defaultCode: String = "RUB",
    onSourceChange: (code: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCode by remember { mutableStateOf(defaultCode) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedCode,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            //trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledTextColor = Color.DarkGray,
                disabledContainerColor = Color.White
            ),
            //shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .height(50.dp)
                .menuAnchor()
                .width(70.dp)
                .height(45.dp)
                .shadow(elevation = 5.dp, RoundedCornerShape(20.dp))
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
        ) {
            countryFlags.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.key,
                            textAlign = TextAlign.Center
                        )
                           },
                    onClick = {
                        selectedCode = it.key
                        expanded = false
                        onSourceChange(it.key)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun CurrentSourcePickerPreview() {
    Column {
        countryFlags.keys.forEach {
            CurrentSourcePicker(defaultCode = it, onSourceChange = {})
        }
    }
}