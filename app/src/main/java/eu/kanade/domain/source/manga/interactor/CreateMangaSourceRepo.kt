package eu.kanade.domain.source.manga.interactor

import eu.kanade.domain.source.service.SourcePreferences
import tachiyomi.core.preference.plusAssign

class CreateMangaSourceRepo(private val preferences: SourcePreferences) {

    fun await(name: String): Result {
        // Do not allow invalid formats
        if (!name.matches(repoRegex) || name.startsWith(OFFICIAL_REPO_BASE_URL)) {
            return Result.InvalidUrl
        }

        preferences.mangaExtensionRepos() += name.substringBeforeLast("/index.min.json")

        return Result.Success
    }

    sealed interface Result {
        data object InvalidUrl : Result
        data object Success : Result
    }
}

const val OFFICIAL_REPO_BASE_URL = "https://raw.githubusercontent.com/tachiyomiorg/extensions/repo"
private val repoRegex = """^https://.*/index\.min\.json$""".toRegex()
