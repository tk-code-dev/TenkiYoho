package tk.example.android.tenkiyoho.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tk.example.android.tenkiyoho.domain.usecase.WeatherUseCase
import tk.example.android.tenkiyoho.domain.usecase.WeatherUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    internal abstract fun bindWeatherUseCase(useCaseImpl: WeatherUseCaseImpl): WeatherUseCase
}