varying float MyDepth;

void main()
{
    gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * gl_Vertex;
    MyDepth = gl_Position.z/2;
}