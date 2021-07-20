package minjae.project.tradeapplication.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import minjae.project.tradeapplication.R
import minjae.project.tradeapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    // 먼저 gradle에서 뷰 바인딩해준다. 전역번수로 한 이유는 사용할때 말고는 null 해줘야 하기 때문이다.
    private var binding: FragmentHomeBinding? = null
    private lateinit var articleAdapter : ArticleAdapter

    //엑티비티에서는 oncreateview에서 했지만 프레그먼트에서는 onviewcreated에서 한다.
    // 지역변수 안에서 실행하고 전역변수 binding을 사용하여 null을 하기도 한다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fragment_home xml을 바인딩하는코드
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding=fragmentHomeBinding

        articleAdapter = ArticleAdapter()
        //밑에는 파일을 넣기전에 작동 되는지 모르기 때문에 임의로 넣어준 코드이다.
        articleAdapter.submitList(mutableListOf<ArticleModel>()
            .apply {
            add(ArticleModel("0","aaaa",1000000, "5000원", ""))
            add(ArticleModel("1","bbb",2000000, "90만원", ""))

        })

//엑티비티는 context가 될수있지만 fragment에서는 될수없어서 getContext 함수를 사용해서 context를 가져와야한다. 근데 코틀린에서는 get생략가능하니까 context를 입력해준다.
        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter

    }
}