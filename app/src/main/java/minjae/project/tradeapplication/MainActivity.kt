package minjae.project.tradeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import minjae.project.tradeapplication.chatlist.ChatListFragment
import minjae.project.tradeapplication.home.HomeFragment
import minjae.project.tradeapplication.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val myPageFragment = MyPageFragment()
        val chatListFragment = ChatListFragment()

        // 첫 프레그먼트 초기화
        replaceFragment(homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // itemId는 interface MenuItem 에서 정의되어있다.
        // return true 가 맞지만 return이 생략되었다.
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(homeFragment)
                R.id.chatList -> replaceFragment(chatListFragment)
                R.id.myPage -> replaceFragment(myPageFragment)
            }
            true

        }
    }
//엑티비티에는 서포트 프레그먼트 매니저가 있다. 트랜잭션은 이작업을 시작한다고 알려주는 기능을한다.
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragment_container, fragment)
                commit()
            }
    }


}