#version 330 core

layout (location = 0) in vec3 v;

uniform mat4 mvp;

uniform vec2 inPos;

out vec2 pos;

void main() {
	
	pos = vec2( v.x-inPos.x, v.y-inPos.y );
	
    gl_Position = mvp*vec4(v, 1);
    
}