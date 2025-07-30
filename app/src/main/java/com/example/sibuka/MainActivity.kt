package com.example.sibuka

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sibuka.activities.LoginActivity
import com.example.sibuka.databinding.ActivityMainBinding
import com.example.sibuka.fragments.BukuFragment
import com.example.sibuka.fragments.NotifikasiFragment
import com.example.sibuka.fragments.PeminjamFragment
import com.example.sibuka.fragments.PinjamFragment
import com.example.sibuka.fragments.ProfilFragment
import com.example.sibuka.utils.FirebaseUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (FirebaseUtils.auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupBottomNavigation()
        if (savedInstanceState == null) {
            loadFragment(BukuFragment())
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_buku -> {
                    loadFragment(BukuFragment())
                    true
                }
                R.id.nav_peminjam -> {
                    loadFragment(PeminjamFragment())
                    true
                }
                R.id.nav_pinjam -> {
                    loadFragment(PinjamFragment())
                    true
                }
                R.id.nav_notifikasi -> {
                    loadFragment(NotifikasiFragment())
                    true
                }
                R.id.nav_profil -> {
                    loadFragment(ProfilFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
