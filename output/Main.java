import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author duc
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastWriter out = new FastWriter(outputStream);
        VNOI_CF_IOIBIN solver = new VNOI_CF_IOIBIN();
        solver.solve(1, in, out);
        out.close();
    }

    static class VNOI_CF_IOIBIN {
        public void solve(int testNumber, FastScanner in, FastWriter out) throws IOException {
        /*int q = in.nextLineAsInt();
        IntNDArray input = in.nextLinesAs2DIntArray(new NDShape(q, 3));
        int n = 0;
        for (int i = 0; i < q; i++) {
            n = Math.max(n, input.get(i, 0));
            n = Math.max(n, input.get(i, 1));
        }
        DisjointSet ds = new DisjointSet(n);
        for (int i = 0; i < q; i++) {
            int u = input.get(i, 0) - 1;
            int v = input.get(i, 1) - 1;
            if (input.get(i, 2) == 1) {
                ds.merge(u, v);
            } else {
                out.write(ds.inSameSet(u, v) ? 1 : 0);
                out.write("\n");
            }
        }*/


            int q = in.nextLineAsInt();
            DisjointSet ds = new DisjointSet(10000);
            for (int i = 0; i < q; i++) {
                int[] line = in.readTokensAsIntArray(3);
                int u = line[0] - 1;
                int v = line[1] - 1;
                int t = line[2];
                if (t == 1) {
                    ds.merge(u, v);
                } else {
                    out.write(ds.inSameSet(u, v) ? 1 : 0);
                    out.write("\n");
                }
            }
            out.flush();
        }

    }

    static class FastScanner {
        private final BufferedReader bufferedReader;

        public FastScanner(InputStream inputStream) {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public int nextLineAsInt() throws IOException {
            return Integer.parseInt(bufferedReader.readLine().trim());
        }

        public int[] readTokensAsIntArray(int capacity) throws IOException {
            String line = bufferedReader.readLine();
            int[] result = new int[capacity];
            int cur = 0;
            int pos = 0;
            for (int i = 0; i <= line.length(); i++) {
                char c = i == line.length() ? ' ' : line.charAt(i);
                if ('0' <= c && c <= '9') {
                    cur = cur * 10 + (c - '0');
                } else {
                    result[pos] = cur;
                    pos++;
                    cur = 0;
                    if (pos >= capacity) {
                        break;
                    }
                }
            }
            return result;
        }

    }

    static class FastWriter {
        private final BufferedWriter bufferedWriter;

        public FastWriter(OutputStream outputStream) {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        }

        public FastWriter(Writer writer) {
            bufferedWriter = new BufferedWriter(writer);
        }

        public void close() throws IOException {
            bufferedWriter.flush();
            bufferedWriter.close();
        }

        public void write(String token) throws IOException {
            bufferedWriter.write(token);
            //bufferedWriter.flush();
        }

        public void write(int token) throws IOException {
            bufferedWriter.write(String.valueOf(token));
            //bufferedWriter.flush();
        }

        public void flush() throws IOException {
            bufferedWriter.flush();
        }

    }

    static class DisjointSet {
        private final int size;
        private final int[] parent;

        public DisjointSet(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        private int getRoot(int u) {
            if (parent[u] == u) {
                return u;
            } else {
                return parent[u] = getRoot(parent[u]);
            }
        }

        public void merge(int u, int v) {
            parent[getRoot(u)] = getRoot(v);
        }

        public boolean inSameSet(int u, int v) {
            return getRoot(u) == getRoot(v);
        }

    }
}

