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

package uk.gov.hmrc.paymentshomepagefrontend.config

import uk.gov.hmrc.paymentshomepagefrontend.testsupport.ItSpec

class AppConfigSpec extends ItSpec {

  val systemUnderTest: AppConfig = app.injector.instanceOf[AppConfig]

  "AppConfig" - {

    "should have welshLanguageSupportEnabled set to false" in {
      systemUnderTest.welshLanguageSupportEnabled shouldBe false
    }

    "should build payApiUrl correctly from services config" in {
      systemUnderTest.payApiUrl shouldBe "http://localhost:9057/pay-api"
    }

    "should build paymentsHomePageUrl correctly from services config" in {
      systemUnderTest.paymentsHomePageUrl shouldBe "http://localhost:10160/payments-homepage"
    }

    "should derive signOutUrl from bas-gateway base-url when platform host is not set" in {
      systemUnderTest.signOutUrl shouldBe "http://localhost:9553/bas-gateway/sign-out-without-state"
    }

    "should read timeoutInSeconds from config as 895 seconds" in {
      systemUnderTest.timeoutInSeconds shouldBe 895
    }

    "should read countdownInSeconds from config as 120 seconds" in {
      systemUnderTest.countdownInSeconds shouldBe 120
    }
  }

}
