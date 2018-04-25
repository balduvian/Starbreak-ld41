#version 330 core

uniform vec4 passColor;

in vec2 pos;

out vec4 color;

void main(){

	float num = mod(cos(pow((pos.x+1)*100,2) * pos.y)+sin(500*(pos.y+1)),1);
	
	color = vec4(num-0.5, num-0.5, num-0.5, 1) + passColor;
}