package alex.memo;

import alex.memo.model.Memory;

public class App 
{
	private final static int SIZE = 65536;
	
    public static void main( String[] args )
    {
    	Memory mem = new Memory(SIZE);
    	int adr1 = mem.alloc(5);
    	int adr2 = mem.alloc(10);
    	int adr3 = mem.alloc(100);
    	mem.free(adr2, 10);
    	mem.free(adr1, 5);
    	mem.write(adr3, (short) 543);
    	mem.write(adr3+9, (short) 34);
    	System.out.println(mem.toString());
    	System.out.println(mem.read(adr3));
    	System.out.println(mem.read(adr3+9));
    }
}
