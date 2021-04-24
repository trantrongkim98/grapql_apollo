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