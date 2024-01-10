import com.melowetty.languagessupportlibrary.utils.TranslateUtils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TranslateUtilsTest {
    @Test
    fun `test translate cyrillic to transliterate`() {
        val actualRussian = "Привет!"

        val expectedTransliterate = "Privet!"
        val actualTransliterate = TranslateUtils.translateToRussian(actualRussian)

        assertEquals(expectedTransliterate, actualTransliterate)
    }
}