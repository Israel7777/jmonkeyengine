/*
 * Copyright (c) 2003-2004, jMonkeyEngine - Mojo Monkey Coding
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the Mojo Monkey Coding, jME, jMonkey Engine, nor the
 * names of its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */
package jmetest.renderer.state;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.light.DirectionalLight;
import com.jme.light.SpotLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Pyramid;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;

/**
 * <code>TestLightState</code>
 * @author Mark Powell
 * @version $Id: TestLightState.java,v 1.12 2004-08-14 00:50:05 cep21 Exp $
 */
public class TestLightState extends SimpleGame {
    private TriMesh t;

    /**
     * Entry point for the test,
     * @param args
     */
    public static void main(String[] args) {
        TestLightState app = new TestLightState();
        app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
        app.start();
    }

    /**
     * builds the trimesh.
     * @see com.jme.app.SimpleGame#initGame()
     */
    protected void simpleInitGame() {
      display.setTitle("Light State Test");
      cam.setLocation(new Vector3f(-10,0,40));
      cam.update();

        Vector3f max = new Vector3f(10,10,10);
        Vector3f min = new Vector3f(0,0,0);

        t = new Box("Box", min,max);
        t.setModelBound(new BoundingSphere());
        t.updateModelBound();

        Pyramid t2 = new Pyramid("Pyramid", 10,20);
        t2.setModelBound(new BoundingSphere());
        t2.updateModelBound();

        t.setLocalTranslation(new Vector3f(0,0,-10));

        t2.setLocalTranslation(new Vector3f(-20,0,0));

        rootNode.attachChild(t);
        rootNode.attachChild(t2);

        SpotLight am = new SpotLight();
        am.setDiffuse(new ColorRGBA(0.0f, 1.0f, 0.0f, 1.0f));
        am.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        am.setDirection(new Vector3f(-1, -0.5f, 0));
        am.setLocation(new Vector3f(25, 10, 0));
        am.setAngle(15);

        SpotLight am2 = new SpotLight();
        am2.setDiffuse(new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f));
        am2.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        am2.setDirection(new Vector3f(1, -0.5f, 0));
        am2.setLocation(new Vector3f(-25, 10, 0));
        am2.setAngle(15);

        DirectionalLight dr = new DirectionalLight();
        dr.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        dr.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        dr.setSpecular(new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f));
        dr.setDirection(new Vector3f(150, 0 , 150));

        lightState.detachAll();
        lightState.attach(am);
        lightState.attach(dr);
        lightState.attach(am2);
        am.setEnabled(true);
        am2.setEnabled(true);
        dr.setEnabled(true);

        TextureState ts = display.getRenderer().createTextureState();
                ts.setEnabled(true);
                ts.setTexture(
                    TextureManager.loadTexture(
                        TestLightState.class.getClassLoader().getResource("jmetest/data/images/Monkey.jpg"),
                        Texture.MM_LINEAR_LINEAR,
                        Texture.FM_LINEAR,
                        true));

        rootNode.setRenderState(ts);
    }
}
