package tk.example.android.tenkiyoho.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tk.example.android.tenkiyoho.data.repository.WeatherRepositoryImpl
import tk.example.android.tenkiyoho.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindCountriesRepository(repository: WeatherRepositoryImpl): WeatherRepository
}