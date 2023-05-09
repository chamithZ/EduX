class Blog {
    var userId : String ? = null
    var title: String? = null
    var content: String? = null
    var author: String? = null


    constructor()

    constructor(userId: String? , title: String?, content: String?, author: String?) {
        this.userId = userId
        this.title = title
        this.content = content
        this.author = author
    }
}
