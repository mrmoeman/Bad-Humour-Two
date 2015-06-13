uniform sampler2D ColourMap;

varying vec3 color;

void main()
{
	if(texture2D(ColourMap, gl_TexCoord[0].st).x + texture2D(ColourMap, gl_TexCoord[0].st).y + texture2D(ColourMap, gl_TexCoord[0].st).z == 0){
    	gl_FragColor = new vec4(0,0,0,0);
    }
    else{
    	gl_FragColor = (texture2D(ColourMap, gl_TexCoord[0].st));
    }
    
}