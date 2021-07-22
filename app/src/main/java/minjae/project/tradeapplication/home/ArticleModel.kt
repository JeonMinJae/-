package minjae.project.tradeapplication.home

data class ArticleModel(
    val sellerId: String,
    val title: String,
    val createdAt: Long,
    val content: String,
    val imageUrlList: List<String>
) {

    //firebase realtime db사용하기위해서 default가 필요하다.
    constructor(): this("", "", 0, "", listOf())

}