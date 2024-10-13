package com.example.radiofrance.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class RadioFranceRemoteDataSourceImplTest : BehaviorSpec({

    val mockServer = MockServer()

    lateinit var apolloClient: ApolloClient
    lateinit var dataSource: RadioFranceRemoteDataSourceImpl

    beforeTest {
        runTest {
            val serverUrl = mockServer.url()

            apolloClient = ApolloClient.Builder()
                .serverUrl(serverUrl)
                .build()

            dataSource = RadioFranceRemoteDataSourceImpl(apolloClient)
        }
    }

    given("a RadioFranceRemoteDataSourceImpl") {

        `when`("getBrands is called and the server returns a valid response") {

            then("it should return a list of BrandDomain") {
                runTest {
                    val mockResponse = MockResponse.Builder()
                        .body(
                            """
                            {
                              "data": {
                                "brands": [
                                  {"id": "1", "title": "Brand A", "baseline": "baseline A"},
                                  {"id": "2", "title": "Brand B", "baseline": "baseline B"}
                                ]
                              }
                            }
                            """.trimIndent()
                        )
                        .build()

                    mockServer.enqueue(mockResponse)

                    val result = dataSource.getBrands()

                    result shouldHaveSize 2
                    result[0].title shouldBe "Brand A"
                    result[1].title shouldBe "Brand B"
                }
            }
        }


        `when`("getShowsById is called and the server returns a valid response with simplified nodes") {

            then("it should return a list of ShowDomain") {
                runTest {
                    val mockResponse = MockResponse.Builder()
                        .body(
                            """
                            {
                              "data": {
                                "shows": {
                                  "edges": [
                                    {
                                      "node": {
                                        "title": "Des vies françaises",
                                        "diffusionsConnection": {
                                          "edges": [
                                            {
                                              "node": {
                                                "title": "Robert Birenbaum, une jeunesse parisienne en résistance - Épisode 2"
                                              }
                                            }
                                          ]
                                        }
                                      }
                                    },
                                    {
                                      "node": {
                                        "title": "Les décolonisations africaines",
                                        "diffusionsConnection": {
                                          "edges": [
                                            {
                                              "node": {
                                                "title": "L'Afrique du Sud : Nelson Mandela"
                                              }
                                            }
                                          ]
                                        }
                                      }
                                    },
                                    {
                                      "node": {
                                        "title": "La victoire en chantant",
                                        "diffusionsConnection": {
                                          "edges": [
                                            {
                                              "node": {
                                                "title": "Les hymnes de sélections (4/4) : en Angleterre, la crème de la crème"
                                              }
                                            }
                                          ]
                                        }
                                      }
                                    }
                                  ]
                                }
                              }
                            }
                            """.trimIndent()
                        )
                        .build()

                    mockServer.enqueue(mockResponse)

                    val result = dataSource.getShowsById("FRANCEINTER")

                    result shouldHaveSize 3
                    result[0].title shouldBe "Des vies françaises"
                    result[1].title shouldBe "Les décolonisations africaines"
                    result[2].title shouldBe "La victoire en chantant"
                }
            }
        }

        `when`("getBrands is called and the server returns an error") {

            then("it should return an empty list") {
                runTest {
                    val mockErrorResponse = MockResponse.Builder()
                        .statusCode(500)
                        .body(
                            """
                            {
                              "errors": [
                                {
                                  "message": "Internal Server Error"
                                }
                              ]
                            }
                            """.trimIndent()
                        )
                        .build()

                    mockServer.enqueue(mockErrorResponse)

                    val result = dataSource.getBrands()

                    result shouldBe emptyList()
                }
            }
        }

        `when`("getShowsById is called with an invalid ID and the server returns an error") {

            then("it should return an empty list") {
                runTest {
                    val mockErrorResponse = MockResponse.Builder()
                        .statusCode(400)
                        .body(
                            """
                            {
                              "errors": [
                                {
                                  "message": "Invalid station ID"
                                }
                              ]
                            }
                            """.trimIndent()
                        )
                        .build()

                    mockServer.enqueue(mockErrorResponse)

                    val result = dataSource.getShowsById("invalidId")

                    result shouldBe emptyList()
                }
            }
        }
    }
})
