package vn.edu.usth.usthweather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vn.edu.usth.usthweather.ui.theme.USTHWeatherTheme

class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            USTHWeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        Log.i("WeatherActivity", "onCreate called")
    }
    override fun onStart() {
        super.onStart()
        Log.i("WeatherActivity", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("WeatherActivity", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("WeatherActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("WeatherActivity", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("WeatherActivity", "onDestroy called")
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    USTHWeatherTheme {
        Greeting2("Android")
    }
}