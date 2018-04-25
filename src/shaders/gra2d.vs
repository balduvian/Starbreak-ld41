#version 330 core

layout (location = 0) in vec3 vertices;

uniform mat4 mvp;

out vec4 outColor;

void main() {
	outColor = vec4(vertices.x, vertices.x, vertices.x, 1);
    gl_Position = mvp*vec4(vertices, 1);
    
}