varying vec3 normal;
varying vec3 lightdirection;
varying vec3 varyingColour;
varying vec3 position;
varying vec4 shadowPos;

uniform mat4 ModelMatrix4x4;
uniform mat4 ProjectionMatrix4x4;

void main()
{
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    //gl_Position = ProjectionMatrix4x4 * ModelMatrix4x4 * gl_Vertex;
    position = gl_ModelViewProjectionMatrix * gl_Vertex;
    normal = normalize((gl_NormalMatrix * gl_Normal).xyz);
    gl_TexCoord[0] = gl_MultiTexCoord0;
    shadowPos = ProjectionMatrix4x4 * ModelMatrix4x4 * gl_Vertex;
    lightdirection = normalize(gl_LightSource[1].position.xyz);
    
}