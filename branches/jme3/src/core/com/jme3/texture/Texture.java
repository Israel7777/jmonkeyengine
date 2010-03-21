/*
 * Copyright (c) 2003-2009 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jme3.texture;

import com.jme3.asset.TextureKey;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.InputCapsule;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.renderer.GLObject;
import com.jme3.renderer.Renderer;
import java.io.IOException;

/**
 * <code>Texture</code> defines a texture object to be used to display an
 * image on a piece of geometry. The image to be displayed is defined by the
 * <code>Image</code> class. All attributes required for texture mapping are
 * contained within this class. This includes mipmapping if desired,
 * magnificationFilter options, apply options and correction options. Default
 * values are as follows: minificationFilter - NearestNeighborNoMipMaps,
 * magnificationFilter - NearestNeighbor, wrap - EdgeClamp on S,T and R, apply -
 * Modulate, enivoronment - None.
 *
 * @see com.jme3.texture.Image
 * @author Mark Powell
 * @author Joshua Slack
 * @version $Id: Texture.java 4131 2009-03-19 20:15:28Z blaine.dev $
 */
public abstract class Texture extends GLObject implements Savable, Cloneable {

    public enum Type {

        /**
         * Two dimensional texture (default). A rectangle.
         */
        TwoDimensional,
        
        /**
         * An array of two dimensional textures. 
         */
        TwoDimensionalArray,

        /**
         * Three dimensional texture. (A cube)
         */
        ThreeDimensional,

        /**
         * A set of 6 TwoDimensional textures arranged as faces of a cube facing
         * inwards.
         */
        CubeMap;
    }

    public enum MinFilter {

        /**
         * Nearest neighbor interpolation is the fastest and crudest filtering
         * method - it simply uses the color of the texel closest to the pixel
         * center for the pixel color. While fast, this results in aliasing and
         * shimmering during minification. (GL equivalent: GL_NEAREST)
         */
        NearestNoMipMaps(false),

        /**
         * In this method the four nearest texels to the pixel center are
         * sampled (at texture level 0), and their colors are combined by
         * weighted averages. Though smoother, without mipmaps it suffers the
         * same aliasing and shimmering problems as nearest
         * NearestNeighborNoMipMaps. (GL equivalent: GL_LINEAR)
         */
        BilinearNoMipMaps(false),

        /**
         * Same as NearestNeighborNoMipMaps except that instead of using samples
         * from texture level 0, the closest mipmap level is chosen based on
         * distance. This reduces the aliasing and shimmering significantly, but
         * does not help with blockiness. (GL equivalent:
         * GL_NEAREST_MIPMAP_NEAREST)
         */
        NearestNearestMipMap(true),

        /**
         * Same as BilinearNoMipMaps except that instead of using samples from
         * texture level 0, the closest mipmap level is chosen based on
         * distance. By using mipmapping we avoid the aliasing and shimmering
         * problems of BilinearNoMipMaps. (GL equivalent:
         * GL_LINEAR_MIPMAP_NEAREST)
         */
        BilinearNearestMipMap(true),

        /**
         * Similar to NearestNeighborNoMipMaps except that instead of using
         * samples from texture level 0, a sample is chosen from each of the
         * closest (by distance) two mipmap levels. A weighted average of these
         * two samples is returned. (GL equivalent: GL_NEAREST_MIPMAP_LINEAR)
         */
        NearestLinearMipMap(true),

        /**
         * Trilinear filtering is a remedy to a common artifact seen in
         * mipmapped bilinearly filtered images: an abrupt and very noticeable
         * change in quality at boundaries where the renderer switches from one
         * mipmap level to the next. Trilinear filtering solves this by doing a
         * texture lookup and bilinear filtering on the two closest mipmap
         * levels (one higher and one lower quality), and then linearly
         * interpolating the results. This results in a smooth degradation of
         * texture quality as distance from the viewer increases, rather than a
         * series of sudden drops. Of course, closer than Level 0 there is only
         * one mipmap level available, and the algorithm reverts to bilinear
         * filtering (GL equivalent: GL_LINEAR_MIPMAP_LINEAR)
         */
        Trilinear(true);

        private boolean usesMipMapLevels;

        private MinFilter(boolean usesMipMapLevels) {
            this.usesMipMapLevels = usesMipMapLevels;
        }

        public boolean usesMipMapLevels() {
            return usesMipMapLevels;
        }
    }

    public enum MagFilter {

        /**
         * Nearest neighbor interpolation is the fastest and crudest filtering
         * mode - it simply uses the color of the texel closest to the pixel
         * center for the pixel color. While fast, this results in texture
         * 'blockiness' during magnification. (GL equivalent: GL_NEAREST)
         */
        Nearest,

