package com.edu.travel.ui.presenration.detail

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.edu.travel.R
import com.edu.travel.route.Route
import com.edu.travel.ui.presenration.components.AddressWidget
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    routeParams: Route.Detail,
    upPress: () -> Unit,
    navigateToRoute: (Route) -> Unit
) {
    val viewModel: DetailViewModel = koinViewModel() {
        parametersOf(routeParams)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    AnimatedContent(
        modifier = Modifier.fillMaxSize(),
        targetState = state.itemEntity == null && state.popularEntity == null,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }
    ) { target ->
        if (target) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                val item = state.itemEntity
                val popular = state.popularEntity

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item?.pic ?: popular?.pic)
                        .build(),
                    contentDescription = item?.title ?: popular?.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                        .height(420.dp)
                        .background(color = Color.LightGray)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 400.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = item?.title ?: popular?.title ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                    )
                    AddressWidget(address = item?.address ?: popular?.address ?: "")
                    RatingWidget(item?.score?.toFloat() ?: popular?.score?.toFloat() ?: 0f)
                    SimpleInfo(
                        item?.distance ?: popular?.distance ?: "-",
                        item?.duration ?: popular?.duration ?: "0",
                        item?.bed ?: popular?.bed ?: 0
                    )
                    Text(
                        text = stringResource(R.string.str_description),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item?.description ?: popular?.description ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                BottomBar(
                    price = item?.price?.toDouble() ?: popular?.price?.toDouble() ?: 0.0,
                    onClick = { navigateToRoute.invoke(Route.Ticket(routeParams.id, routeParams.isPopular)) }
                )
                TopAppBar(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .statusBarsPadding(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        Image(
                            painter = painterResource(R.drawable.back),
                            contentDescription = "back",
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { upPress.invoke() }
                        )
                    },
                    title = {},
                    actions = {
                        Image(
                            painter = painterResource(R.drawable.fav_icon),
                            contentDescription = "fav"
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SimpleInfo(
    distance: String,
    duration: String,
    bed: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            InfoWidget(
                icon = R.drawable.hourglass,
                title = "Duration",
                label = "${duration}min"
            )
            VerticalDivider(color = Color.Black)
            InfoWidget(
                icon = R.drawable.distance,
                title = "Distance",
                label = distance
            )
            VerticalDivider(color = Color.Black)
            InfoWidget(
                icon = R.drawable.bed,
                title = "Bed",
                label = "$bed"
            )
        }
    }
}

@Composable
private fun InfoWidget(
    @DrawableRes icon: Int,
    title: String,
    label: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1
        )
    }
}

@Composable
private fun BoxScope.BottomBar(
    price: Double,
    onClick: () -> Unit

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart)
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                ) {
                    append("$")
                    append(price.toString())
                }
                append("/Person")
            },
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Button(onClick = onClick) {
            Text(text = stringResource(R.string.str_add_to_cart))
        }
    }
}

@Composable
private fun RatingWidget(
    rating: Float
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        RatingBar(
            value = rating,
            style = RatingBarStyle.Fill(),
            onValueChange = {},
            onRatingChanged = {}
        )
        Text(
            text = rating.toString(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(routeParams = Route.Detail(-1), upPress = {}, navigateToRoute = {})
}
