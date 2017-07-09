# SceneryTest

This is a small Scala project that looks at the examples for the [scenery](https://github.com/scenerygraphics/scenery) 3D rendering library.
The original examples are in Kotlin and Java, so I added a few extension methods to make have clean code.
At the moment, I'm just trying out this library, if I ever use it for a serious project, I might release this
Scala extension layer as a separate library.

This project is (C)opyright 2017 by Hanns Holger Rutz. All rights reserved. Since the examples are more or less
direct translations from the scenery project, this Scala project is released under the same
[GNU Lesser General Public License](https://raw.github.com/Sciss/SceneryTest/master/LICENSE) v3+ and comes with 
absolutely no warranties. To contact the author, send an email to `contact at sciss.de`.

## requirements / running

This project compiles against Scala 2.12, using sbt 0.13. To run the examples, just use `sbt run` and
select the number of the example. For convenience, the [sbt extras]() script by Paul Phillips is included,
licensed under BSD 3-clause. So if you do not want to install sbt, just use `./sbt run`.

Some demos, such as `BoxedProteinExample` require models that have to be [downloaded separately](https://github.com/scenerygraphics/scenery#examples),
because they are quite large (over 100 MB zipped). However, you can just use `sbt download-demo-models`
to install them inside the project's base directory.

Note that you get an eviction warning `graphics.scenery:spirvcrossj:0.2.4 -> 0.2.6`. This is because I could not
use the 0.2.4 version from Maven Central. So far this did not seem to cause problems.

## contributing

Please see the file [CONTRIBUTING.md](CONTRIBUTING.md)
