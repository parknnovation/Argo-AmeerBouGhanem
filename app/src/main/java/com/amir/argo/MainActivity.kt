package com.amir.argo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    val fragment1 = SliderFragment()
    val fragment2 = SliderFragment()
    val fragment3 = SliderFragment()

    lateinit var adapter: myPagerAdapter
    lateinit var activity: Activity

    lateinit var preference :SharedPreferences
    val pref_show_intro = "Intro"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_next = findViewById<Button>(R.id.btn_next)
        val btn_skip = findViewById<Button>(R.id.btn_skip)
        val view_pager = findViewById<ViewPager>(R.id.view_pager)
        val indicator1 = findViewById<TextView>(R.id.indicator1)
        val indicator2 = findViewById<TextView>(R.id.indicator2)
        val indicator3 = findViewById<TextView>(R.id.indicator3)


        activity = this
        preference =getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)

        if (!preference.getBoolean(pref_show_intro, true))
        {
            startActivity(Intent(activity, RealMainActivity::class.java))
            finish()
        }

        fragment1.setTitle("homepage")
        fragment2.setTitle("navpage")
        fragment3.setTitle("detectpage")

        adapter = myPagerAdapter(supportFragmentManager)
        adapter.list.add(fragment1)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)

        view_pager.adapter = adapter
        btn_next.setOnClickListener{
            view_pager.currentItem++
        }

        btn_skip.setOnClickListener{ goToRealMeanActivivty()}
        view_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == adapter.list.size-1)
                {
                    //lastPage
                    btn_next.text = "Done"
                    btn_next.setOnClickListener{
                        goToRealMeanActivivty()
                    }
                }
                else
                {
                    //has next
                    btn_next.text = "Next"
                    btn_next.setOnClickListener{
                        view_pager.currentItem++
                    }
                }
                when(view_pager.currentItem)
                {
                    0->{
                        indicator1.setTextColor(Color.RED)
                        indicator2.setTextColor(Color.GRAY)
                        indicator3.setTextColor(Color.GRAY)
                    }
                    1->{
                        indicator1.setTextColor(Color.GRAY)
                        indicator2.setTextColor(Color.RED)
                        indicator3.setTextColor(Color.GRAY)
                    }
                    2->{
                        indicator1.setTextColor(Color.GRAY)
                        indicator2.setTextColor(Color.GRAY)
                        indicator3.setTextColor(Color.RED)
                    }
                }

            }
        })

    }

    fun goToRealMeanActivivty()
    {
        startActivity(Intent(activity, RealMainActivity::class.java))
        finish()
        val editor = preference.edit()
        editor.putBoolean(pref_show_intro, false)
        editor.apply()
    }

    class myPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager){

        val list: MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }

    }

}