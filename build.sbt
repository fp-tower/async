import Dependencies._

lazy val baseSettings: Seq[Setting[_]] = Seq(
  scalaVersion := "2.13.0",
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:existentials",
    "-language:postfixOps",
    "-unchecked",
    "-Ywarn-value-discard"
  ),
  addCompilerPlugin(kindProjector),
  libraryDependencies ++= Seq(
    cats,
    catsFree,
    catsEffect,
    refined,
    circe,
    scalacheck,
    discipline,
    disciplineTest % Test
  )
)

lazy val async = project
  .in(file("."))
  .settings(moduleName := "async")
  .settings(baseSettings: _*)
  .aggregate(exercises, slides)
  .dependsOn(exercises, slides)

lazy val exercises = project
  .settings(moduleName := "async-exercises")
  .settings(baseSettings: _*)

lazy val slides = project
  .dependsOn(exercises)
  .settings(moduleName := "async-slides")
  .settings(baseSettings: _*)
  .settings(
    tutSourceDirectory := baseDirectory.value / "tut",
    tutTargetDirectory := baseDirectory.value / "docs"
  )
  .enablePlugins(TutPlugin)


addCommandAlias("testAnswers", "testOnly *AnswersTest")

addCommandAlias("testExercises1", "testOnly sideeffect.*ExercisesTest")
