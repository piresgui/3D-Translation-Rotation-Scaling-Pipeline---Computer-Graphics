import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainCanvas extends JPanel implements Runnable{
	int W = 640;
	int H = 480;

	Thread runner;
	boolean ativo = true;
	int paintcounter = 0;

	BufferedImage imageBuffer;
	byte bufferDeVideo[];

	Random rand = new Random();

	byte memoriaPlacaVideo[];
	short paleta[][];

	int framecount = 0;
	int fps = 0;

	Font f = new Font("", Font.PLAIN, 30);

	int clickX = 0;
	int clickY = 0;
	int mouseX = 0;
	int mouseY = 0;

	int pixelSize = 0;
	int Largura = 0;
	int Altura = 0;

	BufferedImage imgtmp = null;

	float posx = 00;
	float posy = 00;

	boolean LEFT = false;
	boolean RIGHT = false;
	boolean UP = false;
	boolean DOWN = false;

	float filtroR = 1;
	float filtroG = 1;
	float filtroB = 1;

	float q1x = 10,q1y = 100;
	float q2x = 10,q2y = 200;

	ArrayList<Linha2D> linhas = new ArrayList<Linha2D>();

	Ponto2D p0 = null;
	Ponto2D pC = new Ponto2D(320, 240);

	ArrayList<Triangulo3D> triangulos = new ArrayList<Triangulo3D>();

	Ponto3D eixoP1 = new Ponto3D(450, 180, 0);
	Ponto3D eixoP2 = new Ponto3D(450, 380, 0);

	public MainCanvas() {

		File f = new File("imgbmp.bmp");
		try {
			FileInputStream fin = new FileInputStream(f);

			byte todosodbytes[] = new byte[64000];
			int byteslidos = fin.read(todosodbytes);
			System.out.println("Bytes Lidos "+byteslidos);
			for(int i = 0; i < byteslidos;i++) {
				System.out.println(i+": "+todosodbytes[i]);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		linhas.add(new Linha2D(200,100,250,200));
		linhas.add(new Linha2D(250,200,150,200));
		linhas.add(new Linha2D(150,200,200,100));

		Ponto3D v0 = new Ponto3D( 1,  1,  1);
		Ponto3D v1 = new Ponto3D( 1, -1, -1);
		Ponto3D v2 = new Ponto3D(-1,  1, -1);
		Ponto3D v3 = new Ponto3D(-1, -1,  1);
		triangulos.add(new Triangulo3D(v0, v1, v2));
		triangulos.add(new Triangulo3D(v0, v3, v1));
		triangulos.add(new Triangulo3D(v0, v2, v3));
		triangulos.add(new Triangulo3D(v1, v3, v2));

		Matriz4x4 inicial = Matriz4x4.translacao(450, 280, 0).multiplica(Matriz4x4.escala(60, 60, 60));
		for(int i = 0; i < triangulos.size();i++) {
			triangulos.get(i).transforma(inicial);
		}

		setSize(640,480);
		setFocusable(true);

		Largura = 640;
		Altura = 480;

		pixelSize = 640*480;

		imgtmp = loadImage("gato.jpg");

		imageBuffer = new BufferedImage(640,480, BufferedImage.TYPE_4BYTE_ABGR);

		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();

		System.out.println("Buffer SIZE "+bufferDeVideo.length );

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_W) {
					UP = false;
				}
				if(key == KeyEvent.VK_S) {
					DOWN = false;
				}
				if(key == KeyEvent.VK_A) {
					LEFT = false;
				}
				if(key == KeyEvent.VK_D) {
					RIGHT = false;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();

				if(key == KeyEvent.VK_W) {
					UP = true;
					for(int i = 0; i < linhas.size();i++) {
						linhas.get(i).translate(0, -10);
					}
				}
				if(key == KeyEvent.VK_S) {
					DOWN = true;
					for(int i = 0; i < linhas.size();i++) {
						linhas.get(i).translate(0, 10);
					}
				}
				if(key == KeyEvent.VK_A) {
					LEFT = true;
					for(int i = 0; i < linhas.size();i++) {
						linhas.get(i).translate(-10, 0);
					}
				}
				if(key == KeyEvent.VK_D) {
					RIGHT = true;
					for(int i = 0; i < linhas.size();i++) {
						linhas.get(i).translate(10, 0);
					}
				}
				if(key == KeyEvent.VK_Z) {
					RIGHT = true;
					for(int i = 0; i < linhas.size();i++) {
						linhas.get(i).scale(1.25f, 1.25f);
					}
				}
				if(key == KeyEvent.VK_X) {
					RIGHT = true;
					for(int i = 0; i < linhas.size();i++) {
						linhas.get(i).scale(0.75f, 0.75f);
					}
				}
				if(key == KeyEvent.VK_Q) {
					RIGHT = true;
					for(int i = 0; i < linhas.size();i++) {
						linhas.get(i).translate(-pC.X, -pC.Y);
						linhas.get(i).rotate((float)(Math.PI/16));
						linhas.get(i).translate(pC.X, pC.Y);
					}
				}
				if(key == KeyEvent.VK_E) {
					RIGHT = true;
					for(int i = 0; i < linhas.size();i++) {
						linhas.get(i).translate(-pC.X, -pC.Y);
						linhas.get(i).rotate((float)(-Math.PI/16));
						linhas.get(i).translate(pC.X, pC.Y);
					}
				}
				if(key == KeyEvent.VK_UP) {
					transformaObjeto3D(Matriz4x4.translacao(0, -10, 0));
				}
				if(key == KeyEvent.VK_DOWN) {
					transformaObjeto3D(Matriz4x4.translacao(0, 10, 0));
				}
				if(key == KeyEvent.VK_LEFT) {
					transformaObjeto3D(Matriz4x4.translacao(-10, 0, 0));
				}
				if(key == KeyEvent.VK_RIGHT) {
					transformaObjeto3D(Matriz4x4.translacao(10, 0, 0));
				}
				if(key == KeyEvent.VK_N) {
					transformaObjeto3D(Matriz4x4.escala(1.25f, 1.25f, 1.25f));
				}
				if(key == KeyEvent.VK_M) {
					transformaObjeto3D(Matriz4x4.escala(0.75f, 0.75f, 0.75f));
				}
				if(key == KeyEvent.VK_I) {
					transformaObjeto3D(Matriz4x4.rotacaoX((float)(Math.PI/16)));
				}
				if(key == KeyEvent.VK_K) {
					transformaObjeto3D(Matriz4x4.rotacaoX((float)(-Math.PI/16)));
				}
				if(key == KeyEvent.VK_J) {
					transformaObjeto3D(Matriz4x4.rotacaoY((float)(Math.PI/16)));
				}
				if(key == KeyEvent.VK_L) {
					transformaObjeto3D(Matriz4x4.rotacaoY((float)(-Math.PI/16)));
				}
				if(key == KeyEvent.VK_U) {
					transformaObjeto3D(Matriz4x4.rotacaoZ((float)(Math.PI/16)));
				}
				if(key == KeyEvent.VK_O) {
					transformaObjeto3D(Matriz4x4.rotacaoZ((float)(-Math.PI/16)));
				}
				if(key == KeyEvent.VK_Y) {
					rotacionaObjetoEixo((float)(Math.PI/16));
				}
				if(key == KeyEvent.VK_H) {
					rotacionaObjetoEixo((float)(-Math.PI/16));
				}
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

				clickX = e.getX();
				clickY = e.getY();

				if(e.getButton()==1) {

					if(p0==null) {
						p0 = new Ponto2D(clickX, clickY);
					}else {
						linhas.add(new Linha2D(p0.X, p0.Y, clickX, clickY));
						p0 = null;
					}
				}else if(e.getButton()==3) {
					pC = new Ponto2D(clickX, clickY);
				}

				System.out.println("CLICO "+e.getButton());
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {

				mouseX = arg0.getX();
				mouseY = arg0.getY();
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {

			}
		});

	}
	private void drawImageToBuffer(BufferedImage image,int x,int y, float fr, float fg, float fb) {
		byte[] imgBuffer = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();

		int iw = image.getWidth();
		int ih = image.getHeight();

		for(int yi = 0; yi < ih; yi++) {
			for(int xi = 0; xi < iw; xi++) {
				int pixi = yi*iw*4 + xi*4;
				int pixb = (yi+y)*W*4 + (xi+x)*4;
				bufferDeVideo[pixb] = imgBuffer[pixi];

				int b = (imgBuffer[pixi+1]&0xff);
				int g =	(imgBuffer[pixi+2]&0xff);
				int r = (imgBuffer[pixi+3]&0xff);

				b = (int)(b*fb);
				g = (int)(g*fg);
				r = (int)(r*fr);

				b = Math.min(255, b);
				g = Math.min(255, g);
				r = Math.min(255, r);

				bufferDeVideo[pixb+1] = (byte)(b&0xff);
				bufferDeVideo[pixb+2] = (byte)(g&0xff);
				bufferDeVideo[pixb+3] = (byte)(r&0xff);
			}
		}
	}
	@Override
	public void paint(Graphics g) {

		for(int i = 0; i < bufferDeVideo.length; i++) {
			bufferDeVideo[i] = 0;
		}

		g.setFont(f);

		g.setColor(Color.white);
		g.fillRect(0, 0, 640, 480);

		g.drawImage(imageBuffer,0,0,null);

		g.setColor(Color.black);

		for(int i = 0; i < linhas.size();i++) {
			linhas.get(i).draw(g);
		}

		g.setColor(new Color(0,128,0));
		for(int i = 0; i < triangulos.size();i++) {
			triangulos.get(i).draw(g);
		}

		g.setColor(Color.magenta);
		g.drawLine((int)eixoP1.X, (int)eixoP1.Y, (int)eixoP2.X, (int)eixoP2.Y);
		g.fillOval((int)eixoP1.X-3, (int)eixoP1.Y-3, 6, 6);
		g.fillOval((int)eixoP2.X-3, (int)eixoP2.Y-3, 6, 6);

		g.setColor(Color.red);
		if(p0!=null) {
			g.drawLine((int)p0.X, (int)p0.Y, mouseX, mouseY);
		}

		g.setColor(Color.BLUE);
		if(pC!=null) {
			g.fillRect((int)pC.X-2, (int)pC.Y-2, 5, 5);
		}

		g.setColor(Color.black);
		g.drawString("FPS "+fps+" mouse: "+mouseX+","+mouseY, 10, 25);
	}

	public void desenhaLinhaHorizontal(int x, int y,int w) {
		int pospix = y*(W*4)+x*4;

		for(int i = 0; i < w;i++) {

			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=4;
		}
	}

	public void desenhaLinhaVertical(int x, int y,int h) {
		int pospix = y*(W*4)+x*4;

		for(int i = 0; i < h;i++) {

			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)255;
			pospix+=(W*4);
		}
	}

	public void desenhaPixel(int x, int y,int r,int g,int b) {
		int pospix = y*(W*4)+x*4;

		bufferDeVideo[pospix] = (byte)255;
		bufferDeVideo[pospix+1] = (byte)(b&0xff);
		bufferDeVideo[pospix+2] = (byte)(g&0xff);
		bufferDeVideo[pospix+3] = (byte)(r&0xff);

	}

	public void transformaObjeto3D(Matriz4x4 m) {
		float cx = 0, cy = 0, cz = 0;
		for(int i = 0; i < triangulos.size();i++) {
			Ponto3D c = triangulos.get(i).centro();
			cx += c.X;
			cy += c.Y;
			cz += c.Z;
		}
		int n = triangulos.size();
		cx = cx/n;
		cy = cy/n;
		cz = cz/n;

		Matriz4x4 composta = Matriz4x4.translacao(cx, cy, cz).multiplica(m).multiplica(Matriz4x4.translacao(-cx, -cy, -cz));
		for(int i = 0; i < triangulos.size();i++) {
			triangulos.get(i).transforma(composta);
		}
	}

	public void rotacionaObjetoEixo(float ang) {
		Matriz4x4 m = Matriz4x4.rotacaoEixoPontos(eixoP1, eixoP2, ang);
		for(int i = 0; i < triangulos.size();i++) {
			triangulos.get(i).transforma(m);
		}
	}

	public void start(){
		runner = new Thread(this);
		runner.start();
	}

	int timer = 0;
	public void simulaMundo(long diftime){

		float difS = diftime/1000.0f;
		float vel = 50;

		timer+=diftime;

	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		long segundo = time/1000;
		long diftime = 0;
		while(ativo){
			simulaMundo(diftime);
			paintImmediately(0, 0, 640, 480);
			paintcounter+=100;

			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			long newtime = System.currentTimeMillis();
			long novoSegundo = newtime/1000;
			diftime = System.currentTimeMillis() - time;
			time = System.currentTimeMillis();
			framecount++;
			if(novoSegundo!=segundo) {
				fps = framecount;
				framecount = 0;
				segundo = novoSegundo;
			}
		}
	}

	public BufferedImage loadImage(String filename) {
		try {
			imgtmp = ImageIO.read(new File(filename));

			BufferedImage imgout = new BufferedImage(imgtmp.getWidth(), imgtmp.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

			imgout.getGraphics().drawImage(imgtmp, 0, 0, null);

			imgtmp = null;

			return imgout;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}
