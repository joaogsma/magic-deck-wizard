package org.joaogsma.ports

import java.net.HttpURLConnection
import java.net.URL

import scala.io.Source

trait HttpPort {
  /**
    * Returns the text (content) from a REST URL as a String.
    * Inspired by:
    * - https://alvinalexander.com/scala/how-to-write-scala-http-get-request-client-source-fromurl
    * - http://matthewkwong.blogspot.com/2009/09/scala-scalaiosource-fromurl-blockshangs.html
    * - http://alvinalexander.com/blog/post/java/how-open-url-read-contents-httpurl-connection-java
    *
    * The `connectTimeout` and `readTimeout` comes from the Java URLConnection
    * class Javadoc.
    *
    * @param url            The full URL to connect to.
    * @param connectTimeout Sets a specified timeout value, in milliseconds,
    *                       to be used when opening a communications link to the resource referenced
    *                       by this URLConnection. If the timeout expires before the connection can
    *                       be established, a java.net.SocketTimeoutException
    *                       is raised. A timeout of zero is interpreted as an infinite timeout.
    *                       Defaults to 5000 ms.
    * @param readTimeout    If the timeout expires before there is data available
    *                       for read, a java.net.SocketTimeoutException is raised. A timeout of zero
    *                       is interpreted as an infinite timeout. Defaults to 5000 ms.
    * @example get("http://www.example.com/getInfo")
    * @example get("http://www.example.com/getInfo", 5000)
    * @example get("http://www.example.com/getInfo", 5000, 5000)
    */
  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def get(
      url: String,
      connectTimeout: Int = 5000,
      readTimeout: Int = 5000): String = {
    val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)

    val inputStream = connection.getInputStream

    try Source.fromInputStream(inputStream).mkString
    finally if (inputStream != null) inputStream.close()
  }
}
