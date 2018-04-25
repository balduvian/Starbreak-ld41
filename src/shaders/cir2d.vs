#version 330 core

layout (location = 0) in vec3 v;
layout (location = 1) in vec2 iTexCoords;

uniform mat4 mvp;

out vec2 pos;
out vec2 texCoords;

void main() {
	
	pos = vec2(v.x,v.y);
	
    gl_Position = mvp*vec4(v, 1);
    texCoords =  iTexCoords + vec2(1,1);
}