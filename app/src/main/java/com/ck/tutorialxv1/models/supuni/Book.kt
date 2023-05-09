package com.ck.tutorialxv1.models.supuni

class Book {
        var userId : String ? = null
        var title : String? = null
        var author: String? = null
        var quantity: String? = null
        var contact: String? = null


        constructor()

        constructor(userId: String?, title: String? , author: String?, quantity: String?, contact: String?) {
            this.userId = userId
            this.title = title
            this.author = author
            this.quantity = quantity
            this.contact = contact
        }


}