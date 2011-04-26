/*
 * Copyright (c) 2009-2010 jMonkeyEngine
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
#include <jni.h>
/**
 * Author: Normen Hansen
 */
#include "jmeBulletUtil.h"

#ifdef __cplusplus
extern "C" {
#endif

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    enableMotor
     * Signature: (JZFF)V
     */
    JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_HingeJoint_enableMotor
    (JNIEnv * env, jobject object, jlong jointId, jboolean enable, jfloat targetVelocity, jfloat maxMotorImpulse) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        joint->enableAngularMotor(enable, targetVelocity, maxMotorImpulse);
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    getEnableAngularMotor
     * Signature: (J)Z
     */
    JNIEXPORT jboolean JNICALL Java_com_jme3_bullet_joints_HingeJoint_getEnableAngularMotor
    (JNIEnv * env, jobject object, jlong jointId) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        return joint->getEnableAngularMotor();
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    getMotorTargetVelocity
     * Signature: (J)F
     */
    JNIEXPORT jfloat JNICALL Java_com_jme3_bullet_joints_HingeJoint_getMotorTargetVelocity
    (JNIEnv * env, jobject object, jlong jointId) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        return joint->getMotorTargetVelosity();
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    getMaxMotorImpulse
     * Signature: (J)F
     */
    JNIEXPORT jfloat JNICALL Java_com_jme3_bullet_joints_HingeJoint_getMaxMotorImpulse
    (JNIEnv * env, jobject object, jlong jointId) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        return joint->getMaxMotorImpulse();
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    setLimit
     * Signature: (JFF)V
     */
    JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_HingeJoint_setLimit__JFF
    (JNIEnv * env, jobject object, jlong jointId, jfloat low, jfloat high) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        return joint->setLimit(low, high);
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    setLimit
     * Signature: (JFFFFF)V
     */
    JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_HingeJoint_setLimit__JFFFFF
    (JNIEnv * env, jobject object, jlong jointId, jfloat low, jfloat high, jfloat softness, jfloat biasFactor, jfloat relaxationFactor) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        return joint->setLimit(low, high, softness, biasFactor, relaxationFactor);
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    getUpperLimit
     * Signature: (J)F
     */
    JNIEXPORT jfloat JNICALL Java_com_jme3_bullet_joints_HingeJoint_getUpperLimit
    (JNIEnv * env, jobject object, jlong jointId) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        return joint->getUpperLimit();
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    getLowerLimit
     * Signature: (J)F
     */
    JNIEXPORT jfloat JNICALL Java_com_jme3_bullet_joints_HingeJoint_getLowerLimit
    (JNIEnv * env, jobject object, jlong jointId) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        return joint->getLowerLimit();
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    setAngularOnly
     * Signature: (JZ)V
     */
    JNIEXPORT void JNICALL Java_com_jme3_bullet_joints_HingeJoint_setAngularOnly
    (JNIEnv * env, jobject object, jlong jointId, jboolean angular) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        joint->setAngularOnly(angular);
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    getHingeAngle
     * Signature: (J)F
     */
    JNIEXPORT jfloat JNICALL Java_com_jme3_bullet_joints_HingeJoint_getHingeAngle
    (JNIEnv * env, jobject object, jlong jointId) {
        btHingeConstraint* joint = (btHingeConstraint*) jointId;
        return joint->getHingeAngle();
    }

    /*
     * Class:     com_jme3_bullet_joints_HingeJoint
     * Method:    createJoint
     * Signature: (JJLcom/jme3/math/Vector3f;Lcom/jme3/math/Vector3f;Lcom/jme3/math/Vector3f;Lcom/jme3/math/Vector3f;)J
     */
    JNIEXPORT jlong JNICALL Java_com_jme3_bullet_joints_HingeJoint_createJoint
    (JNIEnv * env, jobject object, jlong bodyIdA, jlong bodyIdB, jobject pivotA, jobject rotA, jobject pivotB, jobject rotB) {
        btRigidBody* bodyA = (btRigidBody*) bodyIdA;
        btRigidBody* bodyB = (btRigidBody*) bodyIdB;
        btTransform* transA = new btTransform(btMatrix3x3());
        jmeBulletUtil::convert(pivotA, &transA->getOrigin());
        jmeBulletUtil::convert(rotA, &transA->getBasis());
        btTransform* transB = new btTransform(btMatrix3x3());
        jmeBulletUtil::convert(pivotB, &transB->getOrigin());
        jmeBulletUtil::convert(rotB, &transB->getBasis());
        btHingeConstraint* joint = new btHingeConstraint(*bodyA, *bodyB, *transA, *transB);
        return (long) joint;
    }
#ifdef __cplusplus
}
#endif
