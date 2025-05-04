package com.edu.travel.ui.presenration.ticket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.DashPathEffect
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.edu.travel.R
import com.edu.travel.route.Route
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.nio.file.WatchEvent


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun TicketDetailScreen(
    routeParams: Route.Ticket,
    upPress: () -> Unit
) {

    val viewModel = koinViewModel<TicketViewModel>() {
        parametersOf(routeParams)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        topBar = {
            TicketDetailTopBar(upPress)
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = state.itemEntity == null && state.popularEntity == null
        ) { target ->
           if (target) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
           }else {
               TicketCard(
                   modifier = Modifier.fillMaxWidth()
                       .padding(innerPadding)
                       .padding(horizontal = 12.dp)
                       .padding(top = 16.dp),
                   state = state
               )
           }
        }
    }
}

@Composable
private fun TicketCard(
    modifier: Modifier = Modifier,
    state: TicketState
) {
    val item = state.itemEntity
    val popular = state.popularEntity

    Card(modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        ListItem(
            modifier = Modifier.padding(top = 10.dp),
            headlineContent = {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Text(
                        text = "Upcoming Trip",
                        modifier = Modifier.padding(12.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            trailingContent = {
                Text(text = "Order Id: 987654",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item?.pic ?: popular?.pic)
                .crossfade(true)
                .build(),
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier= Modifier
                .padding(16.dp)
                .padding(top = 4.dp)
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.LightGray)
        )
        ListItem(
            headlineContent = {
                Text(text = item?.title ?: popular?.title ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            supportingContent = {
                Text(
                    text = "Show this ticket to the your guide before departure"
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Tour Guide",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(2f)
            )
            Text(text = "Time",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = item?.dateTour ?: popular?.dateTour ?: "-",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(2f)
            )
            Text(text = item?.timeTour ?: popular?.timeTour ?: "0",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Tip Duration",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(2f)
            )
            Text(text = "Total Guest",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = item?.duration ?: popular?.duration ?: "-",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(2f)
            )
            Text(text = "0",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }
        UserCard(
            name = item?.tourGuideName ?: popular?.tourGuideName ?: "Name",
            phone = item?.tourGuidePhone ?: popular?.tourGuidePhone ?: "1234567890",
            cover = item?.tourGuidePic ?: popular?.tourGuidePic ?: ""
        )
        TicketDivider()
        Image(
            painter = painterResource(R.drawable.barcode),
            contentDescription = "barcode",
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentScale = ContentScale.FillWidth
        )
        Button (
            onClick = {},
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Download Ticket")
        }
    }
}


@Composable
private fun TicketDivider(
    color: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    radius: Dp = 30.dp,
    dashLength: Float = 20f,
    gapLength: Float = 10f,
    strokeWidth: Float = 5f
) {
    Canvas(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        val paint = Paint().asFrameworkPaint().apply {
            this.strokeWidth = strokeWidth
            this.color = color.toArgb()
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            pathEffect = DashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        }
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(radius.toPx(), radius.toPx()),
            topLeft = Offset( -radius.toPx() / 2f,  -radius.toPx() / 2f)
        )
        drawArc(
            color = color,
            startAngle = 90f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(radius.toPx(), radius.toPx()),
            topLeft = Offset( size.width - radius.toPx() / 2f,  -radius.toPx() / 2f)
        )
        drawIntoCanvas {
            it.nativeCanvas.drawLine(
                radius.toPx() / 2f,
                size.height / 2f,
                size.width - radius.toPx() / 2f,
                size.height / 2f,
                paint
            )
        }
    }
}

@Composable
private fun UserCard(
    name: String,
    cover: String,
    phone: String,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp, vertical = 8.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cover)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = "Tour Guide",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal
                )
                Text(text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Image(
                painter = painterResource(R.drawable.message),
                contentDescription = "message",
                modifier = Modifier.size(42.dp)
                    .clip(CircleShape)
                    .clickable {
                        sendSMS(context, phone)
                    }
            )
            Image(
                painter = painterResource(R.drawable.call),
                contentDescription = "call",
                modifier = Modifier.size(42.dp)
                    .clip(CircleShape)
                    .clickable {
                        callPhone(context, phone)
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketDetailTopBar(
    upPress: () -> Unit
){
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 8.dp, 4.dp),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            Image(painter = painterResource(R.drawable.back),
                  contentDescription = "back",
                  modifier = Modifier.clip(CircleShape)
                    .clickable {
                        upPress.invoke()
                    }
            )
        },
        title = {
            Text(text = stringResource(R.string.str_ticket_detail),
                fontWeight = FontWeight.Bold
            )
        }
    )
}

private fun callPhone(context: Context, phone: String){
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts(
        "tel", phone, null
    ))
    context.startActivity(intent)
}

private fun sendSMS(context: Context, phone: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setData("sms:$phone".toUri())
    intent.putExtra("sms_body", "type your message")
    context.startActivity(intent)
}


@Preview(showBackground = true)
@Composable
fun TicketDetailScreenPreview() {
    TicketDetailScreen(routeParams = Route.Ticket(-1), upPress = {})
}
