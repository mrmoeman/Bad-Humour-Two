varying float MyDepth;

void main()
{
	float Depth = (gl_FragCoord.z-0.8)*5;
    gl_FragColor = vec4(Depth, Depth, Depth,1);
    
}