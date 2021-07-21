package minjae.project.tradeapplication.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import minjae.project.tradeapplication.databinding.ItemArticleBinding
import java.text.SimpleDateFormat
import java.util.*

//ListaAdapter 임폴트시 android인지 androidx.recyclerview인지 잘 봐라
class ArticleAdapter(val onItemClicked: (ArticleModel)-> Unit): ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(diffUtil) {

    //앞에있는 val binding에 root를 주어서 뷰를 초기화해준다. inner클래스는 클래스안에 있는 클래스라는 의미
    //gradle에서 바인딩을 활성화했기 때문에  ItemArticleBinding 명은 xml의 item_article에다가 뒤에 binding이 추가된것이다.
    // 바인딩을 한 이유가 안전하게 레이아웃내의 뷰를 참조하기 위해서이다. findviewbyid와 차이점은 직접참조하기때문에 null안전성을 가진다.
    inner class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(articleModel: ArticleModel){

            //simple dateformat을 사용하여 long형으로 정의한 datetext를 바꾸었다. 그리고 simpledateformat을 사용하기위해 date형으로 바꾸어주었다.
            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(articleModel.createdAt)

            binding.titleTextView.text = articleModel.title
            binding.dateTextView.text = format.format(date).toString()
            binding.priceTextView.text = articleModel.price

            //glide사용 하는데 if는 이미지를 안올리는 경우가 있을수 있어서
            if (articleModel.imageUrl.isNotEmpty()) {
                Glide.with(binding.thumbnailImageView)
                    .load(articleModel.imageUrl)
                    .into(binding.thumbnailImageView)
            }
            //root는 한 항목 전체
            binding.root.setOnClickListener{
                onItemClicked(articleModel)
            }
        }
    }

    // listadapter를 상속했기 때문에 onCreateViewHolder와 onBindViewHolder는 필수로 구현해야한다.
    //inflate는 layoutinflater에서 가져온다. 그리고 adapter라서 context를 this로 할수없기에 context는 viewgroup에 있는 parent를 가져온다. 그리고 2번째는 parent에 붙여주겠다(어디에 붙을지).3번째는(붙일지말지)
    //ItemArticleBinding가 ViewHolder에 감싸져있지 않아서 ViewHolder로 감싸서 return해준다.
    // onCreateViewHolder는 미리 만들어진 뷰 홀더가 없을경우에 새로 생성하는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    //onBindViewHolder는 실제로 뷰홀더가 뷰에 그려지게되면 data를 바인드(그려주는)하는 함수다. *뷰홀더는 미리만들어진 뷰를 의미 + recycleview는 이러한 만들어진것들에 데이터를 넣는것
    //listadapter 의 데이터의 리스트는 currentlist라는 이름으로 저장되어있는데 그 포지션에 있는걸 꺼내서 저장한다는 의미
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    //diffutil은 recycler뷰가 뷰의 포지션이 변경되었을때 새로운 값을 할당할지말지 기준이있는데 같은 값이 올라오면 이미 할당되어있기때문에 다시 할당할필요없기때문에 판단하는 기능을가진다.
    // companion object는 자바의 static과 유사한데 동반하는 객채이다.
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ArticleModel>(){

            //현재 노출되어있는 아이템과 새로운 아이템이 같은지를 비교 주로 고유한 키값 비교한다. 여기선 생성시간이 다르다는가정으로하고 create at을 고유키로 정함
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }

            //현재 노출되어있는 아이템들과 새로운아이템의 = 비교
            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}