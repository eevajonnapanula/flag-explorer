package com.eevajonna.flagexplorer.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.eevajonna.FlagQuery

@Composable
fun MainScreen(
    flags: List<FlagQuery.AllFlag>,
    loading: Boolean,
    modifier: Modifier = Modifier,
    setFlag: (FlagQuery.AllFlag) -> Unit,
) {
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState(),
            ),
    ) {
        if (loading) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            flags.map { flag ->
                FlagRow(flag = flag) {
                    setFlag(it)
                }
            }
        }
    }
}

@Composable
fun FlagRow(flag: FlagQuery.AllFlag, setFlag: (FlagQuery.AllFlag) -> Unit) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = MainScreen.verticalPadding)
            .clickable { setFlag(flag) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MainScreen.verticalSpacing),
    ) {
        AsyncImage(
            modifier = Modifier.height(MainScreen.imageHeight),
            model =
            ImageRequest.Builder(LocalContext.current)
                .decoderFactory(SvgDecoder.Factory())
                .data(flag.svgUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
        )
        Text(
            text = flag.name,
            style = MaterialTheme.typography.titleLarge,
        )
    }
    HorizontalDivider()
}

object MainScreen {
    val verticalPadding = 8.dp
    val imageHeight = 30.dp
    val verticalSpacing = 12.dp
}
