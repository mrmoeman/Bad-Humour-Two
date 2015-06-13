uniform sampler2D ColourMap;

uniform sampler2D EdgeMap;

uniform sampler2D GlowMap;

uniform sampler2D BulletBaseMap;

uniform sampler2D PlanetMap;

varying vec3 color;

void main()
{
	vec4 bulletcolour = (texture2D(GlowMap, gl_TexCoord[0].st) * vec4(23,23,23,1) + texture2D(BulletBaseMap, gl_TexCoord[0].st));
	
	if(texture2D(ColourMap, gl_TexCoord[0].st).x + texture2D(ColourMap, gl_TexCoord[0].st).y +texture2D(ColourMap, gl_TexCoord[0].st).z +texture2D(EdgeMap, gl_TexCoord[0].st).x +texture2D(EdgeMap, gl_TexCoord[0].st).y +texture2D(EdgeMap, gl_TexCoord[0].st).z == 0){
		gl_FragColor = (bulletcolour + texture2D(PlanetMap, gl_TexCoord[0].st))/2;
	}
	else{
		gl_FragColor = texture2D(ColourMap, gl_TexCoord[0].st) + texture2D(EdgeMap, gl_TexCoord[0].st);
	}
    
}