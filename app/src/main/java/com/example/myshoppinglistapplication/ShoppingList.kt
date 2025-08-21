package com.example.myshoppinglistapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete


data class ShoppingItems(val id:Int,
                         var name: String,
                         var quantity:Int,
                         var isEditing:Boolean = false
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApplication() {
    var sItems by remember { mutableStateOf(listOf<ShoppingItems>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {showDialog=true},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Item")
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(14.dp)
        ) {
            items(sItems) {
                    shoppingItem-> ShoppingListItem(item = shoppingItem,{},{})
            }
        }
    }
    if(showDialog){
        AlertDialog( onDismissRequest = {showDialog=false},
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Button(onClick = {
                        if(itemName.isNotBlank()){
                            val newItem = ShoppingItems(
                                id= sItems.size+1,
                                name = itemName,
                                quantity = itemQuantity.toInt()
                            )
                            sItems=sItems+newItem
                            showDialog = false
                            itemName = ""
                        }
                    }){
                        Text("ADD")
                    }
                    Button(onClick = {showDialog=false}){
                        Text("CANCEL")
                    }
                }
            },
            title = { Text("Add Shopping Item") },
            text = {
                Column{
                    OutlinedTextField(value =itemName ,
                        onValueChange ={itemName=it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp))
                    OutlinedTextField(value =itemQuantity ,
                        onValueChange ={itemQuantity=it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp))
                }
            } )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListItem(
    item: ShoppingItems,
    onEditClick:() -> Unit,
    onDeleteClick:()-> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp).border(
            border = BorderStroke(2.dp, Color(0XFF018786)),
            shape = RoundedCornerShape(20)
        )
    ){
        Text(text=item.name,modifier=Modifier.padding(8.dp))
        Text(text="Qti: ${item.quantity}",modifier=Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(8.dp)){
            IconButton(onClick = onEditClick){
                Icon(imageVector = Icons.Default.Edit,contentDescription = "Edit Item")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Item"
                )
            }
        }
    }
}

