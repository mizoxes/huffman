public class HuffmanNode {
    public int value;
    public char character;
    public HuffmanNode left, right;
    
    public HuffmanNode(char character, int value) {
        this.value = value;
        this.character = character;
        this.left = this.right = null;
    }
    
    public HuffmanNode(int value, HuffmanNode left, HuffmanNode right) {
        this.value = value;
        this.character = 0;
        this.left = left;
        this.right = right;
    }
}
