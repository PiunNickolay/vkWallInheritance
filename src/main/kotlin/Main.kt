package ru.netology

data class Likes(var count: Int)
data class Post(
    val id: Int,
    val ownerId: Int?,
    val fromId: Int?,
    val createdBy: Int?,
    val date: Int?,
    val text: String?,
    val replyOwnerId: Int?,
    val replyPostId: Int?,
    val friendsOnly: Boolean,
    val postType: String,
    val likes: Likes = Likes(0),
    val attachments: Array<Attachment>? = null
)
abstract class Attachment(val type: String)

data class Photo(val id: Int, val ownerId: Int, val photo130: String, val photo604: String)
data class PhotoAttachment(val photo: Photo) : Attachment("photo")

data class Video(val id: Int, val ownerId: Int, val title: String, val duration: Int)
data class VideoAttachment(val video: Video) : Attachment("video")

data class Audio(val id: Int, val ownerId: Int, val title: String, val description: String, val duration: Int)
data class AudioAttachment(val audio: Audio) : Attachment("audio")
object WallService {
    var posts = emptyArray<Post>()
    private var nextId = 1

    fun add(post: Post): Post {
        val newPost = post.copy(id = nextId++)
        posts += newPost
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, storedPost) in posts.withIndex()) {
            if (storedPost.id == post.id) {
                posts[index] = post.copy(
                    ownerId = storedPost.ownerId,
                    fromId = storedPost.fromId,
                    createdBy = storedPost.createdBy,
                    date = storedPost.date
                )
                return true
            }
        }
        return false
    }
}

fun main() {
    val photo = Photo(1, 1, "photo1", "photo2")
    val attachment = PhotoAttachment(photo)

    val post = Post(
        id = 0,
        ownerId = 1,
        fromId = 1,
        createdBy = 1,
        date = 20220628,
        text = "Пост с фотографией",
        replyOwnerId = 0,
        replyPostId = 0,
        friendsOnly = false,
        postType = "post",
        attachments = arrayOf(attachment)
    )

    val addedPost = WallService.add(post)
    println(addedPost)

    val updatePost = addedPost.copy(text = "Обновление поста с фото")
    val result = WallService.update(updatePost)
    println(result)
}