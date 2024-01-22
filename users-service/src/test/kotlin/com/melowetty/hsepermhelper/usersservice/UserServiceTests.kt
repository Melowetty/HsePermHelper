package com.melowetty.hsepermhelper.usersservice

import com.melowetty.hsepermhelper.usersservice.dto.TelegramInfoDto
import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.mapper.UserMapper
import com.melowetty.hsepermhelper.usersservice.model.Settings
import com.melowetty.hsepermhelper.usersservice.model.TelegramInfo
import com.melowetty.hsepermhelper.usersservice.model.User
import com.melowetty.hsepermhelper.usersservice.repository.UserRepository
import com.melowetty.hsepermhelper.usersservice.service.impl.UserServiceImpl
import com.melowetty.languagessupportlibrary.model.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.argThat
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
class UserServiceTests {
    @Mock
    lateinit var userRepository: UserRepository
    @Mock
    lateinit var userMapper: UserMapper

    @InjectMocks
    lateinit var userService: UserServiceImpl

    @Test
    fun `test get all users`() {
        val firstUser = User(
            id = "124-124-124-125",
            settings = Settings(
                language = Language.ENGLISH,
            ),
            telegramInfo = TelegramInfo(
                telegramId = 1,
                firstName = "Denis",
                lastName = null,
                username = "melowetty"
            )
        )
        val secondUser = User(
            settings = Settings(
                language = Language.RUSSIAN,
            ),
            telegramInfo = TelegramInfo(
                telegramId = 2,
                firstName = null,
                lastName = null,
                username = null
            )
        )
        `when`(userRepository.findAll()).thenReturn(Flux.just(firstUser, secondUser))

        `when`(userMapper.toDto(argThat { user ->
            user != null &&
            user == firstUser.copy(creationDate = user.creationDate)
        })).thenAnswer {
            val user = it.getArgument<User>(0)
            UserDto(
                id = "124-124-124-125",
                creationDate = user.creationDate,
                settings = mapOf("language" to "ENGLISH"),
                telegramInfo = TelegramInfoDto(
                    telegramId = 1,
                    username = "melowetty",
                    firstName = "Denis",
                    lastName = null,
                )
            )

        }

        `when`(userMapper.toDto(argThat { user ->
            user != null &&
            user == secondUser.copy(id = user.id, creationDate = user.creationDate)
        })).thenAnswer {
            val user = it.getArgument<User>(0)
            UserDto(
                id = user.id,
                creationDate = user.creationDate,
                settings = mapOf("language" to "RUSSIAN"),
                telegramInfo = TelegramInfoDto(
                    telegramId = 2,
                    username = null,
                    firstName = null,
                    lastName = null,
                )
            )
        }

        val users: List<UserDto> = userService.getUsers().collectList().block()?.toList() ?: listOf()
        assertEquals(2, users.size)

        assertEquals("124-124-124-125", users[0].id)
        assertEquals(Language.ENGLISH, Language.fromString(users[0].settings["language"].toString()))
        assertEquals(1, users[0].telegramInfo.telegramId)
        assertEquals("Denis", users[0].telegramInfo.firstName)
        assertEquals(null, users[0].telegramInfo.lastName)
        assertEquals("melowetty", users[0].telegramInfo.username)

        assert(users[1].id.isNotBlank())
        assertEquals(Language.RUSSIAN, Language.fromString(users[1].settings["language"].toString()))
        assertEquals(2, users[1].telegramInfo.telegramId)
        assertEquals(null, users[1].telegramInfo.firstName)
        assertEquals(null, users[1].telegramInfo.lastName)
        assertEquals(null, users[1].telegramInfo.username)
    }
}