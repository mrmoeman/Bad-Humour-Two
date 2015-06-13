uniform vec3 Colour;
varying vec3 normal;

void main()
{
    gl_FragColor = vec4(Colour.r,Colour.g,Colour.b,1);
    
}