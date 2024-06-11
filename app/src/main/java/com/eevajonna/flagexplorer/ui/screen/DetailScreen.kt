package com.eevajonna.flagexplorer.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun DetailScreen(flag: FlagQuery.AllFlag?) {
    flag?.let {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(DetailScreen.verticalSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier.height(DetailScreen.imageHeight),
                model =
                    ImageRequest.Builder(LocalContext.current)
                        .decoderFactory(SvgDecoder.Factory())
                        .data(it.svgUrl)
                        .crossfade(true)
                        .build(),
                contentDescription = null,
            )

            Text(it.name, style = MaterialTheme.typography.titleLarge)
            Text(it.year, style = MaterialTheme.typography.titleMedium)
        }
    }
}

object DetailScreen {
    val verticalSpacing = 12.dp
    val imageHeight = 100.dp
}
