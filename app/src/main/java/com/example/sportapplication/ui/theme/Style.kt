package com.example.sportapplication.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Defines the default appearance of buttons, text fields etc. in the app.

// Primary Button with blue background and white text
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer, // Blue background
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer // White text
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = text)
    }
}

// Secondary Button with outlined style and blue background when focused
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer, // Blue background
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer // White text
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = text)
    }
}

// Custom TextField with blue text and background matching the intro screen

@Composable
fun TextFieldCustom(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Label",
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface, // Background matches intro screen
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedLabelColor = MaterialTheme.colorScheme.tertiaryContainer, // Blue label
            unfocusedLabelColor = MaterialTheme.colorScheme.tertiaryContainer,
            cursorColor = MaterialTheme.colorScheme.tertiaryContainer, // Blue cursor
            focusedTextColor = MaterialTheme.colorScheme.tertiaryContainer, // Blue text
            unfocusedTextColor = MaterialTheme.colorScheme.tertiaryContainer // Blue text when unfocused
        ),
        modifier = modifier
    )
}

// Error TextField with existing error colors
@Composable
fun ErrorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Error Label",
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.errorContainer,
            focusedLabelColor = MaterialTheme.colorScheme.error,
            unfocusedLabelColor = MaterialTheme.colorScheme.onErrorContainer,
            cursorColor = MaterialTheme.colorScheme.error,
            focusedTextColor = MaterialTheme.colorScheme.onErrorContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onErrorContainer
        ),
        modifier = modifier
    )
}

// Additional button style with assist functionality
@Composable
fun AssistButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = text)
    }
}

// Additional filter button style
@Composable
fun FilterButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Blue color for filter button
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer // White text
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = text)
    }
}
