public class Matriz4x4 {
	float m[][] = new float[4][4];

	public Matriz4x4() {
		setIdentidade();
	}

	public void setIdentidade() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				m[i][j] = (i == j) ? 1 : 0;
			}
		}
	}

	public static Matriz4x4 translacao(float tx, float ty, float tz) {
		Matriz4x4 r = new Matriz4x4();
		r.m[0][3] = tx;
		r.m[1][3] = ty;
		r.m[2][3] = tz;
		return r;
	}

	public static Matriz4x4 escala(float sx, float sy, float sz) {
		Matriz4x4 r = new Matriz4x4();
		r.m[0][0] = sx;
		r.m[1][1] = sy;
		r.m[2][2] = sz;
		return r;
	}

	public static Matriz4x4 rotacaoX(float ang) {
		float c = (float)Math.cos(ang);
		float s = (float)Math.sin(ang);
		Matriz4x4 r = new Matriz4x4();
		r.m[1][1] = c;
		r.m[1][2] = -s;
		r.m[2][1] = s;
		r.m[2][2] = c;
		return r;
	}

	public static Matriz4x4 rotacaoY(float ang) {
		float c = (float)Math.cos(ang);
		float s = (float)Math.sin(ang);
		Matriz4x4 r = new Matriz4x4();
		r.m[0][0] = c;
		r.m[0][2] = s;
		r.m[2][0] = -s;
		r.m[2][2] = c;
		return r;
	}

	public static Matriz4x4 rotacaoZ(float ang) {
		float c = (float)Math.cos(ang);
		float s = (float)Math.sin(ang);
		Matriz4x4 r = new Matriz4x4();
		r.m[0][0] = c;
		r.m[0][1] = -s;
		r.m[1][0] = s;
		r.m[1][1] = c;
		return r;
	}

	public static Matriz4x4 rotacaoEixo(float ux, float uy, float uz, float ang) {
		float len = (float)Math.sqrt(ux*ux + uy*uy + uz*uz);
		ux = ux/len;
		uy = uy/len;
		uz = uz/len;

		float c = (float)Math.cos(ang);
		float s = (float)Math.sin(ang);
		float t = 1 - c;

		Matriz4x4 r = new Matriz4x4();
		r.m[0][0] = c + ux*ux*t;
		r.m[0][1] = ux*uy*t - uz*s;
		r.m[0][2] = ux*uz*t + uy*s;

		r.m[1][0] = uy*ux*t + uz*s;
		r.m[1][1] = c + uy*uy*t;
		r.m[1][2] = uy*uz*t - ux*s;

		r.m[2][0] = uz*ux*t - uy*s;
		r.m[2][1] = uz*uy*t + ux*s;
		r.m[2][2] = c + uz*uz*t;
		return r;
	}

	public Matriz4x4 multiplica(Matriz4x4 o) {
		Matriz4x4 r = new Matriz4x4();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				float soma = 0;
				for(int k = 0; k < 4; k++) {
					soma += m[i][k]*o.m[k][j];
				}
				r.m[i][j] = soma;
			}
		}
		return r;
	}

	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < 4; i++) {
			s += "[ ";
			for(int j = 0; j < 4; j++) {
				s += String.format("%8.3f ", m[i][j]);
			}
			s += "]\n";
		}
		return s;
	}
}
