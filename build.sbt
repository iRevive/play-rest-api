import com.typesafe.config.ConfigFactory
import sbtdocker.staging.DefaultDockerfileProcessor
import sbtrelease.ReleaseStateTransformations._

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, sbtdocker.DockerPlugin, JavaAppPackaging, AshScriptPlugin)
  .settings(commonSettings: _*)
  .settings(buildSettings: _*)
  .settings(dockerSettings: _*)
  .settings(devDockerSettings: _*)
  .settings(releaseSettings: _*)
  .settings(codegenSettings: _*)
  .settings(
    name := Settings.name,
    libraryDependencies ++= Dependencies.root
  )

lazy val commonSettings = Seq(
  scalaVersion := Versions.scala,

  scalacOptions ++= commonOptions ++ warnOptions ++ lintOptions,

  libraryDependencies ++= Seq(specs2 % Test, guice),

  resolvers ++= Seq(
    "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
  )
)
lazy val buildSettings = Seq(
  packageName in Universal := name.value,

  mappings in Universal += {
    val conf = (resourceDirectory in Compile).value / "application.conf"
    conf -> "conf/application.conf"
  },

  bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""
)

lazy val dockerSettings = Seq(
  dockerBaseImage := "openjdk:8-alpine",

  buildOptions in docker := BuildOptions(
    removeIntermediateContainers = BuildOptions.Remove.Always,
    pullBaseImage = BuildOptions.Pull.IfMissing
  ),

  imageNames in docker := Seq(
    // Sets the latest tag
    ImageName(
      repository = name.value,
      tag = Some("latest")
    ),
    // Sets a tag that contains the project version
    ImageName(
      repository = name.value,
      tag = Some(version.value)
    )
  ),

  dockerfile in docker := {
    val appDir: File = stage.value
    val targetDir = "/app"

    new Dockerfile {
      from("openjdk:8-alpine")
      entryPoint(s"$targetDir/bin/${executableScriptName.value}")
      copy(appDir, targetDir, chown = "daemon:daemon")
    }
  }
)

lazy val devDockerSettings = {
  val dev = Configurations.config("dev")

  Seq(
    docker in dev := {
      val log = Keys.streams.value.log
      val dockerPath = (DockerKeys.dockerPath in docker).value
      val buildOptions = (DockerKeys.buildOptions in docker).value
      val stageDir = (target in docker).value
      val dockerfile = (DockerKeys.dockerfile in docker).value
      val imageNames = Seq(
        // Sets a tag that contains the current commit hash
        ImageName(
          repository = name.value,
          tag = Some(version.value + "-" + Process("git rev-parse HEAD").lines.head)
        )
      )
      sbtdocker.DockerBuild(dockerfile, DefaultDockerfileProcessor, imageNames, buildOptions, stageDir, dockerPath, log)
    }
  )
}

lazy val releaseSettings = Seq(
  releaseVersionBump := sbtrelease.Version.Bump.Next,

  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runTest,
    setReleaseVersion,
    releaseStepTask(docker in docker),
    releaseStepTask(DockerKeys.dockerPush in docker),
    commitReleaseVersion,
    tagRelease,
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)

lazy val codegenSettings = {
  lazy val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()
  lazy val genTableTask = TaskKey[Unit]("gen-tables")

  lazy val slickCodeGenTask = (baseDirectory, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
    val outputDir = (dir / "app").getPath
    val url = conf.getString("slick.dbs.default.db.url")
    val jdbcDriver =  conf.getString("slick.dbs.default.db.driver")
    val slickDriver = conf.getString("slick.dbs.default.profile").dropRight(1)
    val user = conf.getString("slick.dbs.default.db.user")
    val password = conf.getString("slick.dbs.default.db.password")
    val pkg = "db.mapping"

    val args = Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password)

    toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, args, s.log))
  }

  Seq(
    genTableTask <<= slickCodeGenTask
  )
}

lazy val commonOptions = Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding", "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfuture", // Turn on future language features.
  "-Yno-adapted-args" // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
)

lazy val warnOptions = Seq(
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-extra-implicit" // Warn when more than one implicit parameter section is defined.
)

lazy val lintOptions = Seq(
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Xlint:unsound-match", // Pattern match may not be typesafe.
  "-Ywarn-infer-any" // Warn when a type argument is inferred to be `Any`.
)