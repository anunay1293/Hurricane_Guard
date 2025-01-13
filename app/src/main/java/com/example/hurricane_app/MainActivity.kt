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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.ui.theme.Hurricane_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hurricane_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShelterDisplay()
                   // MentalHealthScreen()
                }
            }
        }
    }
}


@Composable
fun MentalHealthScreen(){
    val resources = listOf(
        Pair(R.drawable.meditation,R.string.advice_meditation),
        Pair(R.drawable.familysupport,R.string.advice_family_support),
        Pair(R.drawable.relaxation,R.string.advice_relaxation),
        Pair(R.drawable.sleep_schedule,R.string.advice_sleep),
        Pair(R.drawable.limitstress,R.string.advice_limit_news)
        )
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){
        items(resources){resource->
            MentalHealthCard(imageRes = resource.first, adviceTextRes =resource.second )
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