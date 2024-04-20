package org.easy.ai.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.easy.ai.data.aimodel.GeminiModelRepository
import org.easy.ai.datastore.UserPreferencesDataSource
import org.easy.ai.model.ModelPlatform
import javax.inject.Inject

class MultiModalGeneratingUseCase @Inject internal constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
    private val geminiModelRepository: GeminiModelRepository,
) {
    operator fun invoke(prompt: String, images: List<ByteArray>): Flow<String> {
        return userPreferencesDataSource.userData.map { userData ->
            val apiKey = userData.apiKeys[ModelPlatform.GEMINI.name] ?: throw IllegalStateException(
                "api key not set yet."
            )
            geminiModelRepository.generateContent(apiKey, prompt, images)
        }
    }
}