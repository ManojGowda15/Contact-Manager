package com.example.contacts.presentation.screen

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.contacts.presentation.ContactState
import com.example.contacts.presentation.ContactViewModel
import com.example.contacts.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    state: ContactState,
    viewModel: ContactViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Contacts", fontWeight = FontWeight.Bold) },
                actions = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Sort",
                        modifier = Modifier.clickable {
                            viewModel.changeisSorting()
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Routes.AddEdit.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ) {
            LazyColumn {
                items(state.contacts) { contact ->
                    val bitmap = contact.image?.let {
                        BitmapFactory.decodeByteArray(it, 0, it.size).asImageBitmap()
                    }
                    ContactItem(
                        viewModel = viewModel,
                        state = state,
                        name = contact.name,
                        phone = contact.phone,
                        email = contact.email,
                        image = bitmap,
                        imageByteArray = contact.image,
                        id = contact.id,
                        dateOfCreation = contact.dateOfCreation,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}

@Composable
fun ContactItem(
    name: String,
    phone: String,
    email: String,
    imageByteArray: ByteArray?,
    image: ImageBitmap?,
    dateOfCreation: Long,
    id: Int,
    viewModel: ContactViewModel,
    state: ContactState,
    navHostController: NavHostController
) {
    val context = LocalContext.current

    Card(
        onClick = {
            state.id.value = id
            state.name.value = name
            state.phone.value = phone
            state.email.value = email
            state.image.value = imageByteArray
            state.dateOfCreation.value = dateOfCreation
            navHostController.navigate(Routes.AddEdit.route)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            if (image != null) {
                Image(
                    bitmap = image,
                    contentDescription = "Contact Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Contact Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .padding(12.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Text(
                    text = phone.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        state.id.value = id
                        state.name.value = name
                        state.phone.value = phone
                        state.email.value = email
                        state.dateOfCreation.value = dateOfCreation
                        viewModel.deleteContact()
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                IconButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = "tel:$phone".toUri()
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}