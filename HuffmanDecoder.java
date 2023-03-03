import java.util.Stack;

public class HuffmanDecoder {

    private static char decodeR(HuffmanNode tree, BitStream stream) {
        if (tree.character == 0) {
            int bit = stream.getBit();
            if (bit == 0)
                return decodeR(tree.right, stream);
            else
                return decodeR(tree.left, stream);
        } else {
            return tree.character;
        }
    }

    public static String decode(BitStream stream) {

        int originalLength = stream.getInt();

        Stack<HuffmanNode> st = new Stack<>();

        while (true) {
            int bit = stream.getBit();
            if (bit == 0) {
                if (st.size() == 1) {
                    break;
                } else {
                    HuffmanNode n1 = st.pop();
                    HuffmanNode n2 = st.pop();
                    st.push(new HuffmanNode(0, n2, n1));
                }
            } else {
                char c = stream.getChar();
                st.push(new HuffmanNode(c, 0));
            }
        }

        HuffmanNode tree = st.peek();

        String decoded = "";
        for (int i = 0; i < originalLength; i++) {
            decoded += decodeR(tree, stream);
        }
        return decoded;
    }


}