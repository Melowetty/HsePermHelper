import com.melowetty.languagessupportlibrary.model.Language
import com.melowetty.languagessupportlibrary.utils.LanguageUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LanguageUtilsTest {
    @Test
    fun `test get language from headers`() {
        val headersWithLanguage = mapOf("X-Language" to "ENGLISH")
        val headersWithoutLanguage = mapOf("Other-Header" to "value")

        val languageFromHeaders = LanguageUtils.getLanguageFromHeaders(headersWithLanguage)
        val defaultLanguage = LanguageUtils.getLanguageFromHeaders(headersWithoutLanguage)

        assertEquals(Language.ENGLISH, languageFromHeaders)
        assertEquals(Language.RUSSIAN, defaultLanguage)
    }

    @Test
    fun `test get language from headers when header in lowercase`() {
        val headers = mapOf("x-language" to "ENGLISH")
        val languageFromHeaders = LanguageUtils.getLanguageFromHeaders(headers)

        assertEquals(Language.ENGLISH, languageFromHeaders)
    }

    @Test
    fun `test get language from headers when language in lowercase`() {
        val headers = mapOf("X-Language" to "russian")
        val languageFromHeaders = LanguageUtils.getLanguageFromHeaders(headers)

        assertEquals(Language.RUSSIAN, languageFromHeaders)
    }

    @Test
    fun `test get default language when header key is contains but value not contains in languages`() {
        val headers = mapOf("X-Language" to "UNKNOWN")
        val languageFromHeaders = LanguageUtils.getLanguageFromHeaders(headers)

        assertEquals(Language.RUSSIAN, languageFromHeaders)
    }
}