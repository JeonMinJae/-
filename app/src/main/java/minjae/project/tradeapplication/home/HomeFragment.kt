package minjae.project.tradeapplication.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import minjae.project.tradeapplication.DBKey.Companion.DB_ARTICLES
import minjae.project.tradeapplication.R
import minjae.project.tradeapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    // 먼저 gradle에서 뷰 바인딩해준다. 전역번수로 한 이유는 사용할때 말고는 null 해줘야 하기 때문이다.
    private var binding: FragmentHomeBinding? = null
    private lateinit var articleAdapter : ArticleAdapter
    private lateinit var articleDB : DatabaseReference

    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ChildEventListener{
        // article 모델을 snapshot 하나하나가 article 모델이므로 arcile model로 치환해서 어댑트에 submit으로
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }
        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }


    //엑티비티에서는 oncreateview에서 했지만 프레그먼트에서는 onviewcreated에서 한다.
    // 지역변수 안에서 실행하고 전역변수 binding을 사용하여 null을 하기도 한다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fragment_home xml을 바인딩하는코드
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding=fragmentHomeBinding

        articleList.clear() // 뷰만 초기화된거지 홈프레그먼트가 초기화되지 않으면 계속 데이터가 쌓이기때문에 해줘야한다.

        articleDB = Firebase.database.reference.child(DB_ARTICLES)

        articleAdapter = ArticleAdapter()
        //밑에는 파일을 넣기전에 작동 되는지 모르기 때문에 임의로 넣어준 코드이다.
        /*articleAdapter.submitList(mutableListOf<ArticleModel>()
            .apply {
            add(ArticleModel("0","aaaa",1000000, "5000원", ""))
            add(ArticleModel("1","bbb",2000000, "90만원", ""))

        })*/

//엑티비티는 context가 될수있지만 fragment에서는 될수없어서 getContext 함수를 사용해서 context를 가져와야한다. 근데 코틀린에서는 get생략가능하니까 context를 입력해준다.
        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter

        fragmentHomeBinding.addFloatingButton.setOnClickListener{
            val intent = Intent(requireContext(), AddArticleActivity::class.java) //프레그먼트라 this 불가해서 require사용
            startActivity(intent)
            /*//todo 로그인 기능 구현후 사용
            if(auth.currentUser != null) {
                val intent = Intent(requireContext(), AddArticleActivity::class.java) //프레그먼트라 this 불가해서 require사용
                startActivity(intent)
            } else{
                Snackbar.make(view, "로그인후 사용해주세요", Snackbar.LENGTH_LONG).show()
            }*/
        }

        //addChildEventListener는 한번 등록하면 이벤트 발생할때마다 등록된다.
        // onviewcreate마다 붙이면 중복이 되기때문에 리스너를 전역에 하고 뷰크레이트 될때마다 attach해준다. destory될때마다 remove해주게 만든다.
        articleDB.addChildEventListener(listener)
    }

    override fun onResume() {
        super.onResume()

        articleAdapter.notifyDataSetChanged() //뷰 다시그림
    }

    override fun onDestroy() {
        super.onDestroy()

        articleDB.removeEventListener(listener)
    }


}