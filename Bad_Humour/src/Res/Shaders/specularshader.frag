uniform sampler2D ColourMap;
uniform sampler2D ShadowMap;
uniform float Alpha;
varying vec3 normal;
varying vec3 lightdirection;
varying vec3 varyingColour;
varying vec3 position;
varying vec4 shadowPos;

void main()
{
	
	vec4 MyColour;
	float Intensity;
	Intensity = dot(normalize(gl_LightSource[1].position.xyz - position),normal);
	
	Intensity = clamp(Intensity, 0, 1);
	
	//shadowPos.x/1024;
	//shadowPos.y/1920;
	//shadowPos.xyz = shadowPos.xyz/shadowPos.w;
	//shadowPos.xy = (shadowPos.xy *0.5) + 0.5;
	shadowPos.xyz = (shadowPos.xyz / shadowPos.w + 1.0) / 2.0; 
	
	
	vec2 poissonDisk[4] = vec2[](
  		vec2( -0.94201624, -0.39906216 ),
 	 	vec2( 0.94558609, -0.76890725 ),
  		vec2( -0.094184101, -0.92938870 ),
 	 	vec2( 0.34495938, 0.29387760 )
	);
	
	
	
	
	float bias = 0.008;
	float visibility = 1.0;
	for (int i=0;i<4;i++){
		if ( texture2D( ShadowMap, shadowPos.xy + poissonDisk[i]/1400.0 ).x  <  (shadowPos.z-0.8)*5 - bias){
    		visibility -=0.2;
		}
	}
	
	
	lightdirection = vec3(gl_LightSource[1].position.xyz-position);
	
	vec3 reflectionDirection = normalize(reflect(-lightdirection, normal));	//
	//float diffuseLightIntensity = max(0, dot(normal, lightdirection));	//
	float specular = max(0.0, dot(normal, reflectionDirection));
	
	//if (diffuseLightIntensity != 0) {
	
		float fspecular = pow(specular, 255);
	
		varyingColour.rgb += vec3(fspecular, fspecular, fspecular);
	//}
	
	
	
    //gl_FragColor = (texture2D(ColourMap, gl_TexCoord[0].st)) * vec4(Intensity,Intensity,Intensity,1)* vec4(varyingColour, 1) + vec4(0.05, 0.05, 0.05, 1);
    gl_FragColor = vec4((texture2D(ColourMap, gl_TexCoord[0].st)).xyz, Alpha) * vec4(vec3((Intensity,Intensity,Intensity) + vec3(varyingColour)) * visibility, Alpha);
    //gl_FragColor = (texture2D(ColourMap, gl_TexCoord[0].st)) * vec3(varyingColour) + vec4(0.05, 0.05, 0.05, 1);
    //gl_FragColor = (texture2D(ShadowMap, shadowPos.xy));
    //gl_FragColor = (texture2D(ShadowMap, vec2(shadowPos.x, shadowPos.y)));
    //gl_FragColor = (texture2D(ShadowMap, vec2(gl_FragCoord.x/1920, gl_FragCoord.y/1024)));
    //gl_FragColor = vec4(gl_FragCoord.x/1920, gl_FragCoord.y/1024, 0, 1);
    //gl_FragColor = vec4(gl_FragCoord.x, gl_FragCoord.y, 0, 1);
    //gl_FragColor = vec4(shadowPos.x, shadowPos.y, 0, 1);
    //gl_FragColor = (texture2D(ShadowMap, gl_TexCoord[0].st));
    //gl_FragColor = vec4(varyingColour, 1);
    //gl_FragColor = vec4(visibility, visibility, visibility, 1);
    //gl_FragColor = vec4(shadowPos.z, shadowPos.z, shadowPos.z, 1);
    //gl_FragColor = vec4(gl_LightSource[1].position.xyz, 1);
    //gl_FragColor = vec4(shadowPos.s,shadowPos,1,1);
}