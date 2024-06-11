package com.eevajonna.flagexplorer.data

import com.apollographql.apollo3.ApolloClient

const val API_ENDPOINT = "https://pride.dev/api/graphql"

val apolloClient =
    ApolloClient.Builder()
        .serverUrl(API_ENDPOINT)
        .build()
