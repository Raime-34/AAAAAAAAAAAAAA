package com.example.currency.presentation.main_screen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import com.example.currency.data.countryFlags
import com.example.currency.presentation.core.CountryFlag
import com.example.currency.presentation.main_screen.MainScreenItemState
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entriesOf

@Composable
fun CurrencyProfileScreen(
    itemState: MainScreenItemState,
    currentSource: String
) {
    val flagModifier = Modifier.size(60.dp)
    val sourceCode = countryFlags.get(currentSource)
    val countryFlag = countryFlags.get(itemState.code)

    var value1 by remember{ mutableStateOf(1.0) }
    var value2 by remember{ mutableStateOf(limitDecimalPlaces(itemState.value, 3)) }

    val chartEntryModelProducer = ChartEntryModelProducer(itemState.values)
    val lineChart = lineChart(
        lines = listOf(
            LineChart.LineSpec(
                lineColor = Color.Green.toArgb(),
                lineBackgroundShader = DynamicShaders.fromBrush(
                    Brush.verticalGradient(
                        listOf(
                            Color.Green.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                            Color.Green.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                        )
                    )
                )
            )
        )
    )

    Surface(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .shadow(elevation = 20.dp, shape = RoundedCornerShape(40.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Row {
                CurrencyProfileTextField(sourceCode, value1, flagModifier) {
                    value1 = limitDecimalPlaces(it.coerceIn(Double.MIN_VALUE, Double.MAX_VALUE), 3)
                    value2 = limitDecimalPlaces((value1 * itemState.value).coerceIn(Double.MIN_VALUE, Double.MAX_VALUE),3)
                }
                Spacer(modifier = Modifier.width(80.dp))
                CurrencyProfileTextField(countryFlag, value2, flagModifier) {
                    value2 = limitDecimalPlaces(it.coerceIn(Double.MIN_VALUE, Double.MAX_VALUE),3)
                    value1 = limitDecimalPlaces((value2/itemState.value).coerceIn(Double.MIN_VALUE, Double.MAX_VALUE),3)
                    Log.e("Bruh", value1.toString())
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    //.background(Color.Red)
            ) {
                ProvideChartStyle(ChartStyle.fromColors(
                    axisLabelColor = Color.DarkGray,
                    axisGuidelineColor = Color.DarkGray,
                    axisLineColor = Color.DarkGray,
                    entityColors = listOf(Color.Green),
                    Color.Green
                )
                ) {
                    Chart(
                        chart = lineChart,
                        chartModelProducer = chartEntryModelProducer,
                        chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp)
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 412, heightDp = 732)
@Composable
fun CurrencyProfileScreenPreview() {
    CurrencyProfileScreen(itemState = MainScreenItemState(code = "RUB", value = 10.0, values = entriesOf(10f, 20f, 30f).toMutableList()), "RUB")
}

@Composable
fun CurrencyProfileTextField(countryFlag: String?, value: Double, modifier: Modifier = Modifier, onValueChange: (Double) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CountryFlag(countryFlag = countryFlag, modifier)
            Spacer(modifier = Modifier.height(10.dp))
            countryFlag?.let { Text(text = countryFlag) }
        }
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = value.toString(),
            onValueChange = {
                val newValue = it.toDoubleOrNull()
                if(newValue != null)
                    onValueChange(it.toDouble())
                            },
            modifier = Modifier.width(80.dp),
            shape = CutCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            //singleLine = true
        )
    }
}

fun limitDecimalPlaces(number: Double, decimalPlaces: Int): Double {
    val multiplier = Math.pow(10.0, decimalPlaces.toDouble())
    return Math.round(number * multiplier) / multiplier
}