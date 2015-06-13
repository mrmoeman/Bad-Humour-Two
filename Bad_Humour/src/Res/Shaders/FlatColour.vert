varying vec3 color;

void main()
{
	color = gl_Color.rgb;
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}