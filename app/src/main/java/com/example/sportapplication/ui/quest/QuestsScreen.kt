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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.R
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Reward
import kotlinx.coroutines.launch

@Composable
fun QuestsScreenRoute(
    navigateToSelectedQuestScreen: (Long) -> Unit
) {
    val viewModel : QuestsViewModel = hiltViewModel()

    QuestsScreen(
        onQuestClick = { navigateToSelectedQuestScreen(0) } //@TODO
    )
}

@Composable
fun QuestsScreen(
    onQuestClick: (Quest) -> Unit
) {
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
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
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {currentPage ->
            when (currentPage) {
                0 -> AllQuestsScreen(onQuestClick = onQuestClick)
                1 -> FavouritesQuestsScreen(onQuestClick = onQuestClick)
            }
        }
    }


}

@Composable
fun FavouritesQuestsScreen(onQuestClick: (Quest) -> Unit) {
    val quests = mutableListOf<Quest>().apply {
        addAll(dailyQuests)
        addAll(noEquipmentQuests)
    }
    Column {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quests) { quest ->
                FavouriteQuestItem(
                    quest = quest,
                    onQuestClick = onQuestClick
                )
            }
        }
    }
}

@Composable
fun FavouriteQuestItem(quest: Quest, onQuestClick: (Quest) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .width(200.dp)
                .height(150.dp),
            imageVector = ImageVector.vectorResource(id = quest.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = quest.title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .width(200.dp),
            text = stringResource(id = quest.description),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AllQuestsScreen(
    onQuestClick: (Quest) -> Unit
) {
    Column (
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = stringResource(id = R.string.daily_quests),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        QuestsLazyRow(
            quests = dailyQuests,
            onQuestClick = onQuestClick
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.no_equipment_quests),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        QuestsLazyRow(
            quests = noEquipmentQuests,
            onQuestClick = onQuestClick
        )
    }
}


@Composable
fun QuestsLazyRow(
    quests: List<Quest>,
    onQuestClick: (Quest) -> Unit
    ) {
    Column {
        LazyRow() {
            items(quests) { quest ->
                QuestItem(
                    quest = quest,
                    onClick = {  onQuestClick(quest)}
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

@Composable
fun QuestItem(
    quest: Quest, onClick: () -> Unit,
) {
    Column (
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .width(200.dp)
                .height(150.dp),
            imageVector = ImageVector.vectorResource(id = quest.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = quest.title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .width(200.dp),
            text = stringResource(id = quest.description),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

val dailyQuests = listOf(
    Quest(
        image = R.drawable.ic_launcher_background,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    ),
    Quest(
        image = R.drawable.ic_launcher_foreground,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    ),
    Quest(
        image = R.drawable.ic_launcher_background,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    ),
    Quest(
        image = R.drawable.ic_launcher_foreground,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    ),
)

val noEquipmentQuests = listOf(
    Quest(
        image = R.drawable.ic_launcher_background,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    ),
    Quest(
        image = R.drawable.ic_launcher_foreground,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    ),
    Quest(
        image = R.drawable.ic_launcher_background,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    ),
    Quest(
        image = R.drawable.ic_launcher_foreground,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    ),
)