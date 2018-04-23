package code;

import java.util.Date;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import importedInterfaces.ProcessMethods;
import importedInterfaces.User32;
import objects.Checkpoint;
import objects.FormattedTime;
import objects.Time;
import static code.Main.printInLang;

public class Run {

    //final static long baseAddress = 0x10002AFA8L;
    //final static int[] offsets = new int[]{0x50,0x14};

	static Pointer process;
	static long actionAddress;
	static long levelAddress;

    public static int PROCESS_VM_READ= 0x0010;
    public static int PROCESS_VM_WRITE = 0x0020;
    public static int PROCESS_VM_OPERATION = 0x0008;

    public static void run(List<Checkpoint> checks)
    {
    	System.out.println(Main.language.get("countdown_start_1")+checks.size()+Main.language.get("countdown_start_2"));
        /*long dynAddress = findDynAddress(process,offsets,baseAddress);*/
        User32 user32 = (User32) Native.loadLibrary("user32", User32.class);
        IntByReference lpdwProcessId = new IntByReference();
        user32.GetWindowThreadProcessId(user32.FindWindowA(null, "Mupen 64  0.5"), lpdwProcessId);
        int pid = lpdwProcessId.getValue();
    	process = ProcessMethods.openProcess(PROCESS_VM_READ|PROCESS_VM_WRITE|PROCESS_VM_OPERATION, pid);
    	actionAddress = 0x20FAB69BCF4L; //TODO auto-get --------  Action
    	levelAddress = actionAddress+0xCEL;  //Level (NICHT LevelIndex)
    	boolean erreicht = false;
    	Time[] times = new Time[checks.size()+1];
    	int actualCheckpoint=0;
    	try {
    		Thread.sleep(1000);
    		System.out.println("3");
    		Thread.sleep(1000);
    		System.out.println("2");
    		Thread.sleep(1000);
    		System.out.println("1");
    		Thread.sleep(1000);
    		printInLang("countdown_end");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	Date date = new Date();
    	long start = date.getTime();
    	while (checks.size()>=1) {
    		Checkpoint actual = checks.remove(0);
			System.out.println(Main.language.get("actual")+actual.getAllHR());
    		while (!erreicht) {
    			erreicht = testFor(actual, actualCheckpoint, times, start, true);
                try {
    				Thread.sleep(1000/30);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
        	printInLang("achieved");
    		if(actual.type[actual.done].equals("action") && checks.size()>=1) {
    			while(erreicht) {
    				erreicht = testFor(actual, 0, new Time[1], 0, false);
    			}
    		}
    		else {
    			erreicht = false;
    		}
    		actualCheckpoint++;
    	}
    	date = new Date();
    	times[times.length-1]=new Time(Main.language.get("total"),date.getTime()-start);
    	printInLang("calculate_times");
    	FormattedTime[] formattedTimes = new FormattedTime[times.length];
    	int i=0;
    	for (Time timing : times) {
    		long time = timing.time;
    		int done = 1;
    		
    		int milli = 0;
    		int sec = 0;
    		int min = 0;
    		int hour = 0;
    		while(done!=0) {
    			done=0;
    			if(time>=1000) {
    				time-=1000;
    				sec+=1;
    				done++;
    			}
    			if(sec>=60) {
    				sec-=60;
    				min+=1;
    				done++;
    			}
    			if(min>=60) {
    				min-=60;
    				hour+=1;
    				done++;
    			}
    		}
    		milli=(int) time;
    		formattedTimes[i] = new FormattedTime(timing.name,hour+" "+Main.language.get("hours")+", "+min+" "+Main.language.get("minutes")+", "+sec+" "+Main.language.get("seconds")+" "+Main.language.get("and")+" "+milli+" "+Main.language.get("mseconds"));
    		i++;
    	}
    	
    	for (FormattedTime time : formattedTimes) {
    		System.out.println(time.name+" in "+time.time);
    	}
    }
    
    public static boolean testFor(Checkpoint actual, int actualCheckpoint, Time[] times, long start, boolean real) {
    	boolean erreicht = false;
    	for (int i=0; i<actual.name.length; i++) {
			if (actual.type[i].equals("action")) {
            	Memory actionMem = ProcessMethods.readMemory(process,actionAddress,4);
                int actionInt = actionMem.getInt(0);
                String actionHexString = Integer.toHexString(actionInt).toUpperCase();
                if (actionHexString.equals(actual.hexCheckpoint[i])) {
                	erreicht=true;
                	erreicht(real, actualCheckpoint, actual, times, start, i);
                }
    		}
    		else if (actual.type[i].equals("enter")) {
            	Memory levelMem = ProcessMethods.readMemory(process,levelAddress,4);
                int levelInt = levelMem.getInt(0);
                
                if(Integer.toString(levelInt).equals(actual.hexCheckpoint[i])) {
                	erreicht=true;
                	erreicht(real, actualCheckpoint, actual, times, start, i);
                }
    		}
    		else {
    			System.out.println(Main.language.get("unknown_type")+actual.type[i]);
    		}
		}
		return erreicht;
    }
    
    static void erreicht(boolean real, int actualCheckpoint, Checkpoint actual, Time[] times, long start, int i) {
    	if(real) {
    		actual.done=i;
        	Date date = new Date();
        	if(actualCheckpoint>0) {
            	times[actualCheckpoint]=new Time(actual.name[i],date.getTime()-start-times[actualCheckpoint-1].time);
        	}
        	else {
            	times[actualCheckpoint]=new Time(actual.name[i],date.getTime()-start);
        	}
    	}
    }

    

    
}