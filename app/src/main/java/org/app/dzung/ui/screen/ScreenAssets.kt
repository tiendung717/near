package org.app.dzung.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.app.dzung.R
import org.app.dzung.common.StateResult
import org.app.dzung.data.model.nft.Nft
import org.app.dzung.ui.component.Header
import org.app.dzung.ui.component.AssetItem
import org.app.dzung.ui.event.NavEvent
import org.app.dzung.ui.event.initEventHandler
import org.app.dzung.ui.navigation.NavTarget
import org.app.dzung.ui.component.UserInfo
import org.app.dzung.viewmodel.initAssetViewModel

@Composable
fun ScreenAssets() {
    val eventHandler = initEventHandler()
    val assetViewModel = initAssetViewModel()

    val assets: MutableState<StateResult<List<Nft>>> = remember {
        mutableStateOf(StateResult.None)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            UserInfo()
        }

        if (assets.value is StateResult.Success) {
            item {
                Header(
                    header = stringResource(id = R.string.my_nft),
                    action = stringResource(id = R.string.create_nft)
                ) {
                    eventHandler.postNavEvent(NavEvent.Action(NavTarget.CreateNFT))
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    assets.value.takeValueOrThrow().forEach { asset ->
                        AssetItem(nft = asset) {
                            assetViewModel.setCurrentAsset(asset)
                            eventHandler.postNavEvent(
                                NavEvent.Action(NavTarget.NFTDetails)
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = "fetchData") {
        assetViewModel.getAllAssets(assets)
    }
}