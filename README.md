# Computer Graphics M1 — Guilherme Pires & Lucas Bitencourt

Java/Swing assignments for the Computer Graphics course (UNIVALI), building a small software rendering pipeline from scratch: raw pixel buffers, 2D primitives, and 3D transformations with homogeneous coordinates.

## Projects

### CG_1
Raw framebuffer manipulation with `BufferedImage` and direct byte-array pixel access, plus BMP file loading.

### CG_1_2
Extends CG_1 with image compositing (drawing a loaded image into the pixel buffer) and per-channel color filtering.

### CG_2_1
2D line drawing and interactive 2D transformations (translate, scale, rotate) driven by mouse and keyboard, plus a 3D pipeline: `Ponto3D`, `Triangulo3D`, and `Matriz4x4` implement translation, scaling, and rotation around the X, Y and Z axes using 4x4 homogeneous matrices. A tetrahedron (4 triangles) is rendered with an orthographic projection and can be manipulated live.

## Running

Each project is a standalone Eclipse project with sources under `src/`. To run one from the command line (requires a JDK):

```bash
cd CG_2_1
javac -d bin src/*.java
java -cp bin MainClass
```

## Controls (CG_2_1)

3D object (tetrahedron):
- Arrow keys: translate on X/Y
- `N` / `M`: scale up / down
- `I` / `K`: rotate around X axis
- `J` / `L`: rotate around Y axis
- `U` / `O`: rotate around Z axis

2D triangle/lines:
- `W A S D`: translate
- `Z` / `X`: scale up / down
- `Q` / `E`: rotate
- Left click: add a point/line, right click: move rotation center
