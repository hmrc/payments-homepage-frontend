import sbt.*

object AppDependencies {

  private val bootstrapVersion        = "10.9.0"
  private val playFrontendHmrcVersion = "13.17.0"
  private val catsVersion             = "3.7.1"

  val compile: Seq[ModuleID] = Seq[ModuleID](
    "uk.gov.hmrc"   %% "bootstrap-frontend-play-30" % bootstrapVersion,
    "uk.gov.hmrc"   %% "play-frontend-hmrc-play-30" % playFrontendHmrcVersion,
    "org.typelevel" %% "cats-effect"                % catsVersion
  )

  private val jsoupVersion = "1.24.1"

  val test: Seq[ModuleID] = Seq[ModuleID](
    "uk.gov.hmrc" %% "bootstrap-test-play-30" % bootstrapVersion,
    "org.jsoup"    % "jsoup"                  % jsoupVersion
  ).map(_ % Test)

  val it: Seq[ModuleID] = Seq.empty[ModuleID]
}
