# Currency info
Interview task. Made with Retrofit, Room, Coroutines, MVVM. Uses to learn values rates and add some to favorites.

## Installation

  1. Clone this project
  2. Run `./gradlew build`

## About app
Application fetchs rates from https://exchangeratesapi.io/, basing on EUR rate. On first startup all fetched data saves to local database, which then will check for update on every startup, or, if swipe-to-refresh list on popular list fragment.
User can also save certain rates in favorites, which values will also be updated, if fetch data is latest. 
All rates lists can be sorted in 4 ways: 
  - From A-Z
  - From Z-A
  - Value ascending
  - Value descending

Default list sort is set to From A-Z. 

## Preview

[![Screenshot-20220326-143141.png](https://i.postimg.cc/RVLs5X4K/Screenshot-20220326-143141.png)](https://postimg.cc/ykWX0hJ8)
[![Screenshot-20220326-143155.png](https://i.postimg.cc/cJrX3WYB/Screenshot-20220326-143155.png)](https://postimg.cc/5HWBcZsX)
[![Screenshot-20220326-143256.png](https://i.postimg.cc/cLDF6CKJ/Screenshot-20220326-143256.png)](https://postimg.cc/JskN215w)
[![Screenshot-20220326-145650.png](https://i.postimg.cc/wTwXQP9f/Screenshot-20220326-145650.png)](https://postimg.cc/6yG49M2n)
