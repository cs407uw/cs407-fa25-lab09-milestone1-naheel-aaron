package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        // Center the ball and zero everything
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if (isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        val a0x = accX
        val a1x = xAcc
        val a0y = accY
        val a1y = yAcc

        // Eq. (1): v1 = v0 + 1/2 (a1 + a0) Δt
        val v1x = velocityX + 0.5f * (a0x + a1x) * dT
        val v1y = velocityY + 0.5f * (a0y + a1y) * dT

        // Eq. (2): l = v0 Δt + 1/6 (3 a0 + a1) Δt²
        val dt2 = dT * dT
        val dx = velocityX * dT + (1f / 6f) * (3f * a0x + a1x) * dt2
        val dy = velocityY * dT + (1f / 6f) * (3f * a0y + a1y) * dt2

        posX += dx
        posY += dy

        velocityX = v1x
        velocityY = v1y
        accX = a1x
        accY = a1y

        checkBoundaries()
    }



    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        val maxX = backgroundWidth - ballSize
        val maxY = backgroundHeight - ballSize

        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        } else if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        } else if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }

}
