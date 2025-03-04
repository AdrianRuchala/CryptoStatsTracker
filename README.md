# CryptoStatsTracker

CryptoStatsTracker is a application built with Kotlin and Jetpack Compose, using the MVI 
(Model-View-Intent) architectural pattern that allows users to track
cryptocurrency prices in real-time. The app provides an easy way to browse popular crypto coins,
view detailed information, and follow their price changes.

Users can log in using their Google account, add coins to their favorites list, and track
their favorite cryptocurrencies in one place. All favorite coins IDs, are stored in Firebase
Realtime Database.

Crypto data is fetched from the CoinGecko API: https://api.coingecko.com/.

## Technologies Used:

- Kotlin
- Jetpack Compose
- Material Design
- Navigation Compose
- Hilt
- Retrofit
- Friebase Authentication
- Firebase Realtime Database
- OkHttp
- Moshi
- Glide (Landscapist)
- ViewModel
