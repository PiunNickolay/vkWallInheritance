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
data class Doc(val id: Int, val ownerId: Int, val title: String, val size: Int, val ext: String)
data class DocAttachment(val doc: Doc) : Attachment("doc")
data class Link(val url: String, val title: String, val description: String)
data class LinkAttachment(val link: Link) : Attachment("link")

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
                    date = storedPost.date,
                    likes = storedPost.likes,
                    attachments = storedPost.attachments
                )
                return true
            }
        }
        return false
    }
}

fun main() {
    val photo = Photo(1, 1, "photo1", "photo2")
    val photoAttachment = PhotoAttachment(photo)

    val video = Video(1, 1, "Video1", 300)
    val videoAttachment = VideoAttachment(video)

    val audio = Audio(1, 1, "Audio1", "Description", 200)
    val audioAttachment = AudioAttachment(audio)

    val doc = Doc(1, 1, "Doc1", 1024, "txt")
    val docAttachment = DocAttachment(doc)

    val link = Link("https://netology.ru", "ссылка2", "Ссылка на Нетологию")
    val linkAttachment = LinkAttachment(link)

    val post = Post(
        id = 0,
        ownerId = 1,
        fromId = 1,
        createdBy = 1,
        date = 20220628,
        text = "Пост с различными вложениями",
        replyOwnerId = 0,
        replyPostId = 0,
        friendsOnly = false,
        postType = "post",
        attachments = arrayOf(photoAttachment, videoAttachment, audioAttachment, docAttachment, linkAttachment)
    )

    val addedPost = WallService.add(post)
    println(addedPost)

    val updatePost = addedPost.copy(text = "Обновленный пост с различными вложениями")
    val result = WallService.update(updatePost)
    println(result)
}
