package com.mbahrami.foodapp.ui.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MidAppBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchFieldValueChange: (String) -> Unit,
    selectedFilterChar: String,
    onFilterCharClicked: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = LocalConfiguration.current.screenWidthDp.minus(28).dp)
            .padding(horizontal = 16.dp)
    ) {
        SearchField(
            modifier.weight(1f),
            searchQuery = searchQuery,
            onSearchFieldValueChange = onSearchFieldValueChange
        )
        SortDropDown(
            selectedChar = selectedFilterChar,
            onItemClicked = onFilterCharClicked
        )
    }
}

@Composable
fun SearchField(
    modifier: Modifier,
    searchQuery: String,
    onSearchFieldValueChange: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .height(56.dp),
        shape = CircleShape,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                value = searchQuery,
                onValueChange = onSearchFieldValueChange,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null
                    )
                },
                placeholder = { Text("Search...") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.Black
                )
            )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SortDropDown(selectedChar: String, onItemClicked: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Surface(modifier = Modifier.size(56.dp),
        shape = CircleShape,
        elevation = 4.dp,
        onClick = { expanded = true }) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = selectedChar, style = MaterialTheme.typography.h4)
        }
        Box {
            AlphabetMenu(
                expanded = expanded,
                onDismiss = { expanded = false },
                onItemClicked = { char ->
                    onItemClicked(char)
                    expanded = false
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlphabetMenu(expanded: Boolean, onDismiss: () -> Unit, onItemClicked: (String) -> Unit) {
    DropdownMenu(
        modifier = Modifier.height(300.dp),
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        ('A'..'Z').forEach { char ->
            Surface(modifier = Modifier
                .width(55.dp)
                .height(40.dp),
                onClick = { onItemClicked(char.toString()) }) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "$char",
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold)
                    )
                }

            }
        }

    }
}
