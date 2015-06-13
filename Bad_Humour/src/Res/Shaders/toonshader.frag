uniform sampler2D ColourMap;
varying vec3 normal;
varying vec3 lightdirection;
varying vec3 varyingColour;
varying vec3 position;

void main()
{
	
	vec4 MyColour;
	float Intensity;
	Intensity = dot(normalize(gl_LightSource[1].position.xyz - position),normal);
	
	Intensity = clamp(Intensity, 0.2, 1);
	
	lightdirection = vec3(gl_LightSource[1].position.xyz-position);
	
	vec3 reflectionDirection = normalize(reflect(-lightdirection, normal));	//
	//float diffuseLightIntensity = max(0, dot(normal, lightdirection));	//
	float specular = max(0.0, dot(normal, reflectionDirection));
	
	//if (diffuseLightIntensity != 0) {
	
		float fspecular = pow(specular, 255);
	
		varyingColour.rgb += vec3(fspecular, fspecular, fspecular);
	//}
	
	
	
    //gl_FragColor = (texture2D(ColourMap, gl_TexCoord[0].st)) * vec4(Intensity,Intensity,Intensity,1)* vec4(varyingColour, 1) + vec4(0.05, 0.05, 0.05, 1);
    //gl_FragColor = (texture2D(ColourMap, gl_TexCoord[0].st)) * vec4(Intensity,Intensity,Intensity,1) ;
    //gl_FragColor = (texture2D(ColourMap, gl_TexCoord[0].st)) * vec4(varyingColour, 1) + vec4(0.05, 0.05, 0.05, 1);
      
      gl_FragColor = vec4((texture2D(ColourMap, gl_TexCoord[0].st)).xyz, 1) * vec4(vec3((Intensity,Intensity,Intensity) + vec3(varyingColour)), 1);
    
      
    //gl_FragColor = vec4(varyingColour, 1);
    //gl_FragColor = vec4(gl_LightSource[1].position.xyz, 1);
    
}