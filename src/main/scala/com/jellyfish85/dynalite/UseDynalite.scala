package com.jellyfish85.dynalite

import java.io.InputStream

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.{ScanResult, ScanRequest}


import org.apache.commons.configuration.{PropertiesConfiguration, Configuration}

/**
  * Created by letusfly95 on 2016/02/15.
  */
object UseDynalite {

  val dynamo: DynamoDB = new DynamoDB(new AmazonDynamoDBClient(
    new ProfileCredentialsProvider())
  )

  def main(args: Array[String]): Unit = {
    val inputStream: InputStream =
      getClass.getResourceAsStream("/endpoint.properties")

    val configuration: PropertiesConfiguration =
      new PropertiesConfiguration()

    configuration.load(inputStream, "UTF8")

    val endpoint = configuration.getString("dynalite.endpoint")

    val client = new AmazonDynamoDBClient(new ProfileCredentialsProvider())
    client.setEndpoint(endpoint)

    println(client.describeTable("Movies"))

    val scanRequest: ScanRequest = new ScanRequest().withTableName("Movies")
    val result: ScanResult = client.scan(scanRequest)

    result.getItems.toArray.toList.foreach(println)

  }

}
