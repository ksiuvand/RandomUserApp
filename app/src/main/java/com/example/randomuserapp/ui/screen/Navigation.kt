package com.example.randomuserapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.randomuserapp.viewmodel.UserViewModel
import com.example.randomuserapp.viewmodel.UserViewModelFactory
import com.example.randomuserapp.data.api.RandomUserApi
import com.example.randomuserapp.data.repository.UserRepository
import com.example.randomuserapp.data.local.AppDatabase

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserAppNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val userDao = db.userDao()

    val api = RandomUserApi.getInstance()
    val repository = UserRepository(api, userDao)
    val factory = UserViewModelFactory(repository)
    val viewModel: UserViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = "users_list"
    ) {
        composable("users_list") {
            UsersListScreen(
                viewModel = viewModel,
                onUserClick = { user ->
                    navController.navigate("user_detail/${user.login.uuid}")
                }
            )
        }
        composable(
            route = "user_detail/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            val user = viewModel.users.value.find { it.login.uuid == userId }
            UserDetailScreen(user = user, navController = navController)
        }
    }
}