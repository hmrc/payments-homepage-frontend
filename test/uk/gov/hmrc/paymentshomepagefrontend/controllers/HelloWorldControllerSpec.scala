/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.paymentshomepagefrontend.controllers

import org.jsoup.Jsoup
import play.api.test.Helpers.*
import play.api.test.FakeRequest
import uk.gov.hmrc.paymentshomepagefrontend.testsupport.ItSpec

class HelloWorldControllerSpec extends ItSpec {

  val systemUnderTest: HelloWorldController = app.injector.instanceOf[HelloWorldController]

  "HelloWorldController" - {
    "hello" - {
      "should return 200 with Hello world page html body" in {
        val result   = systemUnderTest.helloWorld(FakeRequest())
        status(result) shouldBe 200
        val document = Jsoup.parse(contentAsString(result))
        document.select("h1").text() shouldBe "payments-homepage-frontend"
      }
    }
  }
}
