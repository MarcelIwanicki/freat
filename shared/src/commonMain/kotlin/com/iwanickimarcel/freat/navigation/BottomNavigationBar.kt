package com.iwanickimarcel.freat.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

data class BottomNavigationItem(
    val screen: Screen,
    val title: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    screen: Screen,
) {
    val navigator = LocalNavigator.current ?: return

    val items = listOf(
        BottomNavigationItem(
            screen = Home,
            title = "Home",
            filledIcon = Icons.Filled.Home,
            outlinedIcon = Icons.Outlined.Home,
        ),
        BottomNavigationItem(
            screen = Recipes,
            title = "Recipes",
            filledIcon = Icons.Filled.FoodBank,
            outlinedIcon = Icons.Outlined.FoodBank
        ),
        BottomNavigationItem(
            screen = Products(),
            title = "Products",
            filledIcon = Icons.Filled.Fastfood,
            outlinedIcon = Icons.Outlined.Fastfood
        ),
        BottomNavigationItem(
            screen = Settings,
            title = "Settings",
            filledIcon = Icons.Filled.Settings,
            outlinedIcon = Icons.Outlined.Settings
        ),
    )

    var selectedItemIndex = items.indexOfFirst {
        it.screen == screen
    }

    NavigationBar {
        items.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navigator.replaceAll(bottomNavigationItem.screen)
                },
                label = {
                    Text(bottomNavigationItem.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (bottomNavigationItem.badgeCount != null) {
                                Badge {
                                    Text(
                                        text = bottomNavigationItem.badgeCount.toString()
                                    )
                                }
                            } else if (bottomNavigationItem.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selectedItemIndex == index) {
                                bottomNavigationItem.filledIcon
                            } else {
                                bottomNavigationItem.outlinedIcon
                            },
                            contentDescription = bottomNavigationItem.title
                        )
                    }
                }
            )
        }
    }
}