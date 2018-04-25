#version 330 core

uniform sampler2D sampler;

in vec2 texCoords;
in vec4 cornerColor;

out vec4 color;

void main(){
	color = texture(sampler, texCoords);
	color *= cornerColor;
}