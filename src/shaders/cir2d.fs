#version 330 core

uniform vec4 passColor;
uniform float size;

uniform sampler2D sampler;

in vec2 pos;
in vec2 texCoords;

out vec4 color;

void main(){
	
	float num = ( pow(pos.x,2) + pow(pos.y,2) );
	
	if(num <= (0.25*size)) {
		color = texture(sampler, texCoords) * passColor * vec4(1,1,1,1);
	}else {
		discard;
	}
	
}