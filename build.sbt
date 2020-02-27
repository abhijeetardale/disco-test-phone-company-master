lazy val phoneCompany = (project in file(".")).settings(
  Seq(
    name := "disco-test-phone-company",
    version := "1.0",
    scalaVersion := "2.12.3"
  )
)

mainClass in (Compile, run) := Some("com.phone.Main")

libraryDependencies ++= Seq(
  "com.google.inject"   % "guice"                    % "4.2.2",
  "com.lightbend.akka" %% "akka-stream-alpakka-csv"  % "1.1.2",
  "com.typesafe.akka"  %% "akka-actor"               % "2.5.17",
  "org.scalatest"      %% "scalatest"                % "3.0.0"  % "test",
  "org.scalacheck"     %% "scalacheck"               % "1.14.0" % "test"
)
