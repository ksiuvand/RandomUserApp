package com.example.randomuserapp.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.randomuserapp.R
import com.example.randomuserapp.data.local.UserEntity
import androidx.core.net.toUri

@SuppressLint("QueryPermissionsNeeded")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(user: UserEntity?, navController: NavController) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.user_details),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                user?.let {user ->
                        AsyncImage(
                            model = user.picture.large,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(200.dp)
                                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                                .align(Alignment.CenterHorizontally)
                        )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        item {
                            UserInfoItem(stringResource(R.string.user_full_name), "${user.name.title} ${user.name.first} ${user.name.last}")
                            UserInfoItem(stringResource(R.string.user_gender), user.gender)
                            Text(
                                text = "Телефон: ${user.phone}",
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_DIAL).apply {
                                            data = "tel:${user.phone}".toUri()
                                        }
                                        if (intent.resolveActivity(context.packageManager) != null) {
                                            context.startActivity(intent)
                                        }else {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.call_app_not_found),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            )
                            UserInfoItem(stringResource(R.string.country), user.location.country)
                            UserInfoItem(stringResource(R.string.state), user.location.state)
                            UserInfoItem(stringResource(R.string.city), user.location.city)
                            UserInfoItem(stringResource(R.string.street), "${user.location.street.name}, ${user.location.street.number}")
                            Text(
                                text = stringResource(R.string.maps_open),
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        val latitude = user.location.coordinates.latitude
                                        val longitude = user.location.coordinates.longitude
                                        val yandexMapUrl = "https://yandex.ru/maps/?ll=$longitude,$latitude&z=16"

                                        val mapsIntent = Intent(Intent.ACTION_VIEW, yandexMapUrl.toUri())

                                        if (mapsIntent.resolveActivity(context.packageManager) != null) {
                                            context.startActivity(mapsIntent)
                                        } else {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.maps_app_not_found),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            )
                            UserInfoItem(stringResource(R.string.time_zone), "${user.location.timezone.description}, ${user.location.timezone.offset}")
                            Text(
                                text = "Email: ${user.email}",
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = "mailto:${user.email}".toUri()
                                        }
                                        if (intent.resolveActivity(context.packageManager) != null) {
                                            context.startActivity(intent)
                                        } else {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.email_app_not_found),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            )
                            UserInfoItem(stringResource(R.string.uuid), user.login.uuid)
                            UserInfoItem(stringResource(R.string.username), user.login.username)
                            UserInfoItem(stringResource(R.string.password), user.login.password)
                            UserInfoItem(stringResource(R.string.age), "${user.dob.age}")
                            UserInfoItem(stringResource(R.string.nat), user.nat)
                        }
                    }
                }
            }?: Text("User not found")
        }
    )
}