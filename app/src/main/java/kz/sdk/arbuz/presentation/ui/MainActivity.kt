package kz.sdk.arbuz.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kz.sdk.arbuz.R
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.sdk.arbuz.MyApp
import kz.sdk.arbuz.databinding.ActivityMainBinding
import kz.sdk.arbuz.presentation.utils.BottomNavigationViewListener
import kz.sdk.arbuz.presentation.viewmodel.ProductViewModel
import kz.sdk.arbuz.presentation.viewmodel.ProductViewModelFactory

class MainActivity : AppCompatActivity(), BottomNavigationViewListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigation

        val badge = bottomNavigationView.getOrCreateBadge(R.id.cartFragment)
        badge.isVisible = false

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        val productViewModel: ProductViewModel by viewModels {
            ProductViewModelFactory((application as MyApp).repository)
        }

        productViewModel.cartProducts.observe(this) { products ->
            val uniqueItemsCount = products.size
            if (uniqueItemsCount > 0) {
                badge.number = uniqueItemsCount
                badge.isVisible = true
            } else {
                badge.isVisible = false
            }
        }
    }

    override fun showBottomNavigationView(show: Boolean) {
        binding.bottomNavigation.visibility = if (show) View.VISIBLE else View.GONE
    }
}