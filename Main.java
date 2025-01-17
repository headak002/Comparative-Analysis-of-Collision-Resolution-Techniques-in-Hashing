import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HashTableVisualizer extends JPanel implements ActionListener {

    private static final int SIZE = 15;
    private static final int CIRCLE_RADIUS = 20;
    private static final int MARGIN = 20;
    private static final int SPACING = 10;
    private static final Color TABLE_COLOR = new Color(230, 230, 230);
    private static final Color BORDER_COLOR = new Color(100, 100, 100);
    private static final Color KEY_COLOR = new Color(70, 130, 180);
    private static final Color COLLISION_COLOR = new Color(255, 99, 71);
    private static final int ARROW_LENGTH = 20;

    private ArrayList<Integer>[] hashTable;
    private int[] keys;
    private int currentKeyIndex;

    public HashTableVisualizer() {
        hashTable = new ArrayList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            hashTable[i] = new ArrayList<>();
        }

        keys = new int[]{2, 15, 23, 17, 31, 12, 8, 4, 11, 5, 9, 6, 7, 1, 10};
        currentKeyIndex = 0;

        setPreferredSize(new Dimension(SIZE * (CIRCLE_RADIUS * 2 + SPACING) + MARGIN * 2));

        Timer timer = new Timer(100, this); // Increased timer interval for a slower animation
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        // Draw the hash table slots
        g2.setColor(TABLE_COLOR);
        g2.fillRect(MARGIN, MARGIN, SIZE * (CIRCLE_RADIUS * 2 + SPACING), SIZE * CIRCLE_RADIUS);

        for (int i = 0; i < SIZE; i++) {
            int x = MARGIN + i * (CIRCLE_RADIUS * 2 + SPACING);
            int y = MARGIN;
            int width = CIRCLE_RADIUS * 2;
            int height = SIZE * CIRCLE_RADIUS;
            g2.setColor(BORDER_COLOR);
            g2.drawRect(x, y, width, height);

            // Add labels for hash table slots
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString(String.valueOf(i), x + CIRCLE_RADIUS, y + CIRCLE_RADIUS / 2);

            // Draw the circles for keys
            for (int j = 0; j < hashTable[i].size(); j++) {
                int key = hashTable[i].get(j);
                int keyX = x + CIRCLE_RADIUS;
                int keyY = MARGIN + (SIZE - 1 - i) * CIRCLE_RADIUS - j * CIRCLE_RADIUS;

                g2.setColor(KEY_COLOR);
                g2.fillOval(keyX - CIRCLE_RADIUS, keyY - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);

                // Add labels for inserted keys
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString(String.valueOf(key), keyX, keyY + CIRCLE_RADIUS / 2);
            }
        }
    }

    private int hashFunction(int key, int size) {
        return key % size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentKeyIndex < keys.length) {
            int key = keys[currentKeyIndex];
            int index = hashFunction(key, SIZE);

            hashTable[index].add(key);

            currentKeyIndex++;

            repaint();
        }
    }

    public static void main(String[] args) {
        HashTableVisualizer visualizer = new HashTableVisualizer();

        JFrame frame = new JFrame("Hash Table Visualizer");
        frame.add(visualizer);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
