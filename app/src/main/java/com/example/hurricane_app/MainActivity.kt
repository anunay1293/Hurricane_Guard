package com.example.hurricane_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hurricane_app.UserInterface.screens.ChecklistDetailScreen
import com.example.hurricane_app.UserInterface.screens.ChecklistListScreen
import com.example.hurricane_app.UserInterface.screens.HurricaneScreen
import com.example.hurricane_app.navigation.Screen
import com.example.hurricane_app.ui.theme.Hurricane_AppTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.R



import androidx.activity.compose.setContent
import androidx.compose.material3.Surface

import androidx.compose.foundation.layout.fillMaxSize

import androidx.navigation.compose.rememberNavController
import com.example.hurricane_app.ui.theme.Hurricane_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hurricane_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(navController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { AppBottomNavigation(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MentalHealth.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.MentalHealth.route) {
                MentalHealthScreen()
            }
            composable(Screen.Shelter.route) {
                ShelterDisplay()
            }
            composable(Screen.StayAtHome.route) {
                StayAtHomeScreen()
            }
            composable(Screen.Hurricane.route) {
                HurricaneScreen()
            }
            composable(Screen.Checklist.route) {
                ChecklistListScreen(navController = navController)
            }

            // 2) Checklist detail screen, passing a checklistId argument
            composable(
                route = Screen.ChecklistDetail.route + "/{checklistId}",
            ) { backStackEntry ->
                val checklistId = backStackEntry.arguments?.getString("checklistId")?.toIntOrNull() ?: 0
                ChecklistDetailScreen(checklistId = checklistId, navController = navController)
            }
        }
    }
}

@Composable
fun StayAtHomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Guidelines",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        val guidelines = listOf(
            Pair(R.drawable.barricade, R.string.guideline_barricade),
            Pair(R.drawable.gas_precautions, R.string.guideline_gas_precaution),
            Pair(R.drawable.essential_items, R.string.guideline_essential_items),
            Pair(R.drawable.stock_supplies, R.string.guideline_stock_supplies),
            Pair(R.drawable.emergency_contacts, R.string.guideline_emergency_contacts)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(guidelines) { (imageRes, textRes) ->
                MentalHealthCard(imageRes = imageRes, adviceTextRes = textRes)
            }
        }
    }
}


@Composable
fun AppBottomNavigation(navController: NavController) {
    val items = listOf(
        Screen.Hurricane to R.drawable.ic_hurricane,
        Screen.Shelter to R.drawable.ic_shelter,
        Screen.MentalHealth to R.drawable.ic_mental_health,
        Screen.StayAtHome to R.drawable.ic_stay_at_home,
        Screen.Checklist to R.drawable.ic_checklist
    )

    BottomNavigation {
        items.forEach { (screen, iconRes) ->
            BottomNavigationItem(
                selected = navController.currentDestination?.route == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = { Icon(imageVector = ImageVector.vectorResource(id = iconRes), contentDescription = screen.route) },
                label = { Text(text = screen.route.replace("_", " ").capitalize()) }
            )
        }
    }
}
@Composable
fun MentalHealthScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Mental Health",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        val resources = listOf(
            Pair(R.drawable.meditation, R.string.advice_meditation),
            Pair(R.drawable.familysupport, R.string.advice_family_support),
            Pair(R.drawable.relaxation, R.string.advice_relaxation),
            Pair(R.drawable.sleep_schedule, R.string.advice_sleep),
            Pair(R.drawable.limitstress, R.string.advice_limit_news)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(resources) { (imageRes, adviceTextRes) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(MaterialTheme.shapes.medium)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(id = adviceTextRes),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun MentalHealthCard(imageRes: Int,adviceTextRes: Int){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp))
        {
            Image(painter = painterResource(id = imageRes), contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = adviceTextRes),
                style=MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth())
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MentalHealthCardPreview() {
    Hurricane_AppTheme {
        MentalHealthCard(
            imageRes = R.drawable.meditation,
            adviceTextRes = R.string.advice_meditation
        )
    }
}