package com.example.sportapplication.ui.quest

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.database.model.Quest

@Composable
fun QuestsScreenRoute(
    navigateToSelectedQuestScreen: (Long) -> Unit
) {
    val viewModel : QuestsViewModel = hiltViewModel()
    val quests by viewModel.quests.collectAsState()

    QuestsScreen(
        quests = quests,
        onQuestClick = { navigateToSelectedQuestScreen(it.id) }
    )
}

@Composable
fun QuestsScreen(
    quests: List<Quest>,
    onQuestClick: (Quest) -> Unit
) {
    LazyQuestsColumn(
        quests = quests,
        onQuestClick = onQuestClick
    )
}