        /**
         * In this mode the four nearest texels to the pixel center are sampled
         * (at the closest mipmap level), and their colors are combined by
         * weighted average according to distance. This removes the 'blockiness'
         * seen during magnification, as there is now a smooth gradient of color
         * change from one texel to the next, instead of an abrupt jump as the
         * pixel center crosses the texel boundary. (GL equivalent: GL_LINEAR)
         */
        Bilinear;

    }

    public enum WrapMode {
        /**
         * Only the fractional portion of the coordinate is considered.
         */
        Repeat,
        /**
         * Only the fractional portion of the coordinate is considered, but if
         * the integer portion is odd, we'll use 1 - the fractional portion.
         * (Introduced around OpenGL1.4) Falls back on Repeat if not supported.
         */
        MirroredRepeat,
        /**
         * coordinate will be clamped to [0,1]
         */
        Clamp,
        /**
         * mirrors and clamps the texture coordinate, where mirroring and
         * clamping a value f computes:
         * <code>mirrorClamp(f) = min(1, max(1/(2*N),
         * abs(f)))</code> where N
         * is the size of the one-, two-, or three-dimensional texture image in
         * the direction of wrapping. (Introduced after OpenGL1.4) Falls back on
         * Clamp if not supported.
         */
        MirrorClamp,
        /**
         * coordinate will be clamped to the range [-1/(2N), 1 + 1/(2N)] where N
         * is the size of the texture in the direction of clamping. Falls back
         * on Clamp if not supported.
         */
        BorderClamp,
        /**
         * Wrap mode MIRROR_CLAMP_TO_BORDER_EXT mirrors and clamps to border the
         * texture coordinate, where mirroring and clamping to border a value f
         * computes:
         * <code>mirrorClampToBorder(f) = min(1+1/(2*N), max(1/(2*N), abs(f)))</code>
         * where N is the size of the one-, two-, or three-dimensional texture
         * image in the direction of wrapping." (Introduced after OpenGL1.4)
         * Falls back on BorderClamp if not supported.
         */
        MirrorBorderClamp,
        /**
         * coordinate will be clamped to the range [1/(2N), 1 - 1/(2N)] where N
         * is the size of the texture in the direction of clamping. Falls back
         * on Clamp if not supported.
         */
        EdgeClamp,
        /**
         * mirrors and clamps to edge the texture coordinate, where mirroring
         * and clamping to edge a value f computes:
         * <code>mirrorClampToEdge(f) = min(1-1/(2*N), max(1/(2*N), abs(f)))</code>
         * where N is the size of the one-, two-, or three-dimensional texture
         * image in the direction of wrapping. (Introduced after OpenGL1.4)
         * Falls back on EdgeClamp if not supported.
         */
        MirrorEdgeClamp;
    }

    public enum WrapAxis {
        /**
         * S wrapping (u or "horizontal" wrap)
         */
        S,
        /**
         * T wrapping (v or "vertical" wrap)
         */
        T,
        /**
         * R wrapping (w or "depth" wrap)
         */
        R;
    }

    /**
     * If this texture is a depth texture (the format is Depth*) then
     * this value may be used to compare the texture depth to the R texture
     * coordinate. 
     */
    public enum ShadowCompareMode {
        /**
         * Shadow comparison mode is disabled.
         * Texturing is done normally.
         */
        Off,

        /**
         * Compares the 3rd texture coordinate R to the value
         * in this depth texture. If R <= texture value then result is 1.0,
         * otherwise, result is 0.0. If filtering is set to bilinear or trilinear
         * the implementation may sample the texture multiple times to provide
         * smoother results in the range [0, 1].
         */
        LessOrEqual,

        /**
         * Compares the 3rd texture coordinate R to the value
         * in this depth texture. If R >= texture value then result is 1.0,
         * otherwise, result is 0.0. If filtering is set to bilinear or trilinear
         * the implementation may sample the texture multiple times to provide
         * smoother results in the range [0, 1].
         */
        GreaterOrEqual
    }

    /**
     * The name of the texture (if loaded as a resource).
     */
    private String name = null;

    /**
     * The image stored in the texture, can be null if the
     * texture is already on the GPU.
     */
    private Image image = null;

    /**
     * The texture key allows to reload a texture from a file
     * if needed.
     */
    private TextureKey key = null;

    private MinFilter minificationFilter = MinFilter.BilinearNoMipMaps;
    private MagFilter magnificationFilter = MagFilter.Bilinear;
    private ShadowCompareMode shadowCompareMode = ShadowCompareMode.Off;
    private int anisotropicFilter;
    private int imageIndex = 0;

    /**
     * Incorrect ?? Should clone ID
     * @return
     */
    public Texture clone(){
        return (Texture) super.clone();
    }

    /**
     * Constructor instantiates a new <code>Texture</code> object with default
     * attributes.
     */
    public Texture() {
        super(GLObject.Type.Texture);
    }

