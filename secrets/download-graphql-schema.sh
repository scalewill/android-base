#!/bin/bash
./gradlew downloadApolloSchema -Pcom.apollographql.apollo.endpoint=https://rails-base-graphql-api.herokuapp.com/graphql -Pcom.apollographql.apollo.schema=src/main/graphql/com/scalewill/android/schema.json

./gradlew generateApolloSources
