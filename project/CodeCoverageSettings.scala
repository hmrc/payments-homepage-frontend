import sbt.Setting
import scoverage.ScoverageKeys

object CodeCoverageSettings {

  private val excludedPackages: String = Seq(
    "<empty>",
    "Reverse.*",
    "uk.gov.hmrc.BuildInfo",
    "app.*",
    "prod.*",
    ".*Routes.*",
    "testOnly.*",
    "testOnlyDoNotUseInAppConf.*",
    "uk.gov.hmrc.paymentshomepagefrontend.logging.KibanaLogger.*",
    """.*.template.*""",
    """.*\$anon.*"""
  ).mkString(";")

  private val excludedFiles: String = Seq(
    """.*.template.*"""
  ).mkString(";")

  val settings: Seq[Setting[?]] = Seq(
    ScoverageKeys.coverageExcludedPackages := excludedPackages,
    ScoverageKeys.coverageExcludedFiles    := excludedFiles,
    ScoverageKeys.coverageMinimumStmtTotal := 100,
    ScoverageKeys.coverageFailOnMinimum    := false,
    ScoverageKeys.coverageHighlighting     := true
  )
}
