package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    //list fragment yang mau di tampilin di tiap tab
    private val fragmentList = listOf(
        HomeFragment(),
        FavoriteFragment(),
        SearchFragment()
    )

    //judul tab nya
    private val fragmentTitleList = listOf(
        "Home",
        "Favorite",
        "Search"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // set tampilan apk jadi mode terang
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //tampilkan layout activitymain.xml
        setContentView(R.layout.activity_main)

        //hubungin variabel dgn komponen di layout
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        //ngatur adapter biar viewpager2 bisa nampilin fragment sesuai posisinya
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragmentList.size
            override fun createFragment(position: Int): Fragment = fragmentList[position]
        }

        // Menghubungkan TabLayout dengan ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragmentTitleList[position]
        }.attach()
    }
}
