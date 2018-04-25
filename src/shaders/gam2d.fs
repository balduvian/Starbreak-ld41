#version 330 core

uniform vec4 inColor;

uniform sampler2D sampler;

in vec2 texCoords;

out vec4 color;

void main(){
	color = texture(sampler, texCoords);
	color *= inColor;
}

/*float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}*/