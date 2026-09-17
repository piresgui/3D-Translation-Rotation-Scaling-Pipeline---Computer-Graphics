public class Ponto3D {
	float X;
	float Y;
	float Z;

	public Ponto3D(float x, float y, float z) {
		super();
		X = x;
		Y = y;
		Z = z;
	}

	public void transforma(Matriz4x4 mat) {
		float[][] m = mat.m;
		float nX = m[0][0]*X + m[0][1]*Y + m[0][2]*Z + m[0][3];
		float nY = m[1][0]*X + m[1][1]*Y + m[1][2]*Z + m[1][3];
		float nZ = m[2][0]*X + m[2][1]*Y + m[2][2]*Z + m[2][3];
		float nW = m[3][0]*X + m[3][1]*Y + m[3][2]*Z + m[3][3];

		if(nW != 1 && nW != 0) {
			nX = nX/nW;
			nY = nY/nW;
			nZ = nZ/nW;
		}

		X = nX;
		Y = nY;
		Z = nZ;
	}

	public void translate(float x, float y, float z) {
		transforma(Matriz4x4.translacao(x, y, z));
	}
	public void scale(float x, float y, float z) {
		transforma(Matriz4x4.escala(x, y, z));
	}
	public void rotateX(float ang) {
		transforma(Matriz4x4.rotacaoX(ang));
	}
	public void rotateY(float ang) {
		transforma(Matriz4x4.rotacaoY(ang));
	}
	public void rotateZ(float ang) {
		transforma(Matriz4x4.rotacaoZ(ang));
	}

	@Override
	public String toString() {
		return "(" + X + ", " + Y + ", " + Z + ")";
	}
}
