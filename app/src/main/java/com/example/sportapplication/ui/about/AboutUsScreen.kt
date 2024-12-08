package com.example.sportapplication.ui.about

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sportapplication.R

@Composable
fun AboutUsScreen() {
    val fadeInState = remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        fadeInState.value = 1f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .graphicsLayer { alpha = fadeInState.value },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.about_us),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.meet_the_team_behind_this_app),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TeamMemberCard(
                name = "Lill-Kristin Karlsen",
                role = stringResource(R.string.developer),
                imageRes = R.drawable.lill
            )
            TeamMemberCard(
                name = "Tetiana Verkhalantseva",
                role = stringResource(R.string.developer),
                imageRes = R.drawable.tetiana
            )
            TeamMemberCard(
                name = "Oliver Stanislaw Sokol",
                role = stringResource(R.string.developer),
                imageRes = R.drawable.oliver
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Product Idea:",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.this_app_was_created_to_help_users_achieve_their_fitness_and_outdoor_goals_through_gamification_the_app_tracks_user_activity_and_provides_fun_challenges),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun TeamMemberCard(name: String, role: String, imageRes: Int) {
    var isHovered by remember { mutableStateOf(false) }

    val scaleState by animateFloatAsState(
        targetValue = if (isHovered) 1.1f else 1f, // Scale up when hovered
        animationSpec = tween(durationMillis = 300)
    )

    val cardBackgroundColor by animateColorAsState(
        targetValue = if (isHovered) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 300)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(cardBackgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isHovered = true
                        tryAwaitRelease()
                        isHovered = false
                    }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scaleState
                    scaleY = scaleState
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Name and Role
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = role,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
