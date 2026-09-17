# 3D Translation, Rotation & Scaling Pipeline — Computer Graphics

Java/Swing assignment for the Computer Graphics course (UNIVALI): a small software rendering pipeline built from scratch, with 2D line drawing and a 3D transformation pipeline using homogeneous coordinates.

## What it does

2D line drawing and interactive 2D transformations (translate, scale, rotate) driven by mouse and keyboard, plus a 3D pipeline: `Ponto3D`, `Triangulo3D`, and `Matriz4x4` implement translation, scaling, and rotation around the X, Y and Z axes using 4x4 homogeneous matrices. A tetrahedron (4 triangles) is rendered and can be manipulated live, including rotation around an arbitrary axis defined by two points in space (shown as a magenta segment). Rendering can be switched between an orthographic (drop-Z) and an oblique projection.

## Running

Standalone Eclipse project with sources under `src/`. To run it from the command line (requires a JDK):

```bash
javac -d bin src/*.java
java -cp bin MainClass
```

## Controls

3D object (tetrahedron):
- Arrow keys: translate on X/Y
- `N` / `M`: scale up / down
- `I` / `K`: rotate around X axis
- `J` / `L`: rotate around Y axis
- `U` / `O`: rotate around Z axis
- `Y` / `H`: rotate around the two-point axis (magenta segment)
- `P`: toggle orthographic / oblique projection

2D triangle/lines:
- `W A S D`: translate
- `Z` / `X`: scale up / down
- `Q` / `E`: rotate
- Left click: add a point/line, right click: move rotation center
