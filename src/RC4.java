import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ryan Kasprzyk
 * 
 * RC4 implementation
 */

public class RC4 {
    private static int[] S, T, key;
        
    public static void main(String[] args) throws FileNotFoundException, IOException{
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        String sKey = in.readLine();
        String[] aKey = sKey.split(" ");
        key = new int[aKey.length];
        for(int i = 0; i < key.length; i++){
            key[i] = Integer.parseInt(aKey[i]);
        }
        
        String sPlain = in.readLine();
        String[] plain = new String[sPlain.length()/2];
        for(int i = 0; i < plain.length; i++){
            plain[i] = sPlain.substring(i*2, i*2+2);
        }
        
        String sCipher = in.readLine();
        String[] cipher = new String[sCipher.length()/2];
        for(int i = 0; i < cipher.length; i++){
            cipher[i] = sCipher.substring(i*2, i*2+2);
        }
        in.close();
        
        BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
        initialize();
        String[] newCipher = cipher(plain);
        out.write(toString(newCipher));
        out.newLine();
        
        initialize();
        String[] newPlain = cipher(cipher);
        out.write(toString(newPlain));
        out.close();
    }
    
    public static void initialize(){
        S = new int[256];
        T = new int[256];
        int i, j;
        
        /* initialization */
        for(i = 0; i < 256; i++){
            S[i] = i;
            T[i] = key[i % key.length];
        }
        
        /* initial permutation of S */
        j = 0;
        for(i = 0; i < 256; i++){
            j = (j+S[i] + T[i]) % 256;
            swap(i,j);
        }
    }
    
    public static String[] cipher(String[] plain){
        int i = 0; int j = 0; int t;
        String[] cipher = new String[plain.length];
        
        for(int r = 0; r < plain.length; r++){
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;
            swap(i, j);
            t = (S[i] + S[j]) % 256;
            cipher[r] = decToHex(Integer.parseInt(plain[r], 16) ^ S[t]);
        }
        
        return cipher;
    }
                
    public static void swap(int a, int b){
        int temp = S[a];
        S[a] = S[b];
        S[b] = temp;
    }
    
    public static String decToHex(int dec){
        String hex = Integer.toHexString(dec);
        if(hex.length() != 2){
            hex = "0" + hex;
        }
        return hex;
    }
    
    public static String toString(String[] in){
        String out = "";
        for(int i = 0; i < in.length; i++){
            out+=in[i];
        }
        return out;
    }
}
