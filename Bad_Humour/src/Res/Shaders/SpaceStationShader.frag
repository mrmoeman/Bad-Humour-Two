uniform sampler2D ColourMap;
uniform sampler2D AlphaMap;
uniform sampler2D SpecularMap;
varying vec3 normal;
varying vec3 lightdirection;
varying vec3 varyingColour;
varying vec3 position;

void main()
{
	float SpecularValue = (texture2D(SpecularMap, gl_TexCoord[0].st)).y * 255;
	
	vec4 MyColour;
	float Intensity;
	Intensity = dot(normalize(gl_LightSource[1].position.xyz - position),normal);
	
	Intensity = clamp(Intensity, 0.2, 1);
	
	lightdirection = vec3(gl_LightSource[1].position.xyz-position);
	
	vec3 reflectionDirection = normalize(reflect(-lightdirection, normal));
	float specular = max(0.0, dot(normal, reflectionDirection));

	float fspecular = pow(specular, SpecularValue);
	
	varyingColour.rgb += vec3(fspecular, fspecular, fspecular);

      
    vec4 finalcolour;
    if(SpecularValue > 0){
    	finalcolour = vec4((texture2D(ColourMap, gl_TexCoord[0].st)).xyz, 1) * vec4(vec3((Intensity,Intensity,Intensity) + vec3(varyingColour)), 1);
    }
    else{
    	finalcolour = vec4((texture2D(ColourMap, gl_TexCoord[0].st)).xyz, 1) * vec4(Intensity,Intensity,Intensity, 1);
    }
    gl_FragColor = finalcolour * vec4(1,1,1, (texture2D(AlphaMap, gl_TexCoord[0].st)).x);
    
    //gl_FragColor = finalcolour * vec4((texture2D(AlphaMap, gl_TexCoord[0].st)).xyx, 1);
      

    
}