package com.example.randomuserapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.randomuserapp.R
import com.example.randomuserapp.viewmodel.UserViewModel
import com.example.randomuserapp.data.local.UserEntity

@Composable
fun UsersListScreen(onUserClick: (UserEntity) -> Unit, viewModel: UserViewModel) {

    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(users.isEmpty()) {
        if (users.isEmpty()) {
            viewModel.loadUsers()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, "Ошибка: $it", Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { viewModel.loadUsers(forceRefresh = true) },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.refresh_list))
        }

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(users) { user ->
                        UserListItem(
                            user = user,
                            onItemClick = { onUserClick(user) }
                        )
                    }
                }
            }
        }
    }
}