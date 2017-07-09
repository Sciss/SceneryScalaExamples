name          := "SceneryTest"
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

resolvers ++= Seq(
  "clearcontrol"  at "http://dl.bintray.com/clearcontrol/ClearControl",
  "clearvolume"   at "http://dl.bintray.com/clearvolume/ClearVolume",
  "imagej.public" at "http://maven.imagej.net/content/groups/public"
)

fork in run := true
