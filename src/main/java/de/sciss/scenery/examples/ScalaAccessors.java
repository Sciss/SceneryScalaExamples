package de.sciss.scenery.examples;

import graphics.scenery.Hub;
import graphics.scenery.Scene;
import graphics.scenery.backends.Renderer;

public class ScalaAccessors {
    public static Renderer createRenderer(Hub hub, String appName, Scene scene, int winWidth, int winHeight) {
        return Renderer.Factory.createRenderer(hub, appName, scene, winWidth, winHeight);
    }
}
