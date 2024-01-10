import com.melowetty.languagessupportlibrary.model.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LanguageTest {
    @Test
    fun `test Language fromString`() {
        val russian = Language.fromString("RUSSIAN")
        val english = Language.fromString("ENGLISH")
        val englishWithLowerCase = Language.fromString("english")
        val unknown = Language.fromString("UNKNOWN")

        assertEquals(Language.RUSSIAN, russian)
        assertEquals(Language.ENGLISH, english)
        assertEquals(Language.ENGLISH, englishWithLowerCase)
        assertEquals(null, unknown)
    }
}