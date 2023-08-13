package com.kongjak.koreatechboard.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import com.kongjak.koreatechboard.ui.viewmodel.MainViewModel
import com.kongjak.koreatechboard.util.routes.Department
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoreatechBoardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            AppBar()
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        },
        content = { contentPadding ->
            Box(Modifier.padding(contentPadding)) {
                NavigationGraph(navController = navController)
            }
        }
    )
}

@Composable
fun BoardInMain(department: Department, mainViewModel: MainViewModel = hiltViewModel(key = department.name)) {
    var key by remember {
        mutableStateOf(department.boards[0].board)
    }
    var tabIndex by remember { mutableIntStateOf(0) }
    val isLoaded by mainViewModel.isLoaded.observeAsState(false)

    LaunchedEffect(key1 = tabIndex) {
        mainViewModel.getApi(department.name, key)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(text = stringResource(id = department.stringResource), fontSize = 16.sp)
            }

            TabRow(selectedTabIndex = tabIndex, containerColor = Transparent) {
                department.boards.forEachIndexed { index, board ->
                    Tab(
                        text = { Text(text = stringResource(id = board.stringResource)) },
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                            key = board.board

                            mainViewModel.getApi(department.name, board.board)
                        }
                    )
                }
            }

            if (!isLoaded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (mainViewModel.statusCode.value!! == 200) {
                    mainViewModel.boardList[key]!!.value!!.forEach { data ->
                        Box(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .clickable {
                                    TODO("Implement article screen")
                                }
                        ) {
                            Text(text = data.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Divider(color = Gray, thickness = 0.5.dp)
                    }
                } else {
                    Text(text = "Error!")
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        BoardInMain(department = Department.School)
        BoardInMain(department = Department.Dorm)
        // Third BoardInMain will be user's department,
        // Other will fixed to School and Dormitory
        BoardInMain(department = Department.Cse)
    }
}

@Composable
fun BoardScreen() {
    TODO("Implement board list screen")
}

@Composable
fun SettingsScreen() {
    TODO("Implement Settings screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.app_name))
    })
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Board,
        BottomNavigationItem.Settings
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                label = {
                    Text(text = stringResource(id = item.stringResource))
                },

                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.name
                    )
                },

                selected = currentRoute == item.name,

                alwaysShowLabel = true,

                onClick = {
                    navController.navigate(item.name) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

                // Control all the colors of the icon
                colors = NavigationBarItemDefaults.colors()
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavigationItem.Home.name) {
        composable(BottomNavigationItem.Home.name) {
            HomeScreen()
        }
        composable(BottomNavigationItem.Board.name) {
            BoardScreen()
        }
        composable(BottomNavigationItem.Settings.name) {
            SettingsScreen()
        }
    }
}
