name          := "SceneryScalaExamples"
version       := "0.1.0-SNAPSHOT"
scalaVersion  := "2.12.2"
scalacOptions += "-Xlint"
licenses      := Seq("LGPL v3.0+" -> url("http://www.gnu.org/licenses/lgpl-3.0.txt"))

def lwjglVersion = "3.1.2"

libraryDependencies ++= Seq(
  "graphics.scenery" % "scenery"        % "0.1.0",
  "net.clearvolume"  % "cleargl"        % "2.0.4",
  "graphics.scenery" % "jopenvr"        % "1.0.5.3",
  "org.lwjgl"        % "lwjgl"          % lwjglVersion,
  "org.lwjgl"        % "lwjgl-glfw"     % lwjglVersion,
  "org.lwjgl"        % "lwjgl-jemalloc" % lwjglVersion,
  "graphics.scenery" % "spirvcrossj"    % "0.2.6"  // don't know why 0.2.4 can't be downloaded from Maven Central?
)

// we need native jars for lwjgl
// see here how to do it: https://stackoverflow.com/questions/39194625/jogamp-jogl-how-does-sbt-pick-up-the-right-native-jar#39224721
libraryDependencies ++= {
  val architecture = Seq("linux", "macos", "windows")
  architecture.map { arch =>
    "org.lwjgl" % "lwjgl" % lwjglVersion classifier s"natives-$arch"
  }
}

resolvers ++= Seq(
  "clearcontrol"  at "http://dl.bintray.com/clearcontrol/ClearControl",
  "clearvolume"   at "http://dl.bintray.com/clearvolume/ClearVolume",
  "imagej.public" at "http://maven.imagej.net/content/groups/public"
)

fork in run := true

////////////////
// Demo Files //
////////////////

def demoFilesURL = url("https://ulrik.is/scenery-demo-models.zip")

lazy val `download-demo-models` = taskKey[Unit]("Download the resources for some of the demos")

// cf.https://stackoverflow.com/questions/27466869/download-a-zip-from-url-and-extract-it-in-resource-using-sbt
`download-demo-models` := {
  val outDir  = baseDirectory.value
  val st      = streams.value
  if ((outDir / "models" / "README.md").exists()) {
    st.log.info("Demo files already present.")
  } else {
    st.log.info("Downloading demo models...")
    IO.withTemporaryFile(prefix = "temp", postfix = "zip") { tmpFile =>
      IO.download(demoFilesURL, tmpFile)
      IO.unzip(tmpFile, outDir)
    }
  }
}
