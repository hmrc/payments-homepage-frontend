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

import com.google.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class AppConfig @Inject() (config: Configuration, servicesConfig: ServicesConfig) {

  val welshLanguageSupportEnabled: Boolean = config.getOptional[Boolean]("features.welsh-language-support").getOrElse(false)

  val payApiUrl: String           = servicesConfig.baseUrl("pay-api") + "/pay-api"
  val paymentsHomePageUrl: String = servicesConfig.baseUrl("payments-homepage") + "/payments-homepage"

  private val platformHost: Option[String] = config.getOptional[String]("platform.frontend.host")
  private val basGatewayBaseUrl: String    = platformHost.getOrElse(config.get[String]("urls.bas-gateway.base-url"))
  val signOutUrl: String                   = s"$basGatewayBaseUrl/bas-gateway/sign-out-without-state"

  // for the countdown timer
  val timeoutInSeconds: Int   = config.get[Int]("timeoutInSeconds")
  val countdownInSeconds: Int = config.get[Int]("countdownInSeconds")

}
