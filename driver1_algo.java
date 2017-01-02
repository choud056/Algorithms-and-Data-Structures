/*
 * CSCI4041
 * Project 1
 * Submission By: Neelu Choudhary (5085296)
 * Main Class: driver1
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// CLASS BITARRAY
class BitArray {
	private int a[];
	private int M;
		
	public BitArray(int M){
		this.M=M;
		if (M<0){
			throw new IllegalArgumentException(Integer.toString(M));
		}
		else{
			int temp = (int)Math.ceil(M/32.0);
			a=new int[temp];
		Arrays.fill(a,0);
		// There is no need for array b[], as we are only using array a[]
			}
	}

public boolean get(int n){
		if(n<0 || n>= M){
			throw new IllegalArgumentException(Integer.toString(M));
		}
		else{
			int m = (int) Math.pow(2,n % 32);
		   int temp1= (int) Math.floor(n/32.0);
			if ((m & a[temp1])==0){
				return false;
			}else{
				return true;
			}
		}
	}
	
public void set(int n){
		if(n<0 || n>= M) {
			throw new IllegalArgumentException(Integer.toString(M));
		}
		else{
			int m =(int) Math.pow(2,n % 32);
		   int temp1= (int) Math.floor(n/32.0);
		   if((m & a[temp1])==0)
           {
               a[n/32]=(a[n/32] | m);
           }
	}
}
}

// Second class

class BloomFilter {
    private int M, j=3;
    private double N=850.0;
    private BitArray instBitArray;
    
	public BloomFilter(int M){
        this.M=M;
		if (M<0){
			throw new IllegalArgumentException(Integer.toString(M));
		}
		else{
			instBitArray = new BitArray(M);
		}
	}
 	
	private int h1(String w){
		int t=0;
		for (int j=0; j<w.length(); j++){
			t= (t << 1) ^ (w.charAt(j)) ;
			}
		t=Math.abs(t) % M;
		return t;
		}
	
	private int h2(String w){
	int h=0;
    for (int i = 0; i < w.length(); i++) {
        h = (h << 5) ^ ( w.charAt(i));
    }
    h=Math.abs(h)%M;
    return h;
}
	
	private int h3(String w){
		   int i, sum;
		   for (sum=0, i=0; i < w.length(); i++)
		     sum += w.charAt(i);
		   sum= sum % M;
		   return sum;
	}
	
	public void add(String w){
		instBitArray.set(h1(w));
		instBitArray.set(h2(w));
		instBitArray.set(h3(w));
	}
  
   public boolean isIn(String w){
	   return instBitArray.get(h1(w)) && instBitArray.get(h2(w)) && instBitArray.get(h3(w));
   }
	   
   public double accuracy(){
	   return Math.pow((1-Math.exp((-1*j*N)/M)), j);
   }
}

// Class to read the basic.txt file

class LineReader
{
  private String         line;    //  Line waiting to be returned by NEXT.
  private BufferedReader reader;  //  Where to read LINEs from.

//  Constructor. Make a new instance of LINE READER. It reads LINEs from a file
//  whose pathname is PATH.

  public LineReader(String path)
  {
    try
    {
      reader = new BufferedReader(new FileReader(path));
      line = reader.readLine();
    }
    catch (IOException ignore)
    {
      throw new IllegalArgumentException("Can't open '" + path + "'.");
    }
  }

//  HAS NEXT. Test if a LINE is waiting to be returned by NEXT.

  public boolean hasNext()
  {
    return line != null;
  }

//  NEXT. Return the current LINE from PATH and advance to the next LINE, if it
//  exists.

  public String next()
  {
    try
    {
      String temp = line;
      line = reader.readLine();
      return temp;
    }
    catch (IOException ignore)
    {
      throw new IllegalStateException("Can't read any more lines.");
    }
  }
}

//Main Class named driver1

public class driver1 {
	public static void main(String args[]){
		BloomFilter instBloomFilter = new BloomFilter(6857);
		LineReader reader = new LineReader("D:\\Users\\Neelu Choudhary\\workspace\\bucky\\src\\basic.txt");

	      while (reader.hasNext())
	      {
	    	  String basic = reader.next();
	        instBloomFilter.add(basic);
	      }
	      //System.out.println();
	      reader = new LineReader("D:\\Users\\Neelu Choudhary\\workspace\\bucky\\src\\basic.txt");
	      while (reader.hasNext())
	      {
	    	String basic1 = reader.next();
	        //System.out.println(basic1);
	        //System.out.println(instBloomFilter.isIn(basic1));
	    	
	        if(!instBloomFilter.isIn(basic1))
	        {
	            System.out.println(basic1);
	        }
	      }
	      System.out.println("The error (Probability that the method isIn will report a word is in the set, when it is not) of my Bloom Filer is " + instBloomFilter.accuracy());
	}
	
}

// The output of my Bloom Filter is:

/* 
 * The error (Probability that the method isIn will report a word is in the set, when it is not) of my Bloom Filer is  0.029954164646440597
 */
