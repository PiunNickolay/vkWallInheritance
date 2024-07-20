import org.junit.Test

import org.junit.Assert.*
import ru.netology.Photo
import ru.netology.PhotoAttachment
import ru.netology.Post
import ru.netology.WallService

class WallServiceTest {
    @Test
    fun testAddPost() {
        val post = Post(
            id = 0,
            ownerId = 1,
            fromId = 1,
            createdBy = 1,
            date = 20220628,
            text = "Hello, world!",
            replyOwnerId = 0,
            replyPostId = 0,
            friendsOnly = false,
            postType = "post"
        )

        val addedPost = WallService.add(post)
        assertNotEquals(0, addedPost.id)
        assertEquals("Hello, world!", addedPost.text)
    }

    @Test
    fun testUpdatePost() {
        val post = Post(
            id = 0,
            ownerId = 1,
            fromId = 1,
            createdBy = 1,
            date = 20220628,
            text = "Hello, world!",
            replyOwnerId = 0,
            replyPostId = 0,
            friendsOnly = false,
            postType = "post"
        )

        val addedPost = WallService.add(post)
        val updatePost = addedPost.copy(text = "Hello, Kotlin!")

        val result = WallService.update(updatePost)
        assertTrue(result)
        assertEquals("Hello, Kotlin!", WallService.posts.last().text)
    }

    @Test
    fun testAddPostWithAttachment() {
        val photo = Photo(1, 1, "photo1", "photo2")
        val attachment = PhotoAttachment(photo)
        val post = Post(
            id = 0,
            ownerId = 1,
            fromId = 1,
            createdBy = 1,
            date = 20220628,
            text = "Пост с фото",
            replyOwnerId = 0,
            replyPostId = 0,
            friendsOnly = false,
            postType = "post",
            attachments = arrayOf(attachment)
        )

        val addedPost = WallService.add(post)
        assertNotNull(addedPost.attachments)
        assertEquals(1, addedPost.attachments?.size)
        assertEquals("photo", addedPost.attachments?.get(0)?.type)
    }
}