import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder {

    private static HuffmanNode makeTree(String s) {
        HashMap<Character, Integer> m = new HashMap<>();
        for (int i = 0; i < s.length(); i++)
            m.put(s.charAt(i), m.getOrDefault(s.charAt(i), 0) + 1);
            
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(m.size(), (HuffmanNode a, HuffmanNode b) -> a.value - b.value);
        
        for (Map.Entry<Character, Integer> e : m.entrySet())
            pq.add(new HuffmanNode(e.getKey(), e.getValue()));
            
        HuffmanNode root = null;

        // if (pq.size() == 1) {
        //     pq.add(new HuffmanNode((char)1, 0));
        // }
            
        while (pq.size() > 1) {
            HuffmanNode a = pq.peek(); pq.poll();
            HuffmanNode b = pq.peek(); pq.poll();
            root = new HuffmanNode(a.value + b.value, a, b);
            pq.add(root);
        }
        
        return root;
    }

    private static class BitCode {
        public int code;
        public int bitCount;
        public BitCode(int code, int bitCount) {
            this.code = code;
            this.bitCount = bitCount;
        }
    }
    
    private static void makeTable(HuffmanNode tree, int currentCode, int currentBitCount, HashMap<Character, BitCode> table) {
        if (tree.character == 0) {
            makeTable(tree.left, (currentCode << 1) | 1, currentBitCount + 1, table);
            makeTable(tree.right, currentCode << 1, currentBitCount + 1, table);
        } else {
            table.put(tree.character, new BitCode(currentCode, currentBitCount));
        }
    }

    private static void putTree(HuffmanNode tree, BitStream stream) {
        if (tree.character == 0) {
            putTree(tree.left, stream);
            putTree(tree.right, stream);
            stream.putBit(0);
        } else {
            stream.putBit(1);
            stream.putChar(tree.character);
        }
    }

    public static void encode(String s, BitStream stream) {
        HuffmanNode tree = makeTree(s);

        stream.putInt(s.length());

        putTree(tree, stream);
        stream.putBit(0);

        HashMap<Character, BitCode> table = new HashMap<>();
        makeTable(tree, 0, 0, table);
        for (int i = 0; i < s.length(); i++) {
            BitCode bitCode = table.get(s.charAt(i));
            stream.putBits(bitCode.code, bitCode.bitCount);
        }
    }

}