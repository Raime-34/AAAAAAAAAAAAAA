package com.example.currency.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.currency.data.countryFlags
import com.example.currency.ui.theme.CurrencyTheme
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShader
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun MainScreenItem(
    code: String,
    value: Double,
    values: List<FloatEntry>,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit
) {
    val countryCode = if (code.length <= 3) code else code.removePrefix(code.substring(0, 3))
    var countryFlag = countryFlags.get(countryCode)
    val flagModifier = Modifier.size(40.dp)

    Surface(
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(5.dp))
            .clickable(onClick = onItemClicked)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .height(IntrinsicSize.Min)
        ) {
            if(countryFlag == null)
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = flagModifier
                )
            else
                AsyncImage(
                    model = "https://flagsapi.com/$countryFlag/flat/64.png",
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = flagModifier
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = countryCode,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1f))

            val chartEntryModelProducer = ChartEntryModelProducer(values)
            val lineChart = lineChart(
                    lines = listOf(LineChart.LineSpec(
                        lineColor = Color.Green.toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Green.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    Color.Green.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                                )
                            )
                        )
                    )
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    //.background(Color.Red)
            ) {
                Chart(
                    chart = lineChart,
                    chartModelProducer = chartEntryModelProducer,
                    chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = String.format("%.3f", value), fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
fun MainScreenItemPreview() {
    CurrencyTheme {
        MainScreenItem(code = "RUBUSD", value = 100.0, emptyList()) {}
    }
}