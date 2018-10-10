import java.util.Scanner;

class Node{

	String proc_name;
	int run_time;
	Node next_proc;
}
public class RoundRobinScheduler{

	public Node addInQueue(Node proc,int n,Node proc_readd){
		Node proc_tmp = proc;
		//System.out.println("here"+proc_readd.proc_name);
		while(proc_tmp.next_proc != proc){

			proc_tmp = proc_tmp.next_proc;
		}
		Node readded = new Node();
		readded.proc_name = proc_readd.proc_name;
		readded.run_time = proc_readd.run_time;
		proc_tmp.next_proc = readded;
		readded.next_proc = proc;
		return proc;

	}
	public Node processStart(Node proc, int no){
		Node proc_tmp = proc;
		Scanner sc = new Scanner(System.in);
		while(no > 0){
			Node new_proc = new Node();
			System.out.println("Enter the run time of process");
			int run = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter the name of process");
			String name = sc.nextLine();
			new_proc.proc_name = name;
			new_proc.run_time =  run;
			new_proc.next_proc = null;
			if(proc == null){
				proc = new_proc;
				proc_tmp = proc;
			}
			else{

				proc_tmp.next_proc = new_proc;
				proc_tmp = proc_tmp.next_proc;
			}
			no--;
		}
		proc_tmp.next_proc = proc;
		return proc;
	} 
	public void showProcess(Node proc){

		Node proc_tmp = proc;
		while(proc_tmp.next_proc != proc){

			System.out.print(proc_tmp.proc_name+" ");
			proc_tmp = proc_tmp.next_proc;
		}
		System.out.print(proc_tmp.proc_name);
	}

	public Node removeProcess(Node proc, Node rem_proc){
		int flag = -1;
		if(proc == rem_proc){
			flag = 0;
		}
		else if(rem_proc.next_proc == proc){
			flag = 1;
		}
		Node proc_tmp = proc;
		if(flag == 0){
			while(proc_tmp.next_proc.next_proc != proc.next_proc){
				proc_tmp = proc_tmp.next_proc;
			}
			proc_tmp.next_proc = proc.next_proc;
			proc = proc_tmp.next_proc;
		}
		else if(flag == 1){
			while(proc_tmp.next_proc.next_proc != proc){
				proc_tmp = proc_tmp.next_proc;
			}
			proc_tmp.next_proc = proc;
		}
		else{
			while(proc_tmp.next_proc != rem_proc){
				proc_tmp = proc_tmp.next_proc;
			}
			proc_tmp.next_proc = rem_proc.next_proc;
		}
			return proc;
	} 

	public void scheduleProcess(Node proc, int n, int time_quan){

		Node schedule = proc;
		while(schedule.next_proc != proc){

			if(schedule.run_time <= time_quan){

				System.out.println("process = "+schedule.proc_name+" Runtime "+ schedule.run_time+" ");
				schedule.run_time = 0;
				schedule =  removeProcess(proc, schedule);
				System.out.println("remaining");
				showProcess(proc);
				//System.out.println("Here"+ schedule.proc_name);
			}
			else{

				schedule.run_time = schedule.run_time - time_quan;
				proc = addInQueue(proc,n,schedule);
			}
			schedule = schedule.next_proc;
		}
		//System.out.println(proc.proc_name);
	}

	public static void main(String args[]){

		Node proc = null;
		RoundRobinScheduler obj = new RoundRobinScheduler();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of processes");
		int n = sc.nextInt();
		System.out.println("Enter the Time Quantum");
		int time_quan = sc.nextInt();
		proc = obj.processStart(proc, n);
		System.out.println("The process at start are");
		obj.showProcess(proc);
		System.out.println("\nThe process completion order");
		obj.scheduleProcess(proc,n,time_quan);

	}
}