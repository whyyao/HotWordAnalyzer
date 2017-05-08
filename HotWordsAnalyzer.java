//Yuan Yao, perm:8647851, yuanyao00@umail.ucsb.edu
import java.io.*;
import java.util.ArrayList;

public class HotWordsAnalyzer {

	private ArrayList<String> file=new ArrayList<String>();
	private ArrayList<String> mFile=new ArrayList<String>();
	private ArrayList<String> hotWords=new ArrayList<String>();
	private ArrayList<String> realHotWords=new ArrayList<String>();
	private static int sizeOfHot;
	private static int sizeOfmFile;
	
	public HotWordsAnalyzer(String fileHotWords, String document){
		//read files
		FileInputStream fstream;
		try{
			fstream= new FileInputStream(document);
			BufferedReader br= new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			
			while((strLine=br.readLine())!=null){
				String[] parts = strLine.split("\\W+");
				for(int i=0;i<parts.length;i++){
					file.add(parts[i].toLowerCase());
				}
			}
			br.close();
		}catch (Exception e){
				System.out.println("Something went wrong!");
				e.printStackTrace();
			}
		FileInputStream fstream2;
		try{
			fstream2= new FileInputStream(fileHotWords);
			BufferedReader br2= new BufferedReader(new InputStreamReader(fstream2));
			String strLine2;
			while((strLine2=br2.readLine())!=null){
				String[] parts = strLine2.split("\\W+");
				for(int i=0;i<parts.length;i++){
					realHotWords.add(parts[i]);
				}
			}
			br2.close();
		}catch (Exception e){
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}
		for (int j=0;j<realHotWords.size();j++){
			hotWords.add(realHotWords.get(j).toLowerCase());
		}
		//fill in occurrence
		
		for (int i=0;i<file.size();i++){
			for (int j=0;j<hotWords.size();j++){
				if((file.get(i)).equals(hotWords.get(j))){
					mFile.add(hotWords.get(j));
				}
			}
		}
		sizeOfHot=hotWords.size();
		sizeOfmFile=mFile.size();
		
	}
	
	public void printHotWords(){
		for (int i=0;i<sizeOfHot;i++){
			System.out.print(hotWords.get(i)+"/ ");
		}
	}
//	
//	public void printmFile(){
//		for (int i=0;i<sizeOfmFile;i++){
//			System.out.print(mFile.get(i)+"/ ");
//		}
//	}
	
	public int count(){
		return mFile.size();
	}
	public int count( String hotWord ){
		String word=hotWord.toLowerCase();
		boolean temp=false;
		for (int i=0;i<sizeOfHot;i++){
			if(hotWords.get(i).equals(word)){
				temp=true;
			}
		}
		if(temp==true){
		int count=0;
		for(int i=0;i<sizeOfmFile;i++){
			if(mFile.get(i).equals(word)){
				count++;
			}
		}
		return count;
		}
		else{
			return -1;
		}
	}
    public int distinctCount(){
    	int count=0;
    	for (int i=0;i<sizeOfHot;i++){
    		for (int j=0;j<sizeOfmFile;j++){
    			if (hotWords.get(i).equals(mFile.get(j))){
    				count++;
    				break;
    			}
    		}
    	}
    	return count;
    }
    public String[] topHotWords( int count ){
    	if (count==0)
    		count=1;
    	if (count>distinctCount()){
    		count=distinctCount();
    	}
    	String result[]=new String[count];
    	int occ[]=new int[sizeOfHot];
    	for (int i=0;i<sizeOfHot;i++){
    		occ[i]=0;
    	}
    	for (int i=0;i<sizeOfHot;i++){
    		for(int j=0;j<sizeOfmFile;j++){
    			if(hotWords.get(i).equals(mFile.get(j))){
    				occ[i]++;
    			}
    		}
    	}
    	for (int i=0;i<count;i++){
    		int max=occ[0];
    		int index=0;
    		for (int j=0;j<occ.length;j++){
    			if(max<occ[j]){
    				max=occ[j];
    				index=j;
    			}
    		}
//    		System.out.println(realHotWords.get(index));
    		result[i]=realHotWords.get(index);
    		occ[index]=-1;
    		max=0;
    		index=0;
    		
    	}
    	return result;
    }
    public String[] nextHotWords( String hotWord ){
		String word=hotWord.toLowerCase();
		if(sizeOfmFile==1 && hotWords.get(0).equals(word)){
			return null;
		}
		
    	int numbers=count(word);
    	if(numbers==-1){
    		return null;
    	}
    	if(mFile.get(sizeOfmFile-1).equals(word)){
    		numbers--;
    	}
    	String result[]= new String[numbers];
    	int index=0;
    	for(int j=0;j<numbers;j++){
    		for (int i=index;i<sizeOfmFile;i++){
    			if(mFile.get(i).equals(word)){
    				index=i;
    				break;
    			}
    		}
    		index++;
    		String resultWord=mFile.get(index);
    		int index2=0;
    		for(int i=0;i<sizeOfHot;i++){
    			if(hotWords.get(i).equals(resultWord)){
    				index2=i;
    				break;
    			}
    		}
    		result[j]=realHotWords.get(index2);
    	}
    	return result;
    }
    
    public String[] prevHotWords( String hotWord ){
    	String word=hotWord.toLowerCase();
    	if(sizeOfmFile==1 && hotWords.get(0).equals(word)){
			return null;
		}
		
    	int numbers=count(word);
    	if(numbers==-1){
    		return null;
    	}
    	if(mFile.get(0).equals(word)){
    		numbers--;
    	}
    	String result[]= new String[numbers];
       	int index=1;
    	for(int j=0;j<numbers;j++){
    		for (int i=index;i<sizeOfmFile;i++){
    			if(mFile.get(i).equals(word)){
    				index=i;
    				break;
    			}
    		}
    		index--;
       		String resultWord=mFile.get(index);
    		int index2=0;
    		for(int i=0;i<sizeOfHot;i++){
    			if(hotWords.get(i).equals(resultWord)){
    				index2=i;
    				break;
    			}
    		}
    		result[j]=realHotWords.get(index2);
    		index=index+2;
    	}
    	return result;
    	
  	}
    public boolean consecutive( String ... hotW ){
    	boolean result=false;
    	ArrayList<String> temp= new ArrayList<String>();
    	for(String i: hotW){
    		temp.add(i.toLowerCase());
    	}
    	ArrayList<Integer> indices=new ArrayList<Integer>();
   		for(int i=0;i<sizeOfmFile;i++){
			if(mFile.get(i).equals(temp.get(0))){
				if(i==sizeOfmFile-1){
					continue;
				}
				indices.add(i);
				System.out.println(i);
			}
   		}
   		int index;
   		for(int l=1;l<indices.size();l++){
   			for(int j=0;j<indices.size();j++){
   				int t=indices.get(j);
   				t++;
   				indices.set(j, t);
   			}
   			for(int k=0;k<indices.size();k++){
   				index=indices.get(k);
   				if (mFile.get(index).equals(temp.get(l))){
   					continue;
   				}
   				else{
   					indices.remove(k);
   				}
   			}
   		}
			if (indices.size()==0){
   				result=false;
   			}
   			else{
   				result=true;
   			}
   		return result;
    }

}
