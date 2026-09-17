import java.awt.Graphics;

public class Triangulo3D {
	Ponto3D A;
	Ponto3D B;
	Ponto3D C;

	public Triangulo3D(float x1,float y1,float z1, float x2,float y2,float z2, float x3,float y3,float z3) {
		A = new Ponto3D(x1, y1, z1);
		B = new Ponto3D(x2, y2, z2);
		C = new Ponto3D(x3, y3, z3);
	}

	public Triangulo3D(Ponto3D a, Ponto3D b, Ponto3D c) {
		this(a.X, a.Y, a.Z, b.X, b.Y, b.Z, c.X, c.Y, c.Z);
	}

	public void draw(Graphics g) {
		g.drawLine((int)A.X, (int)A.Y, (int)B.X, (int)B.Y);
		g.drawLine((int)B.X, (int)B.Y, (int)C.X, (int)C.Y);
		g.drawLine((int)C.X, (int)C.Y, (int)A.X, (int)A.Y);
	}

	public Ponto3D centro() {
		return new Ponto3D((A.X+B.X+C.X)/3, (A.Y+B.Y+C.Y)/3, (A.Z+B.Z+C.Z)/3);
	}

	public void transforma(Matriz4x4 m) {
		A.transforma(m);
		B.transforma(m);
		C.transforma(m);
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
}
