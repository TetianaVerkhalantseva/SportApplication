package com.example.sportapplication.ui.quest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.R
import com.example.sportapplication.database.model.EventQuest
import kotlinx.coroutines.launch

@Composable
fun QuestsScreenRoute(
    navigateToSelectedQuestScreen: (Long) -> Unit
) {
    // ViewModel to handle quest data
    val viewModel : QuestsViewModel = hiltViewModel()

    // Pass quest data to the main screen
    QuestsScreen(
        eventQuests = viewModel.getQuests(),
        onQuestClick = { navigateToSelectedQuestScreen(it.id) }
    )
}

// This is the QuestsScreen. Currently, all data is hardcoded and the logic is not implemented yet.
// The screen is created to display a temporary view for now.
@Composable
fun QuestsScreen(
    eventQuests: List<EventQuest>, // List of quests to display
    onQuestClick: (EventQuest) -> Unit // Callback for when a quest is clicked
) {
    val pagerState = rememberPagerState { 2 } // State to control the pager
    val coroutineScope = rememberCoroutineScope()

    Column {
        // Header with tabs for All Quests and Favourites
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // All Quests tab
            Column(
                modifier = Modifier
                    .weight(1F)
                    .clickable {
                        coroutineScope.launch {
                            if (pagerState.currentPage != 0)
                                pagerState.animateScrollToPage(0)
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.all_quests),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        if (pagerState.currentPage == 0) Color.Red
                        else Color.Gray
                    )
                )
            }
            // Favourites tab
            Column(
                modifier = Modifier
                    .weight(1F)
                    .clickable {
                        coroutineScope.launch {
                            if (pagerState.currentPage != 1)
                                pagerState.animateScrollToPage(1)
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.favourites),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        if (pagerState.currentPage == 1) Color.Red
                        else Color.Gray
                    )
                )
            }
        }

        // HorizontalPager to switch between All Quests and Favourites
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { currentPage ->
            when (currentPage) {
                0 -> AllQuestsScreen(eventQuests = eventQuests, onQuestClick = onQuestClick)
                1 -> FavouritesQuestsScreen(eventQuests = eventQuests, onQuestClick = onQuestClick)
            }
        }
    }
}

@Composable
fun FavouritesQuestsScreen(
    eventQuests: List<EventQuest>, // List of quests to display in the favourites section
    onQuestClick: (EventQuest) -> Unit // Callback when a quest is clicked
) {
    Column {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            columns = GridCells.Fixed(2), // Display quests in 2 columns
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Display each quest as a FavouriteQuestItem
            items(eventQuests) { quest ->
                FavouriteQuestItem(
                    eventQuest = quest,
                    onQuestClick = onQuestClick
                )
            }
        }
    }
}

@Composable
fun FavouriteQuestItem(eventQuest: EventQuest, onQuestClick: (EventQuest) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onQuestClick(eventQuest) }
    ) {
        // If the quest has an image, display it; otherwise, show a placeholder
        eventQuest.image?.let { imageId ->
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .width(200.dp)
                    .height(150.dp),
                painter = painterResource(id = imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } ?: Spacer(modifier = Modifier
            .width(200.dp)
            .height(150.dp)) // Empty space if no image

        Spacer(modifier = Modifier.height(8.dp))

        // Display the title of the quest
        Text(
            text = stringResource(id = eventQuest.title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display the description or a default text if it's null
        Text(
            modifier = Modifier.width(200.dp),
            text = stringResource(id = eventQuest.description ?: R.string.default_description),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AllQuestsScreen(
    eventQuests: List<EventQuest>, // List of all quests
    onQuestClick: (EventQuest) -> Unit // Callback for quest selection
) {
    Column (
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(rememberScrollState()) // Enable scrolling
    ) {

        // Section for daily quests
        Text(
            text = stringResource(id = R.string.daily_quests),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        QuestsLazyRow(
            eventQuests = eventQuests,
            onQuestClick = onQuestClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Section for no equipment quests
        Text(
            text = stringResource(id = R.string.no_equipment_quests),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        QuestsLazyRow(
            eventQuests = eventQuests,
            onQuestClick = onQuestClick
        )
    }
}


@Composable
fun QuestsLazyRow(
    eventQuests: List<EventQuest>, // List of quests to display horizontally
    onQuestClick: (EventQuest) -> Unit // Callback when a quest is clicked
) {
    Column {
        LazyRow {
            // Display each quest as a QuestItem
            items(eventQuests) { quest ->
                QuestItem(
                    eventQuest = quest,
                    onClick = { onQuestClick(quest) }
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

@Composable
fun QuestItem(
    eventQuest: EventQuest, onClick: () -> Unit, // Quest item with click handler
) {
    Column(
        modifier = Modifier.clickable { onClick() } // Handle quest click
    ) {
        // If the quest has an image, display it; otherwise, show a placeholder
        eventQuest.image?.let { imageId ->
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .width(200.dp)
                    .height(150.dp),
                painter = painterResource(id = imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } ?: Spacer(modifier = Modifier
            .width(200.dp)
            .height(150.dp)) // Placeholder if no image

        Spacer(modifier = Modifier.height(8.dp))

        // Display the title of the quest
        Text(
            text = stringResource(id = eventQuest.title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display the description or a default if null
        Text(
            modifier = Modifier.width(200.dp),
            text = stringResource(id = eventQuest.description ?: R.string.default_description),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
