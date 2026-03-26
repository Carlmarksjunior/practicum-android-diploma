package ru.practicum.android.diploma.util

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.getNavController(@IdRes fragmentId: Int): NavController {
    val navHostFragment = this.supportFragmentManager.findFragmentById(fragmentId) as NavHostFragment
    return navHostFragment.navController
}
