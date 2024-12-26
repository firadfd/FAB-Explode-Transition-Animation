package fd.firad.fabexplodetransitionanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fd.firad.fabexplodetransitionanimation.ui.theme.FABExplodeTransitionAnimationTheme
import kotlinx.serialization.Serializable

const val FAB_EXPLODE_BOUNDS_KEY = "FAB_KEY"

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FABExplodeTransitionAnimationTheme {
                val navController = rememberNavController()
                val fabColor = Color.Green
                SharedTransitionLayout {
                    NavHost(
                        navController = navController,
                        startDestination = MainScreenRoute,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable<MainScreenRoute> {
                            MainScreen(
                                fabColor = fabColor,
                                animatedVisibilityScope = this,
                                onFabClick = {
                                    navController.navigate(SecondScreenRoute)
                                })
                        }
                        composable<SecondScreenRoute> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = fabColor)
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = FAB_EXPLODE_BOUNDS_KEY
                                        ), animatedVisibilityScope = this
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Text("Second Screen")
                            }
                        }
                    }
                }
            }

        }
    }
}


@Serializable
data object MainScreenRoute

@Serializable
data object SecondScreenRoute

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MainScreen(
    fabColor: Color,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onFabClick: () -> Unit,
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = onFabClick,
            containerColor = fabColor,
            modifier = Modifier
                .padding(10.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = FAB_EXPLODE_BOUNDS_KEY
                    ), animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add, tint = Color.Black, contentDescription = "Add"
            )
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("Main Screen", style = TextStyle(color = Color.Black))
        }
    }
}