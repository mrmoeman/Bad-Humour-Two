varying vec3 normal;
varying vec3 lightdirection;
varying vec3 varyingColour;
varying vec3 position;

void main()
{
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    position = gl_ModelViewProjectionMatrix * gl_Vertex;
    normal = normalize((gl_NormalMatrix * gl_Normal).xyz);
    gl_TexCoord[0] = gl_MultiTexCoord0;
     lightdirection = normalize(gl_LightSource[1].position.xyz);
    
}