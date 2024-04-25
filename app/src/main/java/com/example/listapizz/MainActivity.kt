package com.example.listapizz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listapizz.ui.theme.ListaPizzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaPizzTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListaPizz()
                }
            }
        }
    }
}

@Composable
fun ListaPizz(){
    val myList = remember { mutableListOf<Pizza>() }
    var pizzaName by remember { mutableStateOf("")  }

    var priceText by remember {mutableStateOf("") }
    var pizzaSize by remember { mutableStateOf("") }
    var isExpended by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value=pizzaName,
            onValueChange = {
                pizzaName=it
            },
            label={ Text(text="Podaj nazwę pizzy.")},
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value=priceText,
            onValueChange = {
                priceText=it
            },
            label={ Text(text="Podaj cenę pizzy.")},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        
        Spacer(modifier = Modifier.height(10.dp))

        Box{
            Button(onClick = {isExpended=true }) {
                Text(text = "Wielkosć Pizzy")
            }
            DropdownMenu(expanded = isExpended, 
                onDismissRequest = { isExpended=false }) {
                DropdownMenuItem(text = { Text("mała") }, onClick = {
                       pizzaSize= "mała"
                       isExpended=false
                })
                DropdownMenuItem(text = { Text("duża") }, onClick = {
                    pizzaSize= "duża"
                    isExpended=false
                 })

            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Button(onClick = { }) {
            if(pizzaName.isNotEmpty()){
                myList.add(
                    Pizza(
                        name=pizzaName,
                        price=priceText.toDouble(),
                        size = pizzaSize
                    )
                )
                pizzaName=""
                priceText=""
                pizzaSize=""
            }
            
            Text(text = "Dodaj")
        }


        if(myList.isEmpty()){
            Text(
                text="Pusta list",
                style=MaterialTheme.typography.titleMedium,
                modifier=Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier=Modifier.padding(top=8.dp)){

                items(myList){ item ->
                    Text(
                        text = "${item.name}, ${item.price}PLN, ${item.size}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                                    .padding(vertical =4.dp)
                                    .clickable { myList.remove(item) }

                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListaPizzTheme {
        ListaPizz()
    }
}