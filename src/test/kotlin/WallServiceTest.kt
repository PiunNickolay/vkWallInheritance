import org.junit.Assert.*
import org.junit.Test
import ru.netology.*

class WallServiceTest {

    @Test
    fun addPost() {
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
        assertEquals(1, addedPost.id)
        assertEquals("Пост с различными вложениями", addedPost.text)
        assertEquals(5, addedPost.attachments?.size)
        assertEquals("photo", addedPost.attachments?.get(0)?.type)
        assertEquals("video", addedPost.attachments?.get(1)?.type)
        assertEquals("audio", addedPost.attachments?.get(2)?.type)
        assertEquals("doc", addedPost.attachments?.get(3)?.type)
        assertEquals("link", addedPost.attachments?.get(4)?.type)
    }

    @Test
    fun updateExistingPost() {
        val photo = Photo(1, 1, "photo1", "photo2")
        val photoAttachment = PhotoAttachment(photo)

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
            attachments = arrayOf(photoAttachment)
        )

        val addedPost = WallService.add(post)
        val updatePost = addedPost.copy(text = "Обновленный пост с фото")

        val result = WallService.update(updatePost)
        assertTrue(result)

        // Ensure the post was updated correctly
        val updatedPost = WallService.posts.first { it.id == updatePost.id }
        assertEquals("Обновленный пост с фото", updatedPost.text)
        assertEquals(1, updatedPost.attachments?.size)
        assertEquals("photo", updatedPost.attachments?.get(0)?.type)
    }

    @Test
    fun updateNonExistingPost() {
        val nonExistingPost = Post(
            id = 999,
            ownerId = 1,
            fromId = 1,
            createdBy = 1,
            date = 20220628,
            text = "Этот пост не существует",
            replyOwnerId = 0,
            replyPostId = 0,
            friendsOnly = false,
            postType = "post",
            attachments = emptyArray()
        )

        val result = WallService.update(nonExistingPost)
        assertFalse(result)
    }
}
