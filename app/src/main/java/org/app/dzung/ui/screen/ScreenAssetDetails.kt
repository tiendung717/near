package org.app.dzung.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.app.dzung.R
import org.app.dzung.data.model.nft.Nft
import org.app.dzung.ui.event.NavEvent
import org.app.dzung.ui.event.initEventHandler
import org.app.dzung.ui.theme.*
import org.app.dzung.viewmodel.initAssetViewModel

@Composable
fun ScreenAssetDetails() {
    val eventHandler = initEventHandler()
    val assetViewModel = initAssetViewModel()
    val selectedAsset by assetViewModel.selectedAsset

    selectedAsset?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AssetPreview(nft = it) {
                    eventHandler.postNavEvent(NavEvent.NavigateUp())
                }
            }

            item {
                AssetMetaInfo(nft = it)
            }

            item {
                AssetDescription(nft = it)
            }

            item {
                AssetContract(nft = it)
            }
        }
    }
}

@Composable
fun AssetPreview(nft: Nft, onCloseClicked: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        val (type, image, close) = createRefs()
        Image(
            modifier = Modifier
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clickable { onCloseClicked() },
            painter = painterResource(id = nft.getImageResource()),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .constrainAs(type) {
                    bottom.linkTo(image.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    width = Dimension.wrapContent
                }
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp),
            text = nft.type.name,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )

        Icon(
            modifier = Modifier
                .background(Black50, CircleShape)
                .padding(6.dp)
                .size(24.dp)
                .constrainAs(close) {
                    end.linkTo(image.end, margin = 16.dp)
                    top.linkTo(image.top, margin = 32.dp)
                },
            imageVector = Icons.Rounded.Close,
            contentDescription = "Close",
            tint = Color.White
        )
    }
}

@Composable
fun AssetMetaInfo(nft: Nft) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (name, order, creatorName, creatorImage, creatorTitle, claimButton) = createRefs()
        Text(
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                top.linkTo(parent.top, margin = 12.dp)
                width = Dimension.fillToConstraints
            },
            text = nft.name,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.constrainAs(order) {
                start.linkTo(name.start)
                end.linkTo(name.end)
                top.linkTo(name.bottom, margin = 8.dp)
                width = Dimension.fillToConstraints
            },
            text = nft.identifier(),
            color = Blue,
            fontWeight = FontWeight.SemiBold
        )
        Icon(
            modifier = Modifier.constrainAs(creatorImage) {
                start.linkTo(name.start)
                top.linkTo(order.bottom, margin = 12.dp)
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_author_avatar),
            contentDescription = "",
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.constrainAs(creatorTitle) {
                start.linkTo(creatorImage.end, margin = 12.dp)
                end.linkTo(name.end)
                top.linkTo(creatorImage.top)
                bottom.linkTo(creatorName.top)
                width = Dimension.fillToConstraints
            },
            text = stringResource(id = R.string.creator),
            color = Gray1
        )
        Text(
            modifier = Modifier.constrainAs(creatorName) {
                start.linkTo(creatorTitle.start)
                end.linkTo(name.end)
                top.linkTo(creatorTitle.bottom)
                bottom.linkTo(creatorImage.bottom)
                width = Dimension.fillToConstraints
            },
            text = nft.nftOwner.name,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
        Button(
            modifier = Modifier.constrainAs(claimButton) {
                start.linkTo(name.start)
                end.linkTo(name.end)
                top.linkTo(creatorImage.bottom, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            },
            onClick = {

            }) {
            Text(text = stringResource(id = R.string.claim_button))
        }
    }
}

@Composable
fun AssetDescription(nft: Nft) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight()
            .border(1.dp, Gray4, RoundedCornerShape(10.dp))
            .padding(16.dp)

    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(id = R.string.description),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 16.dp),
            text = nft.description,
            color = Gray1,
        )
    }
}

@Composable
fun AssetContract(nft: Nft) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight()
            .border(1.dp, Gray4, RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(id = R.string.nft_info),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        AssetInfoItem(
            name = stringResource(id = R.string.token_id),
            value = nft.info.tokenId
        )

        AssetInfoItem(
            name = stringResource(id = R.string.contract_address),
            value = nft.info.getShortenContract()
        )
    }
}

@Composable
private fun AssetInfoItem(name: String, value: String) {
    Row {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = name,
            textAlign = TextAlign.Start,
            color = Gray1
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = value,
            textAlign = TextAlign.End,
            color = Blue,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}