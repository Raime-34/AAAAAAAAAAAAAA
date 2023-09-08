package com.example.currency.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun CountryFlag(countryFlag: String?, modifier: Modifier = Modifier){
    if(countryFlag == null)
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = modifier
        )
    else
        AsyncImage(
            model = "https://flagsapi.com/$countryFlag/flat/64.png",
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .clip(CircleShape)
                .background(Color.LightGray)
        )
}