package com.edu.travel.ui.presenration.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.edu.travel.R
import com.edu.travel.room.BannerEntity
import com.edu.travel.room.CategoryEntity
import com.edu.travel.room.ItemEntity
import com.edu.travel.room.LocationEntity
import com.edu.travel.room.PopularEntity
import com.edu.travel.route.Route
import com.edu.travel.ui.presenration.components.AddressWidget
import org.koin.androidx.compose.koinViewModel


@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    navigateToRoute: (Route) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            DashBoardTopBar(
                locations = state.locations,
                selectedLocation = state.location,
                onDashboardAction = viewModel::handleDashboardAction
            )
        },
        bottomBar = {
            BottomBar()
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { SearchInput() }
            item { Banner(banners = state.banners) }
            item {
                Category(
                    categories = state.categories,
                    selectedCategory = state.category,
                    onDashboardAction = viewModel::handleDashboardAction
                )
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(text = stringResource(R.string.str_popular),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    trailingContent = {
                        TextButton(onClick = {}) {
                            Text(text = stringResource(R.string.str_see_all))
                        }
                    }
                )
            }
            item { PopularList(populars = state.populars, navigateToRoute = navigateToRoute) }
            item {
                ListItem(
                    headlineContent = {
                        Text(text = stringResource(R.string.str_recommend),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    trailingContent = {
                        TextButton(onClick = {}) {
                            Text(text = stringResource(R.string.str_see_all))
                        }
                    }
                )
            }
            item { RecommendList(recommends = state.recommends, navigateToRoute = navigateToRoute) }
            item {Spacer(Modifier)}
        }
    }
}

@Composable
private fun RecommendList(
    recommends: List<ItemEntity>,
    navigateToRoute: (Route) -> Unit
){
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(Modifier) }
        items(recommends) {
            RecommendItem(
                image = it.pic,
                title = it.title,
                address = it.address,
                price = it.price,
                score = it.score,
                onClick = {
                    navigateToRoute.invoke(Route.Detail(id = it.id))
                }
            )
        }
        item { Spacer(Modifier)}
    }
}

@Composable
private fun RecommendItem(
    image: String, address: String, price: Int, title: String, score: Double,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight(),

        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = onClick,
    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription =  title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(320.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(painter = painterResource(R.drawable.star),
                        contentDescription = "$title-star"
                    )
                    Text(text = score.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
            AddressWidget(address = address)
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )) {
                    append("$")
                    append(price.toString())
                }
                append("/Person")
            },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PopularList(
    populars: List<PopularEntity>,
    navigateToRoute: (Route) -> Unit
){
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(Modifier) }
        items(populars) {
            PopularItem(
                image = it.pic,
                address = it.address,
                price = it.price,
                title = it.title,
                score = it.score,
                onClick = {
                   navigateToRoute.invoke(Route.Detail(id = it.id, isPopular = true))
                }
            )
        }
        item { Spacer(Modifier)}
    }
}

@Composable
private fun PopularItem(
    image: String, address: String, price: Int, title: String, score: Double,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = onClick
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
           AsyncImage(
               model = ImageRequest.Builder(LocalContext.current)
                   .data(image)
                   .crossfade(true)
                   .build(),
               contentDescription =  title,
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .width(125.dp)
                   .height(85.dp)
                   .clip(RoundedCornerShape(12.dp))
           )
            Column(modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary
                )
                AddressWidget(address = address)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )) {
                            append("$")
                            append(price.toString())
                        }
                        append("/Person")
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Image(painter = painterResource(R.drawable.star),
                              contentDescription = "$title-star"
                        )
                        Text(text = score.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Category(
    categories: List<CategoryEntity>,
    selectedCategory: CategoryEntity,
    onDashboardAction: (DashboardAction) -> Unit
) {
   LazyRow(modifier = Modifier
           .fillMaxWidth()
           .padding(16.dp)
           .animateContentSize(),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.SpaceBetween
   ) {
       items(categories) {
           CategoryItem(
               icon = it.imagePath,
               title = it.name,
               selected = it.id == selectedCategory.id,
               onClick = {
                   onDashboardAction.invoke(DashboardAction.ChangedCategoryAction(it))
               }
           )
       }
   }
}

@Composable
private fun CategoryItem(
    icon: String,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
){
    Row(modifier = Modifier
        .animateContentSize()
        .clip(CircleShape)
        .clickable(
            onClick = { onClick.invoke() },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
        .then(
            if (selected) {
                Modifier.background(MaterialTheme.colorScheme.primaryContainer)
            } else {
                Modifier
            }
        )
        .padding(horizontal = 6.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(icon)
                .crossfade(true)
                .build(),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        if (selected){
            Text(text = title, color = MaterialTheme.colorScheme.onPrimaryContainer,
                 modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}

@Composable
private fun Banner(
    banners: List<BannerEntity>
) {
    AnimatedContent(
        targetState = banners.isEmpty()
    ) { target ->
        if (target){
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ){ CircularProgressIndicator() }
        }else {
            val paper = rememberPagerState { banners.size }
            HorizontalPager(state = paper) {
               val banner = banners[it]
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(banner.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = banner.id.toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Composable
private fun SearchInput() {

    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = text,
        placeholder = {
            Text(text= stringResource(R.string.str_search_hint))
        },
        onValueChange = { text = it },
        leadingIcon = {
            Image(painter = painterResource(R.drawable.search_icon),
                contentDescription = "search icon"
            )
        },
        trailingIcon = {
            FilledTonalIconButton(onClick = {}) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = Icons.AutoMirrored.Default.ArrowForward.name
                )
            }
        },
        shape = CircleShape
    )
}

@Composable
private fun BottomBar() {
    val bottomMenus = remember {
        listOf(
            Pair(R.drawable.bottom_btn1, R.string.str_home),
            Pair(R.drawable.bottom_btn2, R.string.str_explorer),
            Pair(R.drawable.bottom_btn3, R.string.str_bookmark),
            Pair(R.drawable.bottom_btn4, R.string.str_profile),
        )
    }

    var currentItem by remember { mutableIntStateOf(bottomMenus.first().first) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        bottomMenus.fastForEach {
            Spacer(Modifier)
            Row(modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = { currentItem = it.first },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ).animateContentSize()
                    .then(
                        if (currentItem == it.first) {
                            Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                        } else {
                            Modifier
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(painter = painterResource(it.first),
                    contentDescription = stringResource(it.second),
                    modifier = Modifier.size(20.dp)
                )
                if (currentItem == it.first) {
                    Text(text = stringResource(it.second),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(Modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardTopBar(
    locations: List<LocationEntity>,
    selectedLocation: LocationEntity,
    onDashboardAction: (DashboardAction) -> Unit
) {

    var expand by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.padding(0.dp)) {
                    TextButton(onClick = {expand = true}) {
                        Text(text = selectedLocation.loc)
                        Spacer(Modifier.width(8.dp))
                        Icon(imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = Icons.Default.ArrowDropDown.name
                        )
                    }
                    DropdownMenu(expanded = expand, onDismissRequest = { expand = false }) {
                        locations.fastForEach {
                            DropdownMenuItem(
                                text = { Text(text = it.loc) },
                                onClick = {
                                    onDashboardAction.invoke(
                                        DashboardAction.ChangedLocationAction(it)
                                    )
                                    expand = false
                                }
                            )
                        }
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Image(painter = painterResource(R.drawable.bell_icon),
                      contentDescription = "bell icon"
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(navigateToRoute = {})
}
