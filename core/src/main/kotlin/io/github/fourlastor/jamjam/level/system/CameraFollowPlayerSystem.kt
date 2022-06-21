package io.github.fourlastor.jamjam.level.system

import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.badlogic.gdx.graphics.Camera
import ktx.graphics.update

@All(DynamicBodyComponent::class, PlayerComponent::class)
class CameraFollowPlayerSystem(
    private val camera: Camera,
) : SingleEntitySystem() {

    private lateinit var bodies: ComponentMapper<DynamicBodyComponent>

    private var lastX = camera.position.x

    override fun processSystem() {
        val entity = entity ?: return
        val center = bodies[entity].body.position
        val x = center.x
        if (lastX == x) {
            return
        }
        lastX = x
        camera.update { position.x = x }
    }
}
