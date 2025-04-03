package com.example.mobile_mastermind

sealed interface MainDestination {
    val route: String
}

data object Login : MainDestination {
    override val route = "login"
}

data object Register : MainDestination {
    override val route = "register"
}

data object Home : MainDestination {
    override val route = "home"
}

data object Game : MainDestination {
    override val route = "game"
}

data object Review : MainDestination {
    override val route = "review"
}

data object Ranking : MainDestination {
    override val route = "ranking"
}

data object Profile : MainDestination {
    override val route = "profile"
}