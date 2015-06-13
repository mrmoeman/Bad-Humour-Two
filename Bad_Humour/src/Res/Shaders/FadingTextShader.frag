uniform sampler2D ColourMap;

varying vec3 color;

void main()
{
	vec4 FragColor = texture2D(ColourMap, gl_TexCoord[0].st);
	FragColor.a = color.r;
	if(texture2D(ColourMap, gl_TexCoord[0].st).r + texture2D(ColourMap, gl_TexCoord[0].st).g + texture2D(ColourMap, gl_TexCoord[0].st).b == 0){
	FragColor.a = 0;
	}

    gl_FragColor = FragColor;
    
}