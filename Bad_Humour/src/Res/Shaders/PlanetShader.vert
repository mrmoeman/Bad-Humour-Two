varying vec3 normal;
varying vec3 lightdirection;
varying vec3 position;
uniform sampler2D AlphaMap;
varying vec4 shadowPos;

uniform mat4 ModelMatrix4x4;
uniform mat4 ProjectionMatrix4x4;

void main()
{
	gl_TexCoord[0] = gl_MultiTexCoord0;

	if(texture2D(AlphaMap, gl_TexCoord[0].st).r>0.3){
		gl_Vertex.x += gl_Normal.x/2 * texture2D(AlphaMap, gl_TexCoord[0].st).r;
		gl_Vertex.y += gl_Normal.y/2 * texture2D(AlphaMap, gl_TexCoord[0].st).r;
		gl_Vertex.z += gl_Normal.z/2 * texture2D(AlphaMap, gl_TexCoord[0].st).r;
	}
	
	shadowPos = ProjectionMatrix4x4 * ModelMatrix4x4 * gl_Vertex;
	
	//float x_d = 512/64; //size of texel
    //float y_d = 512/128;
	
	//float top = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(0.0, y_d)).r;
	//float topleft = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(-x_d, y_d)).r;
	//float topright = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(x_d, y_d)).r;
	//float bottom = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(0.0, -y_d)).r;
	//float bottomleft = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(-x_d, -y_d)).r;
	//float bottomright = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(x_d, -y_d)).r;
	//float left = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(-x_d, 0.0)).r;
	//float right = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(x_d, 0.0)).r;

	//vec3 n;
	//gl_Normal.x = -(bottomright-bottomleft+2*(right-left)+topright-topleft);
	//gl_Normal.z = -(topleft-bottomleft+2*(top-bottom)+topright-bottomright);
	//gl_Normal.y = 1.0;
	//n = normalize(n);
	
	position = gl_ModelViewProjectionMatrix * gl_Vertex;
	
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    normal = normalize((gl_NormalMatrix * gl_Normal).xyz);
    lightdirection = normalize(gl_LightSource[1].position.xyz);
}