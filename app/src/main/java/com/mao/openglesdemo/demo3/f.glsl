precision mediump float;
varying vec2 fTexCoord;
uniform sampler2D fTexture;
void main() {
    gl_FragColor = texture2D(fTexture,fTexCoord);
}