    protected Texture(int id){
        super(GLObject.Type.Texture, id);
    }

    /**
     * @return the MinificationFilterMode of this texture.
     */
    public MinFilter getMinFilter() {
        return minificationFilter;
    }

    /**
     * @param minificationFilter
     *            the new MinificationFilterMode for this texture.
     * @throws IllegalArgumentException
     *             if minificationFilter is null
     */
    public void setMinFilter(MinFilter minificationFilter) {
        if (minificationFilter == null) {
            throw new IllegalArgumentException(
                    "minificationFilter can not be null.");
        }
        this.minificationFilter = minificationFilter;
    }

    /**
     * @return the MagnificationFilterMode of this texture.
     */
    public MagFilter getMagFilter() {
        return magnificationFilter;
    }

    /**
     * @param magnificationFilter
     *            the new MagnificationFilter for this texture.
     * @throws IllegalArgumentException
     *             if magnificationFilter is null
     */
    public void setMagFilter(MagFilter magnificationFilter) {
        if (magnificationFilter == null) {
            throw new IllegalArgumentException(
                    "magnificationFilter can not be null.");
        }
        this.magnificationFilter = magnificationFilter;
    }

    /**
     * @return The ShadowCompareMode of this texture.
     * @see ShadowCompareMode
     */
    public ShadowCompareMode getShadowCompareMode(){
        return shadowCompareMode;
    }

    /**
     * @param compareMode
     *            the new ShadowCompareMode for this texture.
     * @throws IllegalArgumentException
     *             if compareMode is null
     * @see ShadowCompareMode
     */
    public void setShadowCompareMode(ShadowCompareMode compareMode){
        if (compareMode == null){
            throw new IllegalArgumentException(
                    "compareMode can not be null.");
        }
        this.shadowCompareMode = compareMode;
    }

    /**
     * <code>setImage</code> sets the image object that defines the texture.
     *
     * @param image
     *            the image that defines the texture.
     */
    public void setImage(Image image) {
        this.image = image;
        setUpdateNeeded();
    }

    /**
     * @param key The texture key that was used to load this texture
     */
    public void setTextureKey(TextureKey key){
        this.key = key;
    }

    public TextureKey getTextureKey(){
        return key;
    }

    /**
     * <code>getImage</code> returns the image data that makes up this
     * texture. If no image data has been set, this will return null.
     *
     * @return the image data that makes up the texture.
     */
    public Image getImage() {
        return image;
    }

    public void setImageDataIndex(int index){
        this.imageIndex = index;
    }

    public int getImageDataIndex() {
        return imageIndex;
    }

    /**
     * <code>setWrap</code> sets the wrap mode of this texture for a
     * particular axis.
     *
     * @param axis
     *            the texture axis to define a wrapmode on.
     * @param mode
     *            the wrap mode for the given axis of the texture.
     * @throws IllegalArgumentException
     *             if axis or mode are null or invalid for this type of texture
     */
    public abstract void setWrap(WrapAxis axis, WrapMode mode);

    /**
     * <code>setWrap</code> sets the wrap mode of this texture for all axis.
     *
     * @param mode
     *            the wrap mode for the given axis of the texture.
     * @throws IllegalArgumentException
     *             if mode is null or invalid for this type of texture
     */
    public abstract void setWrap(WrapMode mode);

    /**
     * <code>getWrap</code> returns the wrap mode for a given coordinate axis
     * on this texture.
     *
     * @param axis
     *            the axis to return for
     * @return the wrap mode of the texture.
     * @throws IllegalArgumentException
     *             if axis is null or invalid for this type of texture
     */
    public abstract WrapMode getWrap(WrapAxis axis);

    public abstract Type getType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the anisotropic filtering level for this texture. Default value
     * is 1 (no anisotrophy), 2 means x2, 4 is x4, etc.
     */
    public int getAnisotropicFilter() {
        return anisotropicFilter;
    }

    /**
     * @param level
     *            the anisotropic filtering level for this texture.
     */
    public void setAnisotropicFilter(int level) {
        if (level < 1)
            anisotropicFilter = 1;
        else
            anisotropicFilter = level;
    }

    @Override
    public String toString(){
        String imgTxt = null;
        if (image != null){
            imgTxt = ", img="+image.getWidth()
                    +"x"+image.getHeight();
            if (image.getDepth() > 1)
                imgTxt += "x"+image.getDepth();
            imgTxt += "-"+image.getFormat().name();
            if (image.hasMipmaps())
                imgTxt += "/mips";
        }

        return getClass().getSimpleName() + "[name="+name+imgTxt+"]";
    }

    @Override
    public void resetObject() {
        this.id = -1;
        setUpdateNeeded();
    }

