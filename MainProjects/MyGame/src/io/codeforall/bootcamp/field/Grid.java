package io.codeforall.bootcamp.field;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Grid {
        private static final int CELL_SIZE = 40;  // Tamanho de cada célula
        private static final int COLS = 80;       // Número de colunas
        private static final int ROWS = 80;       // Número de linhas
        private static final int PADDING = 10;    // Margem da grid
        public Grid() {
            drawGrid();  // Desenha a grid ao inicializar
        }
        public void drawGrid() {
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    Rectangle cell = new Rectangle(getX(col), getY(row), CELL_SIZE, CELL_SIZE);
                    cell.setColor(Color.LIGHT_GRAY);
                    cell.draw();
                      // Apenas desenha as bordas
                }
            }
        }
        // Calcula a coordenada X com base na coluna
        public static int getX(int col) {
            return PADDING + col * CELL_SIZE;
        }
        // Calcula a coordenada Y com base na linha
        public static int getY(int row) {
            return PADDING + row * CELL_SIZE;
        }
}
