#version 330 core

layout (location = 0) in vec3 vertices;
layout (location = 1) in vec2 iTexCoords;

uniform mat4 mvp;
uniform vec4 frame; 
uniform float enable;
uniform float gradient;

out vec2 texCoords;
out vec4 cornerColor;

void main() {
	
	texCoords = iTexCoords * frame.xy + frame.zw;
    gl_Position = mvp*vec4(vertices, 1);
    
    if(enable == 1){
    	cornerColor = vec4( pow(sin(gradient+gl_Position.x),2), pow(sin(gradient+gl_Position.y+1.5707963),2), pow(sin(gradient+gl_Position.y)*sin(gradient+gl_Position.x+1.5707963),2), 1);
    	//cornerColor = vec4(1,1,0,1); 
    }else{
		cornerColor = vec4(1,1,1,1);    
    }
}