    @Override
    public void deleteObject(Renderer r) {
        r.deleteTexture(this);
    }

//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if (!(other instanceof Texture)) {
//            return false;
//        }
////        super.equals(other);
//
//        Texture that = (Texture) other;
//        if (this.textureId != that.textureId)
//            return false;
//        if (this.textureId == 0) {
//            if (this.getImage() != null
//                    && !this.getImage().equals(that.getImage()))
//                return false;
//            if (this.getImage() == null && that.getImage() != null)
//                return false;
//            if (this.getAnisotropicFilterPercent() != that
//                    .getAnisotropicFilterPercent())
//                return false;
//            if (this.getApply() != that.getApply())
//                return false;
//            if (this.getCombineFuncAlpha() != that.getCombineFuncAlpha())
//                return false;
//            if (this.getCombineFuncRGB() != that.getCombineFuncRGB())
//                return false;
//            if (this.getCombineOp0Alpha() != that.getCombineOp0Alpha())
//                return false;
//            if (this.getCombineOp1RGB() != that.getCombineOp1RGB())
//                return false;
//            if (this.getCombineOp2Alpha() != that.getCombineOp2Alpha())
//                return false;
//            if (this.getCombineOp2RGB() != that.getCombineOp2RGB())
//                return false;
//            if (this.getCombineScaleAlpha() != that.getCombineScaleAlpha())
//                return false;
//            if (this.getCombineScaleRGB() != that.getCombineScaleRGB())
//                return false;
//            if (this.getCombineSrc0Alpha() != that.getCombineSrc0Alpha())
//                return false;
//            if (this.getCombineSrc0RGB() != that.getCombineSrc0RGB())
//                return false;
//            if (this.getCombineSrc1Alpha() != that.getCombineSrc1Alpha())
//                return false;
//            if (this.getCombineSrc1RGB() != that.getCombineSrc1RGB())
//                return false;
//            if (this.getCombineSrc2Alpha() != that.getCombineSrc2Alpha())
//                return false;
//            if (this.getCombineSrc2RGB() != that.getCombineSrc2RGB())
//                return false;
//            if (this.getEnvironmentalMapMode() != that
//                    .getEnvironmentalMapMode())
//                return false;
//            if (this.getMagnificationFilter() != that.getMagnificationFilter())
//                return false;
//            if (this.getMinificationFilter() != that.getMinificationFilter())
//                return false;
//            if (this.getBlendColor() != null
//                    && !this.getBlendColor().equals(that.getBlendColor()))
//                return false;
//            if (this.getBlendColor() == null && that.getBlendColor() != null)
//                return false;
//        }
//        return true;
//    }

//    public abstract Texture createSimpleClone();


   /** Retreive a basic clone of this Texture (ie, clone everything but the
     * image data, which is shared)
     *
     * @return Texture
     */
    public Texture createSimpleClone(Texture rVal) {
        rVal.setMinFilter(minificationFilter);
        rVal.setMagFilter(magnificationFilter);
        rVal.setShadowCompareMode(shadowCompareMode);
        rVal.setImageDataIndex(imageIndex);
//        rVal.setHasBorder(hasBorder);
        rVal.setAnisotropicFilter(anisotropicFilter);
        rVal.setImage(image); // NOT CLONED.
//        rVal.memReq = memReq;
        rVal.setTextureKey(key);
        rVal.setName(name);
        rVal.setId(id);
//        rVal.setBlendColor(blendColor != null ? blendColor.clone() : null);
//        if (getTextureKey() != null) {
//            rVal.setTextureKey(getTextureKey());
//        }
        return rVal;
    }

    public abstract Texture createSimpleClone();

    public void write(JmeExporter e) throws IOException {
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(name, "name", null);
        capsule.write(key, "key", null);
        capsule.write(anisotropicFilter, "anisotropicFilter", 1);
        capsule.write(minificationFilter, "minificationFilter",
                MinFilter.BilinearNoMipMaps);
        capsule.write(magnificationFilter, "magnificationFilter",
                MagFilter.Bilinear);
    }

    public void read(JmeImporter e) throws IOException {
        InputCapsule capsule = e.getCapsule(this);
        name = capsule.readString("name", null);
        key = (TextureKey) capsule.readSavable("key", null);
        // load texture from key
        if (key != null){
            Texture loadedTex = e.getAssetManager().loadTexture(key);
            image = loadedTex.getImage();
        }
//        image = (Image) capsule.readSavable("image", null);
//        if (image == null) {
//        }
        anisotropicFilter = capsule.readInt("anisotropicFilter", 1);
        minificationFilter = capsule.readEnum("minificationFilter",
                MinFilter.class,
                MinFilter.NearestNoMipMaps);
        magnificationFilter = capsule.readEnum("magnificationFilter",
                MagFilter.class, MagFilter.Nearest);
    }
}