package com.klar.shared.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

enum class ExerciseType {
    MULTIPLE_CHOICE,
    WORD_BUBBLES,
    LISTEN_AND_TYPE
}

enum class Level {
    A1, A2, B1, B2, C1, C2
}

data class Exercise(
    val id: String,
    val type: ExerciseType,
    val prompt: String,
    val audioPhrase: String?,
    val correctAnswer: String,
    val options: List<String>,
    val hint: String?,
    val xpReward: Int = 10
)

data class Lesson(
    val lessonId: String,
    val level: Level,
    val title: String,
    val exercises: List<Exercise>
)

interface LessonRepository {
    suspend fun getLessons(): List<Lesson>
    fun getLessonsStream(): Flow<List<Lesson>>
    suspend fun getLessonById(id: String): Lesson?
}

class InMemoryLessonRepository : LessonRepository {

    private val fakeLessons = listOf(
        Lesson(
            lessonId = "lesson_1",
            level = Level.A1,
            title = "Introduction to Greetings",
            exercises = listOf(
                Exercise(
                    id = "ex_1_1",
                    type = ExerciseType.MULTIPLE_CHOICE,
                    prompt = "How do you say 'Hello' in German?",
                    audioPhrase = "Hallo",
                    correctAnswer = "Hallo",
                    options = listOf("Hallo", "Tschüss", "Danke", "Bitte"),
                    hint = "It sounds similar to English",
                    xpReward = 10
                ),
                Exercise(
                    id = "ex_1_2",
                    type = ExerciseType.WORD_BUBBLES,
                    prompt = "Translate: 'How are you?'",
                    audioPhrase = "Wie geht es dir",
                    correctAnswer = "Wie geht es dir",
                    options = listOf("Wie", "geht", "es", "dir", "ist", "du", "gut", "mir"),
                    hint = "Formal way to ask",
                    xpReward = 15
                )
            )
        ),
        Lesson(
            lessonId = "lesson_2",
            level = Level.A1,
            title = "Basic Numbers",
            exercises = listOf(
                Exercise(
                    id = "ex_2_1",
                    type = ExerciseType.MULTIPLE_CHOICE,
                    prompt = "What is 'one' in German?",
                    audioPhrase = "eins",
                    correctAnswer = "eins",
                    options = listOf("eins", "zwei", "drei", "vier"),
                    hint = "Counting from 1 to 10",
                    xpReward = 10
                ),
                Exercise(
                    id = "ex_2_2",
                    type = ExerciseType.WORD_BUBBLES,
                    prompt = "Translate: 'I count to five'",
                    audioPhrase = "Ich zähle bis fünf",
                    correctAnswer = "Ich zähle bis fünf",
                    options = listOf("Ich", "zähle", "bis", "fünf", "du", "wir", "drei", "zehn"),
                    hint = "Basic counting",
                    xpReward = 15
                )
            )
        ),
        Lesson(
            lessonId = "lesson_3",
            level = Level.A1,
            title = "Common Phrases",
            exercises = listOf(
                Exercise(
                    id = "ex_3_1",
                    type = ExerciseType.MULTIPLE_CHOICE,
                    prompt = "How do you say 'Thank you' in German?",
                    audioPhrase = "Danke",
                    correctAnswer = "Danke",
                    options = listOf("Bitte", "Danke", "Ja", "Nein"),
                    hint = "Being polite",
                    xpReward = 10
                ),
                Exercise(
                    id = "ex_3_2",
                    type = ExerciseType.WORD_BUBBLES,
                    prompt = "Translate: 'Where is the bathroom?'",
                    audioPhrase = "Wo ist die Toilette",
                    correctAnswer = "Wo ist die Toilette",
                    options = listOf("Wo", "ist", "die", "Toilette", "das", "Bad", "hier", "dort"),
                    hint = "Useful question when traveling",
                    xpReward = 15
                )
            )
        )
    )

    override suspend fun getLessons(): List<Lesson> {
        return fakeLessons
    }

    override fun getLessonsStream(): Flow<List<Lesson>> = flow {
        emit(fakeLessons)
    }

    override suspend fun getLessonById(id: String): Lesson? {
        return fakeLessons.find { it.lessonId == id }
    }
}
