import com.melowetty.languagessupportlibrary.model.Language
import com.melowetty.languagessupportlibrary.model.MapperWithLanguage
import com.melowetty.languagessupportlibrary.model.Translatable
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MapperWithLanguageTest {
    @Test
    fun `get mapper by language, which contains in instances `() {
        val ruMapper = TestTranslatableRu()
        val enMapper = TestTranslatableEn()
        val mapper = TestMapperWithLanguage(
            instances =  listOf(ruMapper, enMapper),
            defaultInstance = enMapper
        )
        val actualMapper = mapper.getMapper(Language.RUSSIAN)
        val expectedMapper = ruMapper
        assertEquals(expectedMapper, actualMapper)
    }

    @Test
    fun `get mapper by language, which is not contains in instances `() {
        val enMapper = TestTranslatableEn()
        val mapper = TestMapperWithLanguage(
            instances =  listOf(),
            defaultInstance = enMapper
        )
        val actualMapper = mapper.getMapper(Language.RUSSIAN)
        val expectedMapper = enMapper
        assertEquals(expectedMapper, actualMapper)
    }


    class TestMapperWithLanguage(
        instances: List<TestTranslatable>,
        defaultInstance: TestTranslatable
    ): MapperWithLanguage<TestTranslatable>(
        instances = instances,
        defaultInstance = defaultInstance,
    )
    interface TestTranslatable: Translatable
    class TestTranslatableRu: TestTranslatable {
        override fun getLanguage(): Language {
            return Language.RUSSIAN
        }
    }
    class TestTranslatableEn: TestTranslatable {
        override fun getLanguage(): Language {
            return Language.ENGLISH
        }
    }
}