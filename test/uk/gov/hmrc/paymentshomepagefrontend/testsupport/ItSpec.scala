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

package uk.gov.hmrc.paymentshomepagefrontend.testsupport

import com.google.inject.{AbstractModule, Provides, Singleton}
import org.apache.pekko.stream.Materializer
import org.scalatest.freespec.AnyFreeSpecLike
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.inject.guice.{GuiceApplicationBuilder, GuiceableModule}
import play.api.test.{DefaultTestServerFactory, RunningServer}
import play.api.{Application, Mode}
import play.core.server.ServerConfig
import uk.gov.hmrc.http.test.WireMockSupport
import uk.gov.hmrc.paymentshomepagefrontend.testsupport.stubs.AuditConnectorStub

import java.time.*
import java.time.format.DateTimeFormatter
import scala.annotation.unused
import scala.concurrent.ExecutionContext

trait ItSpec extends AnyFreeSpecLike with GuiceOneServerPerSuite with WireMockSupport with RichMatchers { self =>

  implicit val ec: ExecutionContext            = scala.concurrent.ExecutionContext.Implicits.global
  implicit lazy val materializer: Materializer = app.materializer

  private val testServerPort: Int = 19001

  protected lazy val configOverrides: Map[String, Any] = Map()

  protected lazy val configMap: Map[String, Any] = Map[String, Any](
    "play.http.router"               -> "testOnlyDoNotUseInAppConf.Routes",
    "auditing.consumer.baseUri.port" -> self.wireMockPort,
    "auditing.enabled"               -> false,
    "auditing.traceRequests"         -> false,
    "internal-auth.token"            -> "testToken" // we'll use eventually
  ) ++ configOverrides

  override def beforeEach(): Unit = {
    super.beforeEach()
    AuditConnectorStub.stubForImplicitAudit()
    ()
  }

  override def afterEach(): Unit = {
    super.afterEach()
  }

  protected def applicationBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder()
    .overrides(GuiceableModule.fromGuiceModules(Seq(testModule)))
    .configure(configMap)

  override def fakeApplication(): Application = applicationBuilder.build()

  override implicit protected lazy val runningServer: RunningServer =
    TestServerFactory.start(app)

  object TestServerFactory extends DefaultTestServerFactory {
    override protected def serverConfig(app: Application): ServerConfig = {
      val sc: ServerConfig = ServerConfig(port = Some(testServerPort), sslPort = None, mode = Mode.Test, rootDir = app.path)
      sc.copy(configuration = sc.configuration.withFallback(overrideServerConfiguration(app)))
    }
  }

  private lazy val testModule: AbstractModule = new AbstractModule {

    @Provides
    @Singleton
    @unused
    def clock(): Clock = FrozenTime.clock
  }

  object FrozenTime {
    lazy val dateString: String           = "2059-11-25"
    lazy val timeString: String           = s"${dateString}T16:33:51.880"
    lazy val localDateTime: LocalDateTime = {
      // the frozen time has to be in future otherwise things will disappear from mongodb because of ttl
      LocalDateTime.parse(timeString, DateTimeFormatter.ISO_DATE_TIME)
    }
    lazy val instant: Instant             = localDateTime.toInstant(ZoneOffset.UTC)
    lazy val frozenInstant: Instant       = instant
    lazy val clock: Clock                 = Clock.fixed(frozenInstant, ZoneId.of("UTC"))
  }
}
