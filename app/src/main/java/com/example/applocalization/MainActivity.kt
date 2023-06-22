package com.example.applocalization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aaatrial.R
import com.example.applocalization.ui.theme.AAATrialTheme

// Define a sealed class for the options
sealed class GridOption(val image: Int, val title: String) {
    object Option1 : GridOption(R.drawable.india, "हिंदी")
    object Option2 : GridOption(R.drawable.india, "मराठी")
    object Option3 : GridOption(R.drawable.united_states, "English")
    object Option4 : GridOption(R.drawable.germany, "Deutsch")
    object Option5 : GridOption(R.drawable.france, "français")
    object Option6 : GridOption(R.drawable.spain, "español")
    object Option7 : GridOption(R.drawable.russia, "русский")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Preferences.sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        setContent {
            AAATrialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OptionItem(option: GridOption, isSelected: Boolean, onItemClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) (MaterialTheme.colorScheme.primary) else (MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.1f
                )),
            ),
            onClick = onItemClick
        ) {
            Column(
                modifier = Modifier.padding(vertical = 25.dp, horizontal = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(option.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = option.title,
                    style = if (isSelected) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.bodyMedium
                )
            }
        }

    }

    @Composable
    fun MainScreen(modifier: Modifier = Modifier) {

        var selectedOption by remember { mutableStateOf<GridOption?>(null) }

        val options = listOf(
            GridOption.Option1,
            GridOption.Option2,
            GridOption.Option3,
            GridOption.Option4,
            GridOption.Option5,
            GridOption.Option6,
            GridOption.Option7
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = when (selectedOption?.title) {
                    "हिंदी" -> "ऐप की भाषा"
                    "मराठी" -> "अॅप ची भाषा"
                    "English" -> "App's Language"
                    "Deutsch" -> "App-Sprache"
                    "français" -> "Langue de l'app"
                    "español" -> "Idioma de la app"
                    "русский" -> "Язык приложения"
                    else -> "App's Language"
                },
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = when (selectedOption?.title) {
                    "हिंदी" -> "अपनी पसंदीदा भाषा चुनें"
                    "मराठी" -> "आपल्या पसंदीची भाषा निवडा"
                    "English" -> "choose your\npreferred language"
                    "Deutsch" -> "Wählen Sie Ihre bevorzugte Sprache"
                    "français" -> "Choisissez votre langue préférée"
                    "español" -> "Elija su idioma preferido"
                    "русский" -> "Выберите предпочитаемый язык"
                    else -> "choose your\npreferred language"
                },
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(options.size) {
                    OptionItem(options[it], options[it] == selectedOption) {
                        selectedOption = options[it]
                    }
                }
            }
            Button(
                onClick = {
                    val languageCode = when (selectedOption?.title) {
                        "हिंदी" -> "hi"
                        "मराठी" -> "mr"
                        "English" -> "en"
                        "Deutsch" -> "de"
                        "français" -> "fr"
                        "español" -> "es"
                        "русский" -> "ru"
                        else -> "en"
                    }
                    val preferencesHelper = PreferencesHelper(applicationContext)
                    preferencesHelper.putString("Language", languageCode)
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    intent.putExtra("Language", selectedOption?.title)
                    startActivity(intent)
                }, modifier = Modifier.fillMaxWidth(), enabled = (selectedOption != null)
            ) {
                Text(
                    text = when (selectedOption?.title) {
                        "हिंदी" -> "आगे बढ़ें"
                        "मराठी" -> "पुढे जा"
                        "English" -> "Next"
                        "Deutsch" -> "Weiter"
                        "français" -> "Suivant"
                        "español" -> "Siguiente"
                        "русский" -> "Далее"
                        else -> "Next"
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        AAATrialTheme {
            MainScreen()
        }
    }
}