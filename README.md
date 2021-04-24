# Graphql Demo

- [Document](https://www.apollographql.com/docs/android/)

## Installing lib

- build.gradle

```gradle
dependencies {
        ...
        classpath "com.apollographql.apollo:apollo-gradle-plugin:2.5.5"
        ...
    }
```

- app/build.gradle

```gradle
    apply plugin: "com.apollographql.apollo"
    
    dependencies {

    ...
    implementation("androidx.security:security-crypto:1.0.0-rc02")
    /// add lib here
    implementation "com.apollographql.apollo:apollo-runtime:2.5.5"
    // Coroutines extensions for easier asynchronicity handling
    implementation "com.apollographql.apollo:apollo-coroutines-support:2.5.5"
}

/// add here
apollo {
    // instruct the compiler to generate Kotlin models
    generateKotlinModels.set(true)
}

```


## Installing

- install schema.json

 ```shell script
   ./gradlew downloadApolloSchema \
   --endpoint="https://your.domain/graphql/endpoint" \
   --schema="src/main/graphql/com/example/schema.json"
 ```
- If your GraphQL endpoint requires authentication, you can pass custom HTTP headers:

```shell script
  ./gradlew downloadApolloSchema \
  --endpoint="https://your.domain/graphql/endpoint" \
  --schema="app/src/main/graphql/domain" \
  --header="Authorization: Bearer $TOKEN"
```

- **requires --schema="[app/src/main/graphql/]domain"**

- example:
  ```shell script
  mkdir -p app/src/main/graphql/io/tieudan/graphql/
./gradlew :app:downloadApolloSchema --endpoint='https://apollo-fullstack-tutorial.herokuapp.com/' --schema='app/src/main/graphql/io/tieudan/graphql/schema.json'

```

## Usage

- 1. tạo 1 filename.graphql trong app/src/main/graphql/packagename/filename.graphql
- 2. build project
- 3. use
    ```kotlin
    private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {
    check(Looper.myLooper() == Looper.getMainLooper()) {
        "Only the main thread can get the apolloClient instance"
    }

    if (instance != null) {
        return instance!!
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor(context))
        .build()

    instance = ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .subscriptionTransportFactory(WebSocketSubscriptionTransport.Factory("wss://apollo-fullstack-tutorial.herokuapp.com/graphql", okHttpClient))
        .okHttpClient(okHttpClient)
        .build()

    return instance!!
}

private class AuthorizationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", User.getToken(context) ?: "")
            .build()

        return chain.proceed(request)
    }
}
```