uniform sampler2D ColourMap;

uniform sampler2D AlphaMap;

varying vec3 normal;
varying vec3 lightdirection;
varying vec4 shadowPos;

uniform sampler2D ShadowMap;

varying vec3 color;

varying vec3 position;

void main()
{
	vec4 WaterColour;
	vec4 peakColour;
	vec4 LandColour;
	float Opacity = 0.4;
	vec4 MyColour;
	vec3 Intensity;
	
	float x_d = 512/64; //size of texel
    float y_d = 512/1028;
	
	float top = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(0.0, y_d)).r;
	float bottom = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(0.0, -y_d)).r;
	float left = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(-x_d, 0.0)).r;
	float right = texture2D(AlphaMap, gl_TexCoord[0].st + vec2(x_d, 0.0)).r;

	vec3 n = normalize(vec3(bottom - top, left - right, 2.0));
	
	Intensity = dot(lightdirection,normal) * 2;
	Intensity = clamp(Intensity, 0.2, 1);

	float Alpha = texture2D(AlphaMap, gl_TexCoord[0].st).r;
	MyColour = texture2D(ColourMap, gl_TexCoord[0].st);
	WaterColour = vec4(0.29, 0.15, 0.39 + Alpha*2, 1);
	peakColour = vec4(1 * Alpha, 1 * Alpha, 1 * Alpha, 1);
	LandColour = (MyColour + (vec4(0.2,0.2,0.2,1) * vec4(Alpha - 0.2, Alpha - 0.2, Alpha - 0.2, 1)));
	
	
	float visibility = texture2D(ShadowMap, shadowPos);
	if(shadowPos.w >= visibility){
		visibility = 0.5;
	}
	else{
		visibility = 0;
	}
	
	
	if(Alpha < 0.3){
		LandColour = vec4(Opacity * WaterColour.r + (1 - Opacity) * LandColour.r, Opacity * WaterColour.g + (1 - Opacity) * LandColour.g, Opacity * WaterColour.b + (1 - Opacity) * LandColour.b, 1);
	}
	if(Alpha>0.99){
		LandColour = vec4(Opacity * peakColour.r + (1 - Opacity) * LandColour.r, Opacity * peakColour.g + (1 - Opacity) * LandColour.g, Opacity * peakColour.b + (1 - Opacity) * LandColour.b, 1);
	}
	gl_FragColor = LandColour * vec4(Intensity, 1);
	//gl_FragColor = vec4(normal, 1);
    